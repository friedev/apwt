package corelib.display.window;

import corelib.display.glyphs.ColorChar;
import corelib.display.glyphs.ColorSet;
import corelib.display.glyphs.ColorString;
import corelib.display.Display;
import java.util.List;
import squidpony.squidmath.Coord;

/**
 * A class used to display text at a certain location, optionally with a border
 * or divided into sections.
 */
public class AlignedWindow extends Window<ColorSet>
{
    /** The x value at which the first content is written. */
    private int x;
    /** The y value at which the first content is written. */
    private int y;
    /** The separators used to divide the Window at each null content. */
    private List<Line> separators;
    
    /**
     * Creates a Window with all fields defined.
     * @param display the Window's Display
     * @param x the Window's x coordinate
     * @param y the Window's y coordinate
     * @param border the Window's Border
     * @param separators the Window's Separators
     */
    public AlignedWindow(Display display, int x, int y, Border border,
            List<Line> separators)
    {
        super(display, border);
        this.x          = x;
        this.y          = y;
        this.separators = separators;
    }
    
    /**
     * Creates a Window with no separators.
     * @param display the Window's Display
     * @param x the Window's x coordinate
     * @param y the Window's y coordinate
     * @param border the Window's Border
     */
    public AlignedWindow(Display display, int x, int y, Border border)
        {this(display, x, y, border, null);}
    
    /**
     * Creates a borderless Window with no separators.
     * @param display the Window's Display
     * @param x the Window's x coordinate
     * @param y the Window's y coordinate
     */
    public AlignedWindow(Display display, int x, int y)
        {this(display, x, y, new Border(1), null);}

    /** Prints the Window to its Display using WindowBuilder. */
    @Override
    public void display()
    {
        if (contents == null || contents.isEmpty())
            return;
        
        ColorSet[] output = contents.toArray(new ColorSet[contents.size()]);
        
        if (border != null)
        {
            if (hasSeparators())
            {
                WindowBuilder.printBoxed(display, output, y, x, border,
                        separators.toArray(new Line[separators.size()]));
            }
            else
            {
                WindowBuilder.printBoxed(display, output, y, x, border);
            }
        }
        else
        {
            display.write(output, Coord.get(x, y));
        }
    }
    
    /**
     * Returns the x coordinate of the Window.
     * @return the x coordinate of the Window
     */
    public int getX()
        {return x;}
    
    /**
     * Returns the y coordinate of the Window.
     * @return the y coordinate of the Window
     */
    public int getY()
        {return y;}
    
    /**
     * Returns the List of Lines used to separate the Window.
     * @return the List of Lines used to separate the Window
     */
    public List<Line> getSeparators()
        {return separators;}
    
    /**
     * Returns true if the Window has separators.
     * @return true if the Window's List of separators is not null
     */
    public boolean hasSeparators()
        {return separators != null;}
    
    /**
     * Sets the Window's x coordinate to the specified value.
     * @param x the new x coordinate of the Window
     */
    public void setX(int x)
        {this.x = x;}
    
    /**
     * Sets the Window's y coordinate to the specified value.
     * @param y the new y coordinate of the Window
     */
    public void setY(int y)
        {this.y = y;}
    
    /**
     * Moves the Window to the specified coordinates.
     * @param x the new x coordinate of the Window
     * @param y the new y coordinate of the Window
     */
    public void moveTo(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Converts the provided String into a ColorSet and adds it to the Window's
     * contents.
     * content content the String to add
     */
    public void add(String content)
        {contents.add(new ColorSet(content));}
    
    /**
     * Converts the provided ColorString into a ColorSet and adds it to the
     * Window's contents.
     * @param content the ColorString to add
     */
    public void add(ColorString content)
        {contents.add(new ColorSet(content));}
    
    /**
     * Converts the provided array of ColorChars into a ColorSet and adds it to
     * the Window's contents.
     * @param content the array of ColorChars to add
     */
    public void add(ColorChar[] content)
        {contents.add(new ColorSet(content));}
    
    /**
     * Converts the provided array of ColorStrings into a ColorSet and adds it
     * to the Window's contents.
     * @param content the array of ColorStrings to add
     */
    public void add(ColorString[] content)
        {contents.add(ColorSet.toColorSet(content));}
    
    /**
     * Sets the line of the Window's contents at the index to the provided
     * String, converted into a ColorSet.
     * @param index the line of the Window's contents to replace
     * @param content the String that will be set at the line
     */
    public void set(int index, String content)
        {contents.set(index, new ColorSet(content));}
    
    /**
     * Sets the line of the Window's contents at the index to the provided
     * ColorString, converted into a ColorSet.
     * @param index the line of the Window's contents to replace
     * @param content the ColorString that will be set at the line
     */
    public void set(int index, ColorString content)
        {contents.set(index, new ColorSet(content));}
    
    /**
     * Sets the line of the Window's contents at the index to the provided array
     * of ColorChars, converted into a ColorSet.
     * @param index the line of the Window's contents to replace
     * @param content the array of ColorChars that will be set at the line
     */
    public void set(int index, ColorChar[] content)
        {contents.set(index, new ColorSet(content));}
    
    /**
     * Sets the line of the Window's contents at the index to the provided array
     * of ColorStrings, converted into a ColorSet.
     * @param index the line of the Window's contents to replace
     * @param content the array of ColorStrings that will be set at the line
     */
    public void set(int index, ColorString[] content)
        {contents.set(index, ColorSet.toColorSet(content));}
    
    /**
     * Adds a separator associated with the provided Line.
     * @param separator the Line to add as a separator
     */
    public void addSeparator(Line separator)
    {
        contents.add(null);
        separators.add(separator);
    }
    
    /**
     * Sets the separator at the given index to the provided Line.
     * @param index the index to update
     * @param separator the Line to set as the new separator
     */
    public void setSeparator(int index, Line separator)
    {
        if (index >= separators.size() || index < 0)
            return;
        
        separators.set(index, separator);
    }
}