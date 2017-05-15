package corelib.display.glyphs;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * A container class for a String and its foreground and background colors.
 */
public class ColorString extends ColoredObject implements CharSequence
{
    /** The {@link ColorString}'s actual String. */
    public String string;
    
    /**
     * Creates a {@link ColorString} from a String and its two colors.
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
     * Creates a {@link ColorString} from another {@link ColorString}.
     * @param copying the {@link ColorString} to copy
     */
    public ColorString(ColorString copying)
    {
        this(copying.string, copying.getForeground(), copying.getBackground());
    }
    
    /**
     * Creates a {@link ColorString} from a String and a foreground color, but
     * no specified background color.
     * @param s the String
     * @param f the color of the String
     */
    public ColorString(String s, Color f)
        {this(s, f, null);}
    
    /**
     * Creates a {@link ColorString} from a String and no specified colors.
     * @param s the String
     */
    public ColorString(String s)
        {this(s, null, null);}
    
    /**
     * Returns the {@link ColorString}'s actual String.
     * @return the {@link ColorString}'s actual String
     */
    public String getString()
        {return string;}
    
    /**
     * Sets the {@link ColorString}'s actual String.
     * @param string the {@link ColorString}'s new String
     */
    public void setString(String string)
        {this.string = string;} 
    
    /**
     * Converts the {@link ColorString} into an array of
     * {@link ColorChar ColorChars}, each with the colors of the
     * {@link ColorString}.
     * @return an array of {@link ColorChar ColorChars} with the characters of
     * the {@link ColorString} and its colors
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
     * Converts an array of Strings into an array of
     * {@link ColorString ColorStrings} without any specified colors.
     * @param s the array of Strings to convert into
     * {@link ColorString ColorStrings}
     * @return an array of {@link ColorString ColorStrings} with the characters
     * of the Strings and without specified colors
     */
    public static ColorString[] toColorStringArray(String[] s)
    {
        ColorString[] lines = new ColorString[s.length];
        
        for (int i = 0; i < s.length; i++)
            lines[i] = new ColorString(s[i]);
        
        return lines;
    }
    
    /**
     * Converts a List of Strings into an array of
     * {@link ColorString ColorStrings} without any specified colors.
     * @param s the List of Strings to convert into
     * {@link ColorString ColorStrings}
     * @return an array of {@link ColorString ColorStrings} with the characters
     * of the Strings and without specified colors
     */
    public static List<ColorString> toColorStringList(List<String> s)
    {
        LinkedList<ColorString> lines = new LinkedList<>();
        
        for (String cur: s)
            lines.add(new ColorString(cur));
        
        return lines;
    }

    @Override
    public String toString()
        {return string;}
    
    @Override
    public boolean equals(Object o)
    {
        if (o == null || !(o instanceof ColorString))
            return false;
        
        ColorString cast = (ColorString) o;
        if (string == null)
            return cast.string == null;
        
        if (getForeground() == null)
            return cast.getForeground() == null;
        
        if (getBackground() == null)
            return cast.getBackground() == null;
        
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