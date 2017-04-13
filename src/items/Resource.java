package items;

import core.display.Display;

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
    
    /** Prints the name, value, description, and amount of the item. */
    @Override
    public void define()
    {
        Display.println(getName().toUpperCase() + ":");
        Display.printListItem("Value", getValue());
        Display.printListItem("Amount", amount);
        Display.printListItem("Description", getDescription());
    }
}