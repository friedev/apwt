package boldorf.apwt.glyphs;

import boldorf.apwt.Display;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * A List of {@link ColorChar ColorChars} that can be used as a multicolored
 * String. It can also be used as a system of mapping characters to colors.
 */
public class ColorString implements CharSequence
{
    /**
     * The List of specific {@link ColorChar ColorChars} in the
     * {@link ColorString}.
     */
    private List<ColorChar> set;
    
    /**
     * Creates a {@link ColorString} from an existing List of
     * {@link ColorChar ColorChars}.
     * @param s the List of {@link ColorChar ColorChars} that will be used in
     * the {@link ColorString}
     */
    public ColorString(List<ColorChar> s)
        {set = s;}
    
    /**
     * Creates a {@link ColorString} from another {@link ColorString}.
     * @param copying the {@link ColorString} to copy
     */
    public ColorString(ColorString copying)
        {this(new ArrayList<>(copying.set));}
    
    /**
     * Creates a {@link ColorString} with the
     * {@link ColorStringObject#toColorString()} method from a
     * {@link ColorStringObject}.
     * @param o the {@link ColorStringObject} to read from
     */
    public ColorString(ColorStringObject o)
        {this(o.toColorString());}
    
    /**
     * Creates a {@link ColorString} from an array of
     * {@link ColorChar ColorChars}, converting it into a List.
     * @param c the array of {@link ColorChar ColorChars} that will be converted
     * in the List used by the {@link ColorString}
     */
    public ColorString(ColorChar[] c)
        {this(new ArrayList<>(Arrays.asList(c)));}
    
    /**
     * Creates a {@link ColorString} from a String, consisting of multiple
     * characters with the specified colors.
     * @param s the String that will be converted into the List used by the
     * {@link ColorString}
     * @param foreground the color of the characters in the String
     * @param background the color of the background
     */
    public ColorString(String s, Color foreground, Color background)
        {this(colorString(s, foreground, background));}
    
    /**
     * Creates a {@link ColorString} from a String, consisting of multiple
     * characters with the specified foreground color.
     * @param s the String that will be converted into the List used by the
     * {@link ColorString}
     * @param foreground the color of the characters in the String
     */
    public ColorString(String s, Color foreground)
        {this(s, foreground, null);}
    
    /**
     * Creates a {@link ColorString} from a String, consisting of multiple
     * characters without a specified color.
     * @param s the String that will be converted into the List used by the
     * {@link ColorString}
     */
    public ColorString(String s)
        {this(s, null, null);}
    
    /**
     * Creates a {@link ColorString} from a {@link ColorChar}, consisting of a
     * single colored character.
     * @param c the {@link ColorChar} that will be added to the List used by the
     * {@link ColorString}
     */
    public ColorString(ColorChar c)
        {this(); add(c);}
    
    /** Creates a {@link ColorString} with an empty ArrayList. */
    public ColorString()
        {this(new ArrayList<>());}
    
    /**
     * Returns the List of {@link ColorChar ColorChars} used by the
     * {@link ColorString}.
     * @return the {@link ColorString}'s List of {@link ColorChar ColorChars}
     */
    public List getSet()
        {return set;}
    
    /**
     * Adds the given char to the set as an uncolored ColorChar.
     * @param c the char to add
     * @return this for convenient chaining
     */
    public ColorString add(char c)
        {set.add(new ColorChar(c)); return this;}
    
    /**
     * Adds the given {@link ColorChar} to the set.
     * @param c the {@link ColorChar} to add
     * @return this for convenient chaining
     */
    public ColorString add(ColorChar c)
        {set.add(c); return this;}
    
    /**
     * Adds the given String to the set as an uncolored {@link ColorString}.
     * @param s the String to add
     * @return this for convenient chaining
     */
    public ColorString add(String s)
        {return add(new ColorString(s));}
    
    /**
     * Adds the given {@link ColorStringObject} to the set using its
     * {@link ColorStringObject#toColorString()} method.
     * @param o the {@link ColorStringObject} to add
     * @return this for convenient chaining
     */
    public ColorString add(ColorStringObject o)
        {return add(o.toColorString());}
    
    /**
     * Adds the given {@link ColorString} to this {@link ColorString}.
     * @param s the {@link ColorString} to add
     * @return this for convenient chaining
     */
    public ColorString add(ColorString s)
        {set.addAll(Arrays.asList(s.toCharArray())); return this;}
    
    /**
     * Sets the foreground color of all the set's {@link ColorChar ColorChars}
     * to the given Color.
     * @param foreground the foreground color to assign to all the set's
     * {@link ColorChar ColorChars}
     * @return this for convenient chaining
     */
    public ColorString setForeground(Color foreground)
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
    public ColorString setBackground(Color background)
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
    public ColorString setColors(Color foreground, Color background)
        {return setForeground(foreground).setBackground(background);}
    
    /**
     * Sets any unspecified (null) colors of each {@link ColorChar} to the
     * default colors of the provided {@link boldorf.apwt.Display}'s
     * AsciiPanel.
     * @param display the {@link boldorf.apwt.Display} containing the
     * AsciiPanel with which to sync default colors
     * @return this for convenient chaining
     */
    public ColorString syncDefaults(Display display)
    {
        for (ColorChar c: set)
            c.syncDefaults(display);
        return this;
    }
    
    /**
     * Returns the first {@link ColorChar} found in the {@link ColorString} with a
     * character that matches the one provided.
     * @param character the character to look for in the {@link ColorString}'s
     * {@link ColorChar ColorChars}
     * @return the first {@link ColorChar} found in the {@link ColorString} with a
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
     * Returns the {@link ColorString}'s List as an array of
     * {@link ColorChar ColorChars}.
     * @return the {@link ColorString}'s List as an array of
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
    public ColorString subSequence(int start, int end)
        {return new ColorString(set.subList(start, end));}
    
    /**
    * Converts the String into a List of {@link ColorChar ColorChars}, each
    * with the provided foreground and background colors.
    * @return a List of {@link ColorChar ColorChars} with the characters of
    * the String and the provided foreground and background colors
    */
    private static List<ColorChar> colorString(String s, Color foreground,
            Color background)
    {
        List<ColorChar> list = new ArrayList<>(s.length());

        for (char c: s.toCharArray())
            list.add(new ColorChar(c, foreground, background));

        return list;
    }
    
    /**
     * Converts the array of Strings provided into a single {@link ColorString}
     * without specified colors.
     * @param s the array of Strings to convert into a {@link ColorString}
     * @return a {@link ColorString} consisting of {@link ColorChar ColorChars}
     * with the characters of the String
     */
    public static ColorString toColorString(String... s)
    {
        ArrayList<ColorChar> chars = new ArrayList<>();
        for (String ss: s)
            for (int i = 0; i < ss.length(); i++)
                chars.add(new ColorChar(ss.charAt(i)));
        
        return new ColorString(chars.toArray(new ColorChar[chars.size()]));
    }
    
    /**
     * Converts the array of Strings provided into an array of
     * {@link ColorString ColorStrings}, one for each String.
     * @param s the array of Strings to convert into an array of
     * {@link ColorString ColorStrings}
     * @return an array of {@link ColorString ColorStrings}, one for each
     * String, with the characters of the Strings and no specified colors
     */
    public static ColorString[] toColorStringArray(String... s)
    {
        ColorString[] lines = new ColorString[s.length];
        
        for (int i = 0; i < s.length; i++)
            lines[i] = new ColorString(s[i]);
        
        return lines;
    }
    
    /**
     * Converts the 2D array of {@link ColorChar ColorChars} provided into an
     * array of {@link ColorString ColorStrings}, with each {@link ColorChar}[]
     * as a single {@link ColorString}.
     * @param a the array of {@link ColorChar ColorChars} to convert into an
     * array of {@link ColorString ColorStrings}
     * @return an array of {@link ColorString ColorStrings}, one for each
     * {@link ColorChar}
     */
    public static ColorString[] toColorStringArray(ColorChar[]... a)
    {
        ColorString[] lines = new ColorString[a.length];
        
        for (int line = 0; line < a.length; line++)
            lines[line] = new ColorString(a[line]);
        
        return lines;
    }
    
    /**
     * Converts the List of Strings provided into a List of
     * {@link ColorString ColorStrings}, one for each String.
     * @param s the List of Strings to convert into a List of
     * {@link ColorString ColorStrings}
     * @return a List of {@link ColorString ColorStrings}, one for each String,
     * with the characters of the Strings and no specified colors
     */
    public static List<ColorString> toColorStringList(List<String> s)
    {
        LinkedList<ColorString> lines = new LinkedList<>();
        
        for (String cur: s)
            lines.add(new ColorString(cur));
        
        return lines;
    }
    
    /**
     * Converts the 2D array of {@link ColorChar ColorChars} provided into a
     * List of {@link ColorString ColorStrings}, with each {@link ColorChar}[]
     * as a single {@link ColorString}.
     * @param a the array of {@link ColorChar ColorChars} to convert into a List
     * of {@link ColorString ColorStrings}
     * @return a List of {@link ColorString ColorStrings}, one for each
     * {@link ColorChar}
     */
    public static List<ColorString> toColorStringList(ColorChar[]... a)
    {
        LinkedList<ColorString> lines = new LinkedList<>();
        
        for (ColorChar[] cur: a)
            lines.add(new ColorString(cur));
        
        return lines;
    }
}