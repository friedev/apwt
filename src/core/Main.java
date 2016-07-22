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
}