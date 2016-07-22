package core;

import java.util.Scanner;

/** A prompt that manages input and output. */
public class Prompt
{
    public static int CANCEL_INT = -999999999;
    
    private static Scanner in = new Scanner(System.in);
    
    /**
     * Waits for the player to press enter before continuing, as well as
     * prompting the player with a specified message.
     * @param prompt the phrase in the prompt, as in "Press enter to X."
     */
    public static void enterTo(String prompt)
    {
        System.out.print("Press enter to " + prompt + ".");
        getInput();
    }
    
    /**
     * Identical to enterTo, but it indents the message.
     * @param prompt the phrase in the prompt, as in "Press enter to X."
     */
    public static void enterToIndented(String prompt)
    {
        System.out.print("  ");
        enterTo(prompt);
    }
    
    /**
     * Returns a trimmed line of input in lower case.
     * @return the line of input, trimmed, and to lower case
     */
    public static String getInput() {return in.nextLine().trim().toLowerCase();}
    
    /**
     * Prints a prompt before calling getInput().
     * @param prompt the prompt to print
     * @return the line of input, trimmed, and to lower case
     */
    public static String getInput(String prompt)
    {
        System.out.print(" ");
        return getInputNoIndent(prompt);
    }
    
    /**
     * Performs the same function as the prompted getInput(), but does not
     * indent the prompt.
     * @param prompt the prompt to print
     * @return the line of input, trimmed, and to lower case
     */
    public static String getInputNoIndent(String prompt)
    {
        System.out.print(prompt + ": ");
        return getInput();
    }
    
    /**
     * Performs the same function as getInput(), but does not trim the input nor
     * convert it to lower case.
     * @return the line of input, unmodified
     */
    public static String getRawInput() {return in.nextLine();}
    
    /**
     * Prompts for yes/no input until gathered.
     * @param prompt the prompt to print for the Y/N input
     * @return true if y/yes is entered, false if n/no is entered
     */
    public static boolean getYNInput(String prompt)
    {
        while (true)
        {
            System.out.print(" " + prompt + " (Y/N): ");
            switch (getInput())
            {
                case "yes": case "y":
                    return true;
                case "no": case "n":
                    return false;
                default:
                    System.out.println("  Invalid choice. Enter Y or N.");
                    break;
            }
        }
    }
    
    /**
     * Returns an entered integer after prompting the player with the specified
     * prompt.
     * @param prompt the prompt to present the user when requesting the input
     * @return any integer entered by the player, the CANCEL_INT will be used
     * if the user attempts to cancel
     */
    public static int getIntInput(String prompt)
    {
        boolean completed;
        
        do
        {
            // No reassignment is necessary because loop will return
            try
            {
                String intString = getInput(prompt);
                
                if ("cancel".equals(intString))
                    return CANCEL_INT;
                
                return Integer.parseInt(intString);
            }
            catch (NumberFormatException e)
            {
                System.out.println("  Invalid numerical format. Enter an "
                                                                  + "integer.");
                completed = false;
            }
        } while (!completed);
        
        // Not reached
        return CANCEL_INT;
    }
}