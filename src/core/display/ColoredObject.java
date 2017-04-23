package core.display;

import java.awt.Color;

/**
 * 
 */
public abstract class ColoredObject
{
    public Color foreground;
    public Color background;
    
    public ColoredObject(Color f, Color b)
    {
        foreground = f;
        background = b;
    }
    
    public void syncDefaults(asciiPanel.AsciiPanel panel)
    {
        if (foreground == null)
            foreground = panel.getDefaultForegroundColor();
        
        if (background == null)
            background = panel.getDefaultBackgroundColor();
    }
    
    public void syncDefaults(Display display)
        {syncDefaults(display.getPanel());}
}