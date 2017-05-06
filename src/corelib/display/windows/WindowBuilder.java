package corelib.display.windows;

import corelib.display.glyphs.ColorSet;
import corelib.display.glyphs.ColorString;
import corelib.display.Display;
import corelib.display.ExtChars;
import corelib.display.glyphs.ColorChar;
import java.awt.Color;
import java.util.ArrayList;
import squidpony.squidmath.Coord;

/** A tool for creating bordered {@link Window Windows} and other shapes. */
public abstract class WindowBuilder
{
    /**
     * The default line width of {@link Border Borders} and {@link Line Lines}.
     */
    public static final int DEFAULT_LINES = 1;
    
    /**
     * Draws a {@link Line} between two endpoints to the provided Display.
     * @param display the {@link corelib.display.Display} to draw the
     * {@link Line} on
     * @param end1 the first endpoint; must be a different point than the second
     * endpoint, have one axis value in common, and be on the display
     * @param end2 the second endpoint; must be a different point than the first
     * endpoint, have one axis value in common, and be on the display
     * @param border the characters of the {@link Line}; if horizontal, points
     * must share y values, and the opposite is true with x values
     * @return true if the {@link Line} was successfully drawn
     */
    public static boolean drawLine(Display display, Coord end1, Coord end2,
            Line border)
    {
        if (end1.equals(end2))
            throw new IllegalArgumentException("Both endpoints may not be the "
                    + "same point");
        
        if (end1.x != end2.x && end1.y != end2.y)
            throw new IllegalArgumentException("Both endpoints must share an "
                    + "axis value");
        
        if (!display.contains(end1) || !display.contains(end2))
            throw new IndexOutOfBoundsException("The display must contain both "
                    + "endpoints");
        
        if ((end1.x == end2.x && border.horizontal) ||
                (end1.y == end2.y && !border.horizontal))
            throw new IllegalArgumentException("Endpoint dimension does not "
                    + "match line horizontal/vertical field");
        
        border.syncDefaults(display);
        
        display.write(end1, new ColorChar(border.end1,
                border.getForeground(), border.getBackground()));
        display.write(end2, new ColorChar(border.end2,
                border.getForeground(), border.getBackground()));
        
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
                display.write(Coord.get(end1.x, i), new ColorChar(border.line,
                        border.getForeground(), border.getBackground()));
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
                display.write(Coord.get(i, end1.y), new ColorChar(border.line,
                        border.getForeground(), border.getBackground()));
        }
        
        return true;
    }
    
    /**
     * Draws a {@link Border}  between two specified corners to the provided
     * {@link corelib.display.Display}, filled with the provided fill color.
     * @param display the {@link corelib.display.Display} to draw the
     * {@link Border} on
     * @param corner1 the first corner; must be a different point than the
     * second corner, share no axis values, and be on the display
     * @param corner2 the second corner; must be a different point than the
     * first corner, share no axis values, and be on the display
     * @param border the characters of the {@link Border}
     * @param fill the Color to fill the center of the {@link Border} with; if
     * null, no fill will be performed
     * @return true if the {@link Border} was successfully drawn
     */
    public static boolean drawBorder(Display display, Coord corner1,
            Coord corner2, Border border, Color fill)
    {
        if (corner1.x == corner2.x || corner1.y == corner2.y)
            throw new IllegalArgumentException("Corners must have different "
                    + "axis values");
        
        if (!display.contains(corner1) || !display.contains(corner2))
            throw new IllegalArgumentException("The display must contain both "
                    + "corners");
        
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
        
        display.write(tl, new ColorChar(border.cornerTL,
                border.getForeground(), border.getBackground()));
        display.write(tr, new ColorChar(border.cornerTR,
                border.getForeground(), border.getBackground()));
        display.write(bl, new ColorChar(border.cornerBL,
                border.getForeground(), border.getBackground()));
        display.write(br, new ColorChar(border.cornerBR,
                border.getForeground(), border.getBackground()));
        
        for (int x = tl.x + 1; x < tr.x; x++)
        {
            display.getPanel().write(border.edgeT, x, tl.y,
                    border.getForeground(), border.getBackground());
            display.getPanel().write(border.edgeB, x, bl.y,
                    border.getForeground(), border.getBackground());
        }
        
        for (int y = tl.y + 1; y < bl.y; y++)
        {
            display.getPanel().write(border.edgeL, tl.x, y,
                    border.getForeground(), border.getBackground());
            display.getPanel().write(border.edgeR, tr.x, y,
                    border.getForeground(), border.getBackground());
        }
        
        if (fill == null)
            return true;
        
        for (int y = tl.y + 1; y < bl.y; y++)
            for (int x = tl.x + 1; x < tr.x; x++)
                display.write(Coord.get(x, y),
                        new ColorChar(ExtChars.BLOCK, fill));
        
        return true;
    }
    
    /**
     * Draws a {@link Border}  of the specified width between two specified
     * corners to the provided {@link corelib.display.Display}.
     * @param display the {@link corelib.display.Display} to draw the
     * {@link Border} on
     * @param corner1 the first corner; must be a different point than the
     * second corner, share no axis values, and be on the display
     * @param corner2 the second corner; must be a different point than the
     * first corner, share no axis values, and be on the display
     * @param width the width of the {@link Border} to draw; must be 1 or 2
     * @param fill the Color to fill the center of the {@link Border} with; if
     * null, no fill will be performed
     * @return true if the {@link Border} was successfully drawn
     */
    public static boolean drawBorder(Display display, Coord corner1,
            Coord corner2, int width, Color fill)
        {return drawBorder(display, corner1, corner2, new Border(width), fill);}
    
    /**
     * Draws a {@link Border} between two specified corners to the provided
     * {@link corelib.display.Display}, filled with the getBackground() color of
     * the provided {@link Border}.
     * @param display the {@link corelib.display.Display} to draw the
     * {@link Border} on
     * @param corner1 the first corner; must be a different point than the
     * second corner, share no axis values, and be on the display
     * @param corner2 the second corner; must be a different point than the
     * first corner, share no axis values, and be on the display
     * @param border the characters of the {@link Border}
     * @return true if the {@link Border} was successfully drawn
     */
    public static boolean drawBorder(Display display, Coord corner1,
            Coord corner2, Border border)
    {
        border.syncDefaults(display);
        return drawBorder(display, corner1, corner2, border,
                border.getBackground());
    }
    
    /**
     * Draws a {@link Border} of the specified width between two specified
     * corners to the provided Display. No fill will be performed.
     * @param display the {@link corelib.display.Display} to draw the
     * {@link Border} on
     * @param corner1 the first corner; must be a different point than the
     * second corner, share no axis values, and be on the display
     * @param corner2 the second corner; must be a different point than the
     * first corner, share no axis values, and be on the display
     * @param width the width of the {@link Border} to draw; must be 1 or 2
     * @return true if the {@link Border} was successfully drawn
     */
    public static boolean drawBorder(Display display, Coord corner1,
            Coord corner2, int width)
        {return drawBorder(display, corner1, corner2, width, null);}
    
    /**
     * Draws a {@link Border} of the default width between two specified corners
     * to the provided {@link corelib.display.Display}. No fill will be
     * performed.
     * @param display the {@link corelib.display.Display} to draw the
     * {@link Border} on
     * @param corner1 the first corner; must be a different point than the
     * second corner, share no axis values, and be on the display
     * @param corner2 the second corner; must be a different point than the
     * first corner, share no axis values, and be on the display
     * @return true if the {@link Border} was successfully drawn
     */
    public static boolean drawBorder(Display display, Coord corner1,
            Coord corner2)
        {return drawBorder(display, corner1, corner2, DEFAULT_LINES);}
    
    /**
     * Prints the provided text surrounded by the provided {@link Border} to the
     * provided {@link corelib.display.Display}.
     * @param display the {@link corelib.display.Display} to draw on
     * @param text the lines of text, as
     * {@link corelib.display.glyphs.ColorSet ColorSets}, to print
     * @param topLine the line on which the first
     * {@link corelib.display.glyphs.ColorSet} will be written
     * @param leftIndent the number of characters from the left the text will be
     * printed
     * @param border the {@link Border} with which to surround the text
     * @param separators the {@link Line}s with which to separate text
     * @return true if the operation was successful
     */
    public static boolean printBoxed(Display display, ColorSet[] text,
            int topLine, int leftIndent, Border border, Line[] separators)
    {
        if (!display.contains(Coord.get(leftIndent - 1, topLine - 1)))
            throw new IndexOutOfBoundsException("Top left coordinates must be "
                    + ">= 1; were " + leftIndent + " and " + topLine);
        
        if (text == null || text.length == 0)
            throw new IllegalArgumentException(
                    "At least 1 line of text must be provided");
        
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
        
        int curLine          = topLine;
        int curIndent        = leftIndent;
        int overallMaxLength = 0;
        int curMaxLines      = blocks[0].size();
        int overallLines     = blocks[0].size();
        Coord[] textPoints   = new Coord[nBlocks];
        Coord[] endpoints    = null; // Suppresses undefined warning
        
        if (separators != null)
            endpoints = new Coord[separators.length * 2];
        
        for (int block = 0; block < blocks.length; block++)
        {
            textPoints[block] = Coord.get(curIndent, curLine);
            
            int curMaxLength = 0;
            for (ColorSet line: blocks[block])
                if (line.getSet().size() > curMaxLength)
                    curMaxLength = line.getSet().size();
            
            curMaxLength += curIndent - leftIndent;
            
            if (curMaxLength > overallMaxLength)
                overallMaxLength = curMaxLength;
            
            blockCheck:
            if (blocks[block].size() > curMaxLines)
            {
                overallLines -= curMaxLines;
                curMaxLines = blocks[block].size();
                overallLines += curMaxLines;
                
                if (separators == null)
                    break blockCheck;
                
                int checkingBlock = block - 1;
                while (separators.length - 1 >= checkingBlock &&
                        checkingBlock >= 0 &&
                        separators[checkingBlock] != null &&
                        !separators[checkingBlock].horizontal)
                {
                    endpoints[checkingBlock * 2 + 1] = Coord.get(
                            endpoints[checkingBlock * 2 + 1].x,
                            curLine + curMaxLines);
                    checkingBlock--;
                }
            }
            
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

                    endpoints[block * 2] = Coord.get(curIndent - 1,
                            curLine - 1);
                    endpoints[block * 2 + 1] = Coord.get(curIndent - 1,
                            curLine + curMaxLines);
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
        
        for (int block = 0; block < nBlocks; block++)
            display.write(textPoints[block], blocks[block].toArray(
                    new ColorSet[blocks[block].size()]));
        
        return returnValue;
    }
    
    /**
     * Prints the provided text surrounded by the provided {@link Border} to the
     * provided {@link corelib.display.Display}.
     * @param display the {@link corelib.display.Display} to draw on
     * @param text the lines of text, as
     * {@link corelib.display.glyphs.ColorString ColorStrings}, to print
     * @param topLine the line on which the first
     * {@link corelib.display.glyphs.ColorString} will be written
     * @param leftIndent the number of characters from the left the text will be
     * printed
     * @param border the {@link Border} with which to surround the text
     * @param separators the {@link Line}s with which to separate text
     * @return true if the operation was successful
     */
    public static boolean printBoxed(Display display, ColorString[] text,
            int topLine, int leftIndent, Border border, Line[] separators)
    {
        return printBoxed(display, ColorSet.toColorSetArray(text), topLine,
                leftIndent, border, separators);
    }
    
    /**
     * Prints the provided text surrounded by the provided {@link Border} to the
     * provided {@link corelib.display.Display}.
     * @param display the {@link corelib.display.Display} to draw on
     * @param text the lines of text, as Strings, to print
     * @param topLine the line on which the first String will be written
     * @param leftIndent the number of characters from the left the text will be
     * printed
     * @param border the {@link Border} with which to surround the text
     * @param separators the {@link Line}s with which to separate text
     * @return true if the operation was successful
     */
    public static boolean printBoxed(Display display, String[] text,
            int topLine, int leftIndent, Border border, Line[] separators)
    {
        return printBoxed(display, ColorSet.toColorSetArray(text), topLine,
                leftIndent, border, separators);
    }
    
    /**
     * Prints the provided text surrounded by the provided {@link Border} to the
     * provided {@link corelib.display.Display}; no separators will be used.
     * @param display the {@link corelib.display.Display} to draw on
     * @param text the lines of text, as
     * {@link corelib.display.glyphs.ColorSet ColorSets}, to print
     * @param topLine the line on which the first
     * {@link corelib.display.glyphs.ColorSet} will be written
     * @param leftIndent the number of characters from the left the text will be
     * printed
     * @param border the {@link Border} with which to surround the text
     * @return true if the operation was successful
     */
    public static boolean printBoxed(Display display, ColorSet[] text,
            int topLine, int leftIndent, Border border)
        {return printBoxed(display, text, topLine, leftIndent, border, null);}
    
    /**
     * Prints the provided text surrounded by the provided {@link Border} to the
     * provided {@link corelib.display.Display}; no separators will be used.
     * @param display the {@link corelib.display.Display} to draw on
     * @param text the lines of text, as
     * {@link corelib.display.glyphs.ColorString ColorStrings}, to print
     * @param topLine the line on which the first
     * {@link corelib.display.glyphs.ColorString} will be written
     * @param leftIndent the number of characters from the left the text will be
     * printed
     * @param border the {@link Border} with which to surround the text
     * @return true if the operation was successful
     */
    public static boolean printBoxed(Display display, ColorString[] text,
            int topLine, int leftIndent, Border border)
    {
        return printBoxed(display, ColorSet.toColorSetArray(text), topLine,
                leftIndent, border, null);
    }
    
    /**
     * Prints the provided text surrounded by the provided {@link Border} to the
     * provided {@link corelib.display.Display}; no separators will be used.
     * @param display the {@link corelib.display.Display} to draw on
     * @param text the lines of text, as Strings, to print
     * @param topLine the line on which the first String will be written
     * @param leftIndent the number of characters from the left the text will be
     * printed
     * @param border the {@link Border} with which to surround the text
     * @return true if the operation was successful
     */
    public static boolean printBoxed(Display display, String[] text,
            int topLine, int leftIndent, Border border)
    {
        return printBoxed(display, ColorSet.toColorSetArray(text), topLine,
                leftIndent, border, null);
    }
    
    /**
     * Prints the provided text surrounded by a {@link Border} of the provided
     * width to the provided {@link corelib.display.Display}; no separators will
     * be used.
     * @param display the {@link corelib.display.Display} to draw on
     * @param text the lines of text, as Strings, to print
     * @param topLine the line on which the first String will be written
     * @param leftIndent the number of characters from the left the text will be
     * printed
     * @param width the width of the {@link Border} with which to surround the
     * text
     * @return true if the operation was successful
     */
    public static boolean printBoxed(Display display, String[] text,
            int topLine, int leftIndent, int width)
    {
        return printBoxed(display, text, topLine, leftIndent,
                new Border(width));
    }
    
    /**
     * Prints the provided text surrounded by the default {@link Border} to the
     * provided {@link corelib.display.Display}; no separators will be used.
     * @param display the {@link corelib.display.Display} to draw on
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
     * Prints the provided text surrounded by the provided {@link Border} to the
     * center of the provided {@link corelib.display.Display}.
     * @param display the {@link corelib.display.Display} to draw on
     * @param text the lines of text, as
     * {@link corelib.display.glyphs.ColorString ColorStrings}, to print
     * @param topLine the line on which the first
     * {@link corelib.display.glyphs.ColorString} will be written
     * @param border the {@link Border} with which to surround the text
     * @param separator the {@link Line} to use for all horizontal separations
     * @return true if the operation was successful
     */
    public static boolean printCenterBoxed(Display display, ColorString[] text,
            int topLine, Border border, Line separator)
    {
        if (text == null || text.length == 0)
            throw new IllegalArgumentException(
                    "At least 1 line of text must be provided");
        
        if (!display.containsY(topLine - 1) ||
                !display.containsY(topLine + text.length))
            throw new IndexOutOfBoundsException("Top line value must be "
                    + "between 1 and " + (display.getCharHeight() - 1)
                            + "; was " + topLine);
        
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
        
        display.writeCenter(topLine, text);
        
        return returnValue;
    }
    
    /**
     * Prints the provided text surrounded by the provided {@link Border} to the
     * center of the provided {@link corelib.display.Display}.
     * @param display the {@link corelib.display.Display} to draw on
     * @param text the lines of text, as Strings, to print
     * @param topLine the line on which the first String will be written
     * @param border the {@link Border} with which to surround the text
     * @param separator the {@link Line} to use for all horizontal separations
     * @return true if the operation was successful
     */
    public static boolean printCenterBoxed(Display display, String[] text,
            int topLine, Border border, Line separator)
    {
        return printCenterBoxed(display, ColorString.toColorStringArray(text),
                topLine, border, separator);
    }
    
    /**
     * Prints the provided text surrounded by the provided {@link Border} to the
     * center of the provided {@link corelib.display.Display}; no separators 
     * will be used.
     * @param display the {@link corelib.display.Display} to draw on
     * @param text the lines of text, as
     * {@link corelib.display.glyphs.ColorString ColorStrings}, to print
     * @param topLine the line on which the first
     * {@link corelib.display.glyphs.ColorString} will be written
     * @param border the {@link Border} with which to surround the text
     * @return true if the operation was successful
     */
    public static boolean printCenterBoxed(Display display, ColorString[] text,
            int topLine, Border border)
        {return printCenterBoxed(display, text, topLine, border, null);}
    
    /**
     * Prints the provided text surrounded by the provided {@link Border} to the
     * center of the provided {@link corelib.display.Display}; no separators
     * will be used.
     * @param display the {@link corelib.display.Display} to draw on
     * @param text the lines of text, as Strings, to print
     * @param topLine the line on which the first String will be written
     * @param border the {@link Border} with which to surround the text
     * @return true if the operation was successful
     */
    public static boolean printCenterBoxed(Display display, String[] text,
            int topLine, Border border)
        {return printCenterBoxed(display, text, topLine, border, null);}
    
    /**
     * Prints the provided text surrounded by a {@link Border} of the provided
     * width to the center of the provided{@link corelib.display.Display}; no
     * separators will be used.
     * @param display the {@link corelib.display.Display} to draw on
     * @param text the lines of text, as Strings, to print
     * @param topLine the line on which the first String will be written
     * @param width the width of the {@link Border} with which to surround the
     * text
     * @return true if the operation was successful
     */
    public static boolean printCenterBoxed(Display display, String[] text,
            int topLine, int width)
        {return printCenterBoxed(display, text, topLine, new Border(width));}
    
    /**
     * Prints the provided text surrounded by the provided {@link Border} to the
     * center of the provided {@link corelib.display.Display}; no separators
     * will be used.
     * @param display the {@link corelib.display.Display} to draw on
     * @param text the lines of text, as Strings, to print
     * @param topLine the line on which the first String will be written
     * @return true if the operation was successful
     */
    public static boolean printCenterBoxed(Display display, String[] text,
            int topLine)
        {return printCenterBoxed(display, text, topLine, DEFAULT_LINES);}
}