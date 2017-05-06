package corelib.items;

import java.util.List;

/** A collection of {@link Items} up to a maximum capacity. */
public class Container
{
    /**
     * The total volume of {@link Items} that can be stored in this
     * {@link Container}.
     */
    private int capacity;
    
    /** The {@link Item Items} currently stored in this {@link Container}. */
    private List<Item> contents;
    
    /**
     * Creates an empty {@link Container} with the provided capacity.
     * @param capacity the {@link Container}'s capacity
     */
    public Container(int capacity)
    {
        this.capacity = capacity;
        contents = new java.util.ArrayList<>();
    }
    
    /**
     * Returns the {@link Container}'s capacity.
     * @return the {@link Container}'s capacity
     */
    public int getCapacity()
        {return capacity;}
    
    /**
     * Returns the total volume of {@link Item Items} in this {@link Container}.
     * @return the total volume of {@link Item Items} in this {@link Container}
     */
    public int getUsedCapacity()
    {
        int usedCapacity = 0;
        for (Item item: contents)
            usedCapacity += item.getVolume();
        return usedCapacity;
    }
    
    /**
     * Returns the remaining open volume in this {@link Container}.
     * @return the remaining open volume in this {@link Container}
     */
    public int getRemainingCapacity()
        {return capacity - getUsedCapacity();}
    
    /**
     * Returns a String representing the capacity of this {@link Container}, in
     * the format of {@code used capacity / total capacity}.
     * @return a String representing the capacity of this {@link Container}
     */
    public String getCapacityAsFraction()
        {return getUsedCapacity() + "/" + capacity;}
    
    /**
     * Returns the List of {@link Item Items} in this {@link Container}.
     * @return the List of {@link Item Items} in this {@link Container}
     */
    public List<Item> toList()
        {return contents;}
    
    /**
     * Adds the given {@link Item} to the {@link Container}, if possible.
     * @param item the {@link Item} to add
     * @return true if the {@link Item} was added
     */
    public boolean addItem(Item item)
    {
        if (item.getVolume() <= getRemainingCapacity())
        {
            contents.add(item);
            return true;
        }
        
        return false;
    }
    
    /**
     * Removes the given {@link Item} to the {@link Container}, if it is in the
     * {@link Container}.
     * @param item the {@link Item} to remove
     * @return true if the {@link Item} was remove
     */
    public boolean removeItem(Item item)
        {return contents.remove(item);}
    
    /**
     * Finds the first {@link Item} in this {@link Container} with the given
     * name.
     * @param name the name of the {@link Item} to find
     * @return the first {@link Item} found with the given name, null if not
     * found
     */
    public Item findItem(String name)
    {
        for (Item item: contents)
            if (item.isName(name))
                return item;
        
        return null;
    }
    
    /**
     * Finds the first {@link Item} in this {@link Container} with the given
     * name, and removes it.
     * @param name the name of the {@link Item} to remove
     * @return the first {@link Item} found with the given name, null if not
     * found
     */
    public Item takeItem(String name)
    {
        Item item = findItem(name);
        if (item != null)
        {
            removeItem(item);
            return item;
        }
        
        return null;
    }
}