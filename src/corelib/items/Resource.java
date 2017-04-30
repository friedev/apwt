package corelib.items;

import java.util.List;

/** A simple resource that is a BasicItem with an amount field. */
public class Resource extends BaseItem
{
    int amount;
    
    public Resource(String name, String description, int value)
        {this(name, description, value, 0);}
    
    public Resource(String name, String description, int value, int amount)
    {
        super(name, description, value);
        this.amount = amount;
    }
    
    @Override
    public String toString()
        {return getName() + " (" + amount + ")";}
    
    @Override
    protected List<String> defineAsList()
    {
        List<String> definition = super.defineAsList();
        definition.add(" -Amount: " + amount);
        return definition;
    }
}