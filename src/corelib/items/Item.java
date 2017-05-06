package corelib.items;

import java.util.List;

/**
 * A more fleshed-out item that takes up space and can be stored in a container.
 */
public class Item extends BaseItem
{
    /** The size of the {@link Item}. */
    private int volume;
    
    /** The {@link Container} that the {@link Item} is contained in. */
    private Container container;
    
    /**
     * Creates an {@link Item} with all fields defined.
     * @param name the {@link Item}'s name
     * @param description the {@link Item}'s description
     * @param value the {@link Item}'s value
     * @param volume the {@link Item}'s volume
     */
    public Item(String name, String description, int value, int volume)
    {
        super(name, description, value);
        this.volume = volume;
        container = null;
    }
    
    /**
     * Returns this {@link Item}'s volume.
     * @return this {@link Item}'s volume
     */
    public int getVolume()
        {return volume;}
    
    /**
     * Returns the {@link Container} containing this {@link Item}.
     * @return the {@link Container} containing this {@link Item}
     */
    public Container getContainer()
        {return container;}
    
    /**
     * Returns true if this {@link Item} is contained in a {@link Container}.
     * @return true if this {@link Item} is contained in a {@link Container}
     */
    public boolean isContained()
        {return container != null;}
    
    @Override
    public List<String> defineAsList()
    {
        List<String> definition = super.defineAsList();
        definition.add(" -Volume: " + volume);
        if (container != null)
            definition.add(" -Container: " + container);
        return definition;
    }
}