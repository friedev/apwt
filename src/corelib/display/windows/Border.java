package corelib.display.windows;

import corelib.display.glyphs.ColoredObject;
import corelib.display.LineChars;
import java.awt.Color;

/**
 * A class containing the characters necessary to draw a rectangle; fully
 * customizable.
 */
public class Border extends ColoredObject
{
    /** The character on the top edge. */
    public char edgeT;
    /** The character on the bottom edge. */
    public char edgeB;
    /** The character on the left edge. */
    public char edgeL;
    /** The character on the right edge. */
    public char edgeR;
    
    /** The character in the top-left corner. */
    public char cornerTL;
    /** The character in the top-right corner. */
    public char cornerTR;
    /** The character in the bottom-left corner. */
    public char cornerBL;
    /** The character in the bottom-right corner. */
    public char cornerBR;
    
    /**
     * Creates a Border with explicitly-defined characters and colors.
     * @param t the character on the top edge
     * @param b the character on the bottom edge
     * @param l the character on the left edge
     * @param r the character on the right edge
     * @param tl the character in the top-left corner
     * @param tr the character in the top-right corner
     * @param bl the character in the bottom-left corner
     * @param br the character in the bottom-right corner
     * @param fc the color of the characters
     * @param bc the color of the background
     */
    public Border(char t, char b, char l, char r, char tl, char tr, char bl,
            char br, Color fc, Color bc)
    {
        super(fc, bc);
        
        edgeT = t;
        edgeB = b;
        edgeL = l;
        edgeR = r;
        
        cornerTL = tl;
        cornerTR = tr;
        cornerBL = bl;
        cornerBR = br;
    }
    
    /**
     * Creates a Border with explicitly-defined characters and a foreground
     * color.
     * @param t the character on the top edge
     * @param b the character on the bottom edge
     * @param l the character on the left edge
     * @param r the character on the right edge
     * @param tl the character in the top-left corner
     * @param tr the character in the top-right corner
     * @param bl the character in the bottom-left corner
     * @param br the character in the bottom-right corner
     * @param fc the color of the characters
     */
    public Border(char t, char b, char l, char r, char tl, char tr, char bl,
            char br, Color fc)
        {this(t, b, l, r, tl, tr, bl, br, fc, null);}
    
    /**
     * Creates a Border with explicitly-defined characters, but no specified
     * colors.
     * @param t the character on the top edge
     * @param b the character on the bottom edge
     * @param l the character on the left edge
     * @param r the character on the right edge
     * @param tl the character in the top-left corner
     * @param tr the character in the top-right corner
     * @param bl the character in the bottom-left corner
     * @param br the character in the bottom-right corner
     */
    public Border(char t, char b, char l, char r, char tl, char tr, char bl,
            char br)
        {this(t, b, l, r, tl, tr, bl, br, null, null);}
    
    /**
     * Creates a Border with one character used on every edge and corner, along
     * with specified colors.
     * @param c the character to use on every part of the border
     * @param fc the color of the characters
     * @param bc the color of the background
     */
    public Border(char c, Color fc, Color bc)
        {this(c, c, c, c, c, c, c, c, fc, bc);}
    
    /**
     * Creates a Border with one character used on every edge and corner, along
     * with a specified foreground color.
     * @param c the character to use on every part of the border
     * @param fc the color of the characters
     */
    public Border(char c, Color fc)
        {this(c, fc, null);}
    
    /**
     * Creates a Border with one character used on every edge and corner, but no
     * specified colors.
     * @param c the character to use on every part of the border
     */
    public Border(char c)
        {this(c, null, null);}
    
    /**
     * Creates a Border with lines of the specified width, along with specified
     * colors.
     * @param lineWidth the width of the line characters to use, must be 1 or 2
     * @param f the color of the characters
     * @param b the color of the background
     */
    public Border(int lineWidth, Color f, Color b)
    {
        this(LineChars.horizontal(lineWidth), LineChars.horizontal(lineWidth),
             LineChars.vertical(lineWidth),   LineChars.vertical(lineWidth),
             LineChars.topLeft(lineWidth),    LineChars.topRight(lineWidth),
             LineChars.bottomLeft(lineWidth), LineChars.bottomRight(lineWidth),
             f, b);
    }
    
    /**
     * Creates a Border with lines of the specified width, along with a
     * specified foreground color.
     * @param lineWidth the width of the line characters to use, must be 1 or 2
     * @param f the color of the characters
     */
    public Border(int lineWidth, Color f)
        {this(lineWidth, f, null);}
    
    /**
     * Creates a Border with lines of the specified width, but no specified
     * colors.
     * @param lineWidth the width of the line characters to use, must be 1 or 2
     */
    public Border(int lineWidth)
        {this(lineWidth, null, null);}
}