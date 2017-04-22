package core.display;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A system that uses ColorChars to map characters to foreground and background
 * colors.
 */
public class ColorSet
{
    private List<ColorChar> set;
    
    public ColorSet(List<ColorChar> s)
        {set = s;}
    
    public ColorSet(ColorChar[] c)
        {this(new ArrayList<>(java.util.Arrays.asList(c)));}
    
    public ColorSet(ColorString s) {this(s.toCharArray());   }
    public ColorSet(String s)      {this(new ColorString(s));}
    public ColorSet()              {this(new ArrayList<>()); }
    
    public List getSet() {return set;}
    
    public ColorChar getColorChar(char character)
    {
        for (ColorChar cc: set)
            if (cc.character == character)
                return cc;
        
        return null;
    }
    
    public ColorChar[] toCharArray()
        {return set.toArray(new ColorChar[set.size()]);}
    
    
    public static ColorSet toColorSet(String[] s)
    {
        ArrayList<ColorChar> chars = new ArrayList<>();
        for (String ss: s)
            for (int i = 0; i < ss.length(); i++)
                chars.add(new ColorChar(ss.charAt(i)));
        
        return new ColorSet(chars.toArray(new ColorChar[chars.size()]));
    }
    
    public static ColorSet toColorSet(ColorString[] s)
    {
        ArrayList<ColorChar> chars = new ArrayList<>();
        for (ColorString cs: s)
            chars.addAll(Arrays.asList(cs.toCharArray()));
        
        return new ColorSet(chars.toArray(new ColorChar[chars.size()]));
    }
    
    public static ColorSet[] toColorSetArray(String[] s)
    {
        ColorSet[] lines = new ColorSet[s.length];
        
        for (int i = 0; i < s.length; i++)
            lines[i] = new ColorSet(s[i]);
        
        return lines;
    }
    
    public static ColorSet[] toColorSetArray(ColorString[] s)
    {
        ColorSet[] lines = new ColorSet[s.length];
        
        for (int i = 0; i < s.length; i++)
            lines[i] = new ColorSet(s[i]);
        
        return lines;
    }
}