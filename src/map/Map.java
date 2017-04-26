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
}