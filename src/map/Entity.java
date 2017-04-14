package map;

import core.Point;

/** An entity capable of movement around a map. */
public class Entity extends items.Nameable
{
    private Map   map;
    private Point location;
    
    public Entity(String n, Map m) {this(n, m, new Point());}
    
    public Entity(String n, Map m, Point l)
    {
        super(n);
        map = m;
        location = l;
    }
    
    public Map   getMap()      {return map;     }
    public Point getLocation() {return location;}
    
    public void moveTo(Point destination)
    {
        if (map.contains(destination))
            location = destination;
    }
    
    public void move(String dir)
    {
        switch (dir)
        {
            case "up":
                moveTo(new Point(location.x, location.y - 1));
                break;
            case "down":
                moveTo(new Point(location.x, location.y + 1));
                break;
            case "left":
                moveTo(new Point(location.x - 1, location.y));
                break;
            case "right":
                moveTo(new Point(location.x + 1, location.y));
                break;
            // Do nothing by default
        }
    }
}