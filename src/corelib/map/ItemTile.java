package corelib.map;

import corelib.items.Container;

/**
 * A tile integrated with the item system.
 * @param <Property>
 */
public class ItemTile<Property> extends Tile<Property>
{
    public static final int MAX_VOLUME = 100;
    
    private Container floorItems;
    
    public ItemTile(Tile t)
    {
        super(t.glyph, t.moveCost, t.properties);
        floorItems = new Container(MAX_VOLUME);
    }
    
    public Container getItems()
        {return floorItems;}
}