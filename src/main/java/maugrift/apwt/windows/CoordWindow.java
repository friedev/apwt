package maugrift.apwt.windows;

import maugrift.apwt.Display;
import maugrift.apwt.glyphs.ColorString;
import squidpony.squidmath.Coord;

import java.util.List;

/**
 * A {@link Window} located relative to coordinates.
 *
 * @author Maugrift
 */
public abstract class CoordWindow extends Window
{
    /**
     * The coordinates of the {@link CoordWindow}.
     */
    private Coord location;

    /**
     * Creates a {@link CoordWindow} with all fields defined.
     *
     * @param display  the {@link Window}'s {@link maugrift.apwt.Display}
     * @param border   the {@link Window}'s {@link Border}
     * @param contents the {@link Window}'s initial contents
     * @param location the {@link Window}'s location
     */
    public CoordWindow(Display display, Border border, List<ColorString> contents, Coord location)
    {
        super(display, border, contents);
        this.location = location;
    }

    /**
     * Creates a {@link CoordWindow} with no contents.
     *
     * @param display  the {@link Window}'s {@link maugrift.apwt.Display}
     * @param border   the {@link Window}'s {@link Border}
     * @param location the {@link Window}'s location
     */
    public CoordWindow(Display display, Border border, Coord location)
    {
        super(display, border);
        this.location = location;
    }

    /**
     * Creates a borderless {@link CoordWindow}.
     *
     * @param display  the {@link Window}'s {@link maugrift.apwt.Display}
     * @param contents the {@link Window}'s initial contents
     * @param location the {@link Window}'s location
     */
    public CoordWindow(Display display, List<ColorString> contents, Coord location)
    {
        super(display, contents);
        this.location = location;
    }

    /**
     * Creates a borderless {@link CoordWindow} with no contents.
     *
     * @param display  the {@link Window}'s {@link maugrift.apwt.Display}
     * @param location the {@link Window}'s location
     */
    public CoordWindow(Display display, Coord location)
    {
        super(display);
        this.location = location;
    }

    /**
     * Returns the top-left coordinates of the {@link CoordWindow}.
     *
     * @return the top-left coordinates of the {@link CoordWindow}
     */
    public Coord getLocation()
    {
        return location;
    }

    /**
     * Moves the {@link CoordWindow} to the specified coordinates.
     *
     * @param location the new coordinates of the {@link CoordWindow}
     */
    public void setLocation(Coord location)
    {
        this.location = location;
    }

    /**
     * Centers the {@link CoordWindow} across the x-axis.
     */
    public void centerX()
    {
        this.location.setX(getDisplay().getCenterX());
    }

    /**
     * Centers the {@link CoordWindow} across the y-axis.
     */
    public void centerY()
    {
        this.location.setY(getDisplay().getCenterY());
    }
}