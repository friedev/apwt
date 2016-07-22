package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

/**
 * A random name generator that creates names by combining a prefix and a suffix
 * from two specified files.
 */
public class NameGenerator
{
    private File   prefixFile;
    private File   suffixFile;
    private Random random;
    
    /**
     * Invokes the NameGenerator(File, File, Random) constructor with two given
     * file names and a new random generator
     * @param prefix the name of the prefix file
     * @param suffix the name of the suffix file
     * @throws FileNotFoundException if files do not exist
     * @throws NullPointerException if files are null
     */
    public NameGenerator(String prefix, String suffix)
            throws FileNotFoundException
    {this(new File(prefix), new File(suffix), new Random());}
    
    /**
     * Invokes the NameGenerator(File, File, Random) constructor with two given
     * file names and a given seed.
     * @param prefix the name of the prefix file
     * @param suffix the name of the suffix file
     * @param seed the seed of the random generator to be used
     * @throws FileNotFoundException if the files do not exist
     * @throws NullPointerException if files are null
     */
    public NameGenerator(String prefix, String suffix, long seed)
            throws FileNotFoundException
    {this(new File(prefix), new File(suffix), new Random(seed));}
    
    /**
     * Invokes the NameGenerator(File, File, Random) constructor with two given
     * file names and a given random generator.
     * @param prefix the name of the prefix file
     * @param suffix the name of the suffix file
     * @param rand the random generator to use
     * @throws FileNotFoundException if the files do not exist
     * @throws NullPointerException if the files are null
     */
    public NameGenerator(String prefix, String suffix, Random rand)
            throws FileNotFoundException
    {this(new File(prefix), new File(suffix), rand);}
    
    /**
     * Creates a new NameGenerator from a prefix file, a suffix file, and a
     * random generator.
     * @param prefix the file to read the prefix from
     * @param suffix the file to read the suffix from
     * @param rand
     * @throws FileNotFoundException if files do not exist
     * @throws NullPointerException if files are null
     */
    public NameGenerator(File prefix, File suffix, Random rand)
            throws FileNotFoundException
    {
        // Throw exceptions if the files are null or do not exist
        if (prefix == null || suffix == null)
            throw new NullPointerException();
        
        if (!(prefix.exists() && suffix.exists()))
            throw new FileNotFoundException();
        
        prefixFile = prefix;
        suffixFile = suffix;
        random     = rand;
    }
    
    /**
     * Generates a random name by combining a prefix and suffix.
     * @return a name consisting of a line from both the prefix and suffix files
     * @throws FileNotFoundException thrown by line reader if the file does not
     * exist
     */
    public String generateName() throws FileNotFoundException
        {return getRandomLine(prefixFile) + getRandomLine(suffixFile);}
    
    /**
     * Returns a random line from the designated file.
     * @param file the file to return a line from, must exist, be non-null, and
     * have at least one line in it
     * @return a random line from the file
     * @throws FileNotFoundException thrown by reader if the file does not exist
     * @throws NullPointerException if file is null or has no lines
     */
    private String getRandomLine(File file) throws FileNotFoundException
    {
        if (file == null)
            throw new NullPointerException();
        
        // Count the lines of the file
        Scanner reader = new Scanner(file);
        int lineCounter = 0;
        while (reader.hasNextLine())
        {
            reader.nextLine();
            lineCounter++;
        }
        
        // The file has no lines and thus is empty, so throw an exception
        if (lineCounter == 0)
        {
            reader.close();
            throw new NullPointerException();
        }
        
        // Reset reader to the first line
        reader = new Scanner(file);
        int lineNumber = random.nextInt(lineCounter);
        for (int i = 0; i < lineNumber; i++)
            reader.nextLine();
        
        String line = reader.nextLine();
        reader.close();
        return line;
    }
}