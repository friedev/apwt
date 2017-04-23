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
    
    private Display display;
    private List<ColorSet> contents;
    private int     x;
    private int     y;
    private Border  border;
    private List<LineBorder> separators;
    
    public Window(Display d, int xx, int yy, Border b, List<LineBorder> s)
    {
        display    = d;
        x          = xx;
        y          = yy;
        border     = b;
        separators = s;
        contents   = new ArrayList<>();
    }
    
    public Window(Display d, int xx, int yy, Border b)
        {this(d, xx, yy, b, null);}
    
    public Window(Display d, int xx, int yy)
        {this(d, xx, yy, new Border(1), null);}

    public void displayOutput()
    {
        if (contents == null || contents.isEmpty())
            return;
        
        ColorSet[] output = contents.toArray(new ColorSet[contents.size()]);
        
        if (border != null)
        {
            WindowBuilder.printBoxed(display, output, y, x, border,
                    separators.toArray(new LineBorder[separators.size()]));
        }
        else
        {
            display.write(output, new core.Point(x, y));
        }
    }
    
    public List<ColorSet> getContents() {return contents;}
    public Display getDisplay() {return display;        }
    public int     getX()       {return x;              }
    public int     getY()       {return y;              }
    public Border  getBorder()  {return border;         }
    public boolean isBordered() {return border != null; }
    
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
    public void addSeparator(LineBorder separator)
    {
        contents.add(null);
        separators.add(separator);
    }
    public void setSeparator(int i, LineBorder separator)
    {
        if (i >= separators.size() || i < 0)
            return;
        
        separators.set(i, separator);
    }
}