package core.display;

import java.awt.Color;

/**
 * A container class for a character and its foreground and background colors.
 */
public class ColorChar
{
    public char  character;
    public Color foreground;
    public Color background;
    
    public ColorChar(char c, Color f, Color b)
    {
        character  = c;
        foreground = f;
        background = b;
    }
    
    public ColorChar(char c, Color f)
        {this(c, f, null);}
    
    public ColorChar(char c)
        {this(c, null, null);}
    
    public void syncDefaults(asciiPanel.AsciiPanel panel)
    {
        if (foreground == null)
            foreground = panel.getDefaultForegroundColor();
        
        if (background == null)
            background = panel.getDefaultBackgroundColor();
    }
}