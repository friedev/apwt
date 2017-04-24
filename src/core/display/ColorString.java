package core.display;

import java.awt.Color;

/**
 * A container class for a character and its foreground and background colors.
 */
public class ColorString extends ColoredObject
{
    /** The ColorString's actual String. */
    public String string;
    
    /**
     * Creates a ColorString from a String and its two colors.
     * @param s the String
     * @param f the color of the String
     * @param b the color of the background
     */
    public ColorString(String s, Color f, Color b)
    {
        super(f, b);
        string = s;
    }
    
    /**
     * Creates a ColorString from a String and a foreground color, but no
     * specified background color.
     * @param s the String
     * @param f the color of the String
     */
    public ColorString(String s, Color f)
        {this(s, f, null);}
    
    /**
     * Creates a ColorString from a String and no specified colors.
     * @param s the String
     */
    public ColorString(String s)
        {this(s, null, null);}
    
    /**
     * Converts the ColorString into an array of ColorChars, each with the
     * colors of the ColorString.
     * @return an array of ColorChars with the characters of the ColorString and
     * its colors
     */
    public ColorChar[] toCharArray()
    {
        ColorChar[] array = new ColorChar[string.length()];
        
        for (int i = 0; i < string.length(); i++)
            array[i] = new ColorChar(string.charAt(i), foreground, background);
        
        return array;
    }
    
    /**
     * Converts an array of Strings into an array of ColorStrings without
     * specified colors.
     * @param s the array of Strings to convert into ColorStrings
     * @return an array of ColorStrings with the characters of the Strings and
     * without specified colors
     */
    public static ColorString[] toColorStringArray(String[] s)
    {
        ColorString[] cs = new ColorString[s.length];
        
        for (int i = 0; i < s.length; i++)
            cs[i] = new ColorString(s[i]);
        
        return cs;
    }
}