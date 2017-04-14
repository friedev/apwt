package map;

import core.display.Console;

/** A two-dimensional array of tiles that can be traversed by entities. */
public class Map
{
    /** The default side length of the map in tiles. */
    public static final int SIZE = 11;
    
    private Tile[][]  map;
    private final int offset;
    
    /** Generates a map with the default size. */
    public Map() {this(SIZE);}
    
    /**
     * Generates a map of a specified size.
     * @param size the side length of the map in tiles
     */
    public Map(int size)
    {
        map    = new Tile[size][size];
        offset = (int) Math.floor((double) map.length / 2.0);
        initialize();
    }
    
    public BaseTile[][] toArray()  {return map;                         }
    public int      getMinY()      {return -offset;                     }
    public int      getMaxY()      {return (map.length -  1) - offset;  }
    public int      getMinX()      {return -offset;                     }
    public int      getMaxX()      {return (map[0].length - 1) - offset;}
    
    public BaseTile tileAt(int x, int y) {return map[y + offset][x + offset];}
    public BaseTile tileAt(Point p)      {return tileAt(p.x, p.y);}
    
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
    public boolean contains(Point p) {return contains(p.x, p.y);}
    
    /** Prints the tile symbols and a border. */
    public void print()
    {
        for (int y = 0; y < map.length; y++)
        {
            for (int x = 0; x < map[y].length; x++)
                Console.print(map[y][x].getSymbol() + " ");
            
            Console.println();
        }
    }
    
    /** Initializes all the tiles on the map. */
    private void initialize()
    {
        for (int y = 0; y < map.length; y++)
            for (int x = 0; x < map[y].length; x++)
                map[y][x] = new Tile(new Point(x - offset, y - offset), this);
    }
}