package core;

/** A class used to print output rather than doing so directly. */
public class Display
{
    public static void print(String output)
        {System.out.print(output);}
    
    public static void println(String output)
        {System.out.println(output);}
    
    public static void println()
        {println("");}
    
    public static void printSpaces(int spaces)
        {print(getSpaces(spaces));}
    
    public static String getSpaces(int spaces)
    {
        if (spaces <= 0)
            return "";
        
        StringBuilder indentedOutput = new StringBuilder();
        for (int i = 0; i < spaces; i++)
            indentedOutput.append(" ");
        return indentedOutput.toString();
    }
    
    public static void println(int indents, String output)
    {
        printSpaces(indents);
        println(output);
    }
    
    public static void print(int indents, String output)
    {
        printSpaces(indents);
        print(output);
    }
    
    public static void printListItem(String item)
        {printListItem(1, item);}
    
    public static void printListItem(String item, String value)
        {printListItem(item + ": " + value);}
    
    public static void printListItem(String item, int value)
        {printListItem(item, "" + value);}
    
    public static void printListItem(int indents, String item)
        {println(indents, "-" + item);}
    
    public static void printListItem(int indents, String item, String value)
        {printListItem(indents, item + ": " + value);}
    
    public static void printListItem(int indents, String item, int value)
        {printListItem(indents, item, "" + value);}
    
    public static void printSpacing(int spacing)
    {
        if (spacing <= 0)
            return;
        
        for (int i = 0; i < spacing; i++)
            println();
    }
    
    public static void printSpacingBetween(int spacing, String s1, String s2)
        {System.out.printf("%-" + spacing + "s %s", s1, s2);}
}