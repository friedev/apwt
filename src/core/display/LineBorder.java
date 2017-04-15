package core.display;

/**
 * 
 */
public class LineBorder
{
    public char end1;
    public char end2;
    public char line;
    
    public LineBorder(char e1, char e2, char l)
    {
        end1 = e1;
        end2 = e2;
        line = l;
    }
    
    public LineBorder(boolean horizontal, int sideWidth, int lineWidth)
    {
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
}