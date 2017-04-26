package items;

import java.util.List;

/**
 * A more fleshed-out item that takes up space and can be stored in a container.
 */
public class Item extends BaseItem
{
    private int volume;
    private Container container;
    
    public Item(String n, String d, int va, int vo)
    {
        super(n, d, va);
        volume = vo;
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