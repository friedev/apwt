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
 * A two-dimensional array of {@link Tile Tiles} that can be traversed by
 * {@link Entity Entities}.
 * @param <TileProperty> the object that the map's {@link Tile Tiles} will use
 * as a property
 */
public class TileMap<TileProperty> implements Iterable<Tile>
{
    /** The default number of moves that tiles cost to move to. */
    public static final int TILE_COST = 1;
    
    /** The greatest number of attempts that findOpenTile() will make. */
    public static final int MAX_TRIES = 50;
    
    /** The {@link Tile Tiles} that comprise the {@link TileMap}. */
    private Tile<TileProperty>[][] tiles;
    
    /** A list of the {@link Entity Entities} on the {@link TileMap}. */
    private List<Entity> entities;
    
    /** The searched map to be used in pathfinding. */
    private AStarSearch search;
    
    /** The {@link TileMap}'s random number generator. */
    private RNG rng;
    
    /**
     * Generates a {@link TileMap} of a specified size.
     * @param size the side length of the map in {@link Tile Tiles}
     * @param tile the tile that every {@link Tile} will be initialized as
     * @param rng the {@link TileMap}'s random number generator
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
    
    /**
     * Generates a {@link TileMap} from a 2D array of {@link Tile Tiles}.
     * @param tiles the {@link Tile Tiles} that will comprise the map
     * @param rng the {@link TileMap}'s random number generator
     */
    public TileMap(Tile<TileProperty>[][] tiles, RNG rng)
    {
        this.tiles = tiles;
        entities = new LinkedList<>();
        this.rng = rng;
        search = new AStarSearch(toCosts(), AStarSearch.SearchType.DIJKSTRA);
    }
    
    /**
     * Generates a {@link TileMap} of a specified size; will create a new RNG to
     * use.
     * @param size the side length of the map in {@link Tile Tiles}
     * @param tile the tile that every {@link Tile} will be initialized as
     */
    public TileMap(int size, Tile tile)
        {this(size, tile, new RNG());}
    
    /**
     * Generates a {@link TileMap} from a 2D array of {@link Tile Tiles}; will
     * create a new RNG to use.
     * @param tiles the {@link Tile Tiles} that will comprise the map
     */
    public TileMap(Tile[][] tiles)
        {this(tiles, new RNG());}
    
    /**
     * Returns the {@link TileMap} as a 2D array of {@link Tile Tiles}.
     * @return the {@link TileMap} as a 2D array of {@link Tile Tiles}
     */
    public Tile[][] toArray()
        {return tiles;}
    
    /**
     * Returns the number of {@link Tile Tiles} on the {@link TileMap}'s x-axis.
     * @return the number of {@link Tile Tiles} on the {@link TileMap}'s x-axis
     */
    public int getXSize()
        {return tiles[0].length;}
    
    /**
     * Returns the number of {@link Tile Tiles} on the {@link TileMap}'s y-axis.
     * @return the number of {@link Tile Tiles} on the {@link TileMap}'s y-axis
     */
    public int getYSize()
        {return tiles.length;}
    
    /**
     * Returns the {@link Tile} at the given x and y coordinates.
     * @param x the x coordinate to get the {@link Tile} from
     * @param y the y coordinate to get the {@link Tile} from
     * @return the {@link Tile} at the given x and y coordinates
     */
    public Tile tileAt(int x, int y)
        {return tiles[y][x];}
    
    /**
     * Returns the {@link Tile} at the given coordinates.
     * @param c the coordinates to get the {@link Tile} from
     * @return the {@link Tile} at the given coordinates
     */
    public Tile tileAt(Coord c)
        {return tileAt(c.x, c.y);}
    
    /**
     * Returns the searched map to be used for pathfinding.
     * @return the searched map to be used for pathfinding
     */
    public AStarSearch search()
        {return search;}
    
    /**
     * Returns the {@link TileMap}'s random number generator.
     * @return the {@link TileMap}'s random number generator
     */
    public RNG getRNG()
        {return rng;}
    
    /**
     * Returns true if the specified coordinates are on the {@link TileMap}.
     * @param x the x coordinate of the point to check
     * @param y the y coordinate of the point to check
     * @return true if the coordinates correspond with a {@link Tile} on the map
     */
    public boolean contains(int x, int y)
        {return y >= 0 && y < tiles.length && x >= 0 && x < tiles[y].length;}
    
    /**
     * Returns true if the specified coordinates are on the {@link TileMap}.
     * @param c the coordinates of the point to check
     * @return true if the coordinates correspond with a {@link Tile} on the map
     */
    public boolean contains(Coord c)
        {return contains(c.x, c.y);}
    
    /**
     * Returns true if the {@link TileMap} contains a {@link Tile} at the given
     * coordinates that is open.
     * @param c the coordinates of the point to check
     * @return true if the coordinates correspond with an open {@link Tile} on
     * the map
     */
    public boolean isOpen(Coord c)
        {return contains(c) && tileAt(c).isOpen();}
    
    /**
     * Registers the given {@link Entity} on this {@link TileMap}, updating it
     * along with other {@link Entity Entities}.
     * @param entity the {@link Entity} to register
     */
    public void addEntity(Entity entity)
    {
        if (!entities.contains(entity))
            entities.add(entity);
    }
    
    /**
     * Removes the given {@link Entity} from this {@link TileMap}.
     * @param entity the {@link Entity} to remove
     */
    public void removeEntity(Entity entity)
        {entities.remove(entity);}
    
    /**
     * Returns true if the given {@link Entity} is registered on this
     * {@link TileMap}.
     * @param entity the {@link Entity} to check
     * @return true if the given {@link Entity} is registered on this
     * {@link TileMap}
     */
    public boolean hasEntity(Entity entity)
        {return entities.contains(entity);}
    
    /**
     * Updates all {@link Entity Entities} on the {@link TileMap}, updating the
     * search as necessary.
     */
    public void update()
    {
        for (Entity entity: entities)
            if (entity.update())
                search = new AStarSearch(toCosts(),
                        AStarSearch.SearchType.DIJKSTRA);
    }
        
    
    /** Prints the {@link Tile} symbols to the {@link corelib.Console}. */
    public void print()
    {
        for (int y = 0; y < tiles.length; y++)
        {
            for (int x = 0; x < tiles[y].length; x++)
                Console.print(tiles[y][x] + " ");
            
            Console.println();
        }
    }
    
    /**
     * Converts the {@link TileMap} to a 2D array of
     * {@link corelib.display.glyphs.ColorChar ColorChars}, each representing a
     * {@link Tile} between the start and end coordinates.
     * @param start the coordinates to start converting the map at
     * @param end the coordinates to stop converting the map at
     * @return the {@link TileMap} as a 2D array of
     * {@link corelib.display.glyphs.ColorChar ColorChars}
     */
    public ColorChar[][] toGlyphs(Coord start, Coord end)
    {
        if (start.x > end.x || start.y > end.y)
            throw new IllegalArgumentException(
                    "Start coordinates must be <= end coordinates");
        
        if (!contains(start) || !contains(end.subtract(Coord.get(1, 1))))
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
            
            if (location.x >= start.x && location.x <= end.x &&
                    location.y >= start.y && location.y <= end.y)
            {
                Color background = entity.getGlyph().getBackground() == null ?
                        tileAt(location).getGlyph().getBackground() :
                        entity.getGlyph().getBackground();
                
                glyphs[location.y - start.y][location.x - start.x] =
                        new ColorChar(entity.getGlyph().getChar(),
                        entity.getGlyph().getForeground(), background);
            }
        }
        
        return glyphs;
    }
    
    /**
     * Converts the {@link TileMap} to a 2D array of
     * {@link corelib.display.glyphs.ColorChar ColorChars}, each representing a
     * {@link Tile} filling the given {@link corelib.display.Display} canvas.
     * @param center the center {@link Tile} to convert
     * @param displayWidth the width of the {@link corelib.display.Display} to
     * fill
     * @param displayHeight the height of the {@link corelib.display.Display} to
     * fill
     * @return the {@link TileMap} as a 2D array of
     * {@link corelib.display.glyphs.ColorChar ColorChars}
     */
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
    
    /**
     * Converts the {@link TileMap} to a 2D array of
     * {@link corelib.display.glyphs.ColorChar ColorChars}, each representing a
     * {@link Tile} filling the given {@link corelib.display.Display} canvas.
     * @param center the center {@link Tile} to convert
     * @param display the {@link corelib.display.Display} to get a width and
     * height from
     * @return the {@link TileMap} as a 2D array of
     * {@link corelib.display.glyphs.ColorChar ColorChars}
     */
    public ColorChar[][] toGlyphs(Coord center, Display display)
    {
        return toGlyphs(center, display.getCharWidth(),
                display.getCharHeight());
    }
    
    /**
     * Converts the entire {@link TileMap} to a 2D array of
     * {@link corelib.display.glyphs.ColorChar ColorChars}, each representing a
     * {@link Tile}.
     * @return the {@link TileMap} as a 2D array of
     * {@link corelib.display.glyphs.ColorChar ColorChars}
     */
    public ColorChar[][] toGlyphs()
    {
        return toGlyphs(Coord.get(0, 0),
                Coord.get(tiles.length - 1, tiles[0].length - 1));
    }
    
    /**
     * Converts the {@link TileMap} to a 2D array of its {@link Tile Tiles}'
     * movement costs.
     * @return the {@link TileMap} as a 2D array of movement costs
     */
    public double[][] toCosts()
    {
        double[][] costMap = new double[tiles.length][tiles[0].length];
        for (int y = 0; y < tiles.length; y++)
            for (int x = 0; x < tiles[y].length; x++)
                costMap[y][x] = tiles[y][x].getCost();
        return costMap;
    }
    
    /**
     * Converts the {@link TileMap} into the format used by SquidLib, with
     * hashes representing walls and periods representing open tiles.
     * @return the {@link TileMap} in SquidLib format
     */
    public char[][] toSquidGrid()
    {
        char[][] squidGrid = new char[tiles.length][tiles[0].length];
        for (int y = 0; y < tiles.length; y++)
            for (int x= 0; x < tiles[y].length; x++)
                squidGrid[y][x] = tiles[y][x].isOpen() ? '.' : '#';
        return squidGrid;
    }
    
    /**
     * Returns a random coordinate on the {@link TileMap}.
     * @return a random coordinate on the {@link TileMap}
     */
    public Coord getRandomTile()
    {
        return Coord.get(rng.nextInt(tiles.length),
                rng.nextInt(tiles[0].length));
    }
    
    /**
     * Returns the coordinate of an open {@link Tile} on the {@link TileMap}.
     * This method uses random guessing, which makes it faster on most maps but
     * far slower on maps with few open {@link Tile Tiles}. For these maps,
     * refer to {@link #findOpenTileSafely()}.
     * @return the coordinate of an open {@link Tile} on the {@link TileMap}
     */
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
    
    /**
     * Returns the coordinate of an open {@link Tile} on the {@link TileMap}.
     * This method finds all open {@link Tile Tiles} and chooses one randomly.
     * This is slower, but has a constant time and will always find an open
     * {@link Tile} if at least one exists.
     * @return the coordinate of an open {@link Tile} on the {@link TileMap}
     */
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
    
    /**
     * Returns the coordinate of a {@link Tile} with the given property on the
     * {@link TileMap}. This method uses random guessing, which makes it faster
     * on most maps but far slower on maps with few {@link Tile Tiles} with the
     * property. For these maps, refer to {@link #findOpenTileSafely()}.
     * @param property the property to look for
     * @return the coordinate of an open {@link Tile} on the {@link TileMap}
     */
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
    
    /**
     * Returns the coordinate of a {@link Tile} with the given property on the
     * {@link TileMap}. This method finds all open {@link Tile Tiles} and
     * chooses one randomly. This is slower, but has a constant time and will
     * always find a {@link Tile} with the property if at least one exists.
     * @param property the property to look for
     * @return the coordinate of an open {@link Tile} on the {@link TileMap}
     */
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
    
    /** A {@link Tile} iterator for the {@link TileMap} class. */
    private class MapIterator implements Iterator
    {
        /** An iterator to loop through rows. */
        private Iterator<Tile[]> rowIterator;
        
        /** An iterator to loop through columns. */
        private Iterator<Tile> colIterator;
        
        /**
         * Creates a {@link MapIterator} for the given 2D array of
         * {@link Tile Tiles}.
         * @param map the 2D array of {@link Tile Tiles} to iterate over
         */
        public MapIterator(Tile[][] map)
        {
            rowIterator = Arrays.asList(map).iterator();
            colIterator = Arrays.asList(rowIterator.next()).iterator();
        }
        
        /**
         * Creates a {@link MapIterator} for the given {@link TileMap}.
         * @param map the {@link TileMap} containing the 2D array of
         * {@link Tile Tiles} to iterate over
         */
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
    
    /**
     * Generates a cave-like {@link TileMap} using basic cellular automata to
     * smooth a completely random set of {@link Tile Tiles}.
     * @param size the size of the {@link TileMap} to generate, in
     * {@link Tile Tiles}
     * @param smoothing the number of times to run the smoothing algorithm;
     * making this value any higher than about 5 will have little effect and
     * only slow down the program
     * @param floor the {@link Tile} to use as a floor
     * @param wall the {@link Tile} to use as a wall
     * @param rng the random number generator to use
     * @return a cave-like {@link TileMap} of the given size using the specified
     * floor and wall {@link Tile Tiles}
     */
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
    
    /**
     * Smooths the given 2D array of {@link Tile Tiles} using basic cellular
     * automata.
     * @param tiles the 2D array of {@link Tile Tiles} to smooth
     * @param floor the {@link Tile} to use as a floor
     * @param wall the {@link Tile} to use as a wall
     * @return the 2D array of {@link Tile Tiles}, smoothed
     */
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