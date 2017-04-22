package core.display;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple class used to display text at a certain location, with options to
 * center it or surround it with a border.
 */
public class Window
{
    public static final boolean CENTERED = false;
    
    protected Display    display;
    private List<ColorSet> contents;
    protected int        x;
    protected int        y;
    protected Border     border;
    protected LineBorder separator;
    
    public Window(Display d, int xx, int yy, Border b, LineBorder s)
    {
        display   = d;
        x         = xx;
        y         = yy;
        border    = b;
        separator = s;
        contents  = new ArrayList<>();
    }
    
    public Window(Display d, int xx, int yy, Border b)
        {this(d, xx, yy, b, null);}
    
    public Window(Display d, int xx, int yy)
        {this(d, xx, yy, new Border(1));}

    public void displayOutput()
    {
        if (contents == null || contents.isEmpty())
            return;
        
        ColorSet[] output = contents.toArray(new ColorSet[contents.size()]);
        
        if (border != null)
        {
            WindowBuilder.printBoxed(display, output, x, y,
                    border, separator);
            core.display.Console.println("Yep this happened.");
        }
        else
        {
            display.write(output, new core.Point(x, y));
        }
    }
    
    public List<ColorSet> getContents() {return contents;}
    public Display getDisplay()  {return display;        }
    public int     getX()        {return x;              }
    public int     getY()        {return y;              }
    public Border  getBorder()   {return border;         }
    public boolean isBordered()  {return border != null; }
    
    public void setX(int xx) {x = xx;}
    public void setY(int yy) {y = yy;}
    
    public void add(String s)        {contents.add(new ColorSet(s));}
    public void add(ColorString s)   {contents.add(new ColorSet(s));}
    public void add(ColorChar[] c)   {contents.add(new ColorSet(c));}
    public void add(ColorString[] s) {contents.add(ColorSet.toColorSet(s));}
    public void add(ColorSet s)      {contents.add(s);}
    
    public void set(int i, String s)        {contents.set(i, new ColorSet(s));}
    public void set(int i, ColorString s)   {contents.set(i, new ColorSet(s));}
    public void set(int i, ColorChar[] c)   {contents.set(i, new ColorSet(c));}
    public void set(int i, ColorString[] s) {contents.set(i, ColorSet.toColorSet(s));}
    public void set(int i, ColorSet s)      {contents.set(i, s);}
    
    public void addSeparator() {contents.add(null);}
}