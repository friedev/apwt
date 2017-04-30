package corelib;

import corelib.display.Display;
import corelib.storage.Commands;
import squidpony.squidmath.RNG;

/**
 * A sample main class that contains the basic components of the library to get
 * started quickly.
 */
public class Main
{
    // CONSTANTS
    
    /** The number of spaces used to indent notifications. */
    public static final int INDENT_NOTIFICATION = 2;
    
    /** The number of spaces used to indent prompts. */
    public static final int INDENT_PROMPT = 1;
    
    /** The number of spaces used to indent error messages. */
    public static final int INDENT_ERROR = 2;
    
    // FIELDS
    
    /** The random generator. */
    public static RNG rng;
    
    // METHODS
    
    /**
     * Initializes the core components and runs the game.
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        rng = new RNG();
        new Display().init();
        
        /*
        // Item Definition Demo
        for (String line: new items.ContainerItem("The Container",
                "The one container to rule them all.", 10000, 25, 100).define())
            Console.println(line);
        */
        
        /*
        // WeightedLetterNamegen Test - needs more samples!
        WeightedLetterNamegen generator = new WeightedLetterNamegen(
                new String[]{"Boldorf", "Dalian", "Renovhir", "Islix",
                    "Galandor", "Keltzquin", "Mogrithe"});
        for (int i = 0; i < 50; i++)
            Console.println(generator.generate()[0]);
        */
        
        /*
        // Name Generator Demo
        NameGenerator generator = new NameGenerator(new String[]{"names/syl1.txt",
                "names/syl2.txt", "names/syl3.txt"});
        for (int i = 0; i < 10; i++)
            Console.println(generator.generateRandomLengthName(2));
        */
        
        /*
        // Print a welcome message
        Console.println("Welcome to <game>!");
        Prompt.enterTo("begin");
        Console.println();
        
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
                Console.println(INDENT_ERROR, "Command not found.");
                Console.println();
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
        Console.println(1, "action1: does something");
        Console.println(1, "action2: does something");
        Console.println(1, "action3: does something");
        Console.println(1, "cancel: cancel the current action");
        Console.println(1, "quit: exit the program");
        Console.println();
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
        Console.println(message);
        Prompt.enterTo("close");
        System.exit(1);
    }
}