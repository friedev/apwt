package core.display;

import core.display.screens.Screen;
import core.display.screens.MasterScreen;
import asciiPanel.AsciiFont;
import javax.swing.JFrame;
import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import core.Point;
import java.awt.Color;

/**
 * A class designed to create, contain, and operate an AsciiPanel, as well as
 * providing many utility functions.
 */
public class Display extends JFrame implements KeyListener
{
    // CONSTANTS

    /**
     * The width that will be used by default if not supplied in a constructor.
     */
    public static final int DEFAULT_WIDTH = 80;
    
    /**
     * The height that will be used by default if not supplied in a constructor.
     */
    public static final int DEFAULT_HEIGHT = 48;
    
    /**
     * The font that will be used by default if not supplied in a constructor.
     */
    public static final AsciiFont DEFAULT_FONT = AsciiFont.QBICFEET_10x10;
    
    // FIELDS
    
    /** The AsciiPanel that will be used to display all "graphical" data. */
    private AsciiPanel panel;
    
    /**
     * The current screen being displayed by the terminal and where input from
     * the KeyListener is being processed.
     */
    private Screen screen;

    // CONSTRUCTORS
    
    /**
     * The main Display constructor, accepting a constructed AsciiPanel.
     * @param p the panel to be used as the Display's terminal
     */
    public Display(AsciiPanel p)
    {
        super();
        panel = p;
        add(panel);
        pack();
    }
    
    /**
     * A Display constructor that uses the default font.
     * @param width the width of the terminal
     * @param height the height of the terminal
     */
    public Display(int width, int height)
        {this(new AsciiPanel(width, height, DEFAULT_FONT));}
    
    /**
     * A Display constructor that uses the default width and height.
     * @param font the AsciiFont used by the terminal
     */
    public Display(AsciiFont font)
        {this(new AsciiPanel(DEFAULT_WIDTH, DEFAULT_HEIGHT, font));}
    
    /** A Display constructor that uses all default presets. */
    public Display()
        {this(new AsciiPanel(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_FONT));}
    
    /**
     * Initializes the screen and keylistener, as well as setting up default
     * settings outside of a constructor.
     * @return the initialized display, this
     */
    public Display init()
    {
        screen = new MasterScreen(this);
        addKeyListener(this);
        repaint();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        return this;
    }

    // INHERITED METHODS
    
    @Override
    public void repaint()
    {
        panel.clear();
        screen.displayOutput();
        super.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        screen = screen.processInput(e);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
    
    // STATIC GRAPHICAL METHODS
    
    /**
     * Writes a String to the provided AsciiPanel.
     * @param panel the panel on which to write the String
     * @param s the String to write
     * @param p the point, in characters, at which the String will be written
     * @param foreground the color of the String
     * @param background the color of the background
     */
    public static void write(AsciiPanel panel, String s, Point p,
            Color foreground, Color background)
        {panel.write(s, p.x, p.y, foreground, background);}
    
    /**
     * Writes a character to the provided AsciiPanel.
     * @param panel the panel on which to write the character
     * @param c the char to write
     * @param p the point, in characters, at which the character will be written
     * @param foreground the color of the character
     * @param background the color of the background
     */
    public static void write(AsciiPanel panel, char c, Point p,
            Color foreground, Color background)
        {panel.write(c, p.x, p.y, foreground, background);}
    
    /**
     * Writes a ColorChar to the provided AsciiPanel.
     * @param panel the panel on which to write the ColorChar
     * @param cc the ColorChar to write
     * @param p  the point, in characters, at which the ColorChar will be
     * written
     */
    public static void write(AsciiPanel panel, ColorChar cc, Point p)
        {panel.write(cc.character, p.x, p.y, cc.foreground, cc.background);}
    
    /**
     * Writes an array of Strings to the provided AsciiPanel, with each String
     * on the line below the previous.
     * @param panel the panel on which to write the Strings
     * @param s the Strings to write
     * @param p the point, in characters, at which the first String will be
     * written
     * @param foreground the color of the Strings
     * @param background the color of the background
     */
    public static void write(AsciiPanel panel, String[] s, Point p,
            Color foreground, Color background)
    {
        if (s == null || s.length == 0)
            return;
        
        for (int line = 0; line < s.length; line++)
            if (s[line] != null && contains(panel, new Point(p.x, p.y + line)))
                write(panel, s[line], new Point(p.x, p.y + line), foreground,
                        background);
    }
    
    /**
     * Writes a String to the provided AsciiPanel, using the colors defined in
     * the ColorSet.
     * @param panel the panel on which to write the String
     * @param s the String to write
     * @param p the point, in characters, at which the String will be written
     * @param colors the colors of the first ColorChar in this ColorSet will be
     * used on all matching characters of the String printed; if a character is
     * not found in the ColorSet, default colors will be used instead
     */
    public static void write(AsciiPanel panel, String s, ColorSet colors,
            Point p)
    {
        if (colors == null)
            return;
        
        for (int i = 0; i < s.length(); i++)
        {
            char cur = s.charAt(i);
            ColorChar curColors = colors.getColorChar(cur);
            if (curColors == null)
            {
                panel.write(cur, p.x + i, p.y);
            }
            else
            {
                curColors.syncDefaults(panel);
                panel.write(cur, p.x + i, p.y, curColors.foreground,
                        curColors.background);
            }
        }
    }
    
    /**
     * Writes the ColorChars of a ColorSet to the provided AsciiPanel.
     * @param panel the panel on which to write the ColorSet
     * @param s the ColorSet to write
     * @param p the point, in characters, at which the ColorSet will be written
     */
    public static void write(AsciiPanel panel, ColorSet s, Point p)
    {
        if (s == null || s.getSet() == null || s.getSet().isEmpty())
            return;
        
        java.util.List<ColorChar> chars = s.getSet();
        
        for (int i = 0; i < chars.size(); i++)
        {
            chars.get(i).syncDefaults(panel);
            panel.write(chars.get(i).character, p.x + i, p.y,
                    chars.get(i).foreground, chars.get(i).background);
        }
    }
    
    /**
     * Writes an array of ColorSets to the provided AsciiPanel, with each
     * ColorSet on the line below the previous.
     * @param panel the panel on which to write the ColorSets
     * @param s the ColorSets to write
     * @param p the point, in characters, at which the first ColorSet will be
     * written
     */
    public static void write(AsciiPanel panel, ColorSet[] s, Point p)
    {
        if (s == null || s.length == 0)
            return;
        
        for (int line = 0; line < s.length; line++)
            if (s[line] != null && contains(panel, new Point(p.x, p.y + line)))
                write(panel, s[line], new Point(p.x, p.y + line));
    }
    
    /**
     * Writes an array of Strings to the center of the provided AsciiPanel.
     * @param panel the panel on which to write the Strings
     * @param s the Strings to write
     * @param y the line on which the first String will be written
     * @param foreground the color of the Strings
     * @param background the color of the background
     */
    public static void writeCenter(AsciiPanel panel, String[] s, int y,
            Color foreground, Color background)
    {
        if (s == null || s.length == 0)
            return;
        
        for (int line = 0; line < s.length; line++)
            if (s[line] != null)
                panel.writeCenter(s[line], y + line, foreground, background);
    }
    
    /**
     * Writes an array of ColorStrings to the provided AsciiPanel.
     * @param panel the panel on which to write the ColorStrings
     * @param s the ColorStrings to write
     * @param y the line on which the first ColorString will be written
     */
    public static void writeCenter(AsciiPanel panel, ColorString[] s, int y)
    {
        if (s == null || s.length == 0)
            return;
        
        for (int line = 0; line < s.length; line++)
        {
            if (s[line] != null)
            {
                s[line].syncDefaults(panel);
                panel.writeCenter(s[line].string, y + line, s[line].foreground,
                        s[line].background);
            }
        }
    }
    
    /**
     * Writes a String to the provided AsciiPanel, using the default background
     * color.
     * @param panel the panel on which to write the String
     * @param s the String to write
     * @param p the point, in characters, at which the String will be written
     * @param foreground the color of the character
     */
    public static void write(AsciiPanel panel, String s, Point p,
            Color foreground)
        {write(panel, s, p, foreground, panel.getDefaultBackgroundColor());}
    
    /**
     * Writes a character to the provided AsciiPanel, using the default
     * background color.
     * @param panel the panel on which to write the character
     * @param c the char to write
     * @param p the point, in characters, at which the character will be written
     * @param foreground the color of the character
     */
    public static void write(AsciiPanel panel, char c, Point p,
            Color foreground)
        {write(panel, c, p, foreground, panel.getDefaultBackgroundColor());}
    
    /**
     * Writes an array of Strings to the provided AsciiPanel, with each String
     * on the line below the previous and using the default background color.
     * @param panel the panel on which to write the Strings
     * @param s the Strings to write
     * @param p the point, in characters, at which the first String will be
     * written
     * @param foreground the color of the Strings
     */
    public static void write(AsciiPanel panel, String[] s, Point p,
            Color foreground)
        {write(panel, s, p, foreground, panel.getDefaultBackgroundColor());}
    
    /**
     * Writes an array of Strings to the center of the provided AsciiPanel,
     * using the default background color.
     * @param panel the panel on which to write the Strings
     * @param s the Strings to write
     * @param y the line on which the first String will be written
     * @param foreground the color of the Strings
     */
    public static void writeCenter(AsciiPanel panel, String[] s, int y,
            Color foreground)
    {
        writeCenter(panel, s, y, foreground, panel.getDefaultBackgroundColor());
    }
    
    /**
     * Writes a String to the provided AsciiPanel, using the default foreground
     * and background colors.
     * @param panel the panel on which to write the String
     * @param s the String to write
     * @param p the point, in characters, at which the String will be written
     */
    public static void write(AsciiPanel panel, String s, Point p)
    {
        write(panel, s, p, panel.getDefaultForegroundColor(),
                panel.getDefaultBackgroundColor());
    }
    
    /**
     * Writes a character to the provided AsciiPanel, using the default
     * foreground and background colors.
     * @param panel the panel on which to write the character
     * @param c the char to write
     * @param p the point, in characters, at which the character will be written
     */
    public static void write(AsciiPanel panel, char c, Point p)
    {
        write(panel, c, p, panel.getDefaultForegroundColor(),
                panel.getDefaultBackgroundColor());
    }
    
    /**
     * Writes an array of Strings to the provided AsciiPanel, with each String
     * on the line below the previous and using the default foreground and
     * background colors.
     * @param panel the panel on which to write the Strings
     * @param s the Strings to write
     * @param p the point, in characters, at which the String
     */
    public static void write(AsciiPanel panel, String[] s, Point p)
    {
        write(panel, s, p, panel.getDefaultForegroundColor(),
                panel.getDefaultBackgroundColor());
    }
    
    /**
     * Writes an array of Strings to the center of the provided AsciiPanel,
     * using the default foreground and background colors.
     * @param panel the panel on which to write the Strings
     * @param s the Strings to write
     * @param y the line on which the first String will be written
     */
    public static void writeCenter(AsciiPanel panel, String[] s, int y)
    {
        writeCenter(panel, s, y, panel.getDefaultForegroundColor(),
                panel.getDefaultBackgroundColor());
    }
    
    /**
     * Returns true if the AsciiPanel contains the given Point.
     * @param panel the AsciiPanel to check for containment of the Point
     * @param p the Point to check for containment on the AsciiPanel
     * @return true if the AsciiPanel contains the Point
     */
    public static boolean contains(AsciiPanel panel, Point p)
        {return containsX(panel, p.x) && containsY(panel, p.y);}
    
    /**
     * Returns true if the AsciiPanel contains the given x value, in characters.
     * @param panel the AsciiPanel to check for containment of the x value
     * @param x the x value, in characters, to check for containment on the
     * AsciiPanel
     * @return true if the AsciiPanel contains the x value
     */
    public static boolean containsX(AsciiPanel panel, int x)
        {return x >= 0 && x < panel.getWidthInCharacters();}
    
    /**
     * Returns true if the AsciiPanel contains the given y value, in characters.
     * @param panel the AsciiPanel to check for containment of the y value
     * @param y the y value, in characters, to check for containment on the
     * AsciiPanel
     * @return true if the AsciiPanel contains the y value
     */
    public static boolean containsY(AsciiPanel panel, int y)
        {return y >= 0 && y < panel.getHeightInCharacters();}
    
    // LOCAL GRAPHICAL METHODS
    
    /**
     * Returns this Display's AsciiPanel.
     * @return this Display's AsciiPanel
     */
    public AsciiPanel getPanel()
        {return panel;}
    
    /**
     * Writes a String to this Display's AsciiPanel.
     * @param s the String to write
     * @param p the point, in characters, at which the String will be written
     * @param foreground the color of the String
     * @param background the color of the background
     */
    public void write(String s, Point p, Color foreground, Color background)
        {write(panel, s, p, foreground, background);}
    
    /**
     * Writes a character to this Display's AsciiPanel.
     * @param c the char to write
     * @param p the point, in characters, at which the character will be written
     * @param foreground the color of the character
     * @param background the color of the background
     */
    public void write(char c, Point p, Color foreground, Color background)
        {write(panel, c, p, foreground, background);}
    
    /**
     * Writes a ColorChar to this Display's AsciiPanel.
     * @param cc the ColorChar to write
     * @param p  the point, in characters, at which the ColorChar will be
     * written
     */
    public void write(ColorChar cc, Point p)
        {write(panel, cc, p);}
    
    /**
     * Writes an array of Strings to this Display's AsciiPanel, with each String
     * on the line below the previous.
     * @param s the Strings to write
     * @param p the point, in characters, at which the first String will be
     * written
     * @param foreground the color of the Strings
     * @param background the color of the background
     */
    public void write(String[] s, Point p, Color foreground, Color background)
        {write(panel, s, p, foreground, background);}
    
    /**
     * Writes a String to this Display's AsciiPanel, using the colors defined in
     * the ColorSet.
     * @param s the String to write
     * @param p the point, in characters, at which the String will be written
     * @param colors the colors of the first ColorChar in this ColorSet will be
     * used on all matching characters of the String printed; if a character is
     * not found in the ColorSet, default colors will be used instead
     */
    public void write(String s, ColorSet colors, Point p)
        {write(panel, s, colors, p);}
    
    /**
     * Writes the ColorChars of a ColorSet to this Display's AsciiPanel.
     * @param s the ColorSet to write
     * @param p the point, in characters, at which the ColorSet will be written
     */
    public void write(ColorSet s, Point p)
        {write(panel, s, p);}
    
    /**
     * Writes an array of ColorSets to this Display's AsciiPanel, with each
     * ColorSet on the line below the previous.
     * @param s the ColorSets to write
     * @param p the point, in characters, at which the first ColorSet will be
     * written
     */
    public void write(ColorSet[] s, Point p)
        {write(panel, s, p);}
    
    /**
     * Writes an array of Strings to the center of this Display's AsciiPanel.
     * @param s the Strings to write
     * @param y the line on which the first String will be written
     * @param foreground the color of the Strings
     * @param background the color of the background
     */
    public void writeCenter(String[] s, int y, Color foreground,
            Color background)
        {writeCenter(panel, s, y, foreground, background);}
    
    /**
     * Writes an array of ColorStrings to this Display's AsciiPanel.
     * @param s the ColorStrings to write
     * @param y the line on which the first ColorString will be written
     */
    public void writeCenter(ColorString[] s, int y)
        {writeCenter(panel, s, y);}
    
    /**
     * Writes a String to this Display's AsciiPanel, using the default
     * background color.
     * @param s the String to write
     * @param p the point, in characters, at which the String will be written
     * @param foreground the color of the character
     */
    public void write(String s, Point p, Color foreground)
        {write(panel, s, p, foreground);}
    
    /**
     * Writes a character to this Display's AsciiPanel, using the default
     * background color.
     * @param c the char to write
     * @param p the point, in characters, at which the character will be written
     * @param foreground the color of the character
     */
    public void write(char c, Point p, Color foreground)
        {write(panel, c, p, foreground);}
    
    /**
     * Writes an array of Strings to this Display's AsciiPanel, with each String
     * on the line below the previous and using the default background color.
     * @param s the Strings to write
     * @param p the point, in characters, at which the first String will be
     * written
     * @param foreground the color of the Strings
     */
    public void write(String[] s, Point p, Color foreground)
        {write(panel, s, p, foreground);}
    
    /**
     * Writes an array of Strings to the center of this Display's AsciiPanel,
     * using the default background color.
     * @param s the Strings to write
     * @param y the line on which the first String will be written
     * @param foreground the color of the Strings
     */
    public void writeCenter(String[] s, int y, Color foreground)
        {writeCenter(panel, s, y, foreground);}
    
    /**
     * Writes a String to this Display's AsciiPanel, using the default
     * foreground and background colors.
     * @param s the String to write
     * @param p the point, in characters, at which the String will be written
     */
    public void write(String s, Point p)
        {write(panel, s, p);}
    
    /**
     * Writes a character to this Display's AsciiPanel, using the default
     * foreground and background colors.
     * @param c the char to write
     * @param p the point, in characters, at which the character will be written
     */
    public void write(char c, Point p)
        {write(panel, c, p);}
    
    /**
     * Writes an array of Strings to this Display's AsciiPanel, with each String
     * on the line below the previous and using the default foreground and
     * background colors.
     * @param s the Strings to write
     * @param p the point, in characters, at which the first String will be
     * written
     */
    public void write(String[] s, Point p)
        {write(panel, s, p);}
    
    /**
     * Writes an array of Strings to the center of the provided AsciiPanel,
     * using the default foreground and background colors.
     * @param s the Strings to write
     * @param y the line on which the first String will be written
     */
    public void writeCenter(String[] s, int y)
        {writeCenter(panel, s, y);}
    
    /**
     * Returns true if this Display's AsciiPanel contains the given Point.
     * @param p the Point to check for containment on the AsciiPanel
     * @return true if the AsciiPanel contains the Point
     */
    public boolean contains(Point p)
        {return contains(panel, p);}
    
    /**
     * Returns true if this Display's AsciiPanel contains the given x value, in
     * characters.
     * @param x the x value, in characters, to check for containment on the
     * AsciiPanel
     * @return true if the AsciiPanel contains the x value
     */
    public boolean containsX(int x)
        {return containsX(panel, x);}
    
    /**
     * Returns true if this Display's AsciiPanel contains the given y value, in
     * characters.
     * @param y the y value, in characters, to check for containment on the
     * AsciiPanel
     * @return true if the AsciiPanel contains the y value
     */
    public boolean containsY(int y)
        {return containsY(panel, y);}
    
    /**
     * Returns the width of this Display's AsciiPanel in characters.
     * @return the width of this Display's AsciiPanel in characters
     */
    public int getCharWidth() 
        {return panel.getWidthInCharacters();}
    
    /**
     * Returns the height of this Display's AsciiPanel in characters.
     * @return the height of this Display's AsciiPanel in characters
     */
    public int getCharHeight()
        {return panel.getHeightInCharacters();}
}