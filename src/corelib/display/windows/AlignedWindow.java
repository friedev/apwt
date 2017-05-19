package corelib.display.windows;

import corelib.display.glyphs.ColorChar;
import corelib.display.glyphs.ColorSet;
import corelib.display.glyphs.ColorString;
import corelib.display.Display;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import squidpony.squidmath.Coord;

/**
 * A left-aligned {@link Window} with the ability to use multicolored
 * {@link corelib.display.glyphs.ColorSet ColorSets}.
 */
public class AlignedWindow extends Window<ColorSet>
{
    /** The coordinates at which the first content is written. */
    private Coord location;
    
    /**
     * The bottom right corner of the {@link AlignedWindow}, including its
     * {@link Border}. Stored as it is recalculated on each repaint, yet hard to
     * calculate on its own.
     */
    private Coord bottomRight;
    
    /**
     * The separators used to divide the {@link AlignedWindow} at each null
     * content.
     */
    private List<Line> separators;
    
    /**
     * Creates an {@link AlignedWindow} with all fields defined.
     * @param display the {@link Window}'s {@link corelib.display.Display}
     * @param contents the {@link Window}'s contents
     * @param location the {@link Window}'s location
     * @param border the {@link Window}'s {@link Border}
     * @param separators the {@link Window}'s separators
     */
    public AlignedWindow(Display display, List<ColorSet> contents,
            Coord location, Border border, List<Line> separators)
    {
        super(display, border, contents);
        this.location = location;
        this.separators = separators;
    }
    
    /**
     * Creates an {@link AlignedWindow} from another {@link AlignedWindow}.
     * @param copying the {@link AlignedWindow} to copy
     */
    public AlignedWindow(AlignedWindow copying)
    {
        this(copying.getDisplay(), copying.getContents(), copying.location,
                copying.getBorder(), copying.separators);
    }
    
    /**
     * Creates an {@link AlignedWindow} with all fields defined.
     * @param display the {@link Window}'s {@link corelib.display.Display}
     * @param location the {@link Window}'s location
     * @param border the {@link Window}'s {@link Border}
     * @param separators the {@link Window}'s separators
     */
    public AlignedWindow(Display display, Coord location, Border border,
            List<Line> separators)
        {this(display, new ArrayList<>(), location, border, separators);}
    
    /**
     * Creates an {@link AlignedWindow} with no separators.
     * @param display the {@link Window}'s {@link corelib.display.Display}
     * @param contents the {@link Window}'s contents
     * @param location the {@link Window}'s location
     * @param border the {@link Window}'s {@link Border}
     */
    public AlignedWindow(Display display, List<ColorSet> contents,
            Coord location, Border border)
        {this(display, contents, location, border, null);}
    
    /**
     * Creates an {@link AlignedWindow} with a default border and no separators.
     * @param display the {@link Window}'s {@link corelib.display.Display}
     * @param contents the {@link Window}'s contents
     * @param location the {@link Window}'s location
     */
    public AlignedWindow(Display display, List<ColorSet> contents,
            Coord location)
        {this(display, contents, location, new Border(1));}
    
    /**
     * Creates an {@link AlignedWindow} with no separators.
     * @param display the {@link Window}'s {@link corelib.display.Display}
     * @param location the {@link Window}'s location
     * @param border the {@link Window}'s {@link Border}
     */
    public AlignedWindow(Display display, Coord location, Border border)
        {this(display,location, border, new LinkedList<>());}
    
    /**
     * Creates an {@link AlignedWindow} with a default border and no separators.
     * @param display the {@link Window}'s {@link corelib.display.Display}
     * @param location the {@link Window}'s location
     */
    public AlignedWindow(Display display, Coord location)
        {this(display, location, new Border(1));}

    @Override
    public void display()
    {
        if (getContents() == null || getContents().isEmpty())
            return;
        
        try
        {
            if (!getDisplay().contains(location.subtract(1)))
                throw new IndexOutOfBoundsException("Top left coordinates must "
                        + "be >= 1; were " + location.x + " and " + location.y);

            if (getContents() == null || getContents().isEmpty())
                throw new IllegalArgumentException(
                        "At least 1 line of text must be provided");

            int nBlocks = 1;

            for (ColorSet line: getContents())
                if (line == null)
                    nBlocks++;

            ArrayList<ColorSet>[] blocks = new ArrayList[nBlocks]; 

            int curBlock = 0;
            blocks[0] = new ArrayList<>();

            for (ColorSet line: getContents())
            {
                if (line != null)
                {
                    blocks[curBlock].add(line);
                }
                else
                {
                    curBlock++;
                    blocks[curBlock] = new ArrayList<>();
                }
            }

            int curLine          = location.y;
            int curIndent        = location.x;
            int overallMaxLength = 0;
            int curMaxLines      = blocks[0].size();
            int overallLines     = blocks[0].size();
            Coord[] textPoints   = new Coord[nBlocks];
            Coord[] endpoints    = hasSeparators() ?
                    new Coord[separators.size() * 2] : null;

            for (int block = 0; block < blocks.length; block++)
            {
                textPoints[block] = Coord.get(curIndent, curLine);

                int curMaxLength = 0;
                for (ColorSet line: blocks[block])
                    if (line.getSet().size() > curMaxLength)
                        curMaxLength = line.getSet().size();

                curMaxLength += curIndent - location.x;

                if (curMaxLength > overallMaxLength)
                    overallMaxLength = curMaxLength;

                blockCheck:
                if (blocks[block].size() > curMaxLines)
                {
                    overallLines -= curMaxLines;
                    curMaxLines = blocks[block].size();
                    overallLines += curMaxLines;

                    if (separators == null)
                        break blockCheck;

                    int checkingBlock = block - 1;
                    while (separators.size() - 1 >= checkingBlock &&
                            checkingBlock >= 0 &&
                            separators.get(checkingBlock) != null &&
                            !separators.get(checkingBlock).horizontal)
                    {
                        endpoints[checkingBlock * 2 + 1] = Coord.get(
                                endpoints[checkingBlock * 2 + 1].x,
                                curLine + curMaxLines);
                        checkingBlock--;
                    }
                }

                if (block == blocks.length - 1)
                    break;

                if (hasSeparators(block + 1) && separators.get(block) != null)
                {
                    if (separators.get(block).horizontal)
                    {
                        curIndent = location.x;

                        endpoints[block * 2] = Coord.get(location.x - 1,
                                curLine + curMaxLines);
                        endpoints[block * 2 + 1] =
                                Coord.get(curIndent + overallMaxLength,
                                        curLine + curMaxLines);

                        curLine += curMaxLines + 1;
                        curMaxLines = blocks[block + 1].size();
                        overallLines += blocks[block + 1].size() + 1;
                    }
                    else
                    {
                        curIndent = location.x + curMaxLength + 1;

                        endpoints[block * 2] = Coord.get(curIndent - 1,
                                curLine - 1);
                        endpoints[block * 2 + 1] = Coord.get(curIndent - 1,
                                curLine + curMaxLines);
                    }
                }
                else
                {
                    curIndent = location.x;
                    curLine += curMaxLines + 1;
                    curMaxLines = blocks[block + 1].size();
                    overallLines += blocks[block + 1].size() + 1;
                }
            }
            
            bottomRight = location.add(Coord.get(overallMaxLength,
                    overallLines));

            if (isBordered())
            {
                getDisplay().drawBorder(location.subtract(1), bottomRight,
                        getBorder());
            }
            else
            {
                bottomRight = bottomRight.subtract(1);
            }

            if (hasSeparators())
            {
                for (int separator = 0; separator < separators.size();
                        separator++)
                {
                    if (separators.size() - 1 >= separator &&
                            separators.get(separator) != null)
                    {
                        if (separators.get(separator).horizontal)
                        {
                            getDisplay().drawLine(endpoints[separator * 2],
                                endpoints[separator * 2 + 1]
                                        .setX(location.x + overallMaxLength),
                                separators.get(separator));
                        }
                        else
                        {
                            getDisplay().drawLine(endpoints[separator * 2],
                                endpoints[separator * 2 + 1],
                                separators.get(separator));
                        }
                    }
                }
            }

            for (int block = 0; block < nBlocks; block++)
                getDisplay().write(textPoints[block], blocks[block].toArray(
                        new ColorSet[blocks[block].size()]));
        }
        catch (IllegalArgumentException | IndexOutOfBoundsException e)
        {
            // Do not print the Window if exceptions are encountered
            return;
        }
    }
    
    /**
     * Returns the top-left coordinates of the {@link AlignedWindow}.
     * @return the top-left coordinates of the {@link AlignedWindow}
     */
    public Coord getLocation()
        {return location;}
    
    /**
     * Returns the bottom-right coordinates of the {@link AlignedWindow}. For
     * best accuracy when drawing subsequent windows based on this, call
     * {@link #display()} before calling this method.
     * @return the bottom-right coordinates of the {@link AlignedWindow}
     */
    public Coord getBottomRight()
        {return bottomRight;}
    
    /**
     * Returns the List of Lines used to separate the {@link AlignedWindow}.
     * @return the List of Lines used to separate the {@link AlignedWindow}
     */
    public List<Line> getSeparators()
        {return separators;}
    
    /**
     * Returns true if the {@link AlignedWindow} has separators.
     * @return true if the {@link AlignedWindow}'s List of separators is not
     * null or empty
     */
    public boolean hasSeparators()
        {return hasSeparators(1);}
    
    /**
     * Returns true if the {@link AlignedWindow} has at least the given number
     * of separators.
     * @param amount the amount of separators required for the method to return
     * true
     * @return true if the {@link AlignedWindow}'s List of separators has at
     * least the given number of separators in it
     */
    public boolean hasSeparators(int amount)
        {return separators != null && separators.size() >= amount;}
    
    /**
     * Moves the {@link AlignedWindow} to the specified coordinates.
     * @param location the new top-left coordinates of the {@link AlignedWindow}
     */
    public void setLocation(Coord location)
        {this.location = location;}
    
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
     * Converts the provided array of ColorChars into a
     * {@link corelib.display.glyphs.ColorSet} and adds it to the
     * {@link AlignedWindow}'s contents.
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
     * @return this for convenient chaining
     */
    public AlignedWindow addSeparator(Line separator)
    {
        getContents().add(null);
        separators.add(separator);
        return this;
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