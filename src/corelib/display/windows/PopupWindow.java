package corelib.display.windows;

import corelib.display.glyphs.ColorSet;
import corelib.display.Display;
import java.util.ArrayList;
import java.util.List;
import squidpony.squidmath.Coord;

/** A centered {@link Window} meant for temporary use as a popup. */
public class PopupWindow extends Window
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
    public PopupWindow(Display display, List<ColorSet> contents, int y,
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
        this(copying.getDisplay(), new ArrayList<>(copying.getContents()),
                copying.y, copying.getBorder(), copying.separator);
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
    public PopupWindow(Display display, List<ColorSet> contents, int y,
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
     * @param contents the {@link Window}'s contents
     * @param y the {@link Window}'s y coordinate
     */
    public PopupWindow(Display display, List<ColorSet> contents, int y)
        {this(display, contents, y, new Border(1));}
    
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
        
        try
        {
            if (getContents() == null || getContents().isEmpty())
                throw new IllegalArgumentException(
                        "At least 1 line of text must be provided");

            if (!getDisplay().containsY(y - 1) ||
                    !getDisplay().containsY(y + getContents().size()))
                throw new IndexOutOfBoundsException("Top line value must be "
                        + "between 1 and " + (getDisplay().getCharHeight() - 1)
                                + "; was " + y);

            int maxLength = 0;
            for (ColorSet line: getContents())
                if (line != null && line.length() > maxLength)
                    maxLength = line.length();

            if (!getDisplay().containsX(maxLength + 2))
                throw new IndexOutOfBoundsException("Text is too long for the "
                        + "display");
            
            int center = getDisplay().getCharWidth() / 2;
            int offsetRight = ((int) maxLength) / 2;
            int offsetLeft;
            if ((((double) maxLength) / 2.0) % 1.0 == 0.5)
                offsetLeft = offsetRight + 1;
            else
                offsetLeft = offsetRight;

            getDisplay().drawBorder(Coord.get(center - offsetLeft - 1, y - 1),
                    Coord.get(center + offsetRight, y + getContents().size()),
                    getBorder());

            if (separator != null)
                for (int line = 0; line < getContents().size(); line++)
                    if (getContents().get(line) == null)
                        getDisplay().drawLine(Coord.get(center - offsetLeft - 1,
                                y + line),
                                Coord.get(center + offsetRight, y + line),
                                separator);

            getDisplay().writeCenter(y, getContents().toArray(
                    new ColorSet[getContents().size()]));
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
}