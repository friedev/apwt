package corelib.items;

import java.util.List;

/** A simple resource that is a BaseItem with an amount field. */
public class Resource extends BaseItem
{
    /** The amount of the resource. */
    private int amount;
    
    /**
     * Creates a {@link Resource} with all fields defined.
     * @param name the {@link Resource}'s name
     * @param description the {@link Resource}'s description
     * @param value the {@link Resource}'s value
     * @param amount the {@link Resource}'s initial amount
     */
    public Resource(String name, String description, int value, int amount)
    {
        super(name, description, value);
        this.amount = amount;
    }
    
    /**
     * Creates an empty {@link Resource} with all fields defined.
     * @param name the {@link Resource}'s name
     * @param description the {@link Resource}'s description
     * @param value the {@link Resource}'s value
     */
    public Resource(String name, String description, int value)
        {this(name, description, value, 0);}
    
    @Override
    public String toString()
        {return getName() + " (" + amount + ")";}
    
    @Override
    public List<String> defineAsList()
    {
        List<String> definition = super.defineAsList();
        definition.add(" -Amount: " + amount);
        return definition;
    }
}