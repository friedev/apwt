package corelib.display.windows;

import corelib.display.glyphs.ColorString;
import corelib.display.Display;
import java.util.ArrayList;
import java.util.List;

/**
 * A centered {@link Window} meant for temporary use as a popup. AsciiPanel
 * cannot manage centered {@link corelib.display.glyphs.ColorSet ColorSets}, so
 * {@link PopupWindow} uses
 * {@link corelib.display.glyphs.ColorString ColorStrings} instead.
 */
public class PopupWindow extends Window<ColorString>
{
    /** The y value at which the first content is written. */
    private int y;
    
    /**
     * The separator used to divide the {@link PopupWindow} at each null
     * content.
     */
    private Line separator;
    
    /**
     * Creates a {@link PopupWindow} with all fields defined.
     * @param display the {@link Window}'s {@link corelib.display.Display}
     * @param contents the {@link Window}'s contents
     * @param y the {@link Window}'s y coordinate
     * @param border the {@link Window}'s {@link Border}
     * @param separator the {@link Window}'s separator
     */
    public PopupWindow(Display display, List<ColorString> contents, int y,
            Border border, Line separator)
    {
        super(display, border, contents);
        this.y = y;
        this.separator = separator;
    }
    
    /**
     * Creates a {@link PopupWindow} from another {@link PopupWindow}.
     * @param copying the {@link PopupWindow} to copy
     */
    public PopupWindow(PopupWindow copying)
    {
        this(copying.getDisplay(), copying.getContents(), copying.y,
                copying.getBorder(), copying.separator);
    }
    
    /**
     * Creates a {@link PopupWindow} with all fields defined.
     * @param display the {@link Window}'s {@link corelib.display.Display}
     * @param y the {@link Window}'s y coordinate
     * @param border the {@link Window}'s {@link Border}
     * @param separator the {@link Window}'s separator
     */
    public PopupWindow(Display display, int y, Border border, Line separator)
        {this(display, new ArrayList<>(), y, border, separator);}
    
    /**
     * Creates a {@link PopupWindow} with no separator.
     * @param display the {@link Window}'s {@link corelib.display.Display}
     * @param contents the {@link Window}'s contents
     * @param y the {@link Window}'s y coordinate
     * @param border the {@link Window}'s {@link Border}
     */
    public PopupWindow(Display display, List<ColorString> contents, int y,
            Border border)
        {this(display, contents, y, border, null);}
    
    /**
     * Creates a {@link PopupWindow} with no separator.
     * @param display the {@link Window}'s {@link corelib.display.Display}
     * @param y the {@link Window}'s y coordinate
     * @param border the {@link Window}'s {@link Border}
     */
    public PopupWindow(Display display, int y, Border border)
        {this(display, new ArrayList<>(), y, border);}
    
    /**
     * Creates a {@link PopupWindow} with a default border and no separator.
     * @param display the {@link Window}'s {@link corelib.display.Display}
     * @param y the {@link Window}'s y coordinate
     */
    public PopupWindow(Display display, int y)
        {this(display, y, new Border(1));}

    @Override
    public void display()
    {
        if (getContents() == null || getContents().isEmpty())
            return;
        
        ColorString[] output =
                getContents().toArray(new ColorString[getContents().size()]);
        
        try
        {
            if (isBordered())
            {
                WindowBuilder.printCenterBoxed(getDisplay(), output, y,
                        getBorder(), separator);
            }
            else
            {
                getDisplay().writeCenter(y, output);
            }
        }
        catch (IllegalArgumentException | IndexOutOfBoundsException e)
        {
            // Do not print the Window if exceptions are encountered
            return;
        }
    }
    
    /**
     * Returns the y coordinate of the {@link PopupWindow}.
     * @return the y coordinate of the {@link PopupWindow}
     */
    public int getY()
        {return y;}
    
    /**
     * Returns the Line used to separate the {@link PopupWindow}.
     * @return the Line used to separate the {@link PopupWindow}
     */
    public Line getSeparator()
        {return separator;}
    
    /**
     * Returns true if the{@link PopupWindow}has a separator.
     * @return true if the {@link PopupWindow}'s separator is not null
     */
    public boolean hasSeparator()
        {return separator != null;}
    
    /**
     * Sets the {@link PopupWindow}'s y coordinate to the specified value.
     * @param y the new y coordinate of the {@link PopupWindow}
     */
    public void setY(int y)
        {this.y = y;}
    
    /**
     * Converts the provided String into a
     * {@link corelib.display.glyphs.ColorString ColorString} and adds it to the
     * {@link PopupWindow}'s contents.
     * @param content the String to add
     */
    public PopupWindow add(String content)
        {getContents().add(new ColorString(content)); return this;}
    
    /**
     * Sets the line of the {@link PopupWindow}'s contents at i to the provided
     * String, converted into a {@link corelib.display.glyphs.ColorString}.
     * @param index the line of the {@link PopupWindow}'s contents to replace
     * @param content the String that will be set at the line
     */
    public void set(int index, String content)
        {getContents().set(index, new ColorString(content));}
    
    /**
     * Inserts the given String into the given index of the
     * {@link PopupWindow}'s contents.
     * @param index the index at which to insert the String
     * @param content the String to insert
     */
    public void insert(int index, String content)
        {getContents().add(index, new ColorString(content));}
}