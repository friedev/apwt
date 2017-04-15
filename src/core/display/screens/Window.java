package core.display.screens;

import core.display.Display;
import core.display.Menu;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * A simple class used to display text at a certain location, with options to
 * center it or surround it with a border.
 */
public class Window extends Screen
{
    public static final boolean CENTERED = false;
    public static final boolean BORDERED = true;
    
    private List<String> contents;
    protected int        x;
    protected int        y;
    protected boolean    centered;
    protected boolean    bordered;
    
    public Window(Display d, int xx, int yy, boolean c, boolean b)
    {
        super(d);
        x        = xx;
        y        = yy;
        centered = c;
        bordered = b;
        contents = new java.util.ArrayList<>();
    }
    
    public Window(Display d, int xx, int yy, boolean c)
        {this(d, xx, yy, c, BORDERED);}
    
    public Window(Display d, int xx, int yy)
        {this(d, xx, yy, CENTERED, BORDERED);}

    @Override
    public void displayOutput()
    {
        if (contents == null || contents.isEmpty())
            return;
        
        String[] output = contents.toArray(new String[contents.size()]);
        
        if (bordered)
        {
            if (centered)
                Menu.printCenterBoxed(display, output, y);
            else
                Menu.printBoxed(display, output, x, y);
        }
        else
        {
            if (centered)
                display.writeCenter(output, y);
            else
                display.write(output, new core.Point(x, y));
        }
    }

    @Override
    public Screen processInput(KeyEvent key)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<String> getContents() {return contents;}
}