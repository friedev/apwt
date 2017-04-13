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
     * The number of indenting spaces to include before a list item by default.
     */
    public static final int LIST_INDENTS = 1;
    
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
    
    /**
     * Prints a String with no newline character following it.
     * @param output the String to print
     */
    public static void print(String output)
        {System.out.print(output);}
    
    /**
     * Prints a String followed by a newline character.
     * @param output the String to print
     */
    public static void println(String output)
        {System.out.println(output);}
    
    /** Prints a newline character. */
    public static void println()
        {println("");}
    
    /**
     * Prints a variable number of spaces.
     * @param spaces the number of space characters to print
     */
    public static void printSpaces(int spaces)
        {print(getSpaces(spaces));}
    
    /**
     * Returns a String containing the number of spaces specified.
     * @param spaces the number of spaces to include in the String
     * @return a String consisting of the number of space characters provided
     */
    public static String getSpaces(int spaces)
    {
        if (spaces <= 0)
            return "";
        
        StringBuilder indentedOutput = new StringBuilder();
        for (int i = 0; i < spaces; i++)
            indentedOutput.append(" ");
        return indentedOutput.toString();
    }
    
    /**
     * Prints a String preceded by the specified number of indents and no
     * newline character.
     * @param indents the number of spaces to indent the output with
     * @param output the String to print
     */
    public static void print(int indents, String output)
    {
        printSpaces(indents);
        print(output);
    }
    
    /**
     * Prints a String preceded by the specified number of indents and followed
     * by a newline character.
     * @param indents the number of space characters to indent the output with
     * @param output the String to print
     */
    public static void println(int indents, String output)
    {
        printSpaces(indents);
        println(output);
    }
    
    /**
     * Prints the specified String preceded by the default number of indenting
     * spaces and a dash.
     * @param item the String to print as a list item
     */
    public static void printListItem(String item)
        {printListItem(LIST_INDENTS, item);}
    
    /**
     * Prints the specified String preceded by the default number of indenting
     * spaces and a dash, and followed by a colon, space, and the provided
     * String as a value.
     * @param item the String to print as a list item
     * @param value the String to print as the value for the list item
     */
    public static void printListItem(String item, String value)
        {printListItem(item + ": " + value);}
    
    /**
     * Prints the specified String preceded by the default number of indenting
     * spaces and a dash, and followed by a colon, space, and the provided
     * integer value.
     * @param item the String to print as a list item
     * @param value the integer to print as the value for the list item
     */
    public static void printListItem(String item, int value)
        {printListItem(item, "" + value);}
    
    /**
     * Prints the specified String preceded by the provided number of indenting
     * spaces and a dash.
     * @param indents the number of space characters to indent the output with
     * @param item the String to print as a list item
     */
    public static void printListItem(int indents, String item)
        {println(indents, "-" + item);}
    
    /**
     * Prints the specified String preceded by the provided number of indenting
     * spaces and a dash, and followed by a colon, space, and the provided
     * String as a value.
     * @param indents the number of space characters to indent the output with
     * @param item the String to print as a list item
     * @param value the String to print as the value for the list item
     */
    public static void printListItem(int indents, String item, String value)
        {printListItem(indents, item + ": " + value);}
    
    /**
     * Prints the specified String preceded by the provided number of indenting
     * spaces and a dash, and followed by a colon, space, and the provided
     * String as a value.
     * @param indents the number of space characters to indent the output with
     * @param item the String to print as a list item
     * @param value the integer to print as the value for the list item
     */
    public static void printListItem(int indents, String item, int value)
        {printListItem(indents, item, "" + value);}
    
    /**
     * Prints the provided number of newline characters, creating spacing
     * between previous lines and lines to follow.
     * @param spacing the number of newline characters to print as spacing
     */
    public static void printSpacing(int spacing)
    {
        if (spacing <= 0)
            return;
        
        for (int i = 0; i < spacing; i++)
            println();
    }
    
    // TODO create a version of printSpacingBetween that returns a String
    
    /**
     * Prints the two provided Strings with at most the provided number of space
     * characters between them.
     * @param spacing the maximum number of spaces to print between items; the
     * first String will overwrite these spaces
     * @param s1 the String to print first; will overwrite the spacing
     * @param s2 the String to print after the first String and spacing
     * characters
     */
    public static void printSpacingBetween(int spacing, String s1, String s2)
        {System.out.printf("%-" + spacing + "s %s", s1, s2);}
}