package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

/**
 * A class to manage multiple aspects of files, primarily saving and loading,
 * but also other tasks such as finding the jar path.
 */
public abstract class FileManager
{
    private static String path = findPath();
    
    public static String getPath() {return path;}
    
    public void setPath(String newPath) {path = newPath;}
    
    /**
     * Finds the path to the jar and its directory, returning the latter.
     * @return the path to the jar file's directory
     */
    public static String findPath()
    {
        String jarPath = Main.class.getProtectionDomain().getCodeSource()
                .getLocation().getPath();
        return jarPath.substring(0, jarPath.lastIndexOf("/") + 1);
    }
    
    /**
     * Creates the player from the save file, if it exists.
     * @param target the name of the file to load from
     * @return the created Ship to be the player
     */
    public static Properties load(String target)
    {
        try
        {
            File file = new File(path + target);
            if (!file.exists())
            {
                // The act of creating this file may be deprecated in the future
                file.createNewFile();
                return new Properties();
            }

            FileInputStream fis = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(fis);
            fis.close();
            return properties;
        }
        catch (FileNotFoundException fnf)
        {
            Main.quitWithMessage("File not found at " + path + target);
        }
        catch (IOException io)
        {
            Main.quitWithMessage("Error encountered while creating or "
                    + "processing " + path + target);
        }
        
        // NOT REACHED
        return null;
    }
    
    /**
     * Saves the player's ship to the save file.
     * @param properties the list of properties to save
     * @param target the name of the file to save to
     */
    public static void save(Properties properties, String target)
    {
        try
        {
            File file = new File(path + target);
            if (!file.exists())
                file.createNewFile();

            FileOutputStream fos = new FileOutputStream(file);
            properties.store(fos, "Saved game properties.");
            fos.close();
        }
        catch (FileNotFoundException fnf)
        {
            Main.quitWithMessage("File not found at " + path + target);
        }
        catch (IOException io)
        {
            Main.quitWithMessage("Error encountered while creating " + path
                    + target);
        }
    }
    
    /**
     * Prints the contents of a file.
     * @param target the file to print the contents of
     */
    public static void print(String target)
    {
        try
        {
            Scanner reader = new Scanner(new File(path + target));
            while (reader.hasNextLine())
                System.out.println(reader.nextLine());
            reader.close();
        }
        catch (FileNotFoundException fnf)
        {
            System.out.println("File not found at " + path + target);
        }
    }
    
    /**
     * Creates an array of Strings, where each item is a line of the file.
     * @param target the file to create an array from
     * @return an array of Strings, where each item is a line of the file
     */
    public static String[] toLineArray(String target)
    {
        try
        {
            java.util.ArrayList<String> lineList = new java.util.ArrayList<>();
            Scanner reader = new Scanner(new File(path + target));
            while (reader.hasNextLine())
                lineList.add(reader.nextLine());
            reader.close();
            return lineList.toArray(new String[lineList.size()]);
        }
        catch (FileNotFoundException fnf)
        {
            Main.quitWithMessage("File not found at " + path + target);
        }
        
        // NOT REACHED
        return null;
    }
    
    /**
     * Returns true if the specified file name exists.
     * @param target the name of the file to check existence of
     * @return true if the file with the given name exists
     */
    public static boolean checkExistence(String target)
        {return new File(path + target).exists();}
    
    /**
     * Deletes a specified file if it exists.
     * @param target the name of the file to delete
     */
    public static void delete(String target)
    {
        File file = new File(path + target);
        if (file.exists())
            file.delete();
    }
}