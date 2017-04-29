package map;

import core.display.ColorChar;
import java.util.Queue;
import squidpony.squidgrid.Direction;
import squidpony.squidmath.Coord;

/** An entity capable of movement around a map. */
public class Entity extends items.Nameable
{
    private Map map;
    private Coord location;
    private ColorChar glyph;
    private int speed;
    private int actions;
    
    public Entity(String name, Map map, Coord location, ColorChar glyph,
            int moveSpeed)
    {
        super(name);
        this.map = map;
        this.location = location;
        this.glyph = glyph;
        this.speed = moveSpeed;
        actions = 0;
    }
    
    public Entity(String name, Map map, ColorChar glyph, int moveSpeed)
    {
        this(name, map, Coord.get(map.getXSize() / 2, map.getYSize() / 2),
                glyph, moveSpeed);
    }
    
    public Entity(String name, Map map, Coord location, ColorChar glyph)
        {this(name, map, location, glyph, Map.TILE_COST);}
    
    public Entity(String name, Map map, ColorChar glyph)
        {this(name, map, glyph, Map.TILE_COST);}
    
    public Map getMap()
        {return map;}
    
    public Coord getLocation()
        {return location;}
    
    public ColorChar getGlyph()
        {return glyph;}
    
    public int getMoveSpeed()
        {return speed;}
    
    private boolean isOpen(Coord destination)
    {
        return map.contains(destination) && map.tileAt(destination).getType()
                .getProperties().contains(TileProperty.OPEN);
    }
    
    public boolean setLocation(Coord destination)
    {
        if (isOpen(destination))
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
    {
        switch (direction)
        {
            case UP:         return changeLocation( 0,  1);
            case DOWN:       return changeLocation( 0, -1);
            case LEFT:       return changeLocation(-1,  0);
            case RIGHT:      return changeLocation( 1,  0);
            case UP_LEFT:    return changeLocation(-1,  1);
            case UP_RIGHT:   return changeLocation( 1,  1);
            case DOWN_LEFT:  return changeLocation(-1, -1);
            case DOWN_RIGHT: return changeLocation( 1, -1);
        }
        
        return false;
    }
    
    public Queue<Coord> pathfind(Coord destination)
    {
        if (!isOpen(destination))
            return null;
        
        return map.search().path(location, destination);
    }
    
    // Returns true if the entity moved
    public boolean update()
    {
        actions += speed;
        Queue<Coord> path =
                pathfind(map.findTileWithProperty(TileProperty.OPEN));
        if (path == null || path.peek() == null)
            return false;
        
        int moves = 0;
        while (path.peek() != null)
        {
            Coord nextMove = path.peek();
            int tileCost = map.tileAt(nextMove).getType().getMoveCost();
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