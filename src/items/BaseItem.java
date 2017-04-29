package items;

import java.util.ArrayList;
import java.util.List;

/** A simple item with only a name, description, and value. */
public abstract class BaseItem extends Nameable
{
    private String description;
    private int    value;
    
    public BaseItem(String name, int value) {this(name, null, value);}
    
    public BaseItem(String name, String description, int value)
    {
        super(name);
        this.description = description;
        this.value = value;
    }
    
    public String getDescription() {return description;}
    public int    getValue()       {return value;}
    
    protected List<String> defineAsList()
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