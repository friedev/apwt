package corelib.display.glyphs;

import corelib.display.Display;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * A List of {@link ColorChar ColorChars} that can be used as a multicolored
 * String. It can also be used as a system of mapping characters to colors.
 */
public class ColorSet implements CharSequence
{
    /**
     * The List of specific {@link ColorChar ColorChars} in the
     * {@link ColorSet}.
     */
    private List<ColorChar> set;
    
    /**
     * Creates a {@link ColorSet} from an existing List of
     * {@link ColorChar ColorChars}.
     * @param s the List of {@link ColorChar ColorChars} that will be used in
     * the {@link ColorSet}
     */
    public ColorSet(List<ColorChar> s)
        {set = s;}
    
    /**
     * Creates a {@link ColorSet} from another {@link ColorSet}.
     * @param copying the {@link ColorSet} to copy
     */
    public ColorSet(ColorSet copying)
        {this(new ArrayList<>(copying.set));}
    
    /**
     * Creates a {@link ColorSet} from an array of {@link ColorChar ColorChars},
     * converting it into a List.
     * @param c the array of {@link ColorChar ColorChars} that will be converted
     * in the List used by the {@link ColorSet}
     */
    public ColorSet(ColorChar[] c)
        {this(new ArrayList<>(java.util.Arrays.asList(c)));}
    
    /**
     * Creates a {@link ColorSet} from a single ColorString, consisting of
     * multiple characters with the same color.
     * @param s the ColorString that will be converted into the List used by the
     * {@link ColorSet}
     */
    public ColorSet(ColorString s)
        {this(s.toCharArray());}
    
    /**
     * Creates a {@link ColorSet} from a String, consisting of multiple
     * characters with the specified colors.
     * @param s the String that will be converted into the List used by the
     * {@link ColorSet}
     * @param foreground the color of the characters in the String
     * @param background the color of the background
     */
    public ColorSet(String s, Color foreground, Color background)
        {this(new ColorString(s, foreground, background));}
    
    /**
     * Creates a {@link ColorSet} from a String, consisting of multiple
     * characters with the specified foreground color.
     * @param s the String that will be converted into the List used by the
     * {@link ColorSet}
     * @param foreground the color of the characters in the String
     */
    public ColorSet(String s, Color foreground)
        {this(new ColorString(s, foreground));}
    
    /**
     * Creates a {@link ColorSet} from a String, consisting of multiple
     * characters without a specified color.
     * @param s the String that will be converted into the List used by the
     * {@link ColorSet}
     */
    public ColorSet(String s)
        {this(new ColorString(s));}
    
    /** Creates a {@link ColorSet} with an empty ArrayList. */
    public ColorSet()
        {this(new ArrayList<>());}
    
    /**
     * Returns the List of {@link ColorChar ColorChars} used by the
     * {@link ColorSet}.
     * @return the {@link ColorSet}'s List of {@link ColorChar ColorChars}
     */
    public List getSet()
        {return set;}
    
    /**
     * Adds the given char to the set as an uncolored ColorChar.
     * @param c the char to add
     * @return this for convenient chaining
     */
    public ColorSet add(char c)
        {set.add(new ColorChar(c)); return this;}
    
    /**
     * Adds the given {@link ColorChar} to the set.
     * @param c the {@link ColorChar} to add
     * @return this for convenient chaining
     */
    public ColorSet add(ColorChar c)
        {set.add(c); return this;}
    
    /**
     * Adds the given String to the set as an uncolored {@link ColorSet}.
     * @param s the String to add
     * @return this for convenient chaining
     */
    public ColorSet add(String s)
        {return add(new ColorSet(s));}
    
    /**
     * Adds the given ColorString to the set as a monochrome {@link ColorSet}.
     * @param s the ColorString to add
     * @return this for convenient chaining
     */
    public ColorSet add(ColorString s)
        {return add(new ColorSet(s));}
    
    /**
     * Adds the given {@link ColorSet} to this {@link ColorSet}.
     * @param s the {@link ColorSet} to add
     * @return this for convenient chaining
     */
    public ColorSet add(ColorSet s)
        {set.addAll(Arrays.asList(s.toCharArray())); return this;}
    
    /**
     * Sets the foreground color of all the set's {@link ColorChar ColorChars}
     * to the given Color.
     * @param foreground the foreground color to assign to all the set's
     * {@link ColorChar ColorChars}
     * @return this for convenient chaining
     */
    public ColorSet setForeground(Color foreground)
    {
        for (int i = 0; i < set.size(); i++)
        {
            ColorChar newChar = new ColorChar(set.get(i));
            newChar.setForeground(foreground);
            set.set(i, newChar);
        }
        return this;
    }
    
    /**
     * Sets the background color of all the set's {@link ColorChar ColorChars}
     * to the given Color.
     * @param background the background color to assign to all the set's
     * {@link ColorChar ColorChars}
     * @return this for convenient chaining
     */
    public ColorSet setBackground(Color background)
    {
        for (int i = 0; i < set.size(); i++)
        {
            ColorChar newChar = new ColorChar(set.get(i));
            newChar.setBackground(background);
            set.set(i, newChar);
        }
        return this;
    }
    
    /**
     * Sets the colors of all the set's {@link ColorChar ColorChars} to the
     * given Colors.
     * @param foreground the foreground color to assign to all the set's
     * {@link ColorChar ColorChars}
     * @param background the background color to assign to all the set's
     * {@link ColorChar ColorChars}
     * @return this for convenient chaining
     */
    public ColorSet setColors(Color foreground, Color background)
        {return setForeground(foreground).setBackground(background);}
    
    /**
     * Sets any unspecified (null) colors of each {@link ColorChar} to the
     * default colors of the provided {@link corelib.display.Display}'s
     * AsciiPanel.
     * @param display the {@link corelib.display.Display} containing the
     * AsciiPanel with which to sync default colors
     * @return this for convenient chaining
     */
    public ColorSet syncDefaults(Display display)
    {
        for (ColorChar c: set)
            c.syncDefaults(display);
        return this;
    }
    
    /**
     * Returns the first {@link ColorChar} found in the {@link ColorSet} with a
     * character that matches the one provided.
     * @param character the character to look for in the {@link ColorSet}'s
     * {@link ColorChar ColorChars}
     * @return the first {@link ColorChar} found in the {@link ColorSet} with a
     * character that matches the one provided, null if none are found
     */
    public ColorChar getColorChar(char character)
    {
        for (ColorChar cc: set)
            if (cc.getChar() == character)
                return cc;
        
        return null;
    }
    
    /**
     * Returns the {@link ColorSet}'s List as an array of
     * {@link ColorChar ColorChars}.
     * @return the {@link ColorSet}'s List as an array of
     * {@link ColorChar ColorChars}
     */
    public ColorChar[] toCharArray()
        {return set.toArray(new ColorChar[set.size()]);}
    
    @Override
    public String toString()
    {
        if (set.isEmpty())
            return "";
        
        StringBuilder builder = new StringBuilder();
        for (ColorChar character: set)
            builder.append(character.getChar());
        return builder.toString();
    }
    
    @Override
    public int length()
        {return set.size();}

    @Override
    public char charAt(int index)
        {return set.get(index).getChar();}

    public ColorChar getColorCharAt(int index)
        {return set.get(index);}
    
    @Override
    public ColorSet subSequence(int start, int end)
    {
        return new ColorSet(set.subList(start, end).toArray(
                new ColorChar[end - start + 1]));
    }
    
    /**
     * Converts the array of Strings provided into a single {@link ColorSet}
     * without specified colors.
     * @param s the array of Strings to convert into a {@link ColorSet}
     * @return a {@link ColorSet} consisting of {@link ColorChar ColorChars}
     * with the characters of the String
     */
    public static ColorSet toColorSet(String... s)
    {
        ArrayList<ColorChar> chars = new ArrayList<>();
        for (String ss: s)
            for (int i = 0; i < ss.length(); i++)
                chars.add(new ColorChar(ss.charAt(i)));
        
        return new ColorSet(chars.toArray(new ColorChar[chars.size()]));
    }
    
    /**
     * Converts the array of {@link ColorString ColorStrings} provided into a
     * single {@link ColorSet} with the colors of each {@link ColorString}.
     * @param s the array of {@link ColorString ColorStrings} to convert into a
     * {@link ColorSet}
     * @return a {@link ColorSet} consisting of {@link ColorChar ColorChars}
     * with the characters of the {@link ColorString ColorStrings} and the color
     * of the {@link ColorString} from which they originated
     */
    public static ColorSet toColorSet(ColorString... s)
    {
        ArrayList<ColorChar> chars = new ArrayList<>();
        for (ColorString cs: s)
            chars.addAll(Arrays.asList(cs.toCharArray()));
        
        return new ColorSet(chars.toArray(new ColorChar[chars.size()]));
    }
    
    /**
     * Converts the array of Strings provided into an array of
     * {@link ColorSet ColorSets}, one for each String.
     * @param s the array of Strings to convert into an array of
     * {@link ColorSet ColorSets}
     * @return an array of {@link ColorSet ColorSets}, one for each String, with
     * the characters of the Strings and no specified colors
     */
    public static ColorSet[] toColorSetArray(String... s)
    {
        ColorSet[] lines = new ColorSet[s.length];
        
        for (int i = 0; i < s.length; i++)
            lines[i] = new ColorSet(s[i]);
        
        return lines;
    }
    
    /**
     * Converts the array of {@link ColorString ColorStrings} provided into an
     * array of {@link ColorSet ColorSets}, one for each {@link ColorString}.
     * @param s the array of {@link ColorString ColorStrings} to convert into an
     * array of {@link ColorSet ColorSets}
     * @return an array of {@link ColorSet ColorSets}, one for each
     * {@link ColorString ColorString}, with the characters of the
     * {@link ColorString ColorStrings} and the colors of the
     * {@link ColorString ColorStrings} from which they originated
     */
    public static ColorSet[] toColorSetArray(ColorString... s)
    {
        ColorSet[] lines = new ColorSet[s.length];
        
        for (int i = 0; i < s.length; i++)
            lines[i] = new ColorSet(s[i]);
        
        return lines;
    }
    
    /**
     * Converts the 2D array of {@link ColorChar ColorChars} provided into an
     * array of {@link ColorSet ColorSets}, with each {@link ColorChar}[] as a
     * single {@link ColorSet}.
     * @param a the array of {@link ColorChar ColorChars} to convert into an
     * array of {@link ColorSet ColorSets}
     * @return an array of {@link ColorSet ColorSets}, one for each
     * {@link ColorChar}
     */
    public static ColorSet[] toColorSetArray(ColorChar[]... a)
    {
        ColorSet[] lines = new ColorSet[a.length];
        
        for (int line = 0; line < a.length; line++)
            lines[line] = new ColorSet(a[line]);
        
        return lines;
    }
    
    /**
     * Converts the List of Strings provided into a List of
     * {@link ColorSet ColorSets}, one for each String.
     * @param s the List of Strings to convert into a List of
     * {@link ColorSet ColorSets}
     * @return a List of {@link ColorSet ColorSets}, one for each String, with
     * the characters of the Strings and no specified colors
     */
    public static List<ColorSet> stringToColorSetList(List<String> s)
    {
        LinkedList<ColorSet> lines = new LinkedList<>();
        
        for (String cur: s)
            lines.add(new ColorSet(cur));
        
        return lines;
    }
    
    /**
     * Converts the List of ColorStrings provided into a List of
     * {@link ColorSet ColorSets},  one for each ColorString.
     * @param s the List of ColorStrings to convert into a List of
     * {@link ColorSet ColorSets}
     * @return a List of {@link ColorSet ColorSets}, one for each
     * {@link ColorString}, with the characters of the ColorStrings and the
     * colors of the {@link ColorString ColorStrings} from which they originated
     */
    public static List<ColorSet> colorStringToColorSetList(List<ColorString> s)
    {
        LinkedList<ColorSet> lines = new LinkedList<>();
        
        for (ColorString cur: s)
            lines.add(new ColorSet(cur));
        
        return lines;
    }
    
    /**
     * Converts the 2D array of {@link ColorChar ColorChars} provided into a
     * List of {@link ColorSet ColorSets}, with each {@link ColorChar}[] as a
     * single {@link ColorSet}.
     * @param a the array of {@link ColorChar ColorChars} to convert into a List
     * of {@link ColorSet ColorSets}
     * @return a List of {@link ColorSet ColorSets}, one for each
     * {@link ColorChar}
     */
    public static List<ColorSet> colorCharToColorSetList(ColorChar[]... a)
    {
        LinkedList<ColorSet> lines = new LinkedList<>();
        
        for (ColorChar[] cur: a)
            lines.add(new ColorSet(cur));
        
        return lines;
    }
}