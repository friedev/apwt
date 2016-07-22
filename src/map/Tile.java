package map;

import items.Container;
import items.Item;

/** An upgraded tile integrated with the item system. */
public class Tile extends BaseTile
{
    public static final int MAX_VOLUME = 100;
    
    private Container items;
    
    public Tile(Point l, Map m)         {this(EMPTY, l, m);}
    public Tile(char s, Point l, Map m)
    {
        super(s, l, m);
        items = new Container(MAX_VOLUME);
    }
    
    public Container getItems() {return items;}
    
    public void addItem(Item item) {items.addItem(item);}
    
    public Item takeItem(String name) {return items.takeItem(name);}
}