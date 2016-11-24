package map;

import items.Container;

/** An expanded entity integrated with the item system. */
public class Character extends Entity
{
    private Container inventory;
    
    public Character(String n, Map m, Point l)
    {
        super(n, m, l);
        inventory = new Container(0);
    }
}