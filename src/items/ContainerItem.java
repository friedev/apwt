package items;

import core.Console;

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
        Console.println(getName().toUpperCase() + ":");
        Console.printListItem("Value", getValue());
        Console.printListItem("Volume", getVolume());
        Console.printListItem("Capacity", container.getCapacityAsFraction());
        if (isContained())
            Console.printListItem("Container", getContainer().toString());
        Console.printListItem("Description", getDescription());
    }
}