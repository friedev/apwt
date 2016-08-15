package core;

/**
 * A sample main class that contains the basic components of the library to get
 * started quickly.
 */
public class Main
{
    public static SeededRandom random;
    
    /**
     * Initializes the core components and runs the game.
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        random = new SeededRandom();
        
        // Print a welcome message
        System.out.println("Welcome to <game>!");
        Prompt.enterTo("begin");
        
        while (true)
        {
            // Play the game
            return;
        }
    }
    
    /**
     * Quits the game immediately with an error message.
     * @param message the message to display before closing the game
     */
    public static void quitWithMessage(String message)
    {
        System.out.println(message);
        Prompt.enterTo("close");
        System.exit(1);
    }
}