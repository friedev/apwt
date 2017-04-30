package corelib.storage;

import corelib.FileManager;
import java.util.Properties;

/** A wrapper class for all of the command keywords. */
public abstract class Commands
{
    public static final Properties LIST_MAIN =
            FileManager.load(Paths.COMMANDS_MAIN);
    
    // MAIN COMMANDS
    public static final String ACTION_1 = "action1";
    public static final String ACTION_2 = "action2";
    public static final String ACTION_3 = "action3";
    public static final String HELP     = "help";
    public static final String QUIT     = "quit";
    
    // UNIVERSAL COMMANDS
    public static final String CANCEL  = "cancel";
    public static final String MAX     = "max";
    public static final String NOTHING = "nothing";
    public static final String INVALID = "invalid";
    
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
        {return CANCEL.equals(parseAction(string, LIST_MAIN));}
}