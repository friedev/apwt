package core;

/**
 * A sample main class that contains the basic components of the library to get
 * started quickly.
 */
public class Main
{
    public static final String CANCEL     = "cancel";
    public static final String CANCEL_KEY = "q";
    
    public static final String COMMANDS_PATH = "commands.properties";
    public static final java.util.Properties COMMANDS =
            FileManager.load(COMMANDS_PATH);
    
    public static final String ACTION_1 = "a";
    public static final String ACTION_2 = "b";
    public static final String ACTION_3 = "c";
    
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
    
    public static boolean readCommand(String[] command)
    {
        if (command == null || command.length == 0)
            return false;
        
        String action = COMMANDS.getProperty(command[0]);
        
        if (action == null)
        {
            Display.println(2, "Command not found.");
            return false;
        }
        
        switch (action)
        {
            case ACTION_1:
                return action1();
            case ACTION_2:
                return action2();
            case ACTION_3:
                return action3();
            default:
                Display.println(2, "Command not found.");
                Display.println();
                return false;
        }
    }
    
    public static boolean action1()
    {
        return true;
    }
    
    public static boolean action2()
    {
        return true;
    }
    
    public static boolean action3()
    {
        return true;
    }
    
    public static boolean isCancel(String string)
        {return CANCEL.equals(string) || CANCEL_KEY.equals(string);}
    
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