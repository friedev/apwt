package corelib.map;

import corelib.Console;
import corelib.display.Display;
import corelib.display.glyphs.ColorChar;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import squidpony.squidmath.AStarSearch;
import squidpony.squidmath.Coord;
import squidpony.squidmath.RNG;

/**
 * A two-dimensional array of tiles that can be traversed by entities.
 * @param <TileProperty>
 */
public class TileMap<TileProperty> implements Iterable<Tile>
{
    /** The default number of moves that tiles cost to move to. */
    public static final int TILE_COST = 1;
    
    /** The default number of moves that entities gain each turn. */
    public static final int ENTITY_SPEED = 1;
    
    public static final int MAX_TRIES = 50;
    
    protected Tile<TileProperty>[][] tiles;
    protected List<Entity> entities;
    protected AStarSearch search;
    protected RNG rng;
    
    /**
     * Generates a map of a specified size.
     * @param size the side length of the map in tiles
     * @param tile the tile that every map tile will be initialized as
     * @param rng the Map's random number generator
     */
    public TileMap(int size, Tile<TileProperty> tile, RNG rng)
    {
        tiles = new Tile[size][size];
        entities = new LinkedList<>();
        this.rng = rng;
        
        // Initialize all Tiles on the map
        for (int y = 0; y < tiles.length; y++)
            for (int x = 0; x < tiles[y].length; x++)
                tiles[y][x] = tile;
        
        search = new AStarSearch(toCosts(), AStarSearch.SearchType.DIJKSTRA);
    }
    
    public TileMap(Tile<TileProperty>[][] tiles, RNG rng)
    {
        this.tiles = tiles;
        entities = new LinkedList<>();
        this.rng = rng;
        search = new AStarSearch(toCosts(), AStarSearch.SearchType.DIJKSTRA);
    }
    
    public TileMap(int size, Tile tile)
        {this(size, tile, new RNG());}
    
    public TileMap(Tile[][] tiles)
        {this(tiles, new RNG());}
    
    public Tile[][] toArray()
        {return tiles;}
    
    public int getXSize()
        {return tiles[0].length;}
    
    public int getYSize()
        {return tiles.length;}
    
    public Tile tileAt(int x, int y)
        {return tiles[y][x];}
    
    public Tile tileAt(Coord c)
        {return tileAt(c.x, c.y);}
    
    public AStarSearch search()
        {return search;}
    
    public RNG getRNG()
        {return rng;}
    
    /**
     * Returns true if the specified coordinates are on the map.
     * @param x the x coordinate of the point to check
     * @param y the y coordinate of the point to check
     * @return true if the coordinates correspond with a point on the map
     */
    public boolean contains(int x, int y)
        {return y >= 0 && y < tiles.length && x >= 0 && x < tiles[y].length;}
    
    /**
     * Performs the same function as contains(int, int), except that it uses a
     * predefined point's coordinates.
     * @param c the coordinates
     * @return true if the point is on the map
     */
    public boolean contains(Coord c)
        {return contains(c.x, c.y);}
    
    public boolean isOpen(Coord c)
        {return contains(c) && tileAt(c).isOpen();}
    
    public void addEntity(Entity e)
    {
        if (!entities.contains(e))
            entities.add(e);
    }
    
    public void removeEntity(Entity e)
        {entities.remove(e);}
    
    public boolean hasEntity(Entity e)
        {return entities.contains(e);}
    
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
            throw new IllegalArgumentException(
                    "Start coordinates must be <= end coordinates");
        
        if (!contains(start) || !contains(end))
            throw new IndexOutOfBoundsException("Start and end coordinates "
                    + "must be contained on the map");
        
        ColorChar[][] glyphs =
                new ColorChar[end.y - start.y][end.x - start.x];
        
        for (int y = 0; y < glyphs.length; y++)
            for (int x = 0; x < glyphs[y].length; x++)
                glyphs[y][x] =
                        tiles[y + start.y][x + start.x].getGlyph();
        
        for (Entity entity: entities)
        {
            Coord location = entity.getLocation();
            
            if (location.x >= start.x && location.x < end.x &&
                    location.y >= start.y && location.y < end.y)
            {
                Color background = entity.glyph.background == null ?
                        tileAt(location).glyph.background :
                        entity.glyph.background;
                
                glyphs[location.y - start.y][location.x - start.x] =
                        new ColorChar(entity.glyph.character,
                        entity.glyph.foreground, background);
            }
        }
        
        return glyphs;
    }
    
    public ColorChar[][] toGlyphs(Coord center, int displayWidth,
            int displayHeight)
    {
        int startX = Math.max(0, Math.min(center.x - displayWidth / 2,
                getXSize() - displayWidth));
        int startY = Math.max(0, Math.min(center.y - displayHeight / 2,
                getYSize() - displayHeight));
        int endX = Math.min(getXSize(), startX + displayWidth);
        int endY = Math.min(getYSize(), startY + displayHeight);

        return toGlyphs(Coord.get(startX, startY), Coord.get(endX, endY));
    }
    
    public ColorChar[][] toGlyphs(Coord center, Display display)
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
                costMap[y][x] = tiles[y][x].getCost();
        return costMap;
    }
    
    public char[][] toSquidGrid()
    {
        char[][] squidGrid = new char[tiles.length][tiles[0].length];
        for (int y = 0; y < tiles.length; y++)
            for (int x= 0; x < tiles[y].length; x++)
                squidGrid[y][x] = tiles[y][x].isOpen() ? '.' : '#';
        return squidGrid;
    }
    
    public Coord getRandomTile()
    {
        return Coord.get(rng.nextInt(tiles.length),
                rng.nextInt(tiles[0].length));
    }
    
    public Coord findOpenTile()
    {
        for (int tries = 0; tries < MAX_TRIES; tries++)
        {
            Coord testPoint = Coord.get(rng.nextInt(getXSize()),
                    rng.nextInt(getYSize()));
            
            if (tileAt(testPoint).isOpen())
                return testPoint;
        }
        
        return null;
    }
    
    public Coord findOpenTileSafely()
    {
        ArrayList<Coord> possibilities = new ArrayList<>();
        for (int y = 0; y < tiles.length; y++)
            for (int x = 0; x < tiles[y].length; x++)
                if (tiles[y][x].isOpen())
                    possibilities.add(Coord.get(x, y));
                
        
        return possibilities.isEmpty() ?
                null : possibilities.get(rng.nextInt(possibilities.size()));
    }
    
    public Coord findTileWithProperty(TileProperty property)
    {
        for (int tries = 0; tries < MAX_TRIES; tries++)
        {
            Coord testPoint = Coord.get(rng.nextInt(getXSize()),
                    rng.nextInt(getYSize()));
            
            if (tileAt(testPoint).getProperties().contains(property))
                return testPoint;
        }
        
        return null;
    }
    
    public Coord findTileWithPropertySafely(TileProperty property)
    {
        ArrayList<Coord> possibilities = new ArrayList<>();
        for (int y = 0; y < tiles.length; y++)
            for (int x = 0; x < tiles[y].length; y++)
                if (tiles[y][x].getProperties().contains(property))
                    possibilities.add(Coord.get(x, y));
                
        
        return possibilities.isEmpty() ?
                null : possibilities.get(rng.nextInt(possibilities.size()));
    }
    
    @Override
    public Iterator<Tile> iterator()
        {return new MapIterator(this);}
    
    private class MapIterator implements Iterator
    {
        private Iterator<Tile[]> rowIterator;
        private Iterator<Tile> colIterator;
        
        public MapIterator(Tile[][] map)
        {
            rowIterator = Arrays.asList(map).iterator();
            colIterator = Arrays.asList(rowIterator.next()).iterator();
        }
        
        public MapIterator(TileMap map)
            {this(map.tiles);}
        
        @Override
        public boolean hasNext()
            {return rowIterator.hasNext() || colIterator.hasNext();}

        @Override
        public Tile next()
        {
            if (colIterator.hasNext())
                return colIterator.next();
            
            if (rowIterator.hasNext())
            {
                colIterator = Arrays.asList(rowIterator.next()).iterator();
                return colIterator.next();
            }
            
            return null;
        }
    }
    
    public static TileMap generateCave(int size, int smoothing, Tile floor,
            Tile wall, RNG rng)
    {
        Tile[][] tiles = new Tile[size][size];
        
        for (int x = 0; x < size; x++)
            for (int y = 0; y < size; y++)
                tiles[x][y] = rng.nextBoolean() ?
                        new Tile(floor) : new Tile(wall);
        
        for (int i = 0; i < smoothing; i++)
            tiles = smoothCaves(tiles, floor, wall);
        
        return new TileMap(tiles);
    }
    
    private static Tile[][] smoothCaves(Tile[][] tiles, Tile floor,
            Tile wall)
    {
        int size = tiles.length;
        Tile[][] smoothedTiles = new Tile[size][size];
        
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

                        if (tiles[x + ox][y + oy].equals(floor))
                            floors++;
                        else
                            walls++;
                    }
                }
                
                smoothedTiles[x][y] = floors >= walls ?
                        new Tile(floor) : new Tile(wall);
            }
        }
        
        return smoothedTiles;
    }
}