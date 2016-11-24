package core;

/** A class used to print output rather than doing so directly. */
public class Display
{
    public static void println()
        {System.out.println();}
    
    public static void println(String output)
        {System.out.println(output);}
    
    public static void print(String output)
        {System.out.print(output);}
    
    public static void printIndents(int indents)
    {
        if (indents <= 0)
            return;
        
        StringBuilder indentedOutput = new StringBuilder();
        for (int i = 0; i < indents; i++)
            indentedOutput.append(" ");
        print(indentedOutput.toString());
    }
    
    public static void println(int indents, String output)
    {
        printIndents(indents);
        println(output);
    }
    
    public static void print(int indents, String output)
    {
        printIndents(indents);
        print(output);
    }
    
    public static void printListItem(String item)
        {println(" -" + item);}
    
    public static void printListItem(String item, String value)
        {printListItem(item + ": " + value);}
    
    public static  void printListItem(String item, int value)
        {printListItem(item, "" + value);}
    
    public static void printSpacing(int spacing)
    {
        if (spacing <= 0)
            return;
        
        for (int i = 0; i < spacing; i++)
            println();
    }
}