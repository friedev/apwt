package items;

/**
 * A container for items up to a certain capacity - note that it is also an item
 * itself.
 */
public class Container extends Item
{
    private int capacity;
    private java.util.List<Item> contents;
    
    public Container(String n, String d, int va, int vo, int c)
    {
        super(n, d, va, vo);
        capacity = c;
        contents = new java.util.ArrayList<>();
    }
    
    public int getCapacity() {return capacity;}
    
    public int getUsedCapacity()
    {
        int usedCapacity = 0;
        for (Item item: contents)
            usedCapacity += item.getVolume();
        return usedCapacity;
    }
    
    public int getRemainingCapacity() {return capacity - getUsedCapacity();}
    
    public String getCapacityAsFraction()
        {return capacity + "/" + getUsedCapacity();}
    
    public boolean addItem(Item item)
    {
        if (item.getVolume() <= getRemainingCapacity())
        {
            contents.add(item);
            return true;
        }
        
        return false;
    }
    
    public boolean removeItem(Item item)
        {return contents.remove(item);}
    
    /** Prints the name, value, description, volume, and capacity of the item. */
    @Override
    public void define()
    {
        System.out.println("  " + getName().toUpperCase() + ":");
        System.out.println("  Value: " + getValue());
        System.out.println("  Volume: " + getVolume());
        System.out.println("  Capacity: " + getCapacityAsFraction());
        if (isContained())
            System.out.println("  Container: " + getContainer());
        System.out.println("  Description: " + getDescription());
    }
}