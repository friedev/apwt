package corelib.map;

import corelib.items.Container;

/** An upgraded tile integrated with the item system. */
public class Tile
{
    public static final int MAX_VOLUME = 100;
    
    private TileType type;
    private Container floorItems;
    
    public Tile(TileType t)
    {
        type = t;
        floorItems = new Container(MAX_VOLUME);
    }
    
    @Override
    public String toString()
        {return Character.toString(type.getGlyph().character);}
    
    public TileType getType()
        {return type;}
    
    public Container getItems()
        {return floorItems;}
}