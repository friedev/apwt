package corelib.display.window;

import corelib.display.glyphs.ColoredObject;
import corelib.display.LineChars;
import java.awt.Color;

/**
 * A class containing the characters necessary to draw a line with two
 * endpoints; fully customizable.
 */
public class Line extends ColoredObject
{
    /** The character at the first (top or left) endpoint. */
    public char end1;
    /** The character at the second (bottom or right) endpoint. */
    public char end2;
    /** The characters to print between endpoints on the line itself. */
    public char line;
    
    /**
     * Specifies the direction in which the line is meant to go; true for
     * horizontal, false for vertical.
     */
    public boolean horizontal;
    
    /**
     * Creates a Border with explicitly-defined characters and colors.
     * @param e1 the character at the first endpoint
     * @param e2 the character at the second endpoint
     * @param l the characters on the line
     * @param h true if the line is meant to be displayed horizontally
     * @param f the color of the characters
     * @param b the color of the background
     */
    public Line(char e1, char e2, char l, boolean h, Color f,
            Color b)
    {
        super(f, b);
        end1 = e1;
        end2 = e2;
        line = l;
        horizontal = h;
    }
    
    /**
     * Creates a Line with explicitly-defined characters and a foreground color.
     * @param e1 the character at the first endpoint
     * @param e2 the character at the second endpoint
     * @param l the characters on the line
     * @param h true if the line is meant to be displayed horizontally
     * @param f the color of the characters
     */
    public Line(char e1, char e2, char l, boolean h, Color f)
        {this(e1, e2, l, h, f, null);}
    
    /**
     * Creates a Line with explicitly-defined characters, but no specified
     * colors.
     * @param e1 the character at the first endpoint
     * @param e2 the character at the second endpoint
     * @param l the characters on the line
     * @param h true if the line is meant to be displayed horizontally
     */
    public Line(char e1, char e2, char l, boolean h)
        {this(e1, e2, l, h, null, null);}
    
    /**
     * Creates a Line connecting two side lines of the provided widths, along
     * with specified colors.
     * @param h true if the connection is horizontal
     * @param side1Width the width of the first side line
     * @param side2Width the width of the second side line
     * @param lineWidth the width of the line
     * @param f the color of the characters
     * @param b the color of the background
     */
    public Line(boolean h, int side1Width, int side2Width, int lineWidth,
            Color f, Color b)
    {
        super(f, b);
        horizontal = h;
        
        if (horizontal)
        {
            end1 = LineChars.splitRight(side1Width, lineWidth);
            end2 = LineChars.splitLeft(side2Width, lineWidth);
            line = LineChars.horizontal(lineWidth);
        }
        else
        {
            end1 = LineChars.splitDown(side1Width, lineWidth);
            end2 = LineChars.splitUp(side2Width, lineWidth);
            line = LineChars.vertical(lineWidth);
        }
    }
    
    /**
     * Creates a Line connecting two side lines of the provided widths, along
     * with a specified foreground color.
     * @param h true if the connection is horizontal
     * @param side1Width the width of the first side line
     * @param side2Width the width of the second side line
     * @param lineWidth the width of the line
     * @param f the color of the characters
     */
    public Line(boolean h, int side1Width, int side2Width, int lineWidth,
            Color f)
        {this(h, side1Width, side2Width, lineWidth, f, null);}
    
    /**
     * Creates a Line connecting two side lines of the provided widths, but no
     * specified colors
     * @param h true if the connection is horizontal
     * @param side1Width the width of the first side line
     * @param side2Width the width of the second side line
     * @param lineWidth the width of the line
     */
    public Line(boolean h, int side1Width, int side2Width, int lineWidth)
        {this(h, side1Width, side2Width, lineWidth, null, null);}
    
    /**
     * Creates a Line connecting two side lines of the same provided width,
     * along with specified colors.
     * @param h true if the connection is horizontal
     * @param sideWidth the width of both side lines
     * @param lineWidth the width of the line
     * @param f the color of the characters
     * @param b the color of the background
     */
    public Line(boolean h, int sideWidth, int lineWidth, Color f, Color b)
        {this(h, sideWidth, sideWidth, lineWidth, f, b);}
    
    /**
     * Creates a Line connecting two side lines of the same provided width,
     * along with a specified foreground color.
     * @param h true if the connection is horizontal
     * @param sideWidth the width of both side lines
     * @param lineWidth the width of the line
     * @param f the color of the characters
     */
    public Line(boolean h, int sideWidth, int lineWidth, Color f)
        {this(h, sideWidth, sideWidth, lineWidth, f, null);}
    
    /**
     * Creates a Line connecting two side lines of the same provided width, but
     * no specified colors.
     * @param h true if the connection is horizontal
     * @param sideWidth the width of both side lines
     * @param lineWidth the width of the line
     */
    public Line(boolean h, int sideWidth, int lineWidth)
        {this(h, sideWidth, sideWidth, lineWidth, null, null);}
    
    /**
     * Creates a Line with the provided width and specified colors; endpoints
     * will use the same characters as the line itself.
     * @param h true if the line is horizontal
     * @param lineWidth the width of the line
     * @param f the color of the characters
     * @param b the color of the background
     */
    public Line(boolean h, int lineWidth, Color f, Color b)
        {this(h, LineChars.getLine(h, lineWidth), f, b);}
    
    /**
     * Creates a Line with the provided width and a specified foreground color;
     * endpoints will use the same characters as the line itself.
     * @param h true if the line is horizontal
     * @param lineWidth the width of the line
     * @param f the color of the characters
     */
    public Line(boolean h, int lineWidth, Color f)
        {this(h, lineWidth, f, null);}
    
    /**
     * Creates a Line with the provided width and no specified colors; endpoints
     * will use the same characters as the line itself.
     * @param h true if the line is horizontal
     * @param lineWidth the width of the line
     */
    public Line(boolean h, int lineWidth)
        {this(h, lineWidth, null, null);}
    
    /**
     * Creates a Line with one character used for the line and both endpoints,
     * along with specified colors.
     * @param h true if the line is meant to be displayed horizontally
     * @param c the character to use for the line and both endpoints
     * @param f the color of the characters
     * @param b the color of the background
     */
    public Line(boolean h, char c, Color f, Color b)
        {this(c, c, c, h, f, b);}
    
    /**
     * Creates a Line with one character used for the line and both endpoints,
     * along with a specified foreground color.
     * @param h true if the line is meant to be displayed horizontally
     * @param c the character to use for the line and both endpoints
     * @param f the color of the characters
     */
    public Line(boolean h, char c, Color f)
        {this(c, c, c, h, f, null);}
    
    /**
     * Creates a Line with one character used for the line and both endpoints,
     * but no specified colors.
     * @param h true if the line is meant to be displayed horizontally
     * @param c the character to use for the line and both endpoints
     */
    public Line(boolean h, char c)
        {this(c, c, c, h, null, null);}
}