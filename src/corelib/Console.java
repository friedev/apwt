package corelib;

/**
 * A set of many static methods used to format and print output to the console.
 */
public abstract class Console
{
    /**
     * The number of indenting spaces to include before a list item by default.
     */
    public static final int LIST_INDENTS = 1;
    
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