package core.display;

import core.Point;

/** * A tool for creating bordered windows and other shapes. */
public abstract class WindowBuilder
{
    public static final int DEFAULT_LINES = 1;
    
    public static boolean drawLine(Display display, Point end1, Point end2,
            LineBorder border)
    {
        if (end1.equals(end2) || (end1.x != end2.x && end1.y != end2.y) ||
                !display.contains(end1) || !display.contains(end2))
            return false;
        
        border.syncDefaults(display);
        
        display.write(border.end1, end1, border.foreground, border.background);
        display.write(border.end2, end2, border.foreground, border.background);
        
        int start, end;
        
        if (end1.x == end2.x)
        {
            if (end1.y < end2.y)
            {
                start = end1.y;
                end   = end2.y;
            }
            else
            {
                start = end2.y;
                end   = end1.y;
            }
            
            for (int i = start + 1; i < end; i++)
                display.write(border.line, new Point(end1.x, i),
                        border.foreground, border.background);
        }
        else
        {
            if (end1.x < end2.x)
            {
                start = end1.x;
                end   = end2.x;
            }
            else
            {
                start = end2.x;
                end   = end1.x;
            }
            
            for (int i = start + 1; i < end; i++)
                display.write(border.line, new Point(i, end1.y),
                        border.foreground, border.background);
        }
        
        return true;
    }
    
    public static boolean drawBorder(Display display, Point corner1,
            Point corner2, Border border)
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
        
        border.syncDefaults(display);
        
        display.write(border.cornerTL, tl,
                border.foreground, border.background);
        display.write(border.cornerTR, tr,
                border.foreground, border.background);
        display.write(border.cornerBL, bl,
                border.foreground, border.background);
        display.write(border.cornerBR, br,
                border.foreground, border.background);
        
        for (int x = tl.x + 1; x < tr.x; x++)
        {
            display.getPanel().write(border.edgeT, x, tl.y,
                    border.foreground, border.background);
            display.getPanel().write(border.edgeB, x, bl.y,
                    border.foreground, border.background);
        }
        
        for (int y = tl.y + 1; y < bl.y; y++)
        {
            display.getPanel().write(border.edgeL, tl.x, y,
                    border.foreground, border.background);
            display.getPanel().write(border.edgeR, tr.x, y,
                    border.foreground, border.background);
        }
        
        return true;
    }
    
    public static boolean drawBorder(Display display, Point corner1,
            Point corner2, int width)
        {return drawBorder(display, corner1, corner2, new Border(width));}
    
    public static boolean drawBorder(Display display, Point corner1,
            Point corner2)
        {return drawBorder(display, corner1, corner2, DEFAULT_LINES);}
    
    public static boolean printBoxed(Display display, ColorSet[] text,
            int topLine, int leftIndent, Border border, LineBorder separator)
    {
        if (!display.contains(new Point(topLine - 1, leftIndent - 1)))
            return false;
        
        int maxLength = 0;
        
        for (ColorSet line: text)
            if (line != null && line.getSet().size() > maxLength)
                maxLength = line.getSet().size();
        
        if (!display.contains(new Point(leftIndent + maxLength,
                topLine + text.length)))
            return false;
        
        display.write(text, new Point(leftIndent, topLine));
        
        boolean returnValue = WindowBuilder.drawBorder(display,
                new Point(leftIndent - 1, topLine - 1),
                new Point(leftIndent + maxLength, topLine + text.length),
                border);
        
        if (separator != null)
            for (int line = 0; line < text.length; line++)
                if (text[line] == null)
                    drawLine(display, new Point(leftIndent - 1, topLine + line),
                            new Point(leftIndent + maxLength, topLine + line),
                            separator);
        
        return returnValue;
    }
    
    public static boolean printBoxed(Display display, ColorString[] text,
            int topLine, int leftIndent, Border border, LineBorder separator)
    {
        return printBoxed(display, ColorSet.toColorSetArray(text), topLine,
                leftIndent, border, separator);
    }
    
    public static boolean printBoxed(Display display, String[] text,
            int topLine, int leftIndent, Border border, LineBorder separator)
    {
        return printBoxed(display, ColorSet.toColorSetArray(text), topLine,
                leftIndent, border, separator);
    }
    
    public static boolean printBoxed(Display display, ColorSet[] text,
            int topLine, int leftIndent, Border border)
        {return printBoxed(display, text, topLine, leftIndent, border, null);}
    
    public static boolean printBoxed(Display display, ColorString[] text,
            int topLine, int leftIndent, Border border)
    {
        return printBoxed(display, ColorSet.toColorSetArray(text), topLine,
                leftIndent, border, null);
    }
    
    public static boolean printBoxed(Display display, String[] text,
            int topLine, int leftIndent, Border border)
    {
        return printBoxed(display, ColorSet.toColorSetArray(text), topLine,
                leftIndent, border, null);
    }
    
    public static boolean printBoxed(Display display, String[] text,
            int topLine, int leftIndent, int width)
    {
        return printBoxed(display, text, topLine, leftIndent,
                new Border(width));
    }
    
    public static boolean printBoxed(Display display, String[] text,
            int topLine, int leftIndent)
        {return printBoxed(display, text, topLine, leftIndent, DEFAULT_LINES);}
    
    public static boolean printCenterBoxed(Display display, ColorString[] text,
            int topLine, Border border, LineBorder separator)
    {
        if (!display.containsY(topLine - 1) ||
                !display.containsY(topLine + text.length))
            return false;
        
        int maxLength = 0;
        for (ColorString line: text)
            if (line != null && line.string.length() > maxLength)
                maxLength = line.string.length();
        
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
        
        boolean returnValue = WindowBuilder.drawBorder(display,
                new Point(center - offsetLeft - 1, topLine - 1),
                new Point(center + offsetRight, topLine + text.length), border);
        
        if (separator != null)
            for (int line = 0; line < text.length; line++)
                if (text[line] == null)
                    drawLine(display,
                            new Point(center - offsetLeft - 1, topLine + line),
                            new Point(center + offsetRight, topLine + line),
                            separator);
        
        return returnValue;
    }
    
    public static boolean printCenterBoxed(Display display, String[] text,
            int topLine, Border border, LineBorder separator)
    {
        return printCenterBoxed(display, ColorString.toColorStringArray(text),
                topLine, border, separator);
    }
    
    public static boolean printCenterBoxed(Display display, ColorString[] text,
            int topLine, Border border)
        {return printCenterBoxed(display, text, topLine, border, null);}
    
    public static boolean printCenterBoxed(Display display, String[] text,
            int topLine, Border border)
        {return printCenterBoxed(display, text, topLine, border, null);}
    
    public static boolean printCenterBoxed(Display display, String[] text,
            int topLine, int width)
        {return printCenterBoxed(display, text, topLine, new Border(width));}
    
    public static boolean printCenterBoxed(Display display, String[] text,
            int topLine)
        {return printCenterBoxed(display, text, topLine, DEFAULT_LINES);}
}