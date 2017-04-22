package core.display;

import java.awt.Color;

/**
 * A container class for a character and its foreground and background colors.
 */
public class ColorString
{
    public String string;
    public Color  foreground;
    public Color  background;
    
    public ColorString(String s, Color f, Color b)
    {
        string     = s;
        foreground = f;
        background = b;
    }
    
    public ColorString(String s, Color f)
        {this(s, f, null);}
    
    public ColorString(String s)
        {this(s, null, null);}
   
    public void syncDefaults(asciiPanel.AsciiPanel panel)
    {
        if (foreground == null)
            foreground = panel.getDefaultForegroundColor();
        
        if (background == null)
            background = panel.getDefaultBackgroundColor();
    }
    
    public ColorChar[] toCharArray()
    {
        ColorChar[] array = new ColorChar[string.length()];
        
        for (int i = 0; i < string.length(); i++)
            array[i] = new ColorChar(string.charAt(i), foreground, background);
        
        return array;
    }
    
    public static ColorString[] toColorStringArray(String[] s)
    {
        ColorString[] cs = new ColorString[s.length];
        
        for (int i = 0; i < s.length; i++)
            cs[i] = new ColorString(s[i]);
        
        return cs;
    }
}