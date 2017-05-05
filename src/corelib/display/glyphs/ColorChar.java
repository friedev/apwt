package corelib.display.glyphs;

import java.awt.Color;

/**
 * A container class for a character and its foreground and background colors.
 */
public class ColorChar extends ColoredObject
{
    /** The ColorChar's actual character. */
    private char character;
    
    /**
     * Creates a ColorChar from a character and its two colors.
     * @param c the character
     * @param f the color of the character
     * @param b the color of the background
     */
    public ColorChar(char c, Color f, Color b)
    {
        super(f, b);
        character = c;
    }
    
    public ColorChar(ColorChar copying)
    {
        this(copying.character, copying.getForeground(),
                copying.getBackground());
    }
    
    /**
     * Creates a ColorChar from a character and a foreground color, but no
     * specified background color.
     * @param character the character
     * @param foreground the color of the character
     */
    public ColorChar(char character, Color foreground)
        {this(character, foreground, null);}
    
    /**
     * Creates a ColorChar from a character and no specified colors.
     * @param character the character
     */
    public ColorChar(char character)
        {this(character, null, null);}
    
    public char getChar()
        {return character;}
    
    public void setChar(char character)
        {this.character = character;}
    
    @Override
    public boolean equals(Object o)
    {
        if (o == null || !(o instanceof ColorChar))
            return false;
        
        ColorChar cast = (ColorChar) o;
        if (character != cast.character)
            return false;
        
        if (getForeground() == null && cast.getForeground() != null)
            return false;
        
        if (getBackground() == null && cast.getBackground() != null)
            return false;
        
        return getForeground().equals(cast.getForeground()) &&
               getBackground().equals(cast.getBackground());
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 31 * hash + this.character;
        return hash;
    }
}