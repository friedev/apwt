package corelib.display.windows;

import corelib.display.glyphs.ColorString;
import corelib.display.Display;

/**
 * A centered Window meant for temporary use as a popup. AsciiPanel cannot
 * manage centered ColorSets, so PopupWindow uses ColorStrings instead.
 */
public class PopupWindow extends Window<ColorString>
{
    /** The y value at which the first content is written. */
    private int y;
    /** The separator used to divide the PopupWindow at each null content. */
    private Line separator;
    
    /**
     * Creates a PopupWindow with all fields defined.
     * @param display the PopupWindow's Display
     * @param y the PopupWindow's y coordinate
     * @param border the PopupWindow's Border
     * @param separator the PopupWindow's Separators
     */
    public PopupWindow(Display display, int y, Border border, Line separator)
    {
        super(display, border);
        this.y = y;
        this.separator = separator;
    }
    
    /**
     * Creates a PopupWindow with no separator.
     * @param display the PopupWindow's Display
     * @param y the PopupWindow's y coordinate
     * @param border the PopupWindow's Border
     */
    public PopupWindow(Display display, int y, Border border)
        {this(display, y, border, null);}
    
    /**
     * Creates a borderless PopupWindow with no separator.
     * @param display the PopupWindow's Display
     * @param y the PopupWindow's y coordinate
     */
    public PopupWindow(Display display, int y)
        {this(display, y, new Border(1), null);}

    @Override
    public void display()
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
     * Returns the y coordinate of the PopupWindow.
     * @return the y coordinate of the PopupWindow
     */
    public int getY()
        {return y;}
    
    /**
     * Returns the Line used to separate the PopupWindow.
     * @return the Line used to separate the PopupWindow
     */
    public Line getSeparator()
        {return separator;}
    
    /**
     * Returns true if the PopupWindow has a separator.
     * @return true if the PopupWindow's separator is not null
     */
    public boolean hasSeparator()
        {return separator != null;}
    
    /**
     * Sets the Window's y coordinate to the specified value.
     * @param y the new y coordinate of the Window
     */
    public void setY(int y)
        {this.y = y;}
    
    /**
     * Converts the provided String into a ColorString and adds it to the
     * PopupWindow's contents.
     * @param content the String to add
     */
    public void add(String content)
        {contents.add(new ColorString(content));}
    
    /**
     * Sets the line of the PopupWindow's contents at i to the provided String,
     * converted into a ColorString.
     * @param index the line of the PopupWindow's contents to replace
     * @param content the String that will be set at the line
     */
    public void set(int index, String content)
        {contents.set(index, new ColorString(content));}
}