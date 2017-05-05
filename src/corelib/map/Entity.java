package corelib.map;

import corelib.display.glyphs.ColorChar;
import squidpony.squidgrid.Direction;
import squidpony.squidmath.Coord;

/** An entity capable of movement around a map. */
public class Entity
{
    private TileMap map;
    private Coord location;
    private ColorChar glyph;
    
    public Entity(TileMap map, Coord location, ColorChar glyph)
    {
        this.map = map;
        this.location = location;
        this.glyph = glyph;
    }
    
    public Entity(TileMap map, ColorChar glyph)
    {
        this(map, Coord.get(map.getXSize() / 2, map.getYSize() / 2),
                glyph);
    }
    
    public TileMap getMap()
        {return map;}
    
    public Coord getLocation()
        {return location;}
    
    public ColorChar getGlyph()
        {return glyph;}
    
    public void addToMap()
        {map.addEntity(this);}
    
    public void removeFromMap()
        {map.removeEntity(this);}
    
    public boolean setLocation(Coord destination)
    {
        if (map.contains(destination))
        {
            location = destination;
            return true;
        }
        
        return false;
    }
    
    public boolean changeLocation(Coord change)
        {return setLocation(location.add(change));}
    
    public boolean changeLocation(Direction direction)
        {return changeLocation(Coord.get(direction.deltaX, direction.deltaY));}
    
    public boolean moveTo(Coord destination)
        {return map.isOpen(destination) ? setLocation(destination) : false;}
    
    public boolean moveBy(Coord change)
        {return moveTo(location.add(change));}
    
    public boolean move(Direction direction)
        {return moveBy(Coord.get(direction.deltaX, direction.deltaY));}
    
    // Returns true if the entity moved
    public boolean update()
        {return false;}
}