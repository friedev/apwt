package corelib.display.glyphs;

import java.awt.Color;
import java.util.Objects;

/**
 * A container class for a String and its foreground and background colors.
 */
public class ColorString extends ColoredObject implements CharSequence
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
    
    public ColorString(ColorString copying)
    {
        this(copying.string, copying.getForeground(), copying.getBackground());
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
    
    public String getString()
        {return string;}
    
    public void setString(String string)
        {this.string = string;} 
    
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
            array[i] = new ColorChar(string.charAt(i), getForeground(),
                    getBackground());
        
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

    @Override
    public boolean equals(Object o)
    {
        if (o == null || !(o instanceof ColorString))
            return false;
        
        ColorString cast = (ColorString) o;
        if (string == null && cast.string != null)
            return false;
        
        if (getForeground() == null && cast.getForeground() != null)
            return false;
        
        if (getBackground() == null && cast.getBackground() != null)
            return false;
        
        return string.equals(cast.string) &&
               getForeground().equals(cast.getForeground()) &&
               getBackground().equals(cast.getBackground());
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.string);
        return hash;
    }
    
    @Override
    public int length()
        {return string.length();}

    @Override
    public char charAt(int index)
        {return string.charAt(index);}

    @Override
    public CharSequence subSequence(int start, int end)
        {return string.subSequence(start, end);}
}