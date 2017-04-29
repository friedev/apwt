package core.display;

import java.util.ArrayList;
import java.util.List;
import squidpony.squidmath.Coord;

/**
 * A class used to display text at a certain location, optionally with a border
 * or divided into sections.
 */
public class Window
{
    /** The Display on which to print the window. */
    private Display display;
    /** The contents of the Window, with each ColorSet as a different line. */
    private List<ColorSet> contents;
    /** The x value at which the first ColorSet is written. */
    private int x;
    /** The y value at which the first ColorSet is written. */
    private int y;
    /** The Border surrounding the Window; not displayed if null. */
    private Border border;
    /** The separators used to divide the Window at each null content. */
    private List<Line> separators;
    
    /**
     * Creates a Window with all fields defined.
     * @param d the Window's Display
     * @param xx the Window's x coordinate
     * @param yy the Window's y coordinate
     * @param b the Window's Border
     * @param s the Window's Separators
     */
    public Window(Display d, int xx, int yy, Border b, List<Line> s)
    {
        display    = d;
        x          = xx;
        y          = yy;
        border     = b;
        separators = s;
        contents   = new ArrayList<>();
    }
    
    /**
     * Creates a Window with no separators.
     * @param d the Window's Display
     * @param xx the Window's x coordinate
     * @param yy the Window's y coordinate
     * @param b the Window's Border
     */
    public Window(Display d, int xx, int yy, Border b)
        {this(d, xx, yy, b, null);}
    
    /**
     * Creates a borderless Window with no separators.
     * @param d the Window's Display
     * @param xx the Window's x coordinate
     * @param yy the Window's y coordinate
     */
    public Window(Display d, int xx, int yy)
        {this(d, xx, yy, new Border(1), null);}

    /** Prints the Window to its Display using WindowBuilder. */
    public void displayOutput()
    {
        if (contents == null || contents.isEmpty())
            return;
        
        ColorSet[] output = contents.toArray(new ColorSet[contents.size()]);
        
        if (border != null)
        {
            WindowBuilder.printBoxed(display, output, y, x, border,
                    separators.toArray(new Line[separators.size()]));
        }
        else
        {
            display.write(output, Coord.get(x, y));
        }
    }
    
    /**
     * Returns the contents of the Window as a List of ColorSets.
     * @return the contents of the Window as a List of ColorSets
     */
    public List<ColorSet> getContents()
        {return contents;}
    
    /**
     * Returns the Display used by the Window.
     * @return the Display used by the Window
     */
    public Display getDisplay()
        {return display;}
    
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
     * Returns the Window's Border.
     * @return the Window's Border
     */
    public Border getBorder()
        {return border;}
    
    /**
     * Returns the List of Lines used to separate the Window.
     * @return the List of Lines used to separate the Window
     */
    public List<Line> getSeparators()
        {return separators;}
    
    /**
     * Returns true if the Window has a Border.
     * @return true if the Window's Border is not null
     */
    public boolean isBordered()
        {return border != null;}
    
    /**
     * Returns true if the Window has separators.
     * @return true if the Window's List of separators is not null
     */
    public boolean hasSeparators()
        {return separators != null;}
    
    /**
     * Sets the Window's x coordinate to the specified value.
     * @param xx the new x coordinate of the Window
     */
    public void setX(int xx)
        {x = xx;}
    
    /**
     * Sets the Window's y coordinate to the specified value.
     * @param yy the new y coordinate of the Window
     */
    public void setY(int yy) {y = yy;}
    
    /**
     * Moves the Window to the specified coordinates.
     * @param xx the new x coordinate of the Window
     * @param yy the new y coordinate of the Window
     */
    public void moveTo(int xx, int yy)
    {
        x = xx;
        y = yy;
    }
    
    /**
     * Converts the provided String into a ColorSet and adds it to the Window's
     * contents.
     * @param s the String to add
     */
    public void add(String s)
        {contents.add(new ColorSet(s));}
    
    /**
     * Converts the provided ColorString into a ColorSet and adds it to the
     * Window's contents.
     * @param s the ColorString to add
     */
    public void add(ColorString s) {contents.add(new ColorSet(s));}
    
    /**
     * Converts the provided array of ColorChars into a ColorSet and adds it to
     * the Window's contents.
     * @param c the array of ColorChars to add
     */
    public void add(ColorChar[] c)  {contents.add(new ColorSet(c));}
    
    /**
     * Converts the provided array of ColorStrings into a ColorSet and adds it
     * to the Window's contents.
     * @param s the array of ColorStrings to add
     */
    public void add(ColorString[] s) {contents.add(ColorSet.toColorSet(s));}
    
    /**
     * Adds the provided ColorSet to the Window's contents.
     * @param s the ColorSet to add
     */
    public void add(ColorSet s) {contents.add(s);}
    
    /**
     * Sets the line of the Window's contents at i to the provided String,
     * converted into a ColorSet.
     * @param i the line of the Window's contents to replace
     * @param s the String that will be set at the line
     */
    public void set(int i, String s)
        {contents.set(i, new ColorSet(s));}
    
    /**
     * Sets the line of the Window's contents at i to the provided ColorString,
     * converted into a ColorSet.
     * @param i the line of the Window's contents to replace
     * @param s the ColorString that will be set at the line
     */
    public void set(int i, ColorString s)
        {contents.set(i, new ColorSet(s));}
    
    /**
     * Sets the line of the Window's contents at i to the provided array of
     * ColorChars, converted into a ColorSet.
     * @param i the line of the Window's contents to replace
     * @param c the array of ColorChars that will be set at the line
     */
    public void set(int i, ColorChar[] c)
        {contents.set(i, new ColorSet(c));}
    
    /**
     * Sets the line of the Window's contents at i to the provided array of
     * ColorStrings, converted into a ColorSet.
     * @param i the line of the Window's contents to replace
     * @param s the array of ColorStrings that will be set at the line
     */
    public void set(int i, ColorString[] s)
        {contents.set(i, ColorSet.toColorSet(s));}
    
    /**
     * Sets the line of the Window's contents at i to the provided ColorSet.
     * @param i the line of the Window's contents to replace
     * @param s the ColorSet that will be set at the line
     */
    public void set(int i, ColorSet s)
        {contents.set(i, s);}
    
    /** Adds a separator with no associated Line. */
    public void addSeparator()
        {contents.add(null);}
    
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
     * @param i the index to update
     * @param separator the Line to set as the new separator
     */
    public void setSeparator(int i, Line separator)
    {
        if (i >= separators.size() || i < 0)
            return;
        
        separators.set(i, separator);
    }
}