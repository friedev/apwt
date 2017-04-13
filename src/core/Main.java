package core;

import core.display.Display;
import core.storage.Commands;

/**
 * A sample main class that contains the basic components of the library to get
 * started quickly.
 */
public class Main
{
    // TODO add bookmarks
    
    public static final int INDENT_NOTIFICATION = 2;
    public static final int INDENT_PROMPT       = 1;
    public static final int INDENT_ERROR        = 2;
    
    /** The random generator. */
    public static SeededRandom random;
    
    /**
     * Initializes the core components and runs the game.
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        random = new SeededRandom();
        
        Display display = new Display();
        
        /*
        // Print a welcome message
        Display.println("Welcome to <game>!");
        Prompt.enterTo("begin");
        Display.println();
        
        while (true)
        {
            // Play the game
            readCommand(Prompt.getInput("Command").split(" "));
        }
        */
    }
    
    /**
     * Reads a command as a String array and performs the relevant action.
     * @param command the command that will be read and performed, must be
     * non-null and have at least one item in it
     * @return true if a command was executed, false if not
     */
    public static boolean readCommand(String[] command)
    {
        if (command == null || command.length == 0)
            return false;
        
        switch (parseAction(command[0], Commands.LIST_MAIN))
        {
            case Commands.ACTION_1:
                return action1();
            case Commands.ACTION_2:
                return action2();
            case Commands.ACTION_3:
                return action3();
            case Commands.HELP:
                return help();
            case Commands.QUIT:
                quit();
            default:
                Display.println(INDENT_ERROR, "Command not found.");
                Display.println();
                return false;
        }
    }
    
    /**
     * Parses the provided String as a command, returning the action keyword
     * read from the Properties object.
     * @param command the String to parse as a command, must be non-null
     * @param keywords the list of command mappings, must be non-null
     * @return the parsed action keyword, null if not found
     */
    public static String parseAction(String command,
            java.util.Properties keywords)
    {
        if (command == null || keywords == null)
            return Commands.INVALID;
        
        if ("".equals(command))
            return Commands.NOTHING;
        
        String parsed = keywords.getProperty(command);
        if (parsed == null)
            return Commands.INVALID;
        
        return parsed;
    }
    
    /**
     * Returns true if the entered String is one of the cancel commands as
     * listed in the Properties object.
     * @param string the string to check
     * @return true if the string, when parsed as a command, matches the cancel
     * keyword
     */
    public static boolean isCancel(String string)
    {
        return Commands.CANCEL.equals(parseAction(string, Commands.LIST_MAIN));
    }
    
    /**
     * Does something.
     * @return true if the action was completed
     */
    public static boolean action1()
    {
        return true;
    }
    
    /**
     * Does something.
     * @return true if the action was completed
     */
    public static boolean action2()
    {
        return true;
    }
    
    /**
     * Does something.
     * @return true if the action was completed
     */
    public static boolean action3()
    {
        return true;
    }
    
    /**
     * Prints a list of commands and their relevant actions.
     * @return true if the action was completed
     */
    public static boolean help()
    {
        // TODO borrow the spacing format String from EverSector
        Display.println(1, "action1: does something");
        Display.println(1, "action2: does something");
        Display.println(1, "action3: does something");
        Display.println(1, "cancel: cancel the current action");
        Display.println(1, "quit: exit the program");
        Display.println();
        return true;
    }
    
    /** Exits the program. */
    public static void quit()
    {
        System.exit(0);
    }
    
    /**
     * Quits the game immediately with an error message.
     * @param message the message to display before closing the game
     */
    public static void quitWithMessage(String message)
    {
        Display.println(message);
        Prompt.enterTo("close");
        System.exit(1);
    }
}