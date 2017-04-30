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
}