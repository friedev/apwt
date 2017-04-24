package core.display;

import java.util.ArrayList;
import java.util.List;

/**
 * A centered Window meant for temporary use as a popup. AsciiPanel cannot
 * manage centered ColorSets, so PopupWindow uses ColorStrings instead.
 */
public class PopupWindow
{
    /** The Display on which to print the window. */
    private Display display;
    /** The contents of the Window, with each ColorString as a different line. */
    private List<ColorString> contents;
    /** The y value at which the first ColorSet is written. */
    private int y;
    /** The Border surrounding the Window; not displayed if null. */
    private Border border;
    /** The separator used to divide the PopupWindow at each null content. */
    private Line separator;
    
    /**
     * Creates a PopupWindow with all fields defined.
     * @param d the PopupWindow's Display
     * @param yy the PopupWindow's y coordinate
     * @param b the PopupWindow's Border
     * @param s the PopupWindow's Separators
     */
    public PopupWindow(Display d, int yy, Border b, Line s)
    {
        display   = d;
        y         = yy;
        border    = b;
        separator = s;
        contents  = new ArrayList<>();
    }
    
    /**
     * Creates a PopupWindow with no separator.
     * @param d the PopupWindow's Display
     * @param yy the PopupWindow's y coordinate
     * @param b the PopupWindow's Border
     */
    public PopupWindow(Display d, int yy, Border b)
        {this(d, yy, b, null);}
    
    /**
     * Creates a borderless PopupWindow with no separator.
     * @param d the PopupWindow's Display
     * @param yy the PopupWindow's y coordinate
     */
    public PopupWindow(Display d, int yy)
        {this(d, yy, new Border(1), null);}

    /** Prints the PopupWindow to its Display using WindowBuilder. */
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
    
    /**
     * Returns the contents of the PopupWindow as a List of ColorStrings.
     * @return the contents of the PopupWindow as a List of ColorStrings
     */
    public List<ColorString> getContents()
        {return contents;}
    
    /**
     * Returns the Display used by the PopupWindow.
     * @return the Display used by the PopupWindow
     */
    public Display getDisplay()
        {return display;}
    
    /**
     * Returns the y coordinate of the PopupWindow.
     * @return the y coordinate of the PopupWindow
     */
    public int getY()
        {return y;}
    
    /**
     * Returns the PopupWindow's Border.
     * @return the PopupWindow's Border
     */
    public Border getBorder()
        {return border;}
    
    /**
     * Returns the Line used to separate the PopupWindow.
     * @return the Line used to separate the PopupWindow
     */
    public Line getSeparator()
        {return separator;}
    
    /**
     * Returns true if the PopupWindow has a Border.
     * @return true if the PopupWindow's Border is not null
     */
    public boolean isBordered()
        {return border != null;}
    
    /**
     * Returns true if the PopupWindow has a separator.
     * @return true if the PopupWindow's separator is not null
     */
    public boolean hasSeparator()
        {return separator != null;}
    
    /**
     * Sets the Window's y coordinate to the specified value.
     * @param yy the new y coordinate of the Window
     */
    public void setY(int yy) {y = yy;}
    
    /**
     * Converts the provided String into a ColorString and adds it to the
     * PopupWindow's contents.
     * @param s the String to add
     */
    public void add(String s)
        {contents.add(new ColorString(s));}
    
    /**
     * Adds the provided ColorString to the PopupWindow's contents.
     * @param s the ColorString to add
     */
    public void add(ColorString s)
        {contents.add(s);}
    
    /**
     * Sets the line of the PopupWindow's contents at i to the provided String,
     * converted into a ColorString.
     * @param i the line of the PopupWindow's contents to replace
     * @param s the String that will be set at the line
     */
    public void set(int i, String s)
        {contents.set(i, new ColorString(s));}
    
    /**
     * Sets the line of the PopupWindow's contents at i to the provided
     * ColorString.
     * @param i the line of the PopupWindow's contents to replace
     * @param s the ColorString that will be set at the line
     */
    public void set(int i, ColorString s)
        {contents.set(i, s);}
    
    /** Adds a separator to the PopupWindow. */
    public void addSeparator()
        {contents.add(null);}
}