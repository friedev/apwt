package corelib.display.windows;

import corelib.display.glyphs.ColorString;
import corelib.display.Display;
import java.util.ArrayList;
import java.util.List;

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
     * Creates a Window with all fields defined.
     * @param display the Window's Display
     * @param contents the Window's contents
     * @param y the Window's y coordinate
     * @param border the Window's Border
     * @param separator the Window's separator
     */
    public PopupWindow(Display display, List<ColorString> contents, int y,
            Border border, Line separator)
    {
        super(display, border, contents);
        this.y = y;
        this.separator = separator;
    }
    
    public PopupWindow(PopupWindow copying)
    {
        this(copying.display, copying.contents, copying.y, copying.border,
                copying.separator);
    }
    
    /**
     * Creates a Window with all fields defined.
     * @param display the Window's Display
     * @param y the Window's y coordinate
     * @param border the Window's Border
     * @param separator the Window's separator
     */
    public PopupWindow(Display display, int y, Border border, Line separator)
        {this(display, new ArrayList<>(), y, border, separator);}
    
    /**
     * Creates a Window with no separator.
     * @param display the Window's Display
     * @param contents the Window's contents
     * @param y the Window's y coordinate
     * @param border the Window's Border
     */
    public PopupWindow(Display display, List<ColorString> contents, int y,
            Border border)
        {this(display, contents, y, border, null);}
    
    /**
     * Creates a Window with no separator.
     * @param display the Window's Display
     * @param y the Window's y coordinate
     * @param border the Window's Border
     */
    public PopupWindow(Display display, int y, Border border)
        {this(display, new ArrayList<>(), y, border);}
    
    /**
     * Creates a Window with a default border and no separator.
     * @param display the Window's Display
     * @param y the Window's y coordinate
     */
    public PopupWindow(Display display, int y)
        {this(display, y, new Border(1));}

    @Override
    public void display()
    {
        if (contents == null || contents.isEmpty())
            return;
        
        ColorString[] output =
                contents.toArray(new ColorString[contents.size()]);
        
        try
        {
            if (border != null)
            {
                WindowBuilder.printCenterBoxed(display, output, y, border,
                            separator);
            }
            else
            {
                display.writeCenter(y, output);
            }
        }
        catch (IllegalArgumentException | IndexOutOfBoundsException e)
        {
            // Do not print the Window if exceptions are encountered
            return;
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
    
    public void insert(int index, String content)
        {contents.add(index, new ColorString(content));}
}