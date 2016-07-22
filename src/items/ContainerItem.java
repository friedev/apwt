package items;

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
        System.out.println("  " + getName().toUpperCase() + ":");
        System.out.println("  Value: " + getValue());
        System.out.println("  Volume: " + getVolume());
        System.out.println("  Capacity: " + container.getCapacityAsFraction());
        if (isContained())
            System.out.println("  Container: " + getContainer());
        System.out.println("  Description: " + getDescription());
    }
}