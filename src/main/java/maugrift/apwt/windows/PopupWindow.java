package maugrift.apwt.windows;

import maugrift.apwt.display.Display;
import maugrift.apwt.glyphs.ColorString;

import java.util.ArrayList;
import java.util.List;

/**
 * A centered {@link Window} meant for temporary use as a popup.
 *
 * @author Maugrift
 */
public class PopupWindow extends Window
{
    /**
     * The separator used to divide the {@link PopupWindow} at each null content.
     */
    private Line separator;

    /**
     * Creates a {@link PopupWindow} with all fields defined.
     *
     * @param display   the {@link Window}'s {@link Display}
     * @param contents  the {@link Window}'s contents
     * @param border    the {@link Window}'s {@link Border}
     * @param separator the {@link Window}'s separator
     */
    public PopupWindow(Display display, List<ColorString> contents, Border border, Line separator)
    {
        super(display, border, contents);
        this.separator = separator;
    }

    /**
     * Creates a {@link PopupWindow} from another {@link PopupWindow}.
     *
     * @param copying the {@link PopupWindow} to copy
     */
    public PopupWindow(PopupWindow copying)
    {
        this(copying.getDisplay(), new ArrayList<>(copying.getContents()), copying.getBorder(), copying.separator);
    }

    /**
     * Creates a {@link PopupWindow} with all fields defined.
     *
     * @param display   the {@link Window}'s {@link Display}
     * @param border    the {@link Window}'s {@link Border}
     * @param separator the {@link Window}'s separator
     */
    public PopupWindow(Display display, Border border, Line separator)
    {
        this(display, new ArrayList<>(), border, separator);
    }

    /**
     * Creates a {@link PopupWindow} with no separator.
     *
     * @param display  the {@link Window}'s {@link Display}
     * @param contents the {@link Window}'s contents
     * @param border   the {@link Window}'s {@link Border}
     */
    public PopupWindow(Display display, List<ColorString> contents, Border border)
    {
        this(display, contents, border, null);
    }

    /**
     * Creates a {@link PopupWindow} with no separator.
     *
     * @param display the {@link Window}'s {@link Display}
     * @param border  the {@link Window}'s {@link Border}
     */
    public PopupWindow(Display display, Border border)
    {
        this(display, new ArrayList<>(), border);
    }

    /**
     * Creates a {@link PopupWindow} with a default border and no separator.
     *
     * @param display  the {@link Window}'s {@link Display}
     * @param contents the {@link Window}'s contents
     */
    public PopupWindow(Display display, List<ColorString> contents)
    {
        this(display, contents, new Border(1));
    }

    /**
     * Creates a {@link PopupWindow} with a default border and no separator.
     *
     * @param display the {@link Window}'s {@link Display}
     */
    public PopupWindow(Display display)
    {
        this(display, new Border(1));
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
            if (getContents() == null || getContents().isEmpty())
            {
                throw new IllegalArgumentException("At least 1 line of text must be provided");
            }

            if (!getDisplay().containsY(getContents().size() + 2))
            {
                throw new IndexOutOfBoundsException("Text is too tall for the display");
            }

            int maxLength = 0;
            for (ColorString line : getContents())
            {
                if (line != null && line.length() > maxLength)
                {
                    maxLength = line.length();
                }
            }

            if (!getDisplay().containsX(maxLength + 2))
            {
                throw new IndexOutOfBoundsException("Text is too wide for the display");
            }

            int centerX = getDisplay().getCenterX();
            int centerY = getDisplay().getCenterY();

            int offsetDown = getContents().size() / 2 - 1;
            int offsetUp = offsetDown;
            if (getContents().size() % 2 == 1)
            {
                offsetDown++;
            }

            int offsetRight = maxLength / 2 - 1;
            int offsetLeft = offsetRight;
            if (maxLength % 2 == 1)
            {
                offsetRight++;
            }

            int top = centerY - offsetUp - 2;
            int bottom = centerY + offsetDown + 1;
            int left = centerX - offsetLeft - 2;
            int right = centerX + offsetRight + 1;

            getDisplay().drawBorder(left, top, right, bottom, getBorder());

            if (separator != null)
            {
                for (int line = 0; line < getContents().size(); line++)
                {
                    if (getContents().get(line) == null)
                    {
                        getDisplay().drawLine(left, top + 1 + line, right, top + 1 + line, separator);
                    }
                }
            }

            getDisplay().writeCenter(getContents().toArray(new ColorString[getContents().size()]));
        }
        catch (IllegalArgumentException | IndexOutOfBoundsException e)
        {
            // Do not print the Window if exceptions are encountered
            return;
        }
    }

    /**
     * Returns the Line used to separate the {@link PopupWindow}.
     *
     * @return the Line used to separate the {@link PopupWindow}
     */
    public Line getSeparator()
    {
        return separator;
    }

    /**
     * Returns true if the{@link PopupWindow}has a separator.
     *
     * @return true if the {@link PopupWindow}'s separator is not null
     */
    public boolean hasSeparator()
    {
        return separator != null;
    }
}
