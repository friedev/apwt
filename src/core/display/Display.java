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

/** A class used to print output rather than doing so directly. */
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
    
    // GRAPHICAL METHODS
    
    public static void write(AsciiPanel panel, String s, Point p,
            Color foreground, Color background)
        {panel.write(s, p.x, p.y, foreground, background);}
    
    public static void write(AsciiPanel panel, char c, Point p,
            Color foreground, Color background)
        {panel.write(c, p.x, p.y, foreground, background);}
    
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
    
    public static void write(AsciiPanel panel, ColorSet[] s, Point p)
    {
        if (s == null || s.length == 0)
            return;
        
        for (int line = 0; line < s.length; line++)
            if (s[line] != null && contains(panel, new Point(p.x, p.y + line)))
                write(panel, s[line], new Point(p.x, p.y + line));
    }
    
    public static void writeCenter(AsciiPanel panel, String[] s, int y,
            Color foreground, Color background)
    {
        if (s == null || s.length == 0)
            return;
        
        for (int line = 0; line < s.length; line++)
            if (s[line] != null)
                panel.writeCenter(s[line], y + line, foreground, background);
    }
    
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
    
    public static void write(AsciiPanel panel, String s, Point p,
            Color foreground)
        {write(panel, s, p, foreground, panel.getDefaultBackgroundColor());}
    
    public static void write(AsciiPanel panel, char c, Point p,
            Color foreground)
        {write(panel, c, p, foreground, panel.getDefaultBackgroundColor());}
    
    public static void write(AsciiPanel panel, String[] s, Point p,
            Color foreground)
        {write(panel, s, p, foreground, panel.getDefaultBackgroundColor());}
    
    public static void writeCenter(AsciiPanel panel, String[] s, int y,
            Color foreground)
    {
        writeCenter(panel, s, y, foreground, panel.getDefaultBackgroundColor());
    }
    
    public static void write(AsciiPanel panel, String s, Point p)
    {
        write(panel, s, p, panel.getDefaultForegroundColor(),
                panel.getDefaultBackgroundColor());
    }
    
    public static void write(AsciiPanel panel, char c, Point p)
    {
        write(panel, c, p, panel.getDefaultForegroundColor(),
                panel.getDefaultBackgroundColor());
    }
    
    public static void write(AsciiPanel panel, String[] s, Point p)
    {
        write(panel, s, p, panel.getDefaultForegroundColor(),
                panel.getDefaultBackgroundColor());
    }
    
    public static void writeCenter(AsciiPanel panel, String[] s, int y)
    {
        writeCenter(panel, s, y, panel.getDefaultForegroundColor(),
                panel.getDefaultBackgroundColor());
    }
    
    public static boolean contains(AsciiPanel panel, Point p)
        {return containsX(panel, p.x) && containsY(panel, p.y);}
    
    public static boolean containsX(AsciiPanel panel, int x)
        {return x >= 0 && x < panel.getWidthInCharacters();}
    
    public static boolean containsY(AsciiPanel panel, int y)
        {return y >= 0 && y < panel.getHeightInCharacters();}
    
    public void write(String s, Point p, Color foreground, Color background)
        {write(panel, s, p, foreground, background);}
    
    public void write(char c, Point p, Color foreground, Color background)
        {write(panel, c, p, foreground, background);}
    
    public void write(String[] s, Point p, Color foreground, Color background)
        {write(panel, s, p, foreground, background);}
    
    public void write(String s, ColorSet colors, Point p)
        {write(panel, s, colors, p);}
    
    public void write(ColorSet s, Point p)
        {write(panel, s, p);}
    
    public void write(ColorSet[] s, Point p)
        {write(panel, s, p);}
    
    public void writeCenter(String[] s, int y, Color foreground,
            Color background)
        {writeCenter(panel, s, y, foreground, background);}
    
    public void writeCenter(ColorString[] s, int y)
        {writeCenter(panel, s, y);}
    
    public void write(String s, Point p, Color foreground)
        {write(panel, s, p, foreground);}
    
    public void write(char c, Point p, Color foreground)
        {write(panel, c, p, foreground);}
    
    public void write(String[] s, Point p, Color foreground)
        {write(panel, s, p, foreground);}
    
    public void writeCenter(String[] s, int y, Color foreground)
        {writeCenter(panel, s, y, foreground);}
    
    public void write(String s, Point p)
        {write(panel, s, p);}
    
    public void write(char c, Point p)
        {write(panel, c, p);}
    
    public void write(String[] s, Point p)
        {write(panel, s, p);}
    
    public void writeCenter(String[] s, int y)
        {writeCenter(panel, s, y);}
    
    public AsciiPanel getPanel() {return panel;}
    
    public boolean contains(Point p)
        {return contains(panel, p);}
    
    public boolean containsX(int x)
        {return containsX(panel, x);}
    
    public boolean containsY(int y)
        {return containsY(panel, y);}
    
    public int getCharWidth()  {return panel.getWidthInCharacters();}
    public int getCharHeight() {return panel.getHeightInCharacters();}
}