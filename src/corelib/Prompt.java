package corelib;

import java.util.Scanner;
import squidpony.squidmath.Coord;

/** A prompt that manages input and output. */
public abstract class Prompt
{
    /** The number of spaces used to indent notifications. */
    public static final int INDENT_NOTIFICATION = 2;
    
    /** The number of spaces used to indent prompts. */
    public static final int INDENT_PROMPT = 1;
    
    /** The number of spaces used to indent error messages. */
    public static final int INDENT_ERROR = 2;
    
    /** The Scanner used to read input. */
    private static Scanner in = new Scanner(System.in);
    
    /**
     * Waits for the player to press enter before continuing, as well as
     * prompting the player with a specified message.
     * @param prompt the phrase in the prompt, as in "Press enter to X."
     */
    public static void enterTo(String prompt)
        {enterTo(0, prompt);}
    
    /**
     * Waits for the player to press enter before continuing, as well as
     * prompting the player with a specified message.
     * @param indents the number of spaces to indent before printing the prompt
     * @param prompt the phrase in the prompt, as in "Press enter to X."
     */
    public static void enterTo(int indents, String prompt)
    {
        Console.print(indents, "Press enter to " + prompt + ".");
        getInput();
    }
    
    /**
     * Returns a trimmed line of input in lower case.
     * @return the line of input, trimmed, and to lower case
     */
    public static String getInput()
        {return getRawInput().trim().toLowerCase();}
    
    /**
     * Prints a prompt before calling getInput().
     * @param prompt the prompt to print
     * @return the line of input, trimmed, and to lower case
     */
    public static String getInput(String prompt)
        {return getInput(0, prompt);}
    
    /**
     * Prints a prompt before calling getInput().
     * @param indents the number of spaces to indent before printing the prompt
     * @param prompt the prompt to print
     * @return the line of input, trimmed, and to lower case
     */
    public static String getInput(int indents, String prompt)
    {
        Console.print(indents, prompt + ": ");
        return getInput();
    }
    
    /**
     * Returns input as long as it contains characters.
     * @param prompt the prompt to print
     * @return the line of input, as long as it exists, trimmed and to lower
     * case
     */
    public static String getExistingInput(String prompt)
    {
        String input = getInput(prompt);
        
        while ("".equals(input) || null == input)
        {
            Console.println(INDENT_PROMPT, "Please enter something.");
            input = getInput(prompt);
        }
        
        return input;
    }
    
    /**
     * Performs the same function as getInput(), but does not trim the input nor
     * convert it to lower case.
     * @return the line of input, unmodified
     */
    public static String getRawInput() {return in.nextLine();}
    
    /**
     * Prints a prompt before calling getRawInput().
     * @param prompt the prompt to print
     * @return the line of input, unmodified
     */
    public static String getRawInput(String prompt)
        {return getRawInput(0, prompt);}
    
    /**
     * Prints the specified number of indents and a prompt before calling
     * getRawInput().
     * @param indents the number of indents to print before printing the prompt
     * @param prompt the prompt to print
     * @return the line of input, unmodified
     */
    public static String getRawInput(int indents, String prompt)
    {
        Console.print(indents, prompt + ": ");
        return getRawInput();
    }
    
    /**
     * Returns raw input as long as it contains characters.
     * @param prompt the prompt to print
     * @return the unmodified line of input, as long as it exists
     */
    public static String getExistingRawInput(String prompt)
    {
        String input = getRawInput(prompt);
        
        while ("".equals(input) || null == input)
        {
            Console.println(INDENT_PROMPT, "Please enter something.");
            input = getRawInput(prompt);
        }
        
        return input;
    }
    
    /**
     * Returns an entered integer after prompting the player with the specified
     * prompt.
     * @param prompt the prompt to present the user when requesting the input
     * @return any integer entered by the player, null will be used if the user
     * attempts to cancel
     */
    public static Integer getIntInput(String prompt)
        {return getIntInput(0, prompt);}
    
    /**
     * Returns an entered integer after prompting the player with the specified
     * prompt.
     * @param indents the number of spaces to indent before printing the prompt
     * @param prompt the prompt to present the user when requesting the input
     * @return any integer entered by the player, null will be used if the user
     * attempts to cancel
     */
    public static Integer getIntInput(int indents, String prompt)
    {
        boolean completed;
        
        do
        {
            // No reassignment is necessary because loop will return
            String intString = getInput(indents, prompt);
            
            // Make "cancel" a constant or request it as a parameter if possible
            if ("cancel".equals(intString))
                return null;
            
            Integer parsedInteger = parseInt(intString);
            if (parsedInteger == null)
            {
                Console.println(INDENT_ERROR,
                        "Invalid numerical format. Enter an integer.");
                completed = false;
            }
            else
            {
                return parsedInteger;
            }
        } while (!completed);
        
        // NOT REACHED
        return null;
    }
    
    /**
     * A modified version of Integer.parseInt() that will return null instead of
     * throwing a NumberFormatException.
     * @param intString the String to parse for integers
     * @return an Integer read from the given String; null if none were found
     */
    public static Integer parseInt(String intString)
    {
        try
        {
            return Integer.parseInt(intString);
        }
        catch (NumberFormatException nf)
        {
            return null;
        }
    }
    
    /**
     * A modified version of Integer.parseInt() that will return the default
     * value instead of throwing a NumberFormatException.
     * @param intString the String to parse for integers
     * @param defaultValue the value to return if the parsing fails
     * @return an int read from the given String; the defaultValue if none were
     * found
     */
    public static int parseInt(String intString, int defaultValue)
    {
        Integer parsedInt = parseInt(intString);
        return parsedInt == null ? defaultValue : parsedInt;
    }
    
    /**
     * Prompts for yes/no input until gathered.
     * @param prompt the prompt to print for the Y/N input
     * @return true if y/yes is entered, false if n/no is entered
     */
    public static boolean getYNInput(String prompt)
        {return getYNInput(0, prompt);}
    
    /**
     * Prompts for yes/no input until gathered.
     * @param prompt the prompt to print for the Y/N input
     * @param indents the number of spaces to indent before printing the prompt
     * @return true if y/yes is entered, false if n/no is entered
     */
    public static boolean getYNInput(int indents, String prompt)
    {
        while (true)
        {
            Console.print(indents, prompt + " (Y/N): ");
            switch (getInput())
            {
                case "yes": case "y":
                    return true;
                case "no": case "n":
                    return false;
                default:
                    Console.println(INDENT_ERROR,
                            "Invalid choice. Enter Y or N.");
                    break;
            }
        }
    }
    
    /**
     * Checks a String for yes/no input and then prompts if invalid.
     * @param value the initial String to check for Y/N input
     * @param prompt the prompt to print for Y/N input if the check fails
     * @return true if y/yes is the value or is entered, false if the same is
     * true for n/no
     */
    public static boolean getYNValue(String value, String prompt)
    {
        switch (value)
        {
            case "yes": case "y":
                return true;
            case "no": case "n":
                return false;
            default:
                Console.println(INDENT_ERROR,
                        "Invalid choice. Enter Y or N.");
                return getYNInput(prompt);
        }
    }
    
    /**
     * Prompts the player for two integers for use as coordinates and returns
     * the resulting point.
     * @return a point with an x and y coordinate specified by the player
     */
    public static Coord getPointInput()
    {
        Integer x = getIntInput("X Coordinate");
        if (x == null)
            return null;
        
        Integer y = getIntInput("Y Coordinate");
        if (y == null)
            return null;
        
        return Coord.get(x, y);
    }
    
    /**
     * Prints a notification to be displayed after an action, followed by a
     * continue prompt.
     * @param message the message to display in the notification
     */
    public static void printNotification(String message)
    {
        Console.println(INDENT_NOTIFICATION, message);
        Prompt.enterTo(INDENT_NOTIFICATION, "continue");
    }
    
    /**
     * Prints a notification to be displayed after an action, followed by a
     * yes/no query.
     * @param message the message to display in the notification
     * @param prompt the prompt to present the player with
     * @return true if the player answers yes to the prompt
     */
    public static boolean printNotificationQuery(String message, String prompt)
    {
        Console.println(INDENT_PROMPT, message);
        return Prompt.getYNInput(INDENT_PROMPT, prompt);
    }
    
    /**
     * Returns the input from the command array if it is valid; otherwise will
     * prompt the operator for input.
     * @param prompt the prompt to display if no valid input is found
     * @param command the array of Strings to get input from
     * @param index the index in the command array to read input from
     * @return a String read from the command array, if possible, or new input
     * from the operator
     */
    public static String getInitialInput(String prompt, String[] command,
            int index)
    {
        return command != null && command.length > index &&
                command[index] != null ? command[index] : getInput(1, prompt);
    }
    
    /**
     * Returns the input from the command array if it is valid; otherwise will
     * prompt the operator for input.
     * @param prompt the prompt to display if no valid input is found
     * @param command the array of Strings to parse input from
     * @param index the index in the command array to read input from
     * @return an Integer read from the command array, if possible, or new input
     * from the operator
     */
    public static Integer getInitialIntInput(String prompt, String[] command,
            int index)
    {
        if (command != null && command.length > index && command[index] != null)
        {
            Integer initialInt = parseInt(command[index]);
            if (initialInt != null)
                return initialInt;
        }
        
        return getIntInput(1, prompt);
    }
    
    /**
     * Returns the input from the command array if it is valid; otherwise will
     * prompt the operator for input.
     * @param prompt the prompt to display if no valid input is found
     * @param command the array of Strings to get input from
     * @param index the index in the command array to read input from
     * @return a Y/N value read from the command array, if possible, or new
     * input from the operator
     */
    public static boolean getInitialYNInput(String prompt, String[] command,
            int index)
    {
        return command != null && command.length > index &&
                command[index] != null ?
                getYNValue(command[index], prompt) : Prompt.getYNInput(prompt);
    }
}