package corelib.display;

import corelib.display.glyphs.ColorSet;
import corelib.display.glyphs.ColorChar;
import corelib.display.glyphs.ColorString;
import corelib.display.screens.Screen;
import javax.swing.JFrame;
import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import squidpony.squidgrid.Direction;
import squidpony.squidmath.Coord;

/**
 * A class designed to create, contain, and operate an
 * {@link asciiPanel.AsciiPanel}, as well as providing many utility functions.
 */
public class Display extends JFrame implements KeyListener
{
    // FIELDS
    
    /**
     * The {@link asciiPanel.AsciiPanel} that will be used to display all
     * "graphical" data.
     */
    private AsciiPanel panel;
    
    /**
     * The current {@link corelib.display.screens.Screen} being displayed by the
     * panel and where input from the KeyListener is being processed.
     */
    private Screen screen;

    // CONSTRUCTORS
    
    /**
     * Creates a {@link Display} to wrap a constructed
     * {@link asciiPanel.AsciiPanel}.
     * @param panel the {@link asciiPanel.AsciiPanel} to wrap with the
     * {@link Display}
     */
    public Display(AsciiPanel panel)
    {
        super();
        this.panel = panel;
        add(this.panel);
        pack();
    }
    
    /**
     * Initializes the {@link corelib.display.screens.Screen} and keylistener,
     * as well as setting up default settings outside of a constructor.
     * @param startScreen the initial {@link corelib.display.screens.Screen} to
     * display
     * @return the initialized display, this
     */
    public Display init(Screen startScreen)
    {
        screen = startScreen;
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
    
    /**
     * Converts the given KeyEvent into a Direction, referring to many different
     * sets of directional keys. These include the arrow keys, WASD, the VI
     * keys, and the number pad.
     * @param key the KeyEvent to convert into a Direction
     * @return the Direction corresponding to the provided KeyEvent
     */
    public static Direction keyToDirection(KeyEvent key)
    {
        if (key == null)
            return null;
        
        switch (key.getKeyCode())
        {
            case KeyEvent.VK_UP: case KeyEvent.VK_K: case KeyEvent.VK_W:
            case KeyEvent.VK_NUMPAD8:
                return Direction.UP;
            case KeyEvent.VK_DOWN: case KeyEvent.VK_J: case KeyEvent.VK_S:
            case KeyEvent.VK_NUMPAD2:
                return Direction.DOWN;
            case KeyEvent.VK_LEFT: case KeyEvent.VK_H: case KeyEvent.VK_A: 
            case KeyEvent.VK_NUMPAD4:
                return Direction.LEFT;
            case KeyEvent.VK_RIGHT: case KeyEvent.VK_L: case KeyEvent.VK_D:
            case KeyEvent.VK_NUMPAD6:
                return Direction.RIGHT;
            case KeyEvent.VK_Y: case KeyEvent.VK_NUMPAD7:
                return Direction.UP_LEFT;
            case KeyEvent.VK_U: case KeyEvent.VK_NUMPAD9:
                return Direction.UP_RIGHT;
            case KeyEvent.VK_B: case KeyEvent.VK_NUMPAD1:
                return Direction.DOWN_LEFT;
            case KeyEvent.VK_N: case KeyEvent.VK_NUMPAD3:
                return Direction.DOWN_RIGHT;
        }
        
        return null;
    }
    
    // STATIC GRAPHICAL METHODS
    
    /**
     * Writes a String to the provided {@link asciiPanel.AsciiPanel}, using the
     * default foreground and background colors.
     * @param panel the {@link asciiPanel.AsciiPanel} on which to write the
     * String
     * @param s the String to write
     * @param point the point, in characters, at which the String will be
     * written
     */
    public static void write(AsciiPanel panel, Coord point, String s)
        {panel.write(s, point.x, point.y);}
    
    /**
     * Writes a character to the provided {@link asciiPanel.AsciiPanel}, using
     * the default foreground and background colors.
     * @param panel the {@link asciiPanel.AsciiPanel} on which to write the
     * character
     * @param c the char to write
     * @param point the point, in characters, at which the character will be
     * written
     */
    public static void write(AsciiPanel panel, Coord point, char c)
        {panel.write(c, point.x, point.y);}
    
    /**
     * Writes a {@link corelib.display.glyphs.ColorChar} to the provided
     * {@link asciiPanel.AsciiPanel}.
     * @param panel the {@link asciiPanel.AsciiPanel} on which to write the
     * {@link corelib.display.glyphs.ColorChar}
     * @param cc the {@link corelib.display.glyphs.ColorChar} to write
     * @param point  the point, in characters, at which the
     * {@link corelib.display.glyphs.ColorChar} will be written
     */
    public static void write(AsciiPanel panel, Coord point, ColorChar cc)
    {
        panel.write(cc.getChar(), point.x, point.y, cc.getForeground(),
                cc.getBackground());
    }
    
    /**
     * Writes an array of Strings to the provided {@link asciiPanel.AsciiPanel},
     * with each String on the line below the previous.
     * @param panel the {@link asciiPanel.AsciiPanel} on which to write the
     * Strings
     * @param s the Strings to write
     * @param point the point, in characters, at which the first String will be
     * written
     */
    public static void write(AsciiPanel panel, Coord point, String... s)
    {
        if (s == null || s.length == 0)
            return;
        
        for (int line = 0; line < s.length; line++)
            if (s[line] != null && contains(panel,
                    Coord.get(point.x, point.y + line)))
                write(panel, Coord.get(point.x, point.y + line), s[line]);
    }
    
    /**
     * Writes a String to the provided {@link asciiPanel.AsciiPanel}, using the
     * colors defined in the {@link corelib.display.glyphs.ColorSet}.
     * @param panel the {@link asciiPanel.AsciiPanel} on which to write the
     * String
     * @param s the String to write
     * @param point the point, in characters, at which the String will be
     * written
     * @param colors the colors of the first
     * {@link corelib.display.glyphs.ColorChar} in this
     * {@link corelib.display.glyphs.ColorSet} will be used on all matching
     * characters of the String printed; if a character is not found in the
     * {@link corelib.display.glyphs.ColorSet}, default colors will be used
     * instead
     */
    public static void write(AsciiPanel panel, Coord point, ColorSet colors,
            String s)
    {
        if (colors == null)
            return;
        
        for (int i = 0; i < s.length(); i++)
        {
            char cur = s.charAt(i);
            ColorChar curColors = colors.getColorChar(cur);
            if (curColors == null)
            {
                panel.write(cur, point.x + i, point.y);
            }
            else
            {
                curColors.syncDefaults(panel);
                panel.write(cur, point.x + i, point.y,
                        curColors.getForeground(), curColors.getBackground());
            }
        }
    }
    
    /**
     * Writes the {@link corelib.display.glyphs.ColorChar ColorChars} of a
     * {@link corelib.display.glyphs.ColorSet} to the provided
     * {@link asciiPanel.AsciiPanel}.
     * @param panel the {@link asciiPanel.AsciiPanel} on which to write the
     * {@link corelib.display.glyphs.ColorSet}
     * @param s the {@link corelib.display.glyphs.ColorSet} to write
     * @param point the point, in characters, at which the
     * {@link corelib.display.glyphs.ColorSet} will be written
     */
    public static void write(AsciiPanel panel, Coord point, ColorSet s)
    {
        if (s == null || s.getSet() == null || s.getSet().isEmpty())
            return;
        
        java.util.List<ColorChar> chars = s.getSet();
        
        for (int i = 0; i < chars.size(); i++)
        {
            chars.get(i).syncDefaults(panel);
            panel.write(chars.get(i).getChar(), point.x + i, point.y,
                    chars.get(i).getForeground(), chars.get(i).getBackground());
        }
    }
    
    /**
     * Writes an array of {@link corelib.display.glyphs.ColorSet ColorSets} to
     * the provided {@link asciiPanel.AsciiPanel}, with each
     * {@link corelib.display.glyphs.ColorSet} on the line below the previous.
     * @param panel the {@link asciiPanel.AsciiPanel} on which to write the
     * {@link corelib.display.glyphs.ColorSet ColorSets}
     * @param s the {@link corelib.display.glyphs.ColorSet ColorSets} to write
     * @param point the point, in characters, at which the first
     * {@link corelib.display.glyphs.ColorSet} will be written
     */
    public static void write(AsciiPanel panel, Coord point,  ColorSet... s)
    {
        if (s == null || s.length == 0)
            return;
        
        for (int line = 0; line < s.length; line++)
            if (s[line] != null && contains(panel,
                    Coord.get(point.x, point.y + line)))
                write(panel, Coord.get(point.x, point.y + line), s[line]);
    }
    
    /**
     * Writes an array of Strings to the center of the provided
     * {@link asciiPanel.AsciiPanel}.
     * @param panel the {@link asciiPanel.AsciiPanel} on which to write the
     * Strings
     * @param s the Strings to write
     * @param y the line on which the first String will be written
     */
    public static void writeCenter(AsciiPanel panel, int y, String... s)
    {
        if (s == null || s.length == 0)
            return;
        
        for (int line = 0; line < s.length; line++)
            if (s[line] != null)
                panel.writeCenter(s[line], y + line);
    }
    
    /**
     * Writes an array of
     * {@link corelib.display.glyphs.ColorString ColorStrings} to the provided
     * {@link asciiPanel.AsciiPanel}.
     * @param panel the {@link asciiPanel.AsciiPanel} on which to write the
     * {@link corelib.display.glyphs.ColorString ColorStrings}
     * @param s the {@link corelib.display.glyphs.ColorString ColorStrings} to
     * write
     * @param y the line on which the first
     * {@link corelib.display.glyphs.ColorString} will be written
     */
    public static void writeCenter(AsciiPanel panel, int y, ColorString... s)
    {
        if (s == null || s.length == 0)
            return;
        
        for (int line = 0; line < s.length; line++)
        {
            if (s[line] != null)
            {
                s[line].syncDefaults(panel);
                panel.writeCenter(s[line].getString(), y + line,
                        s[line].getForeground(), s[line].getBackground());
            }
        }
    }
    
    /**
     * Returns true if the {@link asciiPanel.AsciiPanel} contains the given
     * Coord.
     * @param panel the {@link asciiPanel.AsciiPanel} to check for containment
     * of the Coord
     * @param point the Coord to check for containment on the
     * {@link asciiPanel.AsciiPanel}
     * @return true if the {@link asciiPanel.AsciiPanel} contains the Coord
     */
    public static boolean contains(AsciiPanel panel, Coord point)
        {return containsX(panel, point.x) && containsY(panel, point.y);}
    
    /**
     * Returns true if the {@link asciiPanel.AsciiPanel} contains the given x 
     * value, in characters.
     * @param panel the {@link asciiPanel.AsciiPanel} to check for containment
     * of the x value
     * @param x the x value, in characters, to check for containment on the
     * {@link asciiPanel.AsciiPanel}
     * @return true if the {@link asciiPanel.AsciiPanel} contains the x value
     */
    public static boolean containsX(AsciiPanel panel, int x)
        {return x >= 0 && x < panel.getWidthInCharacters();}
    
    /**
     * Returns true if the {@link asciiPanel.AsciiPanel} contains the given y
     * value, in characters.
     * @param panel the {@link asciiPanel.AsciiPanel} to check for containment
     * of the y value
     * @param y the y value, in characters, to check for containment on the
     * {@link asciiPanel.AsciiPanel}
     * @return true if the {@link asciiPanel.AsciiPanel} contains the y value
     */
    public static boolean containsY(AsciiPanel panel, int y)
        {return y >= 0 && y < panel.getHeightInCharacters();}
    
    // LOCAL GRAPHICAL METHODS
    
    /**
     * Returns this {@link Display}'s {@link asciiPanel.AsciiPanel}.
     * @return this {@link Display}'s {@link asciiPanel.AsciiPanel}
     */
    public AsciiPanel getPanel()
        {return panel;}
    
    /**
     * Writes a String to this {@link Display}'s {@link asciiPanel.AsciiPanel},
     * using the default
     * foreground and background colors.
     * @param s the String to write
     * @param point the point, in characters, at which the String will be
     * written
     */
    public void write(Coord point, String s)
        {write(panel, point, s);}
    
    /**
     * Writes a character to this {@link Display}'s
     * {@link asciiPanel.AsciiPanel}, using the default foreground and
     * background colors.
     * @param c the char to write
     * @param point the point, in characters, at which the character will be
     * written
     */
    public void write(Coord point, char c)
        {write(panel, point, c);}
    
    /**
     * Writes a {@link corelib.display.glyphs.ColorChar} to this
     * {@link Display}'s {@link asciiPanel.AsciiPanel}.
     * @param cc the {@link corelib.display.glyphs.ColorChar} to write
     * @param point  the point, in characters, at which the
     * {@link corelib.display.glyphs.ColorChar} will be
     * written
     */
    public void write(Coord point, ColorChar cc)
        {write(panel, point, cc);}
    
    /**
     * Writes an array of Strings to this {@link Display}'s
     * {@link asciiPanel.AsciiPanel}, with each String
     * on the line below the previous.
     * @param s the Strings to write
     * @param point the point, in characters, at which the first String will be
     * written
     */
    public void write(Coord point, String... s)
        {write(panel, point, s);}
    
    /**
     * Writes a String to this {@link Display}'s {@link asciiPanel.AsciiPanel},
     * using the colors defined in the {@link corelib.display.glyphs.ColorSet}.
     * @param s the String to write
     * @param point the point, in characters, at which the String will be
     * written
     * @param colors the colors of the first
     * {@link corelib.display.glyphs.ColorChar} in this
     * {@link corelib.display.glyphs.ColorSet} will be used on all matching
     * characters of the String printed; if a character is not found in the
     * {@link corelib.display.glyphs.ColorSet}, default colors will be used
     * instead
     */
    public void write(Coord point, ColorSet colors, String s)
        {write(panel, point, colors, s);}
    
    /**
     * Writes the {@link corelib.display.glyphs.ColorChar}s of a
     * {@link corelib.display.glyphs.ColorSet} to this {@link Display}'s
     * {@link asciiPanel.AsciiPanel}.
     * @param s the {@link corelib.display.glyphs.ColorSet} to write
     * @param point the point, in characters, at which the
     * {@link corelib.display.glyphs.ColorSet} will be written
     */
    public void write(Coord point, ColorSet s)
        {write(panel, point, s);}
    
    /**
     * Writes an array of {@link corelib.display.glyphs.ColorSet ColorSets} to
     * this {@link Display}'s {@link asciiPanel.AsciiPanel}, with each
     * {@link corelib.display.glyphs.ColorSet} on the line below the previous.
     * @param s the {@link corelib.display.glyphs.ColorSet ColorSets} to write
     * @param point the point, in characters, at which the first
     * {@link corelib.display.glyphs.ColorSet} will
     * be written
     */
    public void write(Coord point, ColorSet... s)
        {write(panel, point, s);}
    
    /**
     * Writes an array of Strings to the center of this {@link Display}'s
     * {@link asciiPanel.AsciiPanel}.
     * @param s the Strings to write
     * @param y the line on which the first String will be written
     */
    public void writeCenter(int y, String... s)
        {writeCenter(panel, y, s);}
    
    /**
     * Writes an array of
     * {@link corelib.display.glyphs.ColorString ColorStrings} to this
     * {@link Display}'s {@link asciiPanel.AsciiPanel}.
     * @param s the {@link corelib.display.glyphs.ColorString ColorStrings} to
     * write
     * @param y the line on which the first
     * {@link corelib.display.glyphs.ColorString ColorString} will be written
     */
    public void writeCenter(int y, ColorString... s)
        {writeCenter(panel, y, s);}
    
    /**
     * Returns true if this {@link Display}'s {@link asciiPanel.AsciiPanel}
     * contains the given Coord.
     * @param point the Coord to check for containment on the
     * {@link asciiPanel.AsciiPanel}
     * @return true if the {@link asciiPanel.AsciiPanel} contains the Coord
     */
    public boolean contains(Coord point)
        {return contains(panel, point);}
    
    /**
     * Returns true if this {@link Display}'s {@link asciiPanel.AsciiPanel}
     * contains the given x value, in characters.
     * @param x the x value, in characters, to check for containment on the
     * {@link asciiPanel.AsciiPanel}
     * @return true if the {@link asciiPanel.AsciiPanel} contains the x value
     */
    public boolean containsX(int x)
        {return containsX(panel, x);}
    
    /**
     * Returns true if this {@link Display}'s {@link asciiPanel.AsciiPanel}
     * contains the given y value, in characters.
     * @param y the y value, in characters, to check for containment on the
     * {@link asciiPanel.AsciiPanel}
     * @return true if the {@link asciiPanel.AsciiPanel} contains the y value
     */
    public boolean containsY(int y)
        {return containsY(panel, y);}
    
    /**
     * Returns the width of this {@link Display}'s {@link asciiPanel.AsciiPanel}
     * in characters.
     * @return the width of this {@link Display}'s {@link asciiPanel.AsciiPanel}
     * in characters
     */
    public int getCharWidth() 
        {return panel.getWidthInCharacters();}
    
    /**
     * Returns the height of this {@link Display}'s
     * {@link asciiPanel.AsciiPanel} in characters.
     * @return the height of this {@link Display}'s
     * {@link asciiPanel.AsciiPanel} in characters
     */
    public int getCharHeight()
        {return panel.getHeightInCharacters();}
}