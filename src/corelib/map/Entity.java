package corelib.map;

import corelib.display.glyphs.ColorChar;
import corelib.items.Nameable;
import java.util.Queue;
import squidpony.squidgrid.Direction;
import squidpony.squidmath.Coord;

/** An entity capable of movement around a map. */
public class Entity extends Nameable
{
    private TileMap map;
    private Coord location;
    private ColorChar glyph;
    private double speed;
    private double actions;
    
    public Entity(String name, TileMap map, Coord location, ColorChar glyph,
            double moveSpeed)
    {
        super(name);
        this.map = map;
        this.location = location;
        this.glyph = glyph;
        this.speed = moveSpeed;
        actions = 0;
    }
    
    public Entity(String name, TileMap map, ColorChar glyph, double moveSpeed)
    {
        this(name, map, Coord.get(map.getXSize() / 2, map.getYSize() / 2),
                glyph, moveSpeed);
    }
    
    public Entity(String name, TileMap map, Coord location, ColorChar glyph)
        {this(name, map, location, glyph, TileMap.ENTITY_SPEED);}
    
    public Entity(String name, TileMap map, ColorChar glyph)
        {this(name, map, glyph, TileMap.ENTITY_SPEED);}
    
    public TileMap getMap()
        {return map;}
    
    public Coord getLocation()
        {return location;}
    
    public ColorChar getGlyph()
        {return glyph;}
    
    public double getMoveSpeed()
        {return speed;}
    
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
    
    public Queue<Coord> pathfind(Coord destination)
    {
        if (!map.isOpen(destination))
            return null;
        
        return map.search().path(location, destination);
    }
    
    // Returns true if the entity moved
    public boolean update()
    {
        actions += speed;
        Queue<Coord> path = pathfind(map.getRandomTile());
        if (path == null || path.peek() == null)
            return false;
        
        double moves = 0;
        while (path.peek() != null)
        {
            Coord nextMove = path.peek();
            double tileCost = map.tileAt(nextMove).getCost();
            if (setLocation(nextMove))
            {
                path.remove();
                actions -= tileCost;
                moves++;
            }
            else
            {
                break;
            }
        }
        
        return moves > 0;
    }
}