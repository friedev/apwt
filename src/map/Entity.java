package map;

import core.Point;
import core.display.ColorChar;

/** An entity capable of movement around a map. */
public class Entity extends items.Nameable
{
    private Map map;
    private Point location;
    private ColorChar glyph;
    
    public Entity(String n, Map m, Point l, ColorChar g)
    {
        super(n);
        map = m;
        if (l == null)
            location = new Point();
        else
            location = l;
        glyph = g;
    }
    
    public Entity(String n, Map m, ColorChar g)
        {this(n, m, new Point(), g);}
    
    public Map getMap()
        {return map;}
    
    public Point getLocation()
        {return location;}
    
    public ColorChar getGlyph()
        {return glyph;}
    
    public void moveTo(Point destination)
    {
        if (map.contains(destination) && map.tileAt(destination).getType()
                .getProperties().contains(TileProperty.OPEN))
            location = destination;
    }
    
    public void moveTo(int x, int y)
        {moveTo(new Point(x, y));}
    
    public void moveBy(int x, int y)
        {moveTo(location.x + x, location.y + y);}
    
    public void moveNorth()     {moveBy( 0,  1);}
    public void moveSouth()     {moveBy( 0, -1);}    
    public void moveWest()      {moveBy(-1,  0);}
    public void moveEast()      {moveBy( 1,  0);}
    public void moveNorthwest() {moveBy(-1,  1);}
    public void moveNortheast() {moveBy( 1,  1);}
    public void moveSouthwest() {moveBy(-1, -1);}
    public void moveSoutheast() {moveBy( 1, -1);}
}