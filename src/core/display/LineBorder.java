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
    
    public LineBorder(char e1, char e2, char l, Color f, Color b)
    {
        super(f, b);
        end1 = e1;
        end2 = e2;
        line = l;
        foreground = null;
        background = null;
    }
    
    public LineBorder(char e1, char e2, char l, Color f)
        {this(e1, e2, l, f, null);}
    
    public LineBorder(char e1, char e2, char l)
        {this(e1, e2, l, null, null);}
    
    public LineBorder(boolean horizontal, int sideWidth, int lineWidth,
            Color f, Color b)
    {
        super(f, b);
        
        if (horizontal)
        {
            end1 = LineChars.splitRight(sideWidth, lineWidth);
            end2 = LineChars.splitLeft(sideWidth, lineWidth);
            line = LineChars.horizontal(lineWidth);
        }
        else
        {
            end1 = LineChars.splitDown(sideWidth, lineWidth);
            end2 = LineChars.splitUp(sideWidth, lineWidth);
            line = LineChars.vertical(lineWidth);
        }
    }
    
    public LineBorder(boolean horizontal, int sideWidth, int lineWidth,
            Color f)
        {this(horizontal, sideWidth, lineWidth, f, null);}
    
    public LineBorder(boolean horizontal, int sideWidth, int lineWidth)
        {this(horizontal, sideWidth, lineWidth, null, null);}
}