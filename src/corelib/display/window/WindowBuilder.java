package corelib.display.window;

import corelib.display.glyphs.ColorSet;
import corelib.display.glyphs.ColorString;
import corelib.display.Display;
import java.util.ArrayList;
import squidpony.squidmath.Coord;

/** A tool for creating bordered windows and other shapes. */
public abstract class WindowBuilder
{
    /** The default line width of Borders and Lines. */
    public static final int DEFAULT_LINES = 1;
    
    /**
     * Draws a Line between two endpoints to the provided Display.
     * @param display the Display to draw the line on
     * @param end1 the first endpoint; must be a different point than the second
     * endpoint, have one axis value in common, and be on the display
     * @param end2 the second endpoint; must be a different point than the first
     * endpoint, have one axis value in common, and be on the display
     * @param border the characters of the Line; if horizontal, points must
     * share y values, and the opposite is true with x values
     * @return true if the Line was successfully drawn
     */
    public static boolean drawLine(Display display, Coord end1, Coord end2,
            Line border)
    {
        if (end1.equals(end2) || (end1.x != end2.x && end1.y != end2.y) ||
                !display.contains(end1) || !display.contains(end2) ||
                (end1.x == end2.x && border.horizontal) ||
                (end1.y == end2.y && !border.horizontal))
            return false;
        
        border.syncDefaults(display);
        
        display.write(border.end1, end1, border.foreground, border.background);
        display.write(border.end2, end2, border.foreground, border.background);
        
        int start, end;
        
        if (!border.horizontal)
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
                display.write(border.line, Coord.get(end1.x, i),
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
                display.write(border.line, Coord.get(i, end1.y),
                        border.foreground, border.background);
        }
        
        return true;
    }
    
    /**
     * Draws a Border between two specified corners to the provided Display.
     * @param display the Display to draw the Border on
     * @param corner1 the first corner; must be a different point than the
     * second corner, share no axis values, and be on the display
     * @param corner2 the second corner; must be a different point than the
     * first corner, share no axis values, and be on the display
     * @param border the characters of the Border
     * @return true if the Border was successfully drawn
     */
    public static boolean drawBorder(Display display, Coord corner1,
            Coord corner2, Border border)
    {
        if (corner1.equals(corner2) || corner1.x == corner2.x ||
                corner1.y == corner2.y ||
                !display.contains(corner1) ||
                !display.contains(corner2))
            return false;
        
        Coord tl, tr, bl, br;
        if (corner1.y < corner2.y) // corner1 is above corner2
        {
            if (corner1.x < corner2.x) // corner1 is left of corner2
            {
                tl = corner1;
                tr = Coord.get(corner2.x, corner1.y);
                bl = Coord.get(corner1.x, corner2.y);
                br = corner2;
            }
            else // corner1 is right of corner2
            {
                tl = Coord.get(corner2.x, corner1.y);
                tr = corner1;
                bl = corner2;
                br = Coord.get(corner1.x, corner2.y);
            }
        }
        else // corner1 is below corner2
        {
            if (corner1.x < corner2.x) // corner1 is left of corner2
            {
                tl = Coord.get(corner1.x, corner2.y);
                tr = corner2;
                bl = corner1;
                br = Coord.get(corner2.x, corner1.y);
            }
            else // corner1 is right of corner2
            {
                tl = corner2;
                tr = Coord.get(corner1.x, corner2.y);
                bl = Coord.get(corner2.x, corner1.y);
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
    
    /**
     * Draws a Border of the specified width between two specified corners to
     * the provided Display.
     * @param display the Display to draw the Border on
     * @param corner1 the first corner; must be a different point than the
     * second corner, share no axis values, and be on the display
     * @param corner2 the second corner; must be a different point than the
     * first corner, share no axis values, and be on the display
     * @param width the width of the Border to draw; must be 1 or 2
     * @return true if the Border was successfully drawn
     */
    public static boolean drawBorder(Display display, Coord corner1,
            Coord corner2, int width)
        {return drawBorder(display, corner1, corner2, new Border(width));}
    
    /**
     * Draws a Border of the default width between two specified corners to the
     * provided Display.
     * @param display the Display to draw the Border on
     * @param corner1 the first corner; must be a different point than the
     * second corner, share no axis values, and be on the display
     * @param corner2 the second corner; must be a different point than the
     * first corner, share no axis values, and be on the display
     * @return true if the Border was successfully drawn
     */
    public static boolean drawBorder(Display display, Coord corner1,
            Coord corner2)
        {return drawBorder(display, corner1, corner2, DEFAULT_LINES);}
    
    /**
     * Prints the provided text surrounded by the provided Border to the
     * provided display.
     * @param display the Display to draw on
     * @param text the lines of text, as ColorSets, to print
     * @param topLine the line on which the first ColorSet will be written
     * @param leftIndent the number of characters from the left the text will be
     * printed
     * @param border the Border with which to surround the text
     * @param separators the Lines with which to separate text
     * @return true if the operation was successful
     */
    public static boolean printBoxed(Display display, ColorSet[] text,
            int topLine, int leftIndent, Border border, Line[] separators)
    {
        if (!display.contains(Coord.get(topLine - 1, leftIndent - 1)) ||
                text == null || text.length == 0)
            return false;
        
        int nBlocks = 1;
        
        for (ColorSet line: text)
            if (line == null)
                nBlocks++;
        
        ArrayList<ColorSet>[] blocks = new ArrayList[nBlocks]; 
        
        int curBlock = 0;
        blocks[0] = new ArrayList<>();
        
        for (ColorSet line: text)
        {
            if (line != null)
            {
                blocks[curBlock].add(line);
            }
            else
            {
                curBlock++;
                blocks[curBlock] = new ArrayList<>();
            }
        }
        
        int curLine   = topLine;
        int curIndent = leftIndent;
        int overallMaxLength = 0;
        int curMaxLines  = blocks[0].size();
        int overallLines = blocks[0].size();
        Coord[] endpoints = null; // Suppresses undefined warning
        if (separators != null)
            endpoints = new Coord[separators.length * 2];
        
        for (int block = 0; block < blocks.length; block++)
        {
            display.write(blocks[block].toArray(
                    new ColorSet[blocks[block].size()]),
                    Coord.get(curIndent, curLine));
            
            int curMaxLength = 0;
            for (ColorSet line: blocks[block])
                if (line.getSet().size() > curMaxLength)
                    curMaxLength = line.getSet().size();
            
            curMaxLength += curIndent - leftIndent;
            
            if (curMaxLength > overallMaxLength)
                overallMaxLength = curMaxLength;
            
            if (blocks[block].size() > curMaxLines)
                curMaxLines = blocks[block].size();
            
            if (block == blocks.length - 1)
                break;
            
            if (separators != null && separators.length - 1 >= block &&
                    separators[block] != null)
            {
                if (separators[block].horizontal)
                {
                    curIndent = leftIndent;

                    endpoints[block * 2] = Coord.get(leftIndent - 1,
                            curLine + curMaxLines);
                    endpoints[block * 2 + 1] =
                            Coord.get(curIndent + overallMaxLength,
                                    curLine + curMaxLines);

                    curLine += curMaxLines + 1;
                    curMaxLines = blocks[block + 1].size();
                    overallLines += blocks[block + 1].size() + 1;
                }
                else
                {
                    curIndent += curMaxLength + 1;

                    endpoints[block * 2] = Coord.get(curIndent - 1, curLine - 1);
                    endpoints[block * 2 + 1] = Coord.get(curIndent - 1,
                            curLine + curMaxLines);

                    // line counts stay the same
                }
            }
            else
            {
                curIndent = leftIndent;
                curLine += curMaxLines + 1;
                curMaxLines = blocks[block + 1].size();
                overallLines += blocks[block + 1].size() + 1;
            }
        }
        
        boolean returnValue = true;
        
        if (border != null)
        {
            returnValue = returnValue && WindowBuilder.drawBorder(display,
                    Coord.get(leftIndent - 1, topLine - 1),
                    Coord.get(leftIndent + overallMaxLength,
                            topLine + overallLines), border);
        }
        
        if (separators != null && separators.length > 0)
        {
            for (int separator = 0; separator < separators.length; separator++)
            {
                if (separators.length - 1 >= separator &&
                        separators[separator] != null &&
                        separators[separator].horizontal)
                {
                    returnValue = returnValue &&
                        drawLine(display, endpoints[separator * 2],
                        endpoints[separator * 2 + 1], separators[separator]);
                }
            }
            
            for (int separator = 0; separator < separators.length; separator++)
            {
                if (separators.length - 1 >= separator &&
                        separators[separator] != null &&
                        !separators[separator].horizontal)
                {
                    returnValue = returnValue &&
                        drawLine(display, endpoints[separator * 2],
                        endpoints[separator * 2 + 1], separators[separator]);
                }
            }
        }
        
        return returnValue;
    }
    
    /**
     * Prints the provided text surrounded by the provided Border to the
     * provided display.
     * @param display the Display to draw on
     * @param text the lines of text, as ColorStrings, to print
     * @param topLine the line on which the first ColorString will be written
     * @param leftIndent the number of characters from the left the text will be
     * printed
     * @param border the Border with which to surround the text
     * @param separators the Lines with which to separate text
     * @return true if the operation was successful
     */
    public static boolean printBoxed(Display display, ColorString[] text,
            int topLine, int leftIndent, Border border, Line[] separators)
    {
        return printBoxed(display, ColorSet.toColorSetArray(text), topLine,
                leftIndent, border, separators);
    }
    
    /**
     * Prints the provided text surrounded by the provided Border to the
     * provided display.
     * @param display the Display to draw on
     * @param text the lines of text, as Strings, to print
     * @param topLine the line on which the first String will be written
     * @param leftIndent the number of characters from the left the text will be
     * printed
     * @param border the Border with which to surround the text
     * @param separators the Lines with which to separate text
     * @return true if the operation was successful
     */
    public static boolean printBoxed(Display display, String[] text,
            int topLine, int leftIndent, Border border, Line[] separators)
    {
        return printBoxed(display, ColorSet.toColorSetArray(text), topLine,
                leftIndent, border, separators);
    }
    
    /**
     * Prints the provided text surrounded by the provided Border to the
     * provided display; no separators will be used.
     * @param display the Display to draw on
     * @param text the lines of text, as ColorSets, to print
     * @param topLine the line on which the first ColorSet will be written
     * @param leftIndent the number of characters from the left the text will be
     * printed
     * @param border the Border with which to surround the text
     * @return true if the operation was successful
     */
    public static boolean printBoxed(Display display, ColorSet[] text,
            int topLine, int leftIndent, Border border)
        {return printBoxed(display, text, topLine, leftIndent, border, null);}
    
    /**
     * Prints the provided text surrounded by the provided Border to the
     * provided display; no separators will be used.
     * @param display the Display to draw on
     * @param text the lines of text, as ColorStrings, to print
     * @param topLine the line on which the first ColorString will be written
     * @param leftIndent the number of characters from the left the text will be
     * printed
     * @param border the Border with which to surround the text
     * @return true if the operation was successful
     */
    public static boolean printBoxed(Display display, ColorString[] text,
            int topLine, int leftIndent, Border border)
    {
        return printBoxed(display, ColorSet.toColorSetArray(text), topLine,
                leftIndent, border, null);
    }
    
    /**
     * Prints the provided text surrounded by the provided Border to the
     * provided display; no separators will be used.
     * @param display the Display to draw on
     * @param text the lines of text, as Strings, to print
     * @param topLine the line on which the first String will be written
     * @param leftIndent the number of characters from the left the text will be
     * printed
     * @param border the Border with which to surround the text
     * @return true if the operation was successful
     */
    public static boolean printBoxed(Display display, String[] text,
            int topLine, int leftIndent, Border border)
    {
        return printBoxed(display, ColorSet.toColorSetArray(text), topLine,
                leftIndent, border, null);
    }
    
    /**
     * Prints the provided text surrounded by a Border of the provided width to
     * the provided display; no separators will be used.
     * @param display the Display to draw on
     * @param text the lines of text, as Strings, to print
     * @param topLine the line on which the first String will be written
     * @param leftIndent the number of characters from the left the text will be
     * printed
     * @param width the width of the Border with which to surround the text
     * @return true if the operation was successful
     */
    public static boolean printBoxed(Display display, String[] text,
            int topLine, int leftIndent, int width)
    {
        return printBoxed(display, text, topLine, leftIndent,
                new Border(width));
    }
    
    /**
     * Prints the provided text surrounded by the default Border to the
     * provided display; no separators will be used.
     * @param display the Display to draw on
     * @param text the lines of text, as Strings, to print
     * @param topLine the line on which the first String will be written
     * @param leftIndent the number of characters from the left the text will be
     * printed
     * @return true if the operation was successful
     */
    public static boolean printBoxed(Display display, String[] text,
            int topLine, int leftIndent)
        {return printBoxed(display, text, topLine, leftIndent, DEFAULT_LINES);}
    
    /**
     * Prints the provided text surrounded by the provided Border to the center
     * of the provided display.
     * @param display the Display to draw on
     * @param text the lines of text, as ColorStrings, to print
     * @param topLine the line on which the first ColorString will be written
     * @param border the Border with which to surround the text
     * @param separator the Line to use for all horizontal separations
     * @return true if the operation was successful
     */
    public static boolean printCenterBoxed(Display display, ColorString[] text,
            int topLine, Border border, Line separator)
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
                Coord.get(center - offsetLeft - 1, topLine - 1),
                Coord.get(center + offsetRight, topLine + text.length), border);
        
        if (separator != null)
            for (int line = 0; line < text.length; line++)
                if (text[line] == null)
                    drawLine(display,
                            Coord.get(center - offsetLeft - 1, topLine + line),
                            Coord.get(center + offsetRight, topLine + line),
                            separator);
        
        return returnValue;
    }
    
    /**
     * Prints the provided text surrounded by the provided Border to the center
     * of the provided display.
     * @param display the Display to draw on
     * @param text the lines of text, as Strings, to print
     * @param topLine the line on which the first String will be written
     * @param border the Border with which to surround the text
     * @param separator the Line to use for all horizontal separations
     * @return true if the operation was successful
     */
    public static boolean printCenterBoxed(Display display, String[] text,
            int topLine, Border border, Line separator)
    {
        return printCenterBoxed(display, ColorString.toColorStringArray(text),
                topLine, border, separator);
    }
    
    /**
     * Prints the provided text surrounded by the provided Border to the center
     * of the provided display; no separators will be used.
     * @param display the Display to draw on
     * @param text the lines of text, as ColorStrings, to print
     * @param topLine the line on which the first ColorString will be written
     * @param border the Border with which to surround the text
     * @return true if the operation was successful
     */
    public static boolean printCenterBoxed(Display display, ColorString[] text,
            int topLine, Border border)
        {return printCenterBoxed(display, text, topLine, border, null);}
    
    /**
     * Prints the provided text surrounded by the provided Border to the center
     * of the provided display; no separators will be used.
     * @param display the Display to draw on
     * @param text the lines of text, as Strings, to print
     * @param topLine the line on which the first String will be written
     * @param border the Border with which to surround the text
     * @return true if the operation was successful
     */
    public static boolean printCenterBoxed(Display display, String[] text,
            int topLine, Border border)
        {return printCenterBoxed(display, text, topLine, border, null);}
    
    /**
     * Prints the provided text surrounded by a Border of the provided width to
     * the center of the provided display; no separators will be used.
     * @param display the Display to draw on
     * @param text the lines of text, as Strings, to print
     * @param topLine the line on which the first String will be written
     * @param width the width of the Border with which to surround the text
     * @return true if the operation was successful
     */
    public static boolean printCenterBoxed(Display display, String[] text,
            int topLine, int width)
        {return printCenterBoxed(display, text, topLine, new Border(width));}
    
    /**
     * Prints the provided text surrounded by the provided Border to the center
     * of the provided display; no separators will be used.
     * @param display the Display to draw on
     * @param text the lines of text, as Strings, to print
     * @param topLine the line on which the first String will be written
     * @return true if the operation was successful
     */
    public static boolean printCenterBoxed(Display display, String[] text,
            int topLine)
        {return printCenterBoxed(display, text, topLine, DEFAULT_LINES);}
}