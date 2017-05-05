package corelib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import squidpony.squidmath.RNG;

/**
 * A class to manage multiple aspects of files, primarily saving and loading,
 * but also other tasks such as finding the jar path.
 */
public abstract class FileManager
{
    // PATH
    
    /** The path to the data folder. */
    private static String path = findPath();
    
    /**
     * Returns the path to the data folder.
     * @return the path to the data folder as a String
     */
    public static String getPath() {return path;}
    
    /**
     * Sets the data folder path to the String provided.
     * @param newPath the String to be set as the new path
     */
    public void setPath(String newPath) {path = newPath;}
    
    /**
     * Finds the path to the jar and its directory, returning the latter.
     * @return the path to the jar file's directory
     */
    public static String findPath()
    {
        String jarPath = FileManager.class.getProtectionDomain().getCodeSource()
                .getLocation().getPath();
        jarPath = jarPath.substring(0, jarPath.lastIndexOf("/") + 1);
        
        // This last step replaces the NetBeans IDE path with a path to a valid
        // data directory
        return jarPath.replaceFirst("build/classes", "bundle/data");
    }
    
    // FILE MANIPULATION
    
    /**
     * Loads a Properties object from a file, if it exists.
     * @param target the name of the file to load from
     * @return the Properties object loaded from the file
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static Properties load(String target) throws FileNotFoundException,
            IOException
    {
        FileInputStream reader =
                new FileInputStream(new File(path + target));
        Properties properties = new Properties();
        properties.load(reader);
        reader.close();
        return properties;
    }
    
    /**
     * Saves a Properties object to the specified file.
     * @param properties the list of properties to save
     * @param target the name of the file to save to
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static void save(Properties properties, String target)
            throws FileNotFoundException, IOException
    {
        if (target == null)
            throw new NullPointerException("File saving destination may "
                    + "not be null");

        File file = new File(path + target);
        if (!file.exists())
        {
            createContainingFolders(target);
            file.createNewFile();
        }

        FileOutputStream writer = new FileOutputStream(file);
        properties.store(writer, "Saved game properties.");
        writer.close();
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
                Console.println(reader.nextLine());
            reader.close();
        }
        catch (FileNotFoundException fnf)
        {
            Console.println("File not found at " + path + target);
        }
    }
    
    /**
     * Creates an array of Strings, where each item is a line of the file.
     * @param target the file to create an array from
     * @return an array of Strings, where each item is a line of the file
     * @throws java.io.FileNotFoundException
     */
    public static String[] toLineArray(String target)
            throws FileNotFoundException
    {
        java.util.ArrayList<String> lineList = new java.util.ArrayList<>();
        Scanner reader = new Scanner(new File(path + target));
        while (reader.hasNextLine())
            lineList.add(reader.nextLine());
        reader.close();
        return lineList.toArray(new String[lineList.size()]);
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
    
    /**
     * Returns a random line from the designated file.
     * @param target the name of the file to return a line from, must exist, be
     * non-null, and have at least one line in it
     * @param rng the random number generator with which to select a line
     * @return a random line from the file, null if any of the above conditions
     * are not met
     * @throws java.io.FileNotFoundException
     */
    public static String getRandomLine(String target, RNG rng)
            throws FileNotFoundException
    {
        if (target == null)
            throw new NullPointerException("Target file path may not be null");
        
        File file = new File(path + target);
        
        // Count the lines of the file
        Scanner reader = new Scanner(file);
        int lineCounter = 0;
        while (reader.hasNextLine())
        {
            reader.nextLine();
            lineCounter++;
        }

        // The file has no lines and thus is empty, so return null
        if (lineCounter == 0)
        {
            reader.close();
            return null;
        }

        return getLine(target, rng.nextInt(lineCounter));
    }
    
    /**
     * Returns the line at the specified index from the given file.
     * @param target the name of the file to return a line from, must exist, be
     * non-null, and have at least one line in it
     * @param line the index of the line in the file to return (the first line
     * is 0), must be non-negative
     * @return the line from the file, null if any of the above conditions are
     * not met
     * @throws java.io.FileNotFoundException
     */
    public static String getLine(String target, int line)
            throws FileNotFoundException
    {
        if (target == null)
            throw new NullPointerException("Target file path may not be null");
        
        if (line < 0)
            throw new IndexOutOfBoundsException("Line number must be >= 0");
        
        File file = new File(path + target);
        
        Scanner reader = new Scanner(file);
        for (int i = 0; i < line; i++)
        {
            if (reader.hasNextLine())
            {
                reader.nextLine();
            }
            else
            {
                reader.close();
                return null;
            }    
        }

        String lineString = reader.nextLine();
        reader.close();
        return lineString;
    }
    
    /**
     * Writes the specified text to the specified file.
     * @param target the name of the file to write to, must be non-null
     * @param text the text to write to the file, must be non-null and have
     * characters in it
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static void append(String target, String text) throws FileNotFoundException, IOException
    {
        if (target == null)
            throw new NullPointerException("Target file path may not be null");
        
        if (text == null || "".equals(text))
            return;
        
        File file = new File(path + target);
        if (!file.exists())
        {
            createContainingFolders(target);
            file.createNewFile();
        }

        String[] lines = toLineArray(target);

        java.io.PrintWriter writer = new java.io.PrintWriter(file);

        for (String line: lines)
            writer.println(line);

        writer.println(text);
        writer.close();
    }
    
    /**
     * Creates all folders containing the target file or folder, using
     * recursion up to the base directory specified by the path field.
     * @param target the path to the file or folder to create containing folders
     * for
     */
    public static void createContainingFolders(String target)
    {
        if (target == null)
            throw new NullPointerException("Target file path may not be null");
        
        if (target.contains("/"))
        {
            String folder = target.substring(0, target.lastIndexOf("/"));
            createContainingFolders(folder);
            new File(path + folder).mkdir();
        }
        else if (!target.contains("."))
        {
            new File(path + target).mkdir();
        }
    }
}