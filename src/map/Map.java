package map;

import core.Point;
import core.Console;
import core.Main;
import core.display.ColorChar;
import java.util.ArrayList;
import java.util.List;

/** A two-dimensional array of tiles that can be traversed by entities. */
public class Map
{
    private Tile[][] tiles;
    private final int offset;
    private List<Entity> entities;
    
    /**
     * Generates a map of a specified size.
     * @param size the side length of the map in tiles
     */
    public Map(int size)
    {
        tiles = new Tile[size][size];
        offset = (int) Math.floor((double) tiles.length / 2.0);
        entities = new ArrayList<>();
        
        // Initialize all Tiles on the map
        for (int y = 0; y < tiles.length; y++)
            for (int x = 0; x < tiles[y].length; x++)
                tiles[y][x] = new Tile(TileType.FLOOR);
    }
    
    public Map(TileType[][] tiles)
    {
        this.tiles = new Tile[tiles.length][tiles[0].length];
        offset = (int) Math.floor((double) this.tiles.length / 2.0);
        entities = new ArrayList<>();
        
        // Initialize all Tiles on the map
        for (int y = 0; y < this.tiles.length; y++)
            for (int x = 0; x < this.tiles[y].length; x++)
                this.tiles[y][x] = new Tile(tiles[y][x]);
    }
    
    public Tile[][] toArray() {return tiles;                         }
    public int      getMinY() {return -offset;                       }
    public int      getMaxY() {return (tiles.length -  1) - offset;  }
    public int      getMinX() {return -offset;                       }
    public int      getMaxX() {return (tiles[0].length - 1) - offset;}
    
    public int getXSize() {return tiles[0].length;}
    public int getYSize() {return tiles.length;}
    
    public Tile tileAt(int x, int y)
        {return tiles[-y + offset][x + offset];}
    
    public Tile tileAt(Point p)
        {return tileAt(p.x, p.y);}
    
    public Point convertToIndex(Point coordinates)
        {return new Point(coordinates.x + offset, -coordinates.y + offset);}
    
    public Point convertToCoordinates(Point index)
        {return new Point(index.x - offset, -(index.y - offset));}
    
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
     * @param p the point to use coordinates from
     * @return true if the point is on the map
     */
    public boolean contains(Point p)
        {return contains(p.x, p.y);}
    
    public void addEntity(Entity e)
    {
        if (!entities.contains(e))
            entities.add(e);
    }
    
    public void removeEntity(Entity e)
        {entities.remove(e);}
    
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
    
    public ColorChar[][] toGlyphs(Point start, Point end)
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
            Point index = convertToIndex(entity.getLocation());
            if (index.x >= start.x && index.x < end.x &&
                    index.y >= start.y && index.y < end.y)
                glyphs[index.y - start.y][index.x - start.x] = entity.getGlyph();
        }
        
        return glyphs;
    }
    
    public ColorChar[][] toGlyphs(Point center, int displayWidth,
            int displayHeight)
    {
        Point centerIndex = convertToIndex(center);
        
        int startX = Math.max(0, Math.min(centerIndex.x - displayWidth / 2,
                getXSize() - displayWidth));
        int startY = Math.max(0, Math.min(centerIndex.y - displayHeight / 2,
                getYSize() - displayHeight));
        int endX = Math.min(getXSize(), startX + displayWidth);
        int endY = Math.min(getYSize(), startY + displayHeight);

        return toGlyphs(new Point(startX, startY), new Point(endX, endY));
    }
    
    public ColorChar[][] toGlyphs(Point center, core.display.Display display)
    {
        return toGlyphs(center, display.getCharWidth(),
                display.getCharHeight());
    }
    
    public ColorChar[][] toGlyphs()
    {
        return toGlyphs(new Point(0, 0),
                new Point(tiles.length - 1, tiles[0].length - 1));
    }
    
    public Point findTileWithProperty(TileProperty property)
    {
        for (int tries = 0; tries < 50; tries++)
        {
            Point testPoint = convertToCoordinates(new Point(
                    Main.random.get().nextInt(getXSize()),
                    Main.random.get().nextInt(getYSize())));
            
            if (tileAt(testPoint).getType().getProperties().contains(property))
                return testPoint;
        }
        
        return null;
    }
    
    public static Map generateCave(int size, int smoothing, TileType floor,
            TileType wall)
    {
        TileType[][] tiles = new TileType[size][size];
        
        for (int x = 0; x < size; x++)
            for (int y = 0; y < size; y++)
                tiles[x][y] = Main.random.get().nextBoolean() ? floor : wall;
        
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

    public static int[][] generateHeightmap(int size, int range,
            double variationExponent)
    {
        int[][] heightmap = new int[size][size];
        
        // Fill the array with 0s
        for (int y = 0; y < size; y++)
            for (int x = 0; x < size; x++)
                heightmap[x][y] = 0;
        
        // Setup points in the 4 corners of the map
        Point[] corners = new Point[] {new Point(0, 0), new Point (0, size - 1),
                new Point(size - 1, 0), new Point(size - 1, size - 1)};
        
        for (Point corner: corners)
            heightmap[corner.y][corner.x] = Main.random.get().nextInt(range);
        
        // Do the midpoint
        heightmap = midpoint(heightmap, 0, 0, size - 1, size - 1);
        
        double maxHeight = 0;
        
        for (int y = 0; y < heightmap.length; y++)
        {
            for (int x = 0; x < heightmap[y].length; x++)
            {
                double height = Math.pow(heightmap[y][x], variationExponent);
                if (height > maxHeight)
                    maxHeight = height;
            }
        }

        for (int y = 0; y < heightmap.length; y++)
            for (int x = 0; x < heightmap[y].length; x++)
                heightmap[y][x] = (int) (Math.pow(heightmap[y][x],
                        variationExponent) / maxHeight * ((double) range));
        
        return heightmap;
    }

    private static int[][] midpoint(int[][] heightmap, int x1, int y1, int x2,
            int y2)
    {
        // If this is pointing at just one tile, exit because it doesn't need
        // doing
        if (x2 - x1 < 2 && y2 - y1 <2)
            return heightmap;
        
        // Find distance between points and use when generating a random number
        int dist = (x2 - x1 + y2 - y1);
        int hdist = dist / 2;
        
        // Find midpoint
        int midx = (x1 + x2) / 2;
        int midy = (y1 + y2) / 2;
        
        // Get pixel colors of corners
        int c1 = heightmap[x1][y1];
        int c2 = heightmap[x2][y1];
        int c3 = heightmap[x2][y2];
        int c4 = heightmap[x1][y2];

        java.util.Random r = Main.random.get();
        
        // If not already defined, work out the midpoints of the corners of
        // the rectangle by means of an average plus a random number
        if (heightmap[midx][y1] == 0)
           heightmap[midx][y1] = Math.max(0,
                   ((c1 + c2 + r.nextInt(dist) - hdist) / 2));
        
        if (heightmap[midx][y2] == 0)
           heightmap[midx][y2] = Math.max(0,
                   ((c4 + c3 + r.nextInt(dist) - hdist) / 2));
        
        if (heightmap[x1][midy] == 0)
           heightmap[x1][midy] = Math.max(0,
                   ((c1 + c4 + r.nextInt(dist) - hdist) / 2));
        
        if (heightmap[x2][midy] == 0)
           heightmap[x2][midy] = Math.max(0,
                   ((c2 + c3 + r.nextInt(dist) - hdist) / 2));

        // Work out the middle point
        heightmap[midx][midy] = Math.max(0,
                ((c1 + c2 + c3 + c4 + r.nextInt(dist) - hdist) / 4));

        // Now divide this rectangle into 4, and call again for each smaller
        // rectangle
        heightmap = midpoint(heightmap, x1, y1, midx, midy);
        heightmap = midpoint(heightmap, midx, y1, x2, midy);
        heightmap = midpoint(heightmap, x1, midy, midx, y2);
        heightmap = midpoint(heightmap, midx, midy, x2, y2);

        return heightmap;
    }
}