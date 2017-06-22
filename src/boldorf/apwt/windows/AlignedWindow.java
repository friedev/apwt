package boldorf.apwt.windows;

import boldorf.apwt.glyphs.ColorString;
import boldorf.apwt.Display;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import squidpony.squidmath.Coord;

/**
 * A left-aligned {@link Window} with the ability to use multicolored
 * {@link boldorf.apwt.glyphs.ColorString ColorStrings}.
 */
public class AlignedWindow extends CoordWindow
{
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
     * @param display the {@link Window}'s {@link boldorf.apwt.Display}
     * @param contents the {@link Window}'s contents
     * @param location the {@link Window}'s location
     * @param border the {@link Window}'s {@link Border}
     * @param separators the {@link Window}'s separators
     */
    public AlignedWindow(Display display, List<ColorString> contents,
            Coord location, Border border, List<Line> separators)
    {
        super(display, border, contents, location);
        this.separators = separators;
    }
    
    /**
     * Creates an {@link AlignedWindow} from another {@link AlignedWindow}.
     * @param copying the {@link AlignedWindow} to copy
     */
    public AlignedWindow(AlignedWindow copying)
    {
        this(copying.getDisplay(), new ArrayList<>(copying.getContents()),
                copying.getLocation(), copying.getBorder(), copying.separators);
    }
    
    /**
     * Creates an {@link AlignedWindow} with all fields defined.
     * @param display the {@link Window}'s {@link boldorf.apwt.Display}
     * @param location the {@link Window}'s location
     * @param border the {@link Window}'s {@link Border}
     * @param separators the {@link Window}'s separators
     */
    public AlignedWindow(Display display, Coord location, Border border,
            List<Line> separators)
        {this(display, new ArrayList<>(), location, border, separators);}
    
    /**
     * Creates an {@link AlignedWindow} with no separators.
     * @param display the {@link Window}'s {@link boldorf.apwt.Display}
     * @param contents the {@link Window}'s contents
     * @param location the {@link Window}'s location
     * @param border the {@link Window}'s {@link Border}
     */
    public AlignedWindow(Display display, List<ColorString> contents,
            Coord location, Border border)
        {this(display, contents, location, border, null);}
    
    /**
     * Creates an {@link AlignedWindow} with a default border and no separators.
     * @param display the {@link Window}'s {@link boldorf.apwt.Display}
     * @param contents the {@link Window}'s contents
     * @param location the {@link Window}'s location
     */
    public AlignedWindow(Display display, List<ColorString> contents,
            Coord location)
        {this(display, contents, location, new Border(1));}
    
    /**
     * Creates an {@link AlignedWindow} with no separators.
     * @param display the {@link Window}'s {@link boldorf.apwt.Display}
     * @param location the {@link Window}'s location
     * @param border the {@link Window}'s {@link Border}
     */
    public AlignedWindow(Display display, Coord location, Border border)
        {this(display,location, border, new LinkedList<>());}
    
    /**
     * Creates an {@link AlignedWindow} with a default border and no separators.
     * @param display the {@link Window}'s {@link boldorf.apwt.Display}
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
            if (!getDisplay().contains(getLocation().subtract(1)))
                throw new IndexOutOfBoundsException("Top left coordinates must "
                        + "be >= 1; were " + getLocation().x + " and "
                        + getLocation().y);

            if (getContents() == null || getContents().isEmpty())
                throw new IllegalArgumentException(
                        "At least 1 line of text must be provided");

            int nBlocks = 1;

            for (ColorString line: getContents())
                if (line == null)
                    nBlocks++;

            ArrayList<ColorString>[] blocks = new ArrayList[nBlocks]; 

            int curBlock = 0;
            blocks[0] = new ArrayList<>();

            for (ColorString line: getContents())
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

            int curLine          = getLocation().y;
            int curIndent        = getLocation().x;
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
                for (ColorString line: blocks[block])
                    if (line.getSet().size() > curMaxLength)
                        curMaxLength = line.getSet().size();

                curMaxLength += curIndent - getLocation().x;

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
                        curIndent = getLocation().x;

                        endpoints[block * 2] = Coord.get(getLocation().x - 1,
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
                        curIndent = getLocation().x + curMaxLength + 1;

                        endpoints[block * 2] = Coord.get(curIndent - 1,
                                curLine - 1);
                        endpoints[block * 2 + 1] = Coord.get(curIndent - 1,
                                curLine + curMaxLines);
                    }
                }
                else
                {
                    curIndent = getLocation().x;
                    curLine += curMaxLines + 1;
                    curMaxLines = blocks[block + 1].size();
                    overallLines += blocks[block + 1].size() + 1;
                }
            }
            
            bottomRight = getLocation().add(Coord.get(overallMaxLength,
                    overallLines));

            if (isBordered())
            {
                getDisplay().drawBorder(getLocation().subtract(1), bottomRight,
                        getBorder());
            }
            else
            {
                bottomRight = bottomRight.subtract(1);
            }

            if (hasSeparators())
            {
                // Must be done as 2 loops so that vertical separators can
                // overwrite horizontal ones
                
                for (int separator = 0; separator < separators.size();
                        separator++)
                {
                    if (separators.size() - 1 >= separator &&
                            separators.get(separator) != null &&
                            separators.get(separator).horizontal)
                    {
                        getDisplay().drawLine(endpoints[separator * 2],
                            endpoints[separator * 2 + 1]
                                .setX(getLocation().x + overallMaxLength),
                            separators.get(separator));
                    }
                }
                
                for (int separator = 0; separator < separators.size();
                        separator++)
                {
                    if (separators.size() - 1 >= separator &&
                            separators.get(separator) != null &&
                            !separators.get(separator).horizontal)
                    {
                        getDisplay().drawLine(endpoints[separator * 2],
                            endpoints[separator * 2 + 1],
                            separators.get(separator));
                    }
                }
            }

            for (int block = 0; block < nBlocks; block++)
                getDisplay().write(textPoints[block], blocks[block].toArray(
                        new ColorString[blocks[block].size()]));
        }
        catch (IllegalArgumentException | IndexOutOfBoundsException e)
        {
            // Do not print the Window if exceptions are encountered
            return;
        }
    }
    
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