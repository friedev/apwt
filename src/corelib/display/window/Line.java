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
     * @param horizontal true if the connection is horizontal
     * @param side1Width the width of the first side line
     * @param side2Width the width of the second side line
     * @param lineWidth the width of the line
     * @param foreground the color of the characters
     * @param background the color of the background
     */
    public Line(boolean horizontal, int side1Width, int side2Width, int lineWidth,
            Color foreground, Color background)
    {
        super(foreground, background);
        this.horizontal = horizontal;
        
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
     * @param horizontal true if the connection is horizontal
     * @param side1Width the width of the first side line
     * @param side2Width the width of the second side line
     * @param lineWidth the width of the line
     * @param foreground the color of the characters
     */
    public Line(boolean horizontal, int side1Width, int side2Width,
            int lineWidth, Color foreground)
        {this(horizontal, side1Width, side2Width, lineWidth, foreground, null);}
    
    /**
     * Creates a Line connecting two side lines of the provided widths, but no
     * specified colors
     * @param horizontal true if the connection is horizontal
     * @param side1Width the width of the first side line
     * @param side2Width the width of the second side line
     * @param lineWidth the width of the line
     */
    public Line(boolean horizontal, int side1Width, int side2Width,
            int lineWidth)
        {this(horizontal, side1Width, side2Width, lineWidth, null, null);}
    
    /**
     * Creates a Line connecting two side lines of the same provided width,
     * along with specified colors.
     * @param horizontal true if the connection is horizontal
     * @param sideWidth the width of both side lines
     * @param lineWidth the width of the line
     * @param foreground the color of the characters
     * @param background the color of the background
     */
    public Line(boolean horizontal, int sideWidth, int lineWidth,
            Color foreground, Color background)
    {
        this(horizontal, sideWidth, sideWidth, lineWidth, foreground,
                background);
    }
    
    /**
     * Creates a Line connecting two side lines of the same provided width,
     * along with a specified foreground color.
     * @param horizontal true if the connection is horizontal
     * @param sideWidth the width of both side lines
     * @param lineWidth the width of the line
     * @param foreground the color of the characters
     */
    public Line(boolean horizontal, int sideWidth, int lineWidth,
            Color foreground)
        {this(horizontal, sideWidth, sideWidth, lineWidth, foreground, null);}
    
    /**
     * Creates a Line connecting two side lines of the same provided width, but
     * no specified colors.
     * @param horizontal true if the connection is horizontal
     * @param sideWidth the width of both side lines
     * @param lineWidth the width of the line
     */
    public Line(boolean horizontal, int sideWidth, int lineWidth)
        {this(horizontal, sideWidth, sideWidth, lineWidth, null, null);}
    
    /**
     * Creates a Line with the provided width and specified colors; endpoints
     * will use the same characters as the line itself.
     * @param horizontal true if the line is horizontal
     * @param lineWidth the width of the line
     * @param foreground the color of the characters
     * @param background the color of the background
     */
    public Line(boolean horizontal, int lineWidth, Color foreground,
            Color background)
    {
        this(horizontal, LineChars.getLine(horizontal, lineWidth), foreground,
                background);
    }
    
    /**
     * Creates a Line with the provided width and a specified foreground color;
     * endpoints will use the same characters as the line itself.
     * @param horizontal true if the line is horizontal
     * @param lineWidth the width of the line
     * @param foreground the color of the characters
     */
    public Line(boolean horizontal, int lineWidth, Color foreground)
        {this(horizontal, lineWidth, foreground, null);}
    
    /**
     * Creates a Line with the provided width and no specified colors; endpoints
     * will use the same characters as the line itself.
     * @param horizontal true if the line is horizontal
     * @param lineWidth the width of the line
     */
    public Line(boolean horizontal, int lineWidth)
        {this(horizontal, lineWidth, null, null);}
    
    /**
     * Creates a Line with one character used for the line and both endpoints,
     * along with specified colors.
     * @param horizontal true if the line is meant to be displayed horizontally
     * @param c the character to use for the line and both endpoints
     * @param foreground the color of the characters
     * @param background the color of the background
     */
    public Line(boolean horizontal, char c, Color foreground, Color background)
        {this(c, c, c, horizontal, foreground, background);}
    
    /**
     * Creates a Line with one character used for the line and both endpoints,
     * along with a specified foreground color.
     * @param horizontal true if the line is meant to be displayed horizontally
     * @param c the character to use for the line and both endpoints
     * @param foreground the color of the characters
     */
    public Line(boolean horizontal, char c, Color foreground)
        {this(c, c, c, horizontal, foreground, null);}
    
    /**
     * Creates a Line with one character used for the line and both endpoints,
     * but no specified colors.
     * @param horizontal true if the line is meant to be displayed horizontally
     * @param c the character to use for the line and both endpoints
     */
    public Line(boolean horizontal, char c)
        {this(c, c, c, horizontal, null, null);}
}