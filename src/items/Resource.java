package items;

import java.util.List;

/** A simple resource that is a BasicItem with an amount field. */
public class Resource extends BaseItem
{
    int amount;
    
    public Resource(String n, String d, int v) {this(n, d, v, 0);}
    
    public Resource(String n, String d, int v, int a)
    {
        super(n, d, v);
        amount = a;
    }
    
    @Override
    public String toString() {return getName() + " (" + amount + ")";}
    
    @Override
    protected List<String> defineAsList()
    {
        List<String> definition = super.defineAsList();
        definition.add(" -Amount: " + amount);
        return definition;
    }
}