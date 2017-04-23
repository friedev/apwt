package core.display;

import java.awt.Color;

/**
 * A container class for a character and its foreground and background colors.
 */
public class ColorChar extends ColoredObject
{
    public char character;
    
    public ColorChar(char c, Color f, Color b)
    {
        super(f, b);
        character  = c;
    }
    
    public ColorChar(char c, Color f)
        {this(c, f, null);}
    
    public ColorChar(char c)
        {this(c, null, null);}
}