package maugrift.apwt.windows;

import maugrift.apwt.display.AsciiPanelDisplay;
import maugrift.apwt.glyphs.ColorString;

import java.util.List;

/**
 * A {@link Window} located relative to coordinates.
 *
 * @author Maugrift
 */
public abstract class CoordWindow extends Window
{
    /**
     * The x coordinate of the {@link CoordWindow}.
     */
    private int x;

    /**
     * The y coordinate of the {@link CoordWindow}.
     */
    private int y;

    /**
     * Creates a {@link CoordWindow} with all fields defined.
     *
     * @param display  the {@link Window}'s {@link AsciiPanelDisplay}
     * @param border   the {@link Window}'s {@link Border}
     * @param contents the {@link Window}'s initial contents
     * @param x        the {@link Window}'s x coordinate
     * @param y        the {@link Window}'s y coordinate
     */
    public CoordWindow(AsciiPanelDisplay display, Border border, List<ColorString> contents, int x, int y)
    {
        super(display, border, contents);
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a {@link CoordWindow} with no contents.
     *
     * @param display the {@link Window}'s {@link AsciiPanelDisplay}
     * @param border  the {@link Window}'s {@link Border}
     * @param x       the {@link Window}'s x coordinate
     * @param y       the {@link Window}'s y coordinate
     */
    public CoordWindow(AsciiPanelDisplay display, Border border, int x, int y)
    {
        super(display, border);
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a borderless {@link CoordWindow}.
     *
     * @param display  the {@link Window}'s {@link AsciiPanelDisplay}
     * @param contents the {@link Window}'s initial contents
     * @param x        the {@link Window}'s x coordinate
     * @param y        the {@link Window}'s y coordinate
     */
    public CoordWindow(AsciiPanelDisplay display, List<ColorString> contents, int x, int y)
    {
        super(display, contents);
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a borderless {@link CoordWindow} with no contents.
     *
     * @param display the {@link Window}'s {@link AsciiPanelDisplay}
     * @param x       the {@link Window}'s x coordinate
     * @param y       the {@link Window}'s y coordinate
     */
    public CoordWindow(AsciiPanelDisplay display, int x, int y)
    {
        super(display);
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x coordinate of the {@link CoordWindow}.
     *
     * @return the x coordinate of the {@link CoordWindow}
     */
    public int getX()
    {
        return x;
    }

    /**
     * Returns the y coordinate of the {@link CoordWindow}.
     *
     * @return the y coordinate of the {@link CoordWindow}
     */
    public int getY()
    {
        return y;
    }

    /**
     * Moves the {@link CoordWindow} to the specified coordinates.
     *
     * @param x the new x coordinate of the {@link CoordWindow}
     * @param y the new y coordinate of the {@link CoordWindow}
     */
    public void setLocation(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Centers the {@link CoordWindow} across the x-axis.
     */
    public void centerX()
    {
        this.x = getDisplay().getCenterX();
    }

    /**
     * Centers the {@link CoordWindow} across the y-axis.
     */
    public void centerY()
    {
        this.y = getDisplay().getCenterY();
    }
}