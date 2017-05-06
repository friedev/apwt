package corelib.display.windows;

import corelib.display.glyphs.ColorChar;
import corelib.display.glyphs.ColorSet;
import corelib.display.glyphs.ColorString;
import corelib.display.Display;
import java.util.ArrayList;
import java.util.List;
import squidpony.squidmath.Coord;

/**
 * A left-aligned {@link Window} with the ability to use multicolored
 * {@link corelib.display.glyphs.ColorSet ColorSets}.
 */
public class AlignedWindow extends Window<ColorSet>
{
    /** The x value at which the first content is written. */
    private int x;
    
    /** The y value at which the first content is written. */
    private int y;
    
    /** The separators used to divide the {@link AlignedWindow}at each null content. */
    private List<Line> separators;
    
    /**
     * Creates an {@link AlignedWindow}with all fields defined.
     * @param display the {@link Window}'s {@link corelib.display.Display}
     * @param contents the {@link Window}'s contents
     * @param x the {@link Window}'s x coordinate
     * @param y the {@link Window}'s y coordinate
     * @param border the {@link Window}'s {@link Border}
     * @param separators the {@link Window}'s separators
     */
    public AlignedWindow(Display display, List<ColorSet> contents, int x, int y,
            Border border, List<Line> separators)
    {
        super(display, border, contents);
        this.x          = x;
        this.y          = y;
        this.separators = separators;
    }
    
    public AlignedWindow(AlignedWindow copying)
    {
        this(copying.getDisplay(), copying.getContents(), copying.x, copying.y,
                copying.getBorder(), copying.separators);
    }
    
    /**
     * Creates an {@link AlignedWindow}with all fields defined.
     * @param display the {@link Window}'s {@link corelib.display.Display}
     * @param x the {@link Window}'s x coordinate
     * @param y the {@link Window}'s y coordinate
     * @param border the {@link Window}'s {@link Border}
     * @param separators the {@link Window}'s separators
     */
    public AlignedWindow(Display display, int x, int y, Border border,
            List<Line> separators)
        {this(display, new ArrayList<>(), x, y, border, separators);}
    
    /**
     * Creates an {@link AlignedWindow}with no separators.
     * @param display the {@link Window}'s {@link corelib.display.Display}
     * @param contents the {@link Window}'s contents
     * @param x the {@link Window}'s x coordinate
     * @param y the {@link Window}'s y coordinate
     * @param border the {@link Window}'s {@link Border}
     */
    public AlignedWindow(Display display, List<ColorSet> contents, int x, int y,
            Border border)
        {this(display, contents, x, y, border, null);}
    
    /**
     * Creates an {@link AlignedWindow}with a default border and no separators.
     * @param display the {@link Window}'s {@link corelib.display.Display}
     * @param contents the {@link Window}'s contents
     * @param x the {@link Window}'s x coordinate
     * @param y the {@link Window}'s y coordinate
     */
    public AlignedWindow(Display display, List<ColorSet> contents, int x, int y)
        {this(display, contents, x, y, new Border(1));}
    
    /**
     * Creates an {@link AlignedWindow}with no separators.
     * @param display the {@link Window}'s {@link corelib.display.Display}
     * @param x the {@link Window}'s x coordinate
     * @param y the {@link Window}'s y coordinate
     * @param border the {@link Window}'s {@link Border}
     */
    public AlignedWindow(Display display, int x, int y, Border border)
        {this(display, x, y, border, null);}
    
    /**
     * Creates an {@link AlignedWindow}with a default border and no separators.
     * @param display the {@link Window}'s {@link corelib.display.Display}
     * @param x the {@link Window}'s x coordinate
     * @param y the {@link Window}'s y coordinate
     */
    public AlignedWindow(Display display, int x, int y)
        {this(display, x, y, new Border(1));}

    @Override
    public void display()
    {
        if (getContents() == null || getContents().isEmpty())
            return;
        
        ColorSet[] output = getContents().toArray(
                new ColorSet[getContents().size()]);
        
        try
        {
            if (isBordered())
            {
                if (hasSeparators())
                {
                    WindowBuilder.printBoxed(getDisplay(), output, y, x,
                            getBorder(),
                            separators.toArray(new Line[separators.size()]));
                }
                else
                {
                    WindowBuilder.printBoxed(getDisplay(), output, y, x,
                            getBorder());
                }
            }
            else
            {
                getDisplay().write(Coord.get(x, y), output);
            }
        }
        catch (IllegalArgumentException | IndexOutOfBoundsException e)
        {
            // Do not print the Window if exceptions are encountered
            return;
        }
    }
    
    /**
     * Returns the x coordinate of the {@link AlignedWindow}.
     * @return the x coordinate of the {@link AlignedWindow}
     */
    public int getX()
        {return x;}
    
    /**
     * Returns the y coordinate of the {@link AlignedWindow}.
     * @return the y coordinate of the {@link AlignedWindow}
     */
    public int getY()
        {return y;}
    
    /**
     * Returns the List of Lines used to separate the {@link AlignedWindow}.
     * @return the List of Lines used to separate the {@link AlignedWindow}
     */
    public List<Line> getSeparators()
        {return separators;}
    
    /**
     * Returns true if the {@link AlignedWindow}has separators.
     * @return true if the {@link AlignedWindow}'s List of separators is not null
     */
    public boolean hasSeparators()
        {return separators != null && !separators.isEmpty();}
    
    /**
     * Sets the {@link AlignedWindow}'s x coordinate to the specified value.
     * @param x the new x coordinate of the {@link AlignedWindow}
     */
    public void setX(int x)
        {this.x = x;}
    
    /**
     * Sets the {@link AlignedWindow}'s y coordinate to the specified value.
     * @param y the new y coordinate of the {@link AlignedWindow}
     */
    public void setY(int y)
        {this.y = y;}
    
    /**
     * Moves the {@link AlignedWindow} to the specified coordinates.
     * @param x the new x coordinate of the {@link AlignedWindow}
     * @param y the new y coordinate of the {@link AlignedWindow}
     */
    public void moveTo(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Converts the provided String into a
     * {@link corelib.display.glyphs.ColorSet} and adds it to the
     * {@link AlignedWindow}'s contents.
     * @param content content the String to add
     * @return this for convenient chaining
     */
    public AlignedWindow add(String content)
        {getContents().add(new ColorSet(content)); return this;}
    
    /**
     * Converts the provided ColorString into a
     * {@link corelib.display.glyphs.ColorSet} and adds it to the
     * {@link AlignedWindow}'s contents.
     * @param content the {@link corelib.display.glyphs.ColorString} to add
     * @return this for convenient chaining
     */
    public AlignedWindow add(ColorString content)
        {getContents().add(new ColorSet(content)); return this;}
    
    /**
     * Converts the provided array of ColorChars into a {@link corelib.display.glyphs.ColorSet} and adds it to
     * the {@link AlignedWindow}'s contents.
     * @param content the array of
     * {@link corelib.display.glyphs.ColorChar ColorChars} to add
     * @return this for convenient chaining
     */
    public AlignedWindow add(ColorChar[] content)
        {getContents().add(new ColorSet(content)); return this;}
    
    /**
     * Converts the provided array of
     * {@link corelib.display.glyphs.ColorString ColorStrings} into a
     * {@link corelib.display.glyphs.ColorSet} and adds it to the
     * {@link AlignedWindow}'s contents.
     * @param content the array of
     * {@link corelib.display.glyphs.ColorString ColorStrings} to add
     * @return this for convenient chaining
     */
    public AlignedWindow add(ColorString[] content)
        {getContents().add(ColorSet.toColorSet(content)); return this;}
    
    /**
     * Sets the line of the {@link AlignedWindow}'s contents at the index to the
     * provided String, converted into a
     * {@link corelib.display.glyphs.ColorSet}.
     * @param index the line of the {@link AlignedWindow}'s contents to replace
     * @param content the String that will be set at the line
     */
    public void set(int index, String content)
        {getContents().set(index, new ColorSet(content));}
    
    /**
     * Sets the line of the {@link AlignedWindow}'s contents at the index to the
     * provided {@link corelib.display.glyphs.ColorString}, converted into a
     * {@link corelib.display.glyphs.ColorSet}.
     * @param index the line of the {@link AlignedWindow}'s contents to replace
     * @param content the {@link corelib.display.glyphs.ColorString} that will
     * be set at the line
     */
    public void set(int index, ColorString content)
        {getContents().set(index, new ColorSet(content));}
    
    /**
     * Sets the line of the {@link AlignedWindow}'s contents at the index to the
     * provided array of {@link corelib.display.glyphs.ColorChar ColorChars},
     * converted into a {@link corelib.display.glyphs.ColorSet}.
     * @param index the line of the {@link AlignedWindow}'s contents to replace
     * @param content the array of
     * {@link corelib.display.glyphs.ColorChar ColorChars} that will be set at
     * the line
     */
    public void set(int index, ColorChar[] content)
        {getContents().set(index, new ColorSet(content));}
    
    /**
     * Sets the line of the {@link AlignedWindow}'s contents at the index to the
     * provided array of
     * {@link corelib.display.glyphs.ColorString ColorStrings}, converted into a
     * {@link corelib.display.glyphs.ColorSet}.
     * @param index the line of the {@link AlignedWindow}'s contents to replace
     * @param content the array of
     * {@link corelib.display.glyphs.ColorString ColorStrings} that will be set
     * at the line
     */
    public void set(int index, ColorString[] content)
        {getContents().set(index, ColorSet.toColorSet(content));}
    
    /**
     * Inserts the given String into the given index of the
     * {@link AlignedWindow}'s contents.
     * @param index the index at which to insert the String
     * @param content the String to insert
     */
    public void insert(int index, String content)
        {insert(index, new ColorSet(content));}
    
    /**
     * Inserts the given {@link corelib.display.glyphs.ColorString} into the
     * given index of the {@link AlignedWindow}'s contents.
     * @param index the index at which to insert the
     * {@link corelib.display.glyphs.ColorString}
     * @param content the {@link corelib.display.glyphs.ColorString} to insert
     */
    public void insert(int index, ColorString content)
        {insert(index, new ColorSet(content));}
    
    /**
     * Inserts the given array of
     * {@link corelib.display.glyphs.ColorChar ColorChars} into the given index
     * of the {@link AlignedWindow}'s contents.
     * @param index the index at which to insert the array of
     * {@link corelib.display.glyphs.ColorChar ColorChars}
     * @param content the array of
     * {@link corelib.display.glyphs.ColorChar ColorChars} to insert
     */
    public void insert(int index, ColorChar[] content)
        {insert(index, new ColorSet(content));}
    
    /**
     * Inserts the given {@link corelib.display.glyphs.ColorSet} into the given
     * index of the {@link AlignedWindow}'s contents.
     * @param index the index at which to insert the
     * {@link corelib.display.glyphs.ColorSet}
     * @param content the * {@link corelib.display.glyphs.ColorSet} to insert
     */
    public void insert(int index, ColorSet content)
        {getContents().add(index, content);}
    
    /**
     * Adds a separator associated with the provided {@link Line}.
     * @param separator the {@link Line} to add as a separator
     */
    public void addSeparator(Line separator)
    {
        getContents().add(null);
        separators.add(separator);
    }
    
    /**
     * Sets the separator at the given index to the provided {@link Line}.
     * @param index the index to update
     * @param separator the {@link Line} to set as the new separator
     */
    public void setSeparator(int index, Line separator)
    {
        if (index >= separators.size() || index < 0)
            throw new IndexOutOfBoundsException("Index must be between 0 and "
                    + separators.size());
        
        separators.set(index, separator);
    }
}