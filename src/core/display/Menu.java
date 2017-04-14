package core.display;

import asciiPanel.AsciiPanel;
import map.Point;

/**
 * A menu creator that can create specific menus with different line widths.
 */
public abstract class Menu
{
    public static final int DEFAULT_LINES = 1;
    
    // TODO add limits so that nothing is drawn outside the panel
    
    public static boolean draw(AsciiPanel terminal, Point corner1,
            Point corner2, int width)
    {
        if (corner1.equals(corner2) || corner1.getX() == corner2.getX() ||
                corner1.getY() == corner2.getY())
            return false;
        
        Point tl, tr, bl, br;
        if (corner1.getY() < corner2.getY()) // corner1 is above corner2
        {
            if (corner1.getX() < corner2.getX()) // corner1 is left of corner2
            {
                tl = corner1;
                tr = new Point(corner2.getX(), corner1.getY());
                bl = new Point(corner1.getX(), corner2.getY());
                br = corner2;
            }
            else // corner1 is right of corner2
            {
                tl = new Point(corner2.getX(), corner1.getY());
                tr = corner1;
                bl = corner2;
                br = new Point(corner1.getX(), corner2.getY());
            }
        }
        else // corner1 is below corner2
        {
            if (corner1.getX() < corner2.getX()) // corner1 is left of corner2
            {
                tl = new Point(corner1.getX(), corner2.getY());
                tr = corner2;
                bl = corner1;
                br = new Point(corner2.getX(), corner1.getY());
            }
            else // corner1 is right of corner2
            {
                tl = corner2;
                tr = new Point(corner1.getX(), corner2.getY());
                bl = new Point(corner2.getX(), corner1.getY());
                br = corner1;
            }
        }
        
        Display.write(terminal, Line.topLeft(width),     tl);
        Display.write(terminal, Line.topRight(width),    tr);
        Display.write(terminal, Line.bottomLeft(width),  bl);
        Display.write(terminal, Line.bottomRight(width), br);
        
        for (int x = tl.getX() + 1; x < tr.getX(); x++)
        {
            terminal.write(Line.horizontal(width), x, tl.getY());
            terminal.write(Line.horizontal(width), x, bl.getY());
        }
        
        for (int y = tl.getY() + 1; y < bl.getY(); y++)
        {
            terminal.write(Line.vertical(width), tl.getX(), y);
            terminal.write(Line.vertical(width), tr.getX(), y);
        }
        
        return true;
    }
    
    public static boolean draw(AsciiPanel terminal, Point corner1,
            Point corner2)
        {return draw(terminal, corner1, corner2, DEFAULT_LINES);}
    
    public static boolean printBoxed(AsciiPanel terminal, String[] text,
            int topLine, int leftIndent, int width)
    {
        int maxLength = 0;
        for (int i = 0; i < text.length; i++)
        {
            terminal.write(text[i], leftIndent, topLine + i);
            
            if (text[i].length() > maxLength)
                maxLength = text[i].length();
        }
        
        return Menu.draw(terminal, new Point(leftIndent - 1, topLine - 1),
                new Point(leftIndent + maxLength, topLine + text.length), 1);
    }
    
    public static boolean printBoxed(AsciiPanel terminal, String[] text,
            int topLine, int leftIndent)
        {return printBoxed(terminal, text, topLine, leftIndent, DEFAULT_LINES);}
    
    public static boolean printCenterBoxed(AsciiPanel terminal, String[] text,
            int topLine, int width)
    {
        int maxLength = 0;
        for (int i = 0; i < text.length; i++)
        {
            terminal.writeCenter(text[i], topLine + i);
            
            if (text[i].length() > maxLength)
                maxLength = text[i].length();
        }
        
        int center = terminal.getWidthInCharacters() / 2;
        int offsetRight = ((int) maxLength) / 2;
        int offsetLeft;
        if ((((double) maxLength) / 2.0) % 1.0 == 0.5)
            offsetLeft = offsetRight + 1;
        else
            offsetLeft = offsetRight;
        // odd length strings will have even lengths on both sides!
        
        return Menu.draw(terminal, new Point(center - offsetLeft - 1,
                topLine - 1), new Point(center + offsetRight,
                        topLine + text.length), 1);
    }
    
    public static boolean printCenterBoxed(AsciiPanel terminal, String[] text,
            int topLine)
        {return printCenterBoxed(terminal, text, topLine, DEFAULT_LINES);}
}