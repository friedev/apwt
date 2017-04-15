package core.display;

import core.display.screens.Screen;
import core.display.screens.MasterScreen;
import asciiPanel.AsciiFont;
import javax.swing.JFrame;
import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import core.Point;

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
    
    public static void write(AsciiPanel panel, String s, Point p)
        {panel.write(s, p.x, p.y);}
    
    public static void write(AsciiPanel panel, char c, Point p)
        {panel.write(c, p.x, p.y);}
    
    public static void write(AsciiPanel panel, String[] s, Point p)
    {
        if (s == null || s.length == 0)
            return;
        
        for (int line = 0; line < s.length; line++)
            if (s[line] != null && contains(panel, new Point(p.x, p.y + line)))
                write(panel, s[line], new Point(p.x, p.y + line));
    }
    
    public static void writeCenter(AsciiPanel panel, String[] s, int y)
    {
        if (s == null || s.length == 0)
            return;
        
        for (int line = 0; line < s.length; line++)
            if (s[line] != null)
                panel.writeCenter(s[line], y + line);
    }
    
    public static boolean contains(AsciiPanel panel, Point p)
        {return containsX(panel, p.x) && containsY(panel, p.y);}
    
    public static boolean containsX(AsciiPanel panel, int x)
        {return x >= 0 && x < panel.getWidthInCharacters();}
    
    public static boolean containsY(AsciiPanel panel, int y)
        {return y >= 0 && y < panel.getHeightInCharacters();}
    
    public AsciiPanel get() {return panel;}
    
    public void write(String s, Point p)
        {write(panel, s, p);}
    
    public void write(char c, Point p)
        {write(panel, c, p);}
    
    public void write(String[] s, Point p)
        {write(panel, s, p);}
    
    public void writeCenter(String[] s, int y)
        {writeCenter(panel, s, y);}
    
    public boolean contains(Point p)
        {return contains(panel, p);}
    
    public boolean containsX(int x)
        {return containsX(panel, x);}
    
    public boolean containsY(int y)
        {return containsY(panel, y);}
    
    public int getCharWidth()  {return panel.getWidthInCharacters();}
    public int getCharHeight() {return panel.getHeightInCharacters();}
}