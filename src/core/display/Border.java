package core.display;

import java.awt.Color;

/**
 * 
 */
public class Border extends ColoredObject
{
    public char edgeT;
    public char edgeB;
    public char edgeL;
    public char edgeR;
    
    public char cornerTL;
    public char cornerTR;
    public char cornerBL;
    public char cornerBR;
    
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
    
    public Border(char t, char b, char l, char r, char tl, char tr, char bl,
            char br, Color fc)
        {this(t, b, l, r, tl, tr, bl, br, fc, null);}
    
    public Border(char t, char b, char l, char r, char tl, char tr, char bl,
            char br)
        {this(t, b, l, r, tl, tr, bl, br, null, null);}
    
    public Border(char c, Color fc, Color bc)
        {this(c, c, c, c, c, c, c, c, fc, bc);}
    
    public Border(char c, Color fc)
        {this(c, fc, null);}
    
    public Border(char c)
        {this(c, null, null);}
    
    public Border(int lineWidth, Color f, Color b)
    {
        this(LineChars.horizontal(lineWidth), LineChars.horizontal(lineWidth),
             LineChars.vertical(lineWidth),   LineChars.vertical(lineWidth),
             LineChars.topLeft(lineWidth),    LineChars.topRight(lineWidth),
             LineChars.bottomLeft(lineWidth), LineChars.bottomRight(lineWidth),
             f, b);
    }
    
    public Border(int lineWidth, Color f)
        {this(lineWidth, f, null);}
    
    public Border(int lineWidth)
        {this(lineWidth, null, null);}
}