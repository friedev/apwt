package core.display;

import java.awt.Color;

/**
 * 
 */
public class LineBorder extends ColoredObject
{
    public char end1;
    public char end2;
    public char line;
    public boolean horizontal;
    
    public LineBorder(char e1, char e2, char l, boolean h, Color f,
            Color b)
    {
        super(f, b);
        end1 = e1;
        end2 = e2;
        line = l;
        horizontal = h;
    }
    
    public LineBorder(char e1, char e2, char l, boolean h, Color f)
        {this(e1, e2, l, h, f, null);}
    
    public LineBorder(char e1, char e2, char l, boolean h)
        {this(e1, e2, l, h, null, null);}
    
    public LineBorder(boolean h, int side1Width, int side2Width, int lineWidth,
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
    
    public LineBorder(boolean h, int side1Width, int side2Width, int lineWidth,
            Color f)
        {this(h, side1Width, side2Width, lineWidth, f, null);}
    
    public LineBorder(boolean h, int side1Width, int side2Width, int lineWidth)
        {this(h, side1Width, side2Width, lineWidth, null, null);}
    
    public LineBorder(boolean h, int sideWidth, int lineWidth, Color f, Color b)
        {this(h, sideWidth, sideWidth, lineWidth, f, b);}
    
    public LineBorder(boolean h, int sideWidth, int lineWidth, Color f)
        {this(h, sideWidth, sideWidth, lineWidth, f, null);}
    
    public LineBorder(boolean h, int sideWidth, int lineWidth)
        {this(h, sideWidth, sideWidth, lineWidth, null, null);}
}