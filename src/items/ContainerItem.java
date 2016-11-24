package items;

import core.Display;

/**
 * A container that also acts as an item - should be used in cases such as
 * inventories.
 */
public class ContainerItem extends Item
{
    private Container container;
    
    public ContainerItem(String n, String d, int va, int vo, int c)
        {this(n, d, va, vo, new Container(c));}
    
    public ContainerItem(String n, String d, int va, int vo, Container c)
    {
        super(n, d, va, vo);
        container = c;
    }
    
    public Container container() {return container;}
    
    /** Prints the name, value, description, volume, and capacity of the item. */
    @Override
    public void define()
    {
        Display.println(getName().toUpperCase() + ":");
        Display.printListItem("Value", getValue());
        Display.printListItem("Volume", getVolume());
        Display.printListItem("Capacity", container.getCapacityAsFraction());
        if (isContained())
            Display.printListItem("Container", getContainer().toString());
        Display.printListItem("Description", getDescription());
    }
}