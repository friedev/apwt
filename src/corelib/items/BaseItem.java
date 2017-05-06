package corelib.items;

import java.util.ArrayList;
import java.util.List;

/** A simple item with only a name, description, and value. */
public abstract class BaseItem extends Nameable
{
    /** A String describing the {@link BaseItem}. */
    private String description;
    
    /** The value of the {@link BaseItem}. */
    private int value;
    
    /**
     * Creates a {@link BaseItem} with all fields defined.
     * @param name the {@link BaseItem}'s name
     * @param description the {@link BaseItem}'s description
     * @param value the {@link BaseItem}'s value
     */
    public BaseItem(String name, String description, int value)
    {
        super(name);
        this.description = description;
        this.value = value;
    }
    
    /**
     * Creates a {@link BaseItem} with no description.
     * @param name the {@link BaseItem}'s name
     * @param value the {@link BaseItem}'s value
     */
    public BaseItem(String name, int value)
        {this(name, null, value);}
    
    /**
     * Returns this {@link BaseItem}'s description.
     * @return this {@link BaseItem}'s description
     */
    public String getDescription()
        {return description;}
    
    /**
     * Returns this {@link BaseItem}'s value.
     * @return this {@link BaseItem}'s value
     */
    public int getValue()
        {return value;}
    
    /**
     * Returns a list of this item's characteristics.
     * @return a list of this item's characteristics as a {@code List<String>}
     */
    public List<String> defineAsList()
    {
        ArrayList<String> definition = new ArrayList<>();
        definition.add(getName() + ":");
        definition.add(description);
        definition.add(" -Value: " + value);
        return definition;
    }
    
    /**
     * Returns a String[] with lines listing the object's characteristics.
     * @return a String[] with lines listing the object's characteristics
     */
    public String[] define()
    {
        List<String> definition = defineAsList();
        return definition.toArray(new String[definition.size()]);
    }
}