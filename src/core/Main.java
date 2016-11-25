package core;

/**
 * A sample main class that contains the basic components of the library to get
 * started quickly.
 */
public class Main
{
    // TODO add bookmarks
    
    /** The path to the commands file. */
    public static final String COMMANDS_PATH = "commands.properties";
    
    /** The list of commands read from the properties file. */
    public static final java.util.Properties COMMANDS =
            FileManager.load(COMMANDS_PATH);
    
    public static final String ACTION_1 = "a";
    public static final String ACTION_2 = "b";
    public static final String ACTION_3 = "c";
    public static final String HELP     = "help";
    public static final String QUIT     = "quit";
    public static final String CANCEL   = "cancel";
    
    /** The random generator. */
    public static SeededRandom random;
    
    /**
     * Initializes the core components and runs the game.
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        random = new SeededRandom();
        
        // Print a welcome message
        Display.println("Welcome to <game>!");
        Prompt.enterTo("begin");
        
        while (true)
        {
            // Play the game
            readCommand(Prompt.getInput("Command").split(" "));
        }
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
        
        switch (parseAction(command[0]))
        {
            case ACTION_1:
                return action1();
            case ACTION_2:
                return action2();
            case ACTION_3:
                return action3();
            case HELP:
                return help();
            case QUIT:
                quit();
            default:
                Display.println(2, "Command not found.");
                Display.println();
                return false;
        }
    }
    
    /**
     * Parses the provided String as a command, returning the action keyword
     * read from the Properties object.
     * @param command the String to parse as a command, must be non-null
     * @return the parsed action keyword, null if not found
     */
    public static String parseAction(String command)
    {
        if (command == null)
            return null;
        
        return COMMANDS.getProperty(command);
    }
    
    /**
     * Returns true if the entered String is one of the cancel commands as
     * listed in the Properties object.
     * @param string the string to check
     * @return true if the string, when parsed as a command, matches the cancel
     * keyword
     */
    public static boolean isCancel(String string)
        {return CANCEL.equals(parseAction(string));}
    
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