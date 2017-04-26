package items;

import java.util.List;

/** A container that is simply a collection of items with a capacity. */
public class Container
{
    private int capacity;
    private List<Item> contents;
    
    public Container(int capacity)
    {
        this.capacity = capacity;
        contents = new java.util.ArrayList<>();
    }
    
    public int getCapacity()
        {return capacity;}
    
    public int getUsedCapacity()
    {
        int usedCapacity = 0;
        for (Item item: contents)
            usedCapacity += item.getVolume();
        return usedCapacity;
    }
    
    public int getRemainingCapacity()
        {return capacity - getUsedCapacity();}
    
    public String getCapacityAsFraction()
        {return getUsedCapacity() + "/" + capacity;}
    
    public List<Item> toList() {return contents;}
    
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
    
    public Item findItem(String name)
    {
        for (Item item: contents)
            if (item.isName(name))
                return item;
        
        return null;
    }
    
    public Item takeItem(String name)
    {
        Item item = findItem(name);
        if (item != null)
        {
            contents.remove(item);
            return item;
        }
        
        return null;
    }
}