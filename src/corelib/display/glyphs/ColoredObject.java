package corelib.display.glyphs;

import corelib.display.Display;
import java.awt.Color;

/**
 * A parent class for any Object that specifies a foreground and background
 * color.
 */
public abstract class ColoredObject
{
    /** The color of the foreground. */
    private Color foreground;
    /** The color of the background. */
    private Color background;
    
    /**
     * Creates a ColoredObject with a foreground and background color.
     * @param f the color of the foreground
     * @param b the color of the background
     */
    public ColoredObject(Color f, Color b)
    {
        foreground = f;
        background = b;
    }
    
    /**
     * Creates a ColoredObject with a foreground color but no background color.
     * @param f the color of the foreground
     */
    public ColoredObject(Color f)
        {this(f, null);}
    
    /** Creates a ColoredObject with no specified colors. */
    public ColoredObject()
        {this(null, null);}
    
    /**
     * Returns the color of the object.
     * @return the object's foreground color
     */
    public Color getForeground()
        {return foreground;}
    
    /**
     * Returns the object's background color.
     * @return the object's background color
     */
    public Color getBackground()
        {return background;}
    
    /**
     * Sets the color of the object.
     * @param color the object's new foreground color
     */
    public void setForeground(Color color)
        {foreground = color;}
    
    /**
     * Sets the object's background color.
     * @param color the object's new background color
     */
    public void setBackground(Color color)
        {background = color;}
    
    /**
     * Sets the object's colors.
     * @param foreground the object's new foreground color
     * @param background the object's new background color
     */
    public void setColors(Color foreground, Color background)
    {
        this.foreground = foreground;
        this.background = background;
    }
    
    /**
     * Sets any unspecified (null) colors of the ColoredObject to the default
     * colors of the provided AsciiPanel.
     * @param panel the AsciiPanel with which to sync default colors
     */
    public void syncDefaults(asciiPanel.AsciiPanel panel)
    {
        if (foreground == null)
            foreground = panel.getDefaultForegroundColor();
        
        if (background == null)
            background = panel.getDefaultBackgroundColor();
    }
    
    /**
     * Sets any unspecified (null) colors of the ColoredObject to the default
     * colors of the provided Display's AsciiPanel.
     * @param display the Display containing the AsciiPanel with which to sync
     * default colors
     */
    public void syncDefaults(Display display)
        {syncDefaults(display.getPanel());}
}