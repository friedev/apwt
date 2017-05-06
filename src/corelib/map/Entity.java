package corelib.map;

import corelib.display.glyphs.ColorChar;
import squidpony.squidgrid.Direction;
import squidpony.squidmath.Coord;

/**
 * An {@link Entity} represented by a {@link corelib.display.glyphs.ColorChar}
 * capable of movement around a {@link TileMap}.
 */
public class Entity
{
    /** The {@link TileMap} that the {@link Entity} exists on. */
    private TileMap map;
    
    /** The {@link Entity}'s coordinate position on its {@link TileMap}. */
    private Coord location;
    
    /**
     * The {@link corelib.display.glyphs.ColorChar} representing this
     * {@link Entity}.
     */
    private ColorChar glyph;
    
    /**
     * Creates an {@link Entity} with all fields defined.
     * @param map the {@link Entity}'s map
     * @param location the {@link Entity}'s location
     * @param glyph the {@link Entity}'s glyph
     */
    public Entity(TileMap map, Coord location, ColorChar glyph)
    {
        this.map = map;
        this.location = location;
        this.glyph = glyph;
    }
    
    /**
     * Returns this {@link Entity}'s {@link TileMap}.
     * @return this {@link Entity}'s {@link TileMap}
     */
    public TileMap getMap()
        {return map;}
    
    /**
     * Returns this {@link Entity}'s location.
     * @return this {@link Entity}'s location
     */
    public Coord getLocation()
        {return location;}
    
    /**
     * Returns this {@link Entity}'s glyph.
     * @return this {@link Entity}'s glyph
     */
    public ColorChar getGlyph()
        {return glyph;}
    
    /**
     * Registers the {@link Entity} on its {@link TileMap} so that it will be
     * updated along with other {@link Entity Entities}.
     */
    public void addToMap()
        {map.addEntity(this);}
    
    /**
     * Removes the {@link Entity} from its {@link TileMap} so that it is no
     * longer updated with other {@link Entity Entities}.
     */
    public void removeFromMap()
        {map.removeEntity(this);}
    
    /**
     * Sets the {@link Entity}'s location to the given coordinates, regardless
     * of whether the {@link Tile} there is open or not.
     * @param destination the {@link Entity}'s new location
     * @return true if the {@link Entity} was moved to the destination; will
     * only be false if the destination is not on the {@link Entity}'s
     * {@link TileMap}
     */
    public boolean setLocation(Coord destination)
    {
        if (map.contains(destination))
        {
            location = destination;
            return true;
        }
        
        return false;
    }
    
    /**
     * Changes the {@link Entity}'s location by the values of the Coord given.
     * @param change the amounts by which to change the {@link Entity}'s
     * location
     * @return true if the {@link Entity} was moved to the destination; will
     * only be false if the updated location is not on the {@link Entity}'s
     * {@link TileMap}
     */
    public boolean changeLocation(Coord change)
        {return setLocation(location.add(change));}
    
    /**
     * Changes the {@link Entity}'s location by the delta values of the given
     * Direction.
     * @param direction the Direction in which the {@link Entity} will move
     * @return true if the {@link Entity} was moved to the destination; will
     * only be false if the updated location is not on the {@link Entity}'s
     * {@link TileMap}
     */
    public boolean changeLocation(Direction direction)
        {return changeLocation(Coord.get(direction.deltaX, direction.deltaY));}
    
    /**
     * Moves the {@link Entity} to the given coordinates, provided that the
     * {@link Tile} there is open.
     * @param destination the {@link Entity}'s new location
     * @return true if the {@link Entity} was moved to the destination
     */
    public boolean moveTo(Coord destination)
        {return map.isOpen(destination) ? setLocation(destination) : false;}
    
    /**
     * Moves the {@link Entity} by the given amounts, provided that the
     * {@link Tile} there is open.
     * @param change the amounts by which to move the {@link Entity}
     * @return true if the {@link Entity} was moved to the destination
     */
    public boolean moveBy(Coord change)
        {return moveTo(location.add(change));}
    
    /**
     * Moves the {@link Entity} in the given Direction, provided that the
     * {@link Tile} there is open.
     * @param direction the Direction in which the {@link Entity} will move
     * @return true if the {@link Entity} was moved to the destination
     */
    public boolean move(Direction direction)
        {return moveBy(Coord.get(direction.deltaX, direction.deltaY));}
    
    /**
     * Makes the {@link Entity} perform an action on its own. This will be done
     * each "turn" in {@link TileMap}, if the {@link Entity} is registered.
     * @return true if the {@link Entity} moved, signaling to the map that its
     * search must be updated before further updates
     */
    public boolean update()
        {return false;}
}