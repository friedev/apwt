package items;

/** A simple item with only a name, description, and value. */
public class BasicItem extends Nameable
{
    private String description;
    private int    value;
    
    public BasicItem(String n, int v) {this(n, null, v);}
    
    public BasicItem(String n, String d, int v)
    {
        super(n);
        description = d;
        value = v;
    }
    
    public String getDescription() {return description;}
    public int    getValue()       {return value;}
    
    /** Prints the name, value, and description of the item. */
    public void define()
    {
        System.out.println("  " + getName().toUpperCase() + ":");
        System.out.println("  Value: " + value + " Credits");
        System.out.println("  Description: " + description);
    }
}