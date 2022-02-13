package maugrift.apwt.windows;

import maugrift.apwt.display.Display;
import maugrift.apwt.glyphs.ColorString;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A left-aligned {@link Window} with the ability to use multicolored {@link maugrift.apwt.glyphs.ColorString
 * ColorStrings}.
 *
 * @author Maugrift
 */
public class AlignedWindow extends CoordWindow
{
    /**
     * The x value of right edge of the {@link AlignedWindow}, including its {@link Border}. Stored as it is
     * recalculated on each repaint, yet hard to calculate on its own.
     */
    private int right;

    /**
     * The y value of the bottom edge of the {@link AlignedWindow}, including its {@link Border}. Stored as it is
     * recalculated on each repaint, yet hard to calculate on its own.
     */
    private int bottom;

    /**
     * The separators used to divide the {@link AlignedWindow} at each null content.
     */
    private List<Line> separators;

    /**
     * Creates an {@link AlignedWindow} with all fields defined.
     *
     * @param display    the {@link Window}'s {@link Display}
     * @param contents   the {@link Window}'s contents
     * @param x          the {@link Window}'s x coordinate
     * @param y          the {@link Window}'s y coordinate
     * @param border     the {@link Window}'s {@link Border}
     * @param separators the {@link Window}'s separators
     */
    public AlignedWindow(Display display, List<ColorString> contents, int x, int y, Border border,
                         List<Line> separators)
    {
        super(display, border, contents, x, y);
        this.separators = separators;
    }

    /**
     * Creates an {@link AlignedWindow} from another {@link AlignedWindow}.
     *
     * @param copying the {@link AlignedWindow} to copy
     */
    public AlignedWindow(AlignedWindow copying)
    {
        this(copying.getDisplay(), new ArrayList<>(copying.getContents()), copying.getX(), copying.getY(),
                copying.getBorder(), copying.separators);
    }

    /**
     * Creates an {@link AlignedWindow} with all fields defined.
     *
     * @param display    the {@link Window}'s {@link Display}
     * @param x          the {@link Window}'s x coordinate
     * @param y          the {@link Window}'s y coordinate
     * @param border     the {@link Window}'s {@link Border}
     * @param separators the {@link Window}'s separators
     */
    public AlignedWindow(Display display, int x, int y, Border border, List<Line> separators)
    {
        this(display, new ArrayList<>(), x, y, border, separators);
    }

    /**
     * Creates an {@link AlignedWindow} with no separators.
     *
     * @param display  the {@link Window}'s {@link Display}
     * @param contents the {@link Window}'s contents
     * @param x        the {@link Window}'s x coordinate
     * @param y        the {@link Window}'s y coordinate
     * @param border   the {@link Window}'s {@link Border}
     */
    public AlignedWindow(Display display, List<ColorString> contents, int x, int y, Border border)
    {
        this(display, contents, x, y, border, null);
    }

    /**
     * Creates an {@link AlignedWindow} with a default border and no separators.
     *
     * @param display  the {@link Window}'s {@link Display}
     * @param contents the {@link Window}'s contents
     * @param x        the {@link Window}'s x coordinate
     * @param y        the {@link Window}'s y coordinate
     */
    public AlignedWindow(Display display, List<ColorString> contents, int x, int y)
    {
        this(display, contents, x, y, new Border(1));
    }

    /**
     * Creates an {@link AlignedWindow} with no separators.
     *
     * @param display the {@link Window}'s {@link Display}
     * @param x       the {@link Window}'s x coordinate
     * @param y       the {@link Window}'s y coordinate
     * @param border  the {@link Window}'s {@link Border}
     */
    public AlignedWindow(Display display, int x, int y, Border border)
    {
        this(display, x, y, border, new LinkedList<>());
    }

    /**
     * Creates an {@link AlignedWindow} with a default border and no separators.
     *
     * @param display the {@link Window}'s {@link Display}
     * @param x       the {@link Window}'s x coordinate
     * @param y       the {@link Window}'s y coordinate
     */
    public AlignedWindow(Display display, int x, int y)
    {
        this(display, x, y, new Border(1));
    }

    @Override
    public void display()
    {
        if (getContents() == null || getContents().isEmpty())
        {
            return;
        }

        try
        {
            if (!getDisplay().contains(getX() - 1, getY() - 1))
            {
                throw new IndexOutOfBoundsException(
                        "Top left coordinates must be >= 1; were " + getX() + " and " + getY());
            }

            if (getContents() == null || getContents().isEmpty())
            {
                throw new IllegalArgumentException("At least 1 line of text must be provided");
            }

            int nBlocks = 1;

            for (ColorString line : getContents())
            {
                if (line == null)
                {
                    nBlocks++;
                }
            }

            List<ColorString>[] blocks = new ArrayList[nBlocks];

            int curBlock = 0;
            blocks[0] = new ArrayList<>();

            for (ColorString line : getContents())
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

            int curLine = getY();
            int curIndent = getX();
            int overallMaxLength = 0;
            int curMaxLines = blocks[0].size();
            int overallLines = blocks[0].size();
            int[] textX = new int[nBlocks];
            int[] textY = new int[nBlocks];

            int[] endX, endY;
            if (hasSeparators())
            {
                endX = new int[separators.size() * 2];
                endY = new int[separators.size() * 2];
            }
            else
            {
                endX = null;
                endY = null;
            }

            for (int block = 0; block < blocks.length; block++)
            {
                textX[block] = curIndent;
                textY[block] = curLine;

                int curMaxLength = 0;
                for (ColorString line : blocks[block])
                {
                    if (line.getCharacters().size() > curMaxLength)
                    {
                        curMaxLength = line.getCharacters().size();
                    }
                }

                curMaxLength += curIndent - getX();

                if (curMaxLength > overallMaxLength)
                {
                    overallMaxLength = curMaxLength;
                }

                blockCheck:
                if (blocks[block].size() > curMaxLines)
                {
                    overallLines -= curMaxLines;
                    curMaxLines = blocks[block].size();
                    overallLines += curMaxLines;

                    if (separators == null)
                    {
                        break blockCheck;
                    }

                    int checkingBlock = block - 1;
                    while (separators.size() - 1 >= checkingBlock && checkingBlock >= 0 && separators.get(
                            checkingBlock) != null && !separators.get(checkingBlock).horizontal)
                    {
                        endY[checkingBlock * 2 + 1] = curLine + curMaxLines;
                        checkingBlock--;
                    }
                }

                if (block == blocks.length - 1)
                {
                    break;
                }

                if (hasSeparators(block + 1) && separators.get(block) != null)
                {
                    if (separators.get(block).horizontal)
                    {
                        curIndent = getX();

                        endX[block * 2] = getX() - 1;
                        endY[block * 2] = curLine + curMaxLines;
                        endX[block * 2 + 1] = curIndent + overallMaxLength;
                        endY[block * 2 + 1] = curLine + curMaxLines;

                        curLine += curMaxLines + 1;
                        curMaxLines = blocks[block + 1].size();
                        overallLines += blocks[block + 1].size() + 1;
                    }
                    else
                    {
                        curIndent = getX() + curMaxLength + 1;

                        endX[block * 2] = curIndent - 1;
                        endY[block * 2] = curLine - 1;
                        endX[block * 2 + 1] = curIndent - 1;
                        endY[block * 2 + 1] = curLine + curMaxLines;
                    }
                }
                else
                {
                    curIndent = getX();
                    curLine += curMaxLines + 1;
                    curMaxLines = blocks[block + 1].size();
                    overallLines += blocks[block + 1].size() + 1;
                }
            }

            right = getX() + overallLines;
            bottom = getY() + overallMaxLength;

            if (isBordered())
            {
                getDisplay().drawBorder(getX() - 1, getY() - 1, right, bottom, getBorder());
            }
            else
            {
                right--;
                bottom--;
            }

            if (hasSeparators())
            {
                // Must be done as 2 loops so that vertical separators can
                // overwrite horizontal ones

                for (int separator = 0; separator < separators.size(); separator++)
                {
                    if (separators.size() - 1 >= separator && separators.get(separator) != null && separators.get(
                            separator).horizontal)
                    {
                        getDisplay().drawLine(endX[separator * 2], endY[separator * 2], getX() + overallMaxLength,
                                endY[separator * 2 + 1], separators.get(separator));
                    }
                }

                for (int separator = 0; separator < separators.size(); separator++)
                {
                    if (separators.size() - 1 >= separator && separators.get(separator) != null && !separators.get(
                            separator).horizontal)
                    {
                        getDisplay().drawLine(endX[separator * 2], endY[separator * 2], endX[separator * 2 + 1],
                                endY[separator * 2 + 1], separators.get(separator));
                    }
                }
            }

            for (int block = 0; block < nBlocks; block++)
            {
                getDisplay().write(textX[block], textY[block],
                        blocks[block].toArray(new ColorString[blocks[block].size()]));
            }
        }
        catch (IllegalArgumentException | IndexOutOfBoundsException e)
        {
            // Do not print the Window if exceptions are encountered
            return;
        }
    }

    /**
     * Returns the x coordinate of the right side of the {@link AlignedWindow}. For best accuracy when drawing
     * subsequent windows based on this, call {@link #display()} before calling this method.
     *
     * @return the x coordinate of the right side of the {@link AlignedWindow}
     */
    public int getRight()
    {
        return right;
    }

    /**
     * Returns the y coordinate of the bottom of the {@link AlignedWindow}. For best accuracy when drawing
     * subsequent windows based on this, call {@link #display()} before calling this method.
     *
     * @return the y coordinate of the bottom of the {@link AlignedWindow}
     */
    public int getBottom()
    {
        return bottom;
    }

    /**
     * Returns the List of Lines used to separate the {@link AlignedWindow}.
     *
     * @return the List of Lines used to separate the {@link AlignedWindow}
     */
    public List<Line> getSeparators()
    {
        return separators;
    }

    /**
     * Returns true if the {@link AlignedWindow} has separators.
     *
     * @return true if the {@link AlignedWindow}'s List of separators is not null or empty
     */
    public boolean hasSeparators()
    {
        return hasSeparators(1);
    }

    /**
     * Returns true if the {@link AlignedWindow} has at least the given number of separators.
     *
     * @param amount the amount of separators required for the method to return true
     * @return true if the {@link AlignedWindow}'s List of separators has at least the given number of separators in it
     */
    public boolean hasSeparators(int amount)
    {
        return separators != null && separators.size() >= amount;
    }

    /**
     * Adds a separator associated with the provided {@link Line}.
     *
     * @param separator the {@link Line} to add as a separator
     */
    public void addSeparator(Line separator)
    {
        getContents().add(null);
        separators.add(separator);
    }

    /**
     * Sets the separator at the given index to the provided {@link Line}.
     *
     * @param index     the index to update
     * @param separator the {@link Line} to set as the new separator
     */
    public void setSeparator(int index, Line separator)
    {
        if (index >= separators.size() || index < 0)
        {
            throw new IndexOutOfBoundsException("Index must be between 0 and " + separators.size());
        }

        separators.set(index, separator);
    }
}
