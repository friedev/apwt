package corelib.items;

import java.util.List;

/**
 * A item that also acts as a container - should be used in cases such as
 * inventories.
 */
public class ContainerItem extends Item
{
    /** The {@link Container} representing what this {@link Item} contains. */
    private Container container;
    
    /**
     * Creates a {@link ContainerItem} with an existing {@link Container}.
     * @param name the {@link ContainerItem}'s name
     * @param description the {@link ContainerItem}'s description
     * @param value the {@link ContainerItem}'s value
     * @param volume the {@link ContainerItem}'s volume
     * @param container  the {@link ContainerItem}'s {@link Container}
     */
    public ContainerItem(String name, String description, int value, int volume,
            Container container)
    {
        super(name, description, value, volume);
        this.container = container;
    }
    
    /**
     * Creates a {@link ContainerItem} with an empty {@link Container} of the
     * specified capacity.
     * @param name the {@link ContainerItem}'s name
     * @param description the {@link ContainerItem}'s description
     * @param value the {@link ContainerItem}'s value
     * @param volume the {@link ContainerItem}'s volume
     * @param capacity the capacity of the {@link ContainerItem}'s
     * {@link Container}
     */
    public ContainerItem(String name, String description, int value, int volume,
            int capacity)
        {this(name, description, value, volume, new Container(capacity));}
    
    /**
     * Returns this {@link ContainerItem} as a {@link Container}.
     * @return this {@link ContainerItem} as a {@link Container}
     */
    public Container container()
        {return container;}
    
    @Override
    public List<String> defineAsList()
    {
        List<String> definition = super.defineAsList();
        definition.add(" -Capacity: " + container.getCapacityAsFraction());
        return definition;
    }
}