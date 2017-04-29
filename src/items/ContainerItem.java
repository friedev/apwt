package items;

import java.util.List;

/**
 * A item that also acts as a container - should be used in cases such as
 * inventories.
 */
public class ContainerItem extends Item
{
    private Container container;
    
    public ContainerItem(String name, String description, int value, int volume,
            int capacity)
        {this(name, description, value, volume, new Container(capacity));}
    
    public ContainerItem(String name, String description, int value, int volume,
            Container container)
    {
        super(name, description, value, volume);
        this.container = container;
    }
    
    public Container container() {return container;}
    
    @Override
    protected List<String> defineAsList()
    {
        List<String> definition = super.defineAsList();
        definition.add(" -Capacity: " + container.getCapacityAsFraction());
        return definition;
    }
}