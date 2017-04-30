package corelib.display.glyphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A List of ColorChars that can be used as a multicolored String or even as a
 * mapping system.
 */
public class ColorSet implements CharSequence
{
    /** The List of specific ColorChars in the ColorSet. */
    private List<ColorChar> set;
    
    /**
     * Creates a ColorSet from an existing List of ColorChars.
     * @param s the List of ColorChars that will be used in the ColorSet
     */
    public ColorSet(List<ColorChar> s)
        {set = s;}
    
    /**
     * Creates a ColorSet from an array of ColorChars, converting it into a
     * List.
     * @param c the array of ColorChars that will be converted in the List used
     * by the ColorSet
     */
    public ColorSet(ColorChar[] c)
        {this(new ArrayList<>(java.util.Arrays.asList(c)));}
    
    /**
     * Creates a ColorSet from a single ColorString, consisting of multiple
     * characters with the same color.
     * @param s the ColorString that will be converted into the List used by the
     * ColorSet
     */
    public ColorSet(ColorString s)
        {this(s.toCharArray());}
    
    /**
     * Creates a ColorSet from a String, consisting of multiple characters
     * without a specified color.
     * @param s the String that will be converted into the List used by the
     * ColorSet
     */
    public ColorSet(String s)
        {this(new ColorString(s));}
    
    /** Creates a ColorSet with an empty ArrayList. */
    public ColorSet()
        {this(new ArrayList<>());}
    
    /**
     * Returns the List of ColorChars used by the ColorSet.
     * @return the ColorSet's List of ColorChars
     */
    public List getSet()
        {return set;}
    
    public ColorSet add(char c)
        {set.add(new ColorChar(c)); return this;}
    
    public ColorSet add(String s)
        {return add(new ColorSet(s));}
    
    public ColorSet add(ColorString s)
        {return add(new ColorSet(s));}
    
    public ColorSet add(ColorSet s)
        {set.addAll(Arrays.asList(s.toCharArray())); return this;}
    
    /**
     * Returns the first ColorChar found in the ColorSet with a character that
     * matches the one provided.
     * @param character the character to look for in the ColorSet's ColorChars
     * @return the first ColorChar found in the ColorSet with a character that
     * matches the one provided, null if none are found
     */
    public ColorChar getColorChar(char character)
    {
        for (ColorChar cc: set)
            if (cc.character == character)
                return cc;
        
        return null;
    }
    
    /**
     * Returns the ColorSet's List as an array of ColorChars.
     * @return the ColorSet's List as an array of ColorChars
     */
    public ColorChar[] toCharArray()
        {return set.toArray(new ColorChar[set.size()]);}
    
    @Override
    public int length()
        {return set.size();}

    @Override
    public char charAt(int index)
        {return set.get(index).character;}

    @Override
    public CharSequence subSequence(int start, int end)
    {
        return new ColorSet(set.subList(start, end).toArray(
                new ColorChar[end - start + 1]));
    }
    
    /**
     * Converts the array of Strings provided into a single ColorSet without
     * specified colors.
     * @param s the array of Strings to convert into a ColorSet
     * @return a ColorSet consisting of ColorChars with the characters of the
     * String
     */
    public static ColorSet toColorSet(String[] s)
    {
        ArrayList<ColorChar> chars = new ArrayList<>();
        for (String ss: s)
            for (int i = 0; i < ss.length(); i++)
                chars.add(new ColorChar(ss.charAt(i)));
        
        return new ColorSet(chars.toArray(new ColorChar[chars.size()]));
    }
    
    /**
     * Converts the array of ColorStrings provided into a single ColorSet with
     * the colors of each ColorString.
     * @param s the array of ColorStrings to convert into a ColorSet
     * @return a ColorSet consisting of ColorChars with the characters of the
     * ColorStrings and the color of the ColorString from which they originated
     */
    public static ColorSet toColorSet(ColorString[] s)
    {
        ArrayList<ColorChar> chars = new ArrayList<>();
        for (ColorString cs: s)
            chars.addAll(Arrays.asList(cs.toCharArray()));
        
        return new ColorSet(chars.toArray(new ColorChar[chars.size()]));
    }
    
    /**
     * Converts the array of Strings provided into an array of ColorSets, one
     * for each String.
     * @param s the array of Strings to convert into an array of ColorSets
     * @return an array of ColorSets, one for each String, with the characters
     * of the Strings and no specified colors
     */
    public static ColorSet[] toColorSetArray(String[] s)
    {
        ColorSet[] lines = new ColorSet[s.length];
        
        for (int i = 0; i < s.length; i++)
            lines[i] = new ColorSet(s[i]);
        
        return lines;
    }
    
    /**
     * Converts the array of ColorStrings provided into an array of ColorSets,
     * one for each ColorString.
     * @param s the array of ColorStrings to convert into an array of ColorSets
     * @return an array of ColorSets, one for each ColorString, with the
     * characters of the ColorStrings and the colors of the ColorStrings from
     * which they originated
     */
    public static ColorSet[] toColorSetArray(ColorString[] s)
    {
        ColorSet[] lines = new ColorSet[s.length];
        
        for (int i = 0; i < s.length; i++)
            lines[i] = new ColorSet(s[i]);
        
        return lines;
    }
    
    /**
     * Converts the 2D array of ColorChars provided into an array of ColorSets,
     * with each ColorChar[] as a single ColorSet.
     * @param a the array of ColorChars to convert into an array of ColorSets
     * @return an array of ColorSets, one for each ColorChar
     */
    public static ColorSet[] toColorSetArray(ColorChar[][] a)
    {
        ColorSet[] lines = new ColorSet[a.length];
        
        for (int line = 0; line < a.length; line++)
            lines[line] = new ColorSet(a[line]);
        
        return lines;
    }
}