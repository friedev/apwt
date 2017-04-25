package map;

import core.Point;
import items.Container;

/** An expanded entity integrated with the item system. */
public class Being extends Entity
{
    // Replace with a BodyPart[] eventually
    private Container[] inventories;
    
    public Being(String n, Map m, Point l, Container[] inventories)
    {
        super(n, m, l);
        this.inventories = inventories;
    }
}