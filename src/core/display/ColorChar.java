package core.display;

import java.awt.Color;

/**
 * A container class for a character and its foreground and background colors.
 */
public class ColorChar extends ColoredObject
{
    /** The ColorChar's actual character. */
    public char character;
    
    /**
     * Creates a ColorChar from a character and its two colors.
     * @param c the character
     * @param f the color of the character
     * @param b the color of the background
     */
    public ColorChar(char c, Color f, Color b)
    {
        super(f, b);
        character  = c;
    }
    
    /**
     * Creates a ColorChar from a character and a foreground color, but no
     * specified background color.
     * @param c the character
     * @param f the color of the character
     */
    public ColorChar(char c, Color f)
        {this(c, f, null);}
    
    /**
     * Creates a ColorChar from a character and no specified colors.
     * @param c the character
     */
    public ColorChar(char c)
        {this(c, null, null);}
}