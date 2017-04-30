package corelib.items;

import java.util.List;

/**
 * A more fleshed-out item that takes up space and can be stored in a container.
 */
public class Item extends BaseItem
{
    private int volume;
    private Container container;
    
    public Item(String name, String description, int value, int volume)
    {
        super(name, description, value);
        this.volume = volume;
        container = null;
    }
    
    public int       getVolume()    {return volume;           }
    public Container getContainer() {return container;        }
    public boolean   isContained()  {return container != null;}
    
    @Override
    protected List<String> defineAsList()
    {
        List<String> definition = super.defineAsList();
        definition.add(" -Volume: " + volume);
        if (container != null)
            definition.add(" -Container: " + container);
        return definition;
    }
}