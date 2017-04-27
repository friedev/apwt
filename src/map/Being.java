package map;

import core.Point;
import core.display.ColorChar;
import items.Container;

/** An expanded entity integrated with the item system. */
public class Being extends Entity
{
    // Replace with a BodyPart[] eventually
    private Container[] inventories;
    
    public Being(String n, Map m, Point l, ColorChar g, Container[] inventories)
    {
        super(n, m, l, g);
        this.inventories = inventories;
    }
}