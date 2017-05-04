package corelib.map;

import corelib.display.glyphs.ColorChar;
import squidpony.squidgrid.Direction;
import squidpony.squidmath.Coord;

/** An entity capable of movement around a map. */
public class Entity
{
    protected TileMap map;
    protected Coord location;
    protected ColorChar glyph;
    
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
        if (map.isOpen(destination))
        {
            location = destination;
            return true;
        }
        
        return false;
    }
    
    public boolean setLocation(int x, int y)
        {return setLocation(Coord.get(x, y));}
    
    public boolean changeLocation(int x, int y)
        {return setLocation(location.x + x, location.y + y);}
    
    public boolean changeLocation(Direction direction)
        {return changeLocation(direction.deltaX, direction.deltaY);}
    
    // Returns true if the entity moved
    public boolean update()
        {return false;}
}