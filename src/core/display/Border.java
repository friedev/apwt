package core.display;

/**
 * 
 */
public class Border
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
            char br)
    {
        edgeT = t;
        edgeB = b;
        edgeL = l;
        edgeR = r;
        cornerTL = tl;
        cornerTR = tr;
        cornerBL = bl;
        cornerBR = br;
    }
    
    public Border(char c)
        {this(c, c, c, c, c, c, c, c);}
    
    public Border(int lineWidth)
    {
        this(Line.horizontal(lineWidth), Line.horizontal(lineWidth),
             Line.vertical(lineWidth),   Line.vertical(lineWidth),
             Line.topLeft(lineWidth),    Line.topRight(lineWidth),
             Line.bottomLeft(lineWidth), Line.bottomRight(lineWidth));
    }
}