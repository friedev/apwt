package corelib.display.windows;

import corelib.display.Display;
import corelib.display.glyphs.ColorSet;
import java.util.List;
import squidpony.squidmath.Coord;

/** A {@link Window} located relative to coordinates. */
public abstract class CoordWindow extends Window
{
    /** The coordinates of the {@link CoordWindow}. */
    private Coord location;

    /**
     * Creates a {@link CoordWindow} with all fields defined.
     * @param display the {@link Window}'s {@link corelib.display.Display}
     * @param border the {@link Window}'s {@link Border}
     * @param contents the {@link Window}'s initial contents
     * @param location the {@link Window}'s location
     */
    public CoordWindow(Display display, Border border, List<ColorSet> contents,
            Coord location)
    {
        super(display, border, contents);
        this.location = location;
    }
    
    /**
     * Creates a {@link CoordWindow} with no contents.
     * @param display the {@link Window}'s {@link corelib.display.Display}
     * @param border the {@link Window}'s {@link Border}
     * @param location the {@link Window}'s location
     */
    public CoordWindow(Display display, Border border, Coord location)
    {
        super(display, border);
        this.location = location;
    }
    
    /**
     * Creates a borderless {@link CoordWindow}.
     * @param display the {@link Window}'s {@link corelib.display.Display}
     * @param contents the {@link Window}'s initial contents
     * @param location the {@link Window}'s location
     */
    public CoordWindow(Display display, List<ColorSet> contents, Coord location)
    {
        super(display, contents);
        this.location = location;
    }
    
    /**
     * Creates a borderless {@link CoordWindow} with no contents.
     * @param display the {@link Window}'s {@link corelib.display.Display}
     * @param location the {@link Window}'s location
     */
    public CoordWindow(Display display, Coord location)
    {
        super(display);
        this.location = location;
    }
    
    /**
     * Returns the top-left coordinates of the {@link CoordWindow}.
     * @return the top-left coordinates of the {@link CoordWindow}
     */
    public Coord getLocation()
        {return location;}
    
    /**
     * Moves the {@link CoordWindow} to the specified coordinates.
     * @param location the new coordinates of the {@link CoordWindow}
     */
    public void setLocation(Coord location)
        {this.location = location;}
    
    /** Centers the {@link CoordWindow} across the x-axis. */
    public void centerX()
        {this.location.setX(getDisplay().getCenterX());}
    
    /** Centers the {@link CoordWindow} across the y-axis. */
    public void centerY()
        {this.location.setY(getDisplay().getCenterY());}
}