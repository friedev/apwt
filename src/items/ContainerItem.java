package items;

import java.util.List;

/**
 * A item that also acts as a container - should be used in cases such as
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
    
    @Override
    protected List<String> defineAsList()
    {
        List<String> definition = super.defineAsList();
        definition.add(" -Capacity: " + container.getCapacityAsFraction());
        return definition;
    }
}