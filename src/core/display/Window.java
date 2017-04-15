package core.display;

import java.util.List;

/**
 * A simple class used to display text at a certain location, with options to
 * center it or surround it with a border.
 */
public class Window
{
    public static final boolean CENTERED = false;
    
    protected Display    display;
    private List<String> contents;
    protected int        x;
    protected int        y;
    protected boolean    centered;
    protected Border     border;
    
    public Window(Display d, int xx, int yy, boolean c, Border b)
    {
        display  = d;
        x        = xx;
        y        = yy;
        centered = c;
        border   = b;
        contents = new java.util.ArrayList<>();
    }
    
    public Window(Display d, int xx, int yy, boolean c)
        {this(d, xx, yy, c, new Border(1));}
    
    public Window(Display d, int xx, int yy, Border b)
        {this(d, xx, yy, CENTERED, b);}
    
    public Window(Display d, int xx, int yy)
        {this(d, xx, yy, CENTERED, new Border(1));}

    public void displayOutput()
    {
        if (contents == null || contents.isEmpty())
            return;
        
        String[] output = contents.toArray(new String[contents.size()]);
        
        if (border != null)
        {
            if (centered)
                ShapeMaker.printCenterBoxed(display, output, y, border);
            else
                ShapeMaker.printBoxed(display, output, x, y, border);
        }
        else
        {
            if (centered)
                display.writeCenter(output, y);
            else
                display.write(output, new core.Point(x, y));
        }
    }
    
    public List<String> getContents() {return contents;      }
    public Display      getDisplay()  {return display;       }
    public int          getX()        {return x;             }
    public int          getY()        {return y;             }
    public boolean      isCentered()  {return centered;      }
    public Border       getBorder()   {return border;        }
    public boolean      isBordered()  {return border != null;}
    
    public void setX(int xx) {x = xx;}
    public void setY(int yy) {y = yy;}
}