package core.display;

import asciiPanel.AsciiFont;
import javax.swing.JFrame;
import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
    public static final int DEFAULT_HEIGHT = 40;
    
    /**
     * The font that will be used by default if not supplied in a constructor.
     */
    public static final AsciiFont DEFAULT_FONT = AsciiFont.TAFFER_10x10;
    
    // FIELDS
    
    /** The AsciiPanel that will be used to display all "graphical" data. */
    AsciiPanel terminal;
    
    /**
     * The current screen being displayed by the terminal and where input from
     * the KeyListener is being processed.
     */
    Screen screen;

    // CONSTRUCTORS
    
    /**
     * The main Display constructor, accepting a constructed AsciiPanel and
     * start screen.
     * @param panel the panel to be used as the Display's terminal
     * @param startScreen the screen that will be displayed initially
     */
    public Display(AsciiPanel panel, Screen startScreen)
    {
        super();
        terminal = panel;
        add(terminal);
        pack();
        screen = new ExampleScreen();
        addKeyListener(this);
        repaint();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    /**
     * A Display constructor that uses the default font.
     * @param width the width of the terminal
     * @param height the height of the terminal
     * @param startScreen the screen that will be displayed initially
     */
    public Display(int width, int height, Screen startScreen)
        {this(new AsciiPanel(width, height, DEFAULT_FONT), startScreen);}
    
    /**
     * A Display constructor that uses the default width and height.
     * @param font the AsciiFont used by the terminal
     * @param startScreen the screen that will be displayed initially
     */
    public Display(AsciiFont font, Screen startScreen)
    {
        this(new AsciiPanel(DEFAULT_WIDTH, DEFAULT_HEIGHT, font), startScreen);
    }
    
    /**
     * A Display constructor that uses the default font and start screen.
     * @param width the width of the terminal
     * @param height the height of the terminal
     */
    public Display(int width, int height)
    {
        this(new AsciiPanel(width, height, DEFAULT_FONT), new ExampleScreen());
    }
    
    /**
     * A Display constructor that uses the default width, height, and start
     * screen.
     * @param font the AsciiFont used by the terminal
     */
    public Display(AsciiFont font)
    {
        this(new AsciiPanel(DEFAULT_WIDTH, DEFAULT_HEIGHT, font),
                new ExampleScreen());
    }
    
    /**
     * A Display constructor that uses the default width, height, and font.
     * @param startScreen the screen that will be displayed initially
     */
    public Display(Screen startScreen)
    {
        this(new AsciiPanel(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_FONT),
                startScreen);
    }
    
    /** A Display constructor that uses all default presets. */
    public Display()
    {
        this(new AsciiPanel(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_FONT),
                new ExampleScreen());
    }

    // INHERITED METHODS
    
    @Override
    public void repaint()
    {
        terminal.clear();
        screen.displayOutput(terminal);
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
    
    // CONSOLE METHODS
    
    public static void print(String output)
        {System.out.print(output);}
    
    public static void println(String output)
        {System.out.println(output);}
    
    public static void println()
        {println("");}
    
    public static void printSpaces(int spaces)
        {print(getSpaces(spaces));}
    
    public static String getSpaces(int spaces)
    {
        if (spaces <= 0)
            return "";
        
        StringBuilder indentedOutput = new StringBuilder();
        for (int i = 0; i < spaces; i++)
            indentedOutput.append(" ");
        return indentedOutput.toString();
    }
    
    public static void println(int indents, String output)
    {
        printSpaces(indents);
        println(output);
    }
    
    public static void print(int indents, String output)
    {
        printSpaces(indents);
        print(output);
    }
    
    public static void printListItem(String item)
        {printListItem(1, item);}
    
    public static void printListItem(String item, String value)
        {printListItem(item + ": " + value);}
    
    public static void printListItem(String item, int value)
        {printListItem(item, "" + value);}
    
    public static void printListItem(int indents, String item)
        {println(indents, "-" + item);}
    
    public static void printListItem(int indents, String item, String value)
        {printListItem(indents, item + ": " + value);}
    
    public static void printListItem(int indents, String item, int value)
        {printListItem(indents, item, "" + value);}
    
    public static void printSpacing(int spacing)
    {
        if (spacing <= 0)
            return;
        
        for (int i = 0; i < spacing; i++)
            println();
    }
    
    public static void printSpacingBetween(int spacing, String s1, String s2)
        {System.out.printf("%-" + spacing + "s %s", s1, s2);}
}