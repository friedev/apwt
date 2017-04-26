package map;

import core.Point;
import core.Console;
import core.Main;

/** A two-dimensional array of tiles that can be traversed by entities. */
public class Map
{
    private Tile[][] map;
    private final int offset;
    
    /**
     * Generates a map of a specified size.
     * @param size the side length of the map in tiles
     */
    public Map(int size)
    {
        map    = new Tile[size][size];
        offset = (int) Math.floor((double) map.length / 2.0);
        
        // Initialize all Tiles on the map
        for (int y = 0; y < map.length; y++)
            for (int x = 0; x < map[y].length; x++)
                map[y][x] = new Tile(TileType.FLOOR);
    }
    
    public Map(TileType[][] tiles)
    {
        map = new Tile[tiles.length][tiles[0].length];
        offset = (int) Math.floor((double) map.length / 2.0);
        
        // Initialize all tiles
        for (int y = 0; y < map.length; y++)
            for (int x = 0; x < map[y].length; x++)
                map[y][x] = new Tile(tiles[y][x]);
    }
    
    public Tile[][] toArray() {return map;                         }
    public int      getMinY() {return -offset;                     }
    public int      getMaxY() {return (map.length -  1) - offset;  }
    public int      getMinX() {return -offset;                     }
    public int      getMaxX() {return (map[0].length - 1) - offset;}
    
    public Tile tileAt(int x, int y)
        {return map[y + offset][x + offset];}
    
    public Tile tileAt(Point p)
        {return tileAt(p.x, p.y);}
    
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
    
    /** Prints the tile symbols and a border. */
    public void print()
    {
        for (int y = 0; y < map.length; y++)
        {
            for (int x = 0; x < map[y].length; x++)
                Console.print(map[y][x] + " ");
            
            Console.println();
        }
    }
    
    public void display(core.display.Display display, Point start)
    {
        for (int y = 0; y < map.length; y++)
            for (int x = 0; x < map[y].length; x++)
                display.write(map[y][x].getType().getGlyph(),
                        new Point(start.x + x, start.y + y));
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