package core.display;

import java.awt.Color;

/**
 * A container class for a character and its foreground and background colors.
 */
public class ColorString extends ColoredObject
{
    public String string;
    
    public ColorString(String s, Color f, Color b)
    {
        super(f, b);
        string = s;
    }
    
    public ColorString(String s, Color f)
        {this(s, f, null);}
    
    public ColorString(String s)
        {this(s, null, null);}
    
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