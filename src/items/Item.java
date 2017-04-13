package items;

import core.display.Display;

/**
 * A more fleshed-out item that takes up space and can be stored in a container.
 */
public class Item extends BaseItem
{
    private int volume;
    private Container container;
    
    public Item(String n, String d, int va, int vo)
    {
        super(n, d, va);
        volume = vo;
        container = null;
    }
    
    public int       getVolume()    {return volume;           }
    public Container getContainer() {return container;        }
    public boolean   isContained()  {return container != null;}
    
    /**
     * Prints the name, value, description, volume, and container of the item.
     */
    @Override
    public void define()
    {
        Display.println(getName().toUpperCase() + ":");
        Display.printListItem("Value", getValue());
        Display.printListItem("Volume", volume);
        if (container != null)
            Display.printListItem("Container", container.toString());
        Display.printListItem("Description", getDescription());
    }
}