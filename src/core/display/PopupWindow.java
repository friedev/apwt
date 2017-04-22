package core.display;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple class used to display text at a certain location, with options to
 * center it or surround it with a border.
 */
public class PopupWindow
{
    protected Display    display;
    private List<ColorString> contents;
    protected int        y;
    protected Border     border;
    protected LineBorder separator;
    
    public PopupWindow(Display d, int yy, Border b, LineBorder s)
    {
        display   = d;
        y         = yy;
        border    = b;
        separator = s;
        contents  = new ArrayList<>();
    }
    
    public PopupWindow(Display d, int yy, Border b)
        {this(d, yy, b, null);}
    
    public PopupWindow(Display d, int yy)
        {this(d, yy, new Border(1));}

    public void displayOutput()
    {
        if (contents == null || contents.isEmpty())
            return;
        
        ColorString[] output =
                contents.toArray(new ColorString[contents.size()]);
        
        if (border != null)
        {
            WindowBuilder.printCenterBoxed(display, output, y, border,
                        separator);
        }
        else
        {
            display.writeCenter(output, y);
        }
    }
    
    public List<ColorString> getContents() {return contents;}
    public Display getDisplay()  {return display;       }
    public int     getY()        {return y;             }
    public Border  getBorder()   {return border;        }
    public boolean isBordered()  {return border != null;}
    
    public void setY(int yy) {y = yy;}
    
    public void add(String s) {contents.add(new ColorString(s));}
    public void add(ColorString s) {contents.add(s);}
    
    public void set(int i, String s) {contents.set(i, new ColorString(s));}
    public void set(int i, ColorString s) {contents.set(i, s);}
    
    public void addSeparator() {contents.add(null);}
}