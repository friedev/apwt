package core.display;

import core.Point;

/**
 * A menu creator that can create specific menus with different line widths.
 */
public abstract class Menu
{
    public static final int DEFAULT_LINES = 1;
    
    public static boolean draw(Display display, Point corner1,
            Point corner2, int width)
    {
        if (corner1.equals(corner2) || corner1.x == corner2.x ||
                corner1.y == corner2.y ||
                !display.contains(corner1) ||
                !display.contains(corner2))
            return false;
        
        Point tl, tr, bl, br;
        if (corner1.y < corner2.y) // corner1 is above corner2
        {
            if (corner1.x < corner2.x) // corner1 is left of corner2
            {
                tl = corner1;
                tr = new Point(corner2.x, corner1.y);
                bl = new Point(corner1.x, corner2.y);
                br = corner2;
            }
            else // corner1 is right of corner2
            {
                tl = new Point(corner2.x, corner1.y);
                tr = corner1;
                bl = corner2;
                br = new Point(corner1.x, corner2.y);
            }
        }
        else // corner1 is below corner2
        {
            if (corner1.x < corner2.x) // corner1 is left of corner2
            {
                tl = new Point(corner1.x, corner2.y);
                tr = corner2;
                bl = corner1;
                br = new Point(corner2.x, corner1.y);
            }
            else // corner1 is right of corner2
            {
                tl = corner2;
                tr = new Point(corner1.x, corner2.y);
                bl = new Point(corner2.x, corner1.y);
                br = corner1;
            }
        }
        
        display.write(Line.topLeft(width),     tl);
        display.write(Line.topRight(width),    tr);
        display.write(Line.bottomLeft(width),  bl);
        display.write(Line.bottomRight(width), br);
        
        for (int x = tl.x + 1; x < tr.x; x++)
        {
            display.get().write(Line.horizontal(width), x, tl.y);
            display.get().write(Line.horizontal(width), x, bl.y);
        }
        
        for (int y = tl.y + 1; y < bl.y; y++)
        {
            display.get().write(Line.vertical(width), tl.x, y);
            display.get().write(Line.vertical(width), tr.x, y);
        }
        
        return true;
    }
    
    public static boolean draw(Display display, Point corner1,
            Point corner2)
        {return draw(display, corner1, corner2, DEFAULT_LINES);}
    
    public static boolean printBoxed(Display display, String[] text,
            int topLine, int leftIndent, int width)
    {
        if (!display.contains(new Point(topLine - 1, leftIndent - 1)))
            return false;
        
        int maxLength = 0;
        
        for (String line: text)
            if (line.length() > maxLength)
                maxLength = line.length();
        
        if (!display.contains(new Point(leftIndent + maxLength,
                topLine + text.length)))
            return false;
        
        display.write(text, new Point(leftIndent, topLine));
        
        return draw(display, new Point(leftIndent - 1, topLine - 1),
                new Point(leftIndent + maxLength, topLine + text.length),
                width);
    }
    
    public static boolean printBoxed(Display display, String[] text,
            int topLine, int leftIndent)
        {return printBoxed(display, text, topLine, leftIndent, DEFAULT_LINES);}
    
    public static boolean printCenterBoxed(Display display, String[] text,
            int topLine, int width)
    {
        if (!display.containsY(topLine - 1) ||
                !display.containsY(topLine + text.length))
            return false;
        
        int maxLength = 0;
        for (String line: text)
            if (line.length() > maxLength)
                maxLength = line.length();
        
        int center = display.getCharWidth() / 2;
        int offsetRight = ((int) maxLength) / 2;
        int offsetLeft;
        if ((((double) maxLength) / 2.0) % 1.0 == 0.5)
            offsetLeft = offsetRight + 1;
        else
            offsetLeft = offsetRight;
        
        // If the display width is odd, odd String lengths will have equal
        // offsets; if the display width is even, even String lengths have
        // equal offsets
        
        if (!display.containsX(center - offsetLeft - 1) ||
                !display.containsX(center + offsetRight))
            return false;
        
        display.writeCenter(text, topLine);
        
        return draw(display, new Point(center - offsetLeft - 1,
                topLine - 1), new Point(center + offsetRight,
                        topLine + text.length), width);
    }
    
    public static boolean printCenterBoxed(Display display, String[] text,
            int topLine)
        {return printCenterBoxed(display, text, topLine, DEFAULT_LINES);}
}