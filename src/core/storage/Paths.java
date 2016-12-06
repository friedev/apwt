package core.storage;

/** A wrapper class for all of the file paths loaded from the main manifest. */
public abstract class Paths
{
    public static final String MANIFEST_PATH = "manifest.properties";
    public static final java.util.Properties MANIFEST =
            core.FileManager.load(MANIFEST_PATH);
    
    public static final String COMMANDS_MAIN =
            MANIFEST.getProperty("commandsMain");
}