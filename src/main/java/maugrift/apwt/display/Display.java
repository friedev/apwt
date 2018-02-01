package maugrift.apwt.display;

import maugrift.apwt.ExtChars;
import maugrift.apwt.glyphs.ColorChar;
import maugrift.apwt.glyphs.ColorString;
import maugrift.apwt.screens.Screen;
import maugrift.apwt.windows.Border;
import maugrift.apwt.windows.Line;

import java.awt.*;
import java.util.List;

/**
 * An interface for writing to terminals that requires only a few overrides by implementing classes. It derives
 * several utility methods from these for convenience.
 */
public interface Display
{
    /**
     * Returns this {@link Display}'s current {@link maugrift.apwt.screens.Screen}.
     *
     * @return this {@link Display}'s current {@link maugrift.apwt.screens.Screen}
     */
    Screen getScreen();

    /**
     * Returns the width of this {@link Display} in characters.
     *
     * @return the width of this {@link Display} in characters
     */
    int getWidthInCharacters();

    /**
     * Returns the height of this {@link Display} in characters.
     *
     * @return the height of this {@link Display} in characters
     */
    int getHeightInCharacters();

    /**
     * Returns the x coordinate at the center of the {@link Display}.
     *
     * @return the x coordinate at the center of the {@link Display}
     */
    default int getCenterX()
    {
        return getWidthInCharacters() / 2;
    }

    /**
     * Returns the y coordinate at the center of the {@link Display}.
     *
     * @return the y coordinate at the center of the {@link Display}
     */
    default int getCenterY()
    {
        return getHeightInCharacters() / 2;
    }

    /**
     * Returns true if this {@link Display} contains the given Coord.
     *
     * @param x the x value to check
     * @param y the y value to check
     * @return true if the {@link Display} contains the Coord
     */
    default boolean contains(int x, int y)
    {
        return containsX(x) && containsY(y);
    }

    /**
     * Returns true if this {@link Display} contains the given x value, in characters.
     *
     * @param x the x value, in characters, to check for containment on the {@link Display}
     * @return true if the {@link Display} contains the x value
     */
    default boolean containsX(int x)
    {
        return x >= 0 && x < getWidthInCharacters();
    }

    /**
     * Returns true if this {@link Display} contains the given y value, in characters.
     *
     * @param y the y value, in characters, to check for containment on the {@link Display}
     * @return true if the {@link Display} contains the y value
     */
    default boolean containsY(int y)
    {
        return y >= 0 && y < getHeightInCharacters();
    }

    /**
     * Gets the default foreground color of characters printed to the display.
     *
     * @return the default foreground color of characters printed to the display
     */
    Color getDefaultForegroundColor();

    /**
     * Gets the default background color of characters printed to the display.
     *
     * @return the default background color of characters printed to the display
     */
    Color getDefaultBackgroundColor();

    /**
     * Writes a character to this {@link Display}, using the given foreground and background colors.
     *
     * @param x          the x value, in characters, at which the character will be written
     * @param y          the y value, in characters, at which the character will be written
     * @param c          the char to write
     * @param foreground the foreground color
     * @param background the background color
     */
    void write(int x, int y, char c, Color foreground, Color background);

    /**
     * Writes a character to this {@link Display}, using the given foreground color and default background color.
     *
     * @param x          the x value, in characters, at which the character will be written
     * @param y          the y value, in characters, at which the character will be written
     * @param c          the char to write
     * @param foreground the foreground color
     */
    default void write(int x, int y, char c, Color foreground)
    {
        write(x, y, c, foreground, getDefaultBackgroundColor());
    }

    /**
     * Writes a character to this {@link Display}, using the default foreground and background colors.
     *
     * @param c the char to write
     * @param x the x value, in characters, at which the character will be written
     * @param y the y value, in characters, at which the character will be written
     */
    default void write(int x, int y, char c)
    {
        write(x, y, new ColorChar(c));
    }

    /**
     * Writes a {@link maugrift.apwt.glyphs.ColorChar} to this {@link Display}.
     *
     * @param cc the {@link maugrift.apwt.glyphs.ColorChar} to write
     * @param x  the x value, in characters, at which the {@link maugrift.apwt.glyphs.ColorChar} will be written
     * @param y  the y value, in characters, at which the {@link maugrift.apwt.glyphs.ColorChar} will be written
     */
    default void write(int x, int y, ColorChar cc)
    {
        cc.syncDefaults(this);
        write(x, y, cc.getChar(), cc.getForeground(), cc.getBackground());
    }

    /**
     * Writes a String to this {@link Display}, using the default foreground and background colors.
     *
     * @param s          the String to write
     * @param x          the x value, in characters, at which the String will be written
     * @param y          the y value, in characters, at which the String will be written
     * @param foreground the foreground color
     * @param background the background color
     */
    default void write(int x, int y, String s, Color foreground, Color background)
    {
        write(x, y, new ColorString(s, foreground, background));
    }

    /**
     * Writes a String to this {@link Display}, using the default foreground and background colors.
     *
     * @param s          the String to write
     * @param x          the x value, in characters, at which the String will be written
     * @param y          the y value, in characters, at which the String will be written
     * @param foreground the foreground color
     */
    default void write(int x, int y, String s, Color foreground)
    {
        write(x, y, new ColorString(s, foreground));
    }

    /**
     * Writes a String to this {@link Display}, using the default foreground and background colors.
     *
     * @param s the String to write
     * @param x the x value, in characters, at which the String will be written
     * @param y the y value, in characters, at which the String will be written
     */
    default void write(int x, int y, String s)
    {
        write(x, y, new ColorString(s));
    }

    /**
     * Writes an array of Strings to this {@link Display}, with each String on the line below the previous.
     *
     * @param s the Strings to write
     * @param x the x value, in characters, at which the first String will be written
     * @param y the y value, in characters, at which the first String will be written
     */
    default void write(int x, int y, String... s)
    {
        if (s == null || s.length == 0)
        {
            throw new IllegalArgumentException("No Strings were provided to print");
        }

        for (int line = 0; line < s.length; line++)
        {
            if (s[line] != null && contains(x, y + line))
            {
                write(x, y + line, s[line]);
            }
        }
    }

    /**
     * Writes the {@link maugrift.apwt.glyphs.ColorChar}s of a {@link maugrift.apwt.glyphs.ColorString} to this {@link
     * Display}.
     *
     * @param s the {@link maugrift.apwt.glyphs.ColorString} to write
     * @param x the x value, in characters, at which the {@link maugrift.apwt.glyphs.ColorString} will be written
     * @param y the y value, in characters, at which the {@link maugrift.apwt.glyphs.ColorString} will be written
     */
    default void write(int x, int y, ColorString s)
    {
        if (s == null || s.getCharacters() == null || s.getCharacters().isEmpty())
        {
            throw new IllegalArgumentException("Given ColorString is null or empty");
        }

        List<ColorChar> chars = s.getCharacters();

        for (int i = 0; i < chars.size(); i++)
        {
            write(x + i, y, chars.get(i));
        }
    }

    /**
     * Writes an array of {@link maugrift.apwt.glyphs.ColorString ColorStrings} to this {@link Display}, with each
     * {@link maugrift.apwt.glyphs.ColorString} on the line below the previous.
     *
     * @param s the {@link maugrift.apwt.glyphs.ColorString ColorStrings} to write
     * @param x the x value, in characters, at which the first {@link maugrift.apwt.glyphs.ColorString} will be written
     * @param y the y value, in characters, at which the first {@link maugrift.apwt.glyphs.ColorString} will be written
     */
    default void write(int x, int y, ColorString... s)
    {
        if (s == null || s.length == 0)
        {
            throw new IllegalArgumentException("No ColorStrings were provided to print");
        }

        for (int line = 0; line < s.length; line++)
        {
            if (s[line] != null && contains(x, y + line))
            {
                write(x, y + line, s[line]);
            }
        }
    }

    /**
     * Writes an array of Strings centered around the given coordinates.
     *
     * @param s the Strings to write
     * @param x the x value, in characters, of the center point
     * @param y the y value, in characters, of the center point
     */
    default void writeCenter(int x, int y, String... s)
    {
        if (s == null || s.length == 0)
        {
            return;
        }

        for (int line = 0; line < s.length; line++)
        {
            if (s[line] != null)
            {
                write(x - (s[line].length() / 2), y - (s.length / 2) + line + 1, s[line]);
            }
        }
    }

    /**
     * Writes an array of Strings to the center of this {@link Display}.
     *
     * @param s the Strings to write
     * @param y the y value, in characters, of the center point
     */
    default void writeCenter(int y, String... s)
    {
        writeCenter(getCenterX(), y, s);
    }

    /**
     * Writes an array of Strings to the center of this {@link Display}.
     *
     * @param s the Strings to write
     */
    default void writeCenter(String... s)
    {
        writeCenter(getCenterX(), getCenterY(), s);
    }

    /**
     * Writes an array of {@link maugrift.apwt.glyphs.ColorString ColorStrings} centered around the given coordinates.
     *
     * @param s the {@link maugrift.apwt.glyphs.ColorString ColorStrings} to write
     * @param x the x value, in characters, of the center point
     * @param y the y value, in characters, of the center point
     */
    default void writeCenter(int x, int y, ColorString... s)
    {
        if (s == null || s.length == 0)
        {
            return;
        }

        for (int line = 0; line < s.length; line++)
        {
            if (s[line] != null)
            {
                // Create a copy to avoid changing the original parameters
                ColorString copy = new ColorString(s[line]).syncDefaults(this);
                write(x - (copy.length() / 2), y - (s.length / 2) + line, copy);
            }
        }
    }

    /**
     * Writes an array of {@link maugrift.apwt.glyphs.ColorString ColorStrings} to the center of this {@link Display}.
     *
     * @param s the {@link maugrift.apwt.glyphs.ColorString ColorStrings} to write
     * @param y the y value, in characters, of the center point
     */
    default void writeCenter(int y, ColorString... s)
    {
        writeCenter(getCenterX(), y, s);
    }

    /**
     * Writes an array of {@link maugrift.apwt.glyphs.ColorString ColorStrings} to the center of this {@link Display}.
     *
     * @param s the {@link maugrift.apwt.glyphs.ColorString ColorStrings} to write
     */
    default void writeCenter(ColorString... s)
    {
        writeCenter(getCenterX(), getCenterY(), s);
    }

    /**
     * Draws a {@link Line} between two endpoints to the provided Display.
     *
     * @param x1     the x value of the first endpoint; must be a different point than the second endpoint, have one
     *               axis value in common, and be on the display
     * @param y1     the y value of the first endpoint; must be a different point than the second endpoint, have one
     *               axis value in common, and be on the display
     * @param x2     the x value of the second endpoint; must be a different point than the first endpoint, have one
     *               axis value in common, and be on the display
     * @param y2     the y value of the second endpoint; must be a different point than the first endpoint, have one
     *               axis value in common, and be on the display
     * @param border the characters of the {@link Line}; if horizontal, points must share y values, and the opposite is
     *               true with x values
     * @return true if the {@link Line} was successfully drawn
     */
    default boolean drawLine(int x1, int y1, int x2, int y2, Line border)
    {
        if (x1 == x2 && y1 == y2)
        {
            throw new IllegalArgumentException("Endpoints must be different points");
        }

        if (x1 != x2 && y1 != y2)
        {
            throw new IllegalArgumentException("Both endpoints must share an axis value");
        }

        if (!contains(x1, y1) || !contains(x2, y2))
        {
            throw new IndexOutOfBoundsException("The display must contain both endpoints");
        }

        if ((x1 == x2 && border.horizontal) || (y1 == y2 && !border.horizontal))
        {
            throw new IllegalArgumentException("Endpoint dimension does not match line horizontal/vertical field");
        }

        border.syncDefaults(this);

        write(x1, y1, new ColorChar(border.end1, border.getForeground(), border.getBackground()));
        write(x2, y2, new ColorChar(border.end2, border.getForeground(), border.getBackground()));

        int start, end;

        if (!border.horizontal)
        {
            if (y1 < y2)
            {
                start = y1;
                end = y2;
            }
            else
            {
                start = y2;
                end = y1;
            }

            for (int i = start + 1; i < end; i++)
            {
                write(x1, i, new ColorChar(border.line, border.getForeground(), border.getBackground()));
            }
        }
        else
        {
            if (x1 < x2)
            {
                start = x1;
                end = x2;
            }
            else
            {
                start = x2;
                end = x1;
            }

            for (int i = start + 1; i < end; i++)
            {
                write(i, y1, new ColorChar(border.line, border.getForeground(), border.getBackground()));
            }
        }

        return true;
    }

    /**
     * Draws a {@link Border}  between two specified corners to the provided {@link Display}, filled with
     * the provided fill color.
     *
     * @param x1     the x value of the first corner; must be a different point than the second corner, share no axis
     *               values, and be on the display
     * @param y1     the y value of the first corner; must be a different point than the second corner, share no axis
     *               values, and be on the display
     * @param x2     the x value of the second corner; must be a different point than the first corner, share no axis
     *               values, and be on the display
     * @param y2     the y value of the second corner; must be a different point than the first corner, share no axis
     *               values, and be on the display
     * @param border the characters of the {@link Border}
     * @param fill   the Color to fill the center of the {@link Border} with; if null, no fill will be performed
     * @return true if the {@link Border} was successfully drawn
     */
    default boolean drawBorder(int x1, int y1, int x2, int y2, Border border, Color fill)
    {
        if (x1 == x2 || y1 == y2)
        {
            throw new IllegalArgumentException("Corners must have different axis values");
        }

        if (!contains(x1, y1) || !contains(x2, y2))
        {
            throw new IllegalArgumentException("The display must contain both corners");
        }

        int left, right, top, bottom;
        if (x1 < x2)
        {
            left = x1;
            right = x2;
        }
        else
        {
            left = x2;
            right = x1;
        }

        if (y1 < y2)
        {
            top = y1;
            bottom = y2;
        }
        else
        {
            top = y2;
            bottom = y1;
        }

        border.syncDefaults(this);

        write(left, top, new ColorChar(border.cornerTL, border.getForeground(), border.getBackground()));
        write(right, top, new ColorChar(border.cornerTR, border.getForeground(), border.getBackground()));
        write(left, bottom, new ColorChar(border.cornerBL, border.getForeground(), border.getBackground()));
        write(right, bottom, new ColorChar(border.cornerBR, border.getForeground(), border.getBackground()));

        for (int x = left + 1; x < right; x++)
        {
            write(x, top, new ColorChar(border.edgeT, border.getForeground(), border.getBackground()));
            write(x, bottom, new ColorChar(border.edgeB, border.getForeground(), border.getBackground()));
        }

        for (int y = top + 1; y < bottom; y++)
        {
            write(left, y, new ColorChar(border.edgeL, border.getForeground(), border.getBackground()));
            write(right, y, new ColorChar(border.edgeR, border.getForeground(), border.getBackground()));
        }

        if (fill == null)
        {
            return true;
        }

        for (int y = top + 1; y < bottom; y++)
        {
            for (int x = left + 1; x < right; x++)
            {
                write(x, y, new ColorChar(ExtChars.BLOCK, fill));
            }
        }

        return true;
    }

    /**
     * Draws a {@link Border}  of the specified width between two specified corners.
     *
     * @param x1    the x value of the first corner; must be a different point than the second corner, share no axis
     *              values, and be on the display
     * @param y1    the y value of the first corner; must be a different point than the second corner, share no axis
     *              values, and be on the display
     * @param x2    the x value of the second corner; must be a different point than the first corner, share no axis
     *              values, and be on the display
     * @param y2    the y value of the second corner; must be a different point than the first corner, share no axis
     *              values, and be on the display
     * @param width the width of the {@link Border} to draw; must be 1 or 2
     * @param fill  the Color to fill the center of the {@link Border} with; if null, no fill will be performed
     * @return true if the {@link Border} was successfully drawn
     */
    default boolean drawBorder(int x1, int y1, int x2, int y2, int width, Color fill)
    {
        return drawBorder(x1, y1, x2, y2, new Border(width), fill);
    }

    /**
     * Draws a {@link Border} between two specified corners and filled with the background color of the provided {@link
     * Border}.
     *
     * @param x1     the x value of the first corner; must be a different point than the second corner, share no axis
     *               values, and be on the display
     * @param y1     the y value of the first corner; must be a different point than the second corner, share no axis
     *               values, and be on the display
     * @param x2     the x value of the second corner; must be a different point than the first corner, share no axis
     *               values, and be on the display
     * @param y2     the y value of the second corner; must be a different point than the first corner, share no axis
     *               values, and be on the display
     * @param border the characters of the {@link Border}
     * @return true if the {@link Border} was successfully drawn
     */
    default boolean drawBorder(int x1, int y1, int x2, int y2, Border border)
    {
        border.syncDefaults(this);
        return drawBorder(x1, y1, x2, y2, border, border.getBackground());
    }

    /**
     * Draws a {@link Border} of the specified width between two specified corners. No fill will be performed.
     *
     * @param x1    the x value of the first corner; must be a different point than the second corner, share no axis
     *              values, and be on the display
     * @param y1    the y value of the first corner; must be a different point than the second corner, share no axis
     *              values, and be on the display
     * @param x2    the x value of the second corner; must be a different point than the first corner, share no axis
     *              values, and be on the display
     * @param y2    the y value of the second corner; must be a different point than the first corner, share no axis
     *              values, and be on the display
     * @param width the width of the {@link Border} to draw; must be 1 or 2
     * @return true if the {@link Border} was successfully drawn
     */
    default boolean drawBorder(int x1, int y1, int x2, int y2, int width)
    {
        return drawBorder(x1, y1, x2, y2, width, null);
    }
}
