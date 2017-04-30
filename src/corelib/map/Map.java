package corelib.map;

import corelib.Console;
import corelib.display.glyphs.ColorChar;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import squidpony.squidmath.AStarSearch;
import squidpony.squidmath.Coord;
import squidpony.squidmath.RNG;

/** A two-dimensional array of tiles that can be traversed by entities. */
public class Map
{
    /** The default number of moves that tiles cost to move to. */
    public static final int TILE_COST = 100;
    
    /** The default number of moves that entities gain each turn. */
    public static final int ENTITY_SPEED = 10;
    
    private Tile[][] tiles;
    private final int offset;
    private List<Entity> entities;
    private AStarSearch search;
    private RNG rng;
    
    /**
     * Generates a map of a specified size.
     * @param size the side length of the map in tiles
     * @param rng the Map's random number generator
     */
    public Map(int size, RNG rng)
    {
        tiles = new Tile[size][size];
        offset = (int) Math.floor((double) tiles.length / 2.0);
        entities = new LinkedList<>();
        this.rng = rng;
        
        // Initialize all Tiles on the map
        for (int y = 0; y < tiles.length; y++)
            for (int x = 0; x < tiles[y].length; x++)
                tiles[y][x] = new Tile(TileType.FLOOR);
        
        search = new AStarSearch(toCosts(), AStarSearch.SearchType.DIJKSTRA);
    }
    
    public Map(TileType[][] tiles, RNG rng)
    {
        this.tiles = new Tile[tiles.length][tiles[0].length];
        offset = (int) Math.floor((double) this.tiles.length / 2.0);
        entities = new LinkedList<>();
        this.rng = rng;
        
        // Initialize all Tiles on the map
        for (int y = 0; y < this.tiles.length; y++)
            for (int x = 0; x < this.tiles[y].length; x++)
                this.tiles[y][x] = new Tile(tiles[y][x]);
        
        search = new AStarSearch(toCosts(), AStarSearch.SearchType.DIJKSTRA);
    }
    
    public Map(int size)
        {this(size, new RNG());}
    
    public Map(TileType[][] tiles)
        {this(tiles, new RNG());}
    
    public Tile[][] toArray()
        {return tiles;}
    
    public int getMinY()
        {return -offset;}
    
    public int getMaxY()
        {return (tiles.length -  1) - offset;}
    
    public int getMinX()
        {return -offset;}
    
    public int getMaxX()
        {return (tiles[0].length - 1) - offset;}
    
    public int getXSize()
        {return tiles[0].length;}
    
    public int getYSize()
        {return tiles.length;}
    
    public Tile tileAt(int x, int y)
        {return tiles[-y + offset][x + offset];}
    
    public Tile tileAt(Coord c)
        {return tileAt(c.x, c.y);}
    
    public AStarSearch search()
        {return search;}
    
    public RNG getRNG()
        {return rng;}
    
    public Coord coordToIndex(Coord coordinates)
        {return Coord.get(coordinates.x + offset, -coordinates.y + offset);}
    
    public Coord indexToCoord(Coord index)
        {return Coord.get(index.x - offset, -(index.y - offset));}
    
    /**
     * Returns true if the specified coordinates are on the map.
     * @param x the x coordinate of the point to check
     * @param y the y coordinate of the point to check
     * @return true if the coordinates correspond with a point on the map
     */
    public boolean contains(int x, int y)
    {
        return (x >= getMinX() && x <= getMaxX()) &&
               (y >= getMinY() && y <= getMaxY());
    }
    
    /**
     * Performs the same function as contains(int, int), except that it uses a
     * predefined point's coordinates.
     * @param c the coordinates
     * @return true if the point is on the map
     */
    public boolean contains(Coord c)
        {return contains(c.x, c.y);}
    
    public void addEntity(Entity e)
    {
        if (!entities.contains(e))
            entities.add(e);
    }
    
    public void removeEntity(Entity e)
        {entities.remove(e);}
    
    public void update()
    {
        for (Entity entity: entities)
            entity.update();
    }
        
    
    /** Prints the tile symbols and a border. */
    public void print()
    {
        for (int y = 0; y < tiles.length; y++)
        {
            for (int x = 0; x < tiles[y].length; x++)
                Console.print(tiles[y][x] + " ");
            
            Console.println();
        }
    }
    
    public ColorChar[][] toGlyphs(Coord start, Coord end)
    {
        if (start.x > end.x || start.y > end.y)
            return null;
        
        ColorChar[][] glyphs =
                new ColorChar[end.y - start.y][end.x - start.x];
        
        for (int y = 0; y < glyphs.length; y++)
            for (int x = 0; x < glyphs[y].length; x++)
                glyphs[y][x] =
                        tiles[y + start.y][x + start.x].getType().getGlyph();
        
        for (Entity entity: entities)
        {
            Coord index = coordToIndex(entity.getLocation());
            if (index.x >= start.x && index.x < end.x &&
                    index.y >= start.y && index.y < end.y)
                glyphs[index.y - start.y][index.x - start.x] = entity.getGlyph();
        }
        
        return glyphs;
    }
    
    public ColorChar[][] toGlyphs(Coord center, int displayWidth,
            int displayHeight)
    {
        Coord centerIndex = coordToIndex(center);
        
        int startX = Math.max(0, Math.min(centerIndex.x - displayWidth / 2,
                getXSize() - displayWidth));
        int startY = Math.max(0, Math.min(centerIndex.y - displayHeight / 2,
                getYSize() - displayHeight));
        int endX = Math.min(getXSize(), startX + displayWidth);
        int endY = Math.min(getYSize(), startY + displayHeight);

        return toGlyphs(Coord.get(startX, startY), Coord.get(endX, endY));
    }
    
    public ColorChar[][] toGlyphs(Coord center, corelib.display.Display display)
    {
        return toGlyphs(center, display.getCharWidth(),
                display.getCharHeight());
    }
    
    public ColorChar[][] toGlyphs()
    {
        return toGlyphs(Coord.get(0, 0),
                Coord.get(tiles.length - 1, tiles[0].length - 1));
    }
    
    public double[][] toCosts()
    {
        double[][] costMap = new double[tiles.length][tiles[0].length];
        for (int y = 0; y < tiles.length; y++)
            for (int x = 0; x < tiles[y].length; x++)
                costMap[y][x] = (double) tiles[y][x].getType().getMoveCost();
        return costMap;
    }
    
    public Coord findTileWithProperty(TileProperty property)
    {
        for (int tries = 0; tries < 50; tries++)
        {
            Coord testPoint = indexToCoord(Coord.get(
                    rng.nextInt(getXSize()),
                    rng.nextInt(getYSize())));
            
            if (tileAt(testPoint).getType().getProperties().contains(property))
                return testPoint;
        }
        
        return null;
    }
    
    public Coord findTileWithPropertySafely(TileProperty property)
    {
        ArrayList<Coord> possibilities = new ArrayList<>();
        for (int y = 0; y < tiles.length; y++)
            for (int x = 0; x < tiles[y].length; y++)
                if (tiles[y][x].getType().getProperties().contains(property))
                    possibilities.add(indexToCoord(Coord.get(x, y)));
        
        return possibilities.isEmpty() ?
                null : possibilities.get(rng.nextInt(possibilities.size()));
    }
    
    public static Map generateCave(int size, int smoothing, TileType floor,
            TileType wall, RNG rng)
    {
        TileType[][] tiles = new TileType[size][size];
        
        for (int x = 0; x < size; x++)
            for (int y = 0; y < size; y++)
                tiles[x][y] = rng.nextBoolean() ? floor : wall;
        
        for (int i = 0; i < smoothing; i++)
            tiles = smoothCaves(tiles, floor, wall);
        
        return new Map(tiles);
    }
    
    private static TileType[][] smoothCaves(TileType[][] tiles, TileType floor,
            TileType wall)
    {
        int size = tiles.length;
        TileType[][] smoothedTiles = new TileType[size][size];
        
        for (int x = 0; x < size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                int floors = 0;
                int walls = 0;

                for (int ox = -1; ox < 2; ox++)
                {
                    for (int oy = -1; oy < 2; oy++)
                    {
                        if (x + ox < 0 || x + ox >= size ||
                                y + oy < 0 || y + oy >= size)
                            continue;

                        if (tiles[x + ox][y + oy] == floor)
                            floors++;
                        else
                            walls++;
                    }
                }
                
                smoothedTiles[x][y] = floors >= walls ? floor : wall;
            }
        }
        
        return smoothedTiles;
    }
}