package core;

import java.util.Random;

/**
 * A random name generator that creates names by combining a prefix and a suffix
 * from two specified files.
 */
public class NameGenerator
{
    // CONSTANTS
    
    /** The default address for the file containing prefixes. */
    public static final String PREFIX_FILE = "prefix.txt";
    
    /** The default address for the file containing suffixes. */
    public static final String SUFFIX_FILE = "suffix.txt";
    
    // FIELDS
    
    /** The array of prefixes to choose from. */
    private String[] prefixes;
    
    /** The array of suffixes to choose from. */
    private String[] suffixes;
    
    /** The random number generator used to select prefixes and suffixes. */
    private Random random;
    
    // CONSTRUCTORS
    
    /**
     * Invokes the NameGenerator(String, String, Random) constructor with the
     * default file names and a new random generator.
     */
    public NameGenerator()
        {this(PREFIX_FILE, SUFFIX_FILE, new Random());}
    
    /**
     * Invokes the NameGenerator(String, String, Random) constructor with the
     * default file names and a given seed.
     * @param seed the seed of the random generator to be used
     */
    public NameGenerator(long seed)
        {this(PREFIX_FILE, SUFFIX_FILE, new Random(seed));}
    
    /**
     * Invokes the NameGenerator(String, String, Random) constructor with the
     * default file names and a random generator.
     * @param rand the random generator to use
     */
    public NameGenerator(Random rand)
        {this(PREFIX_FILE, SUFFIX_FILE, rand);}
    
    /**
     * Invokes the NameGenerator(String, String, Random) constructor with two
     * given file names and a new random generator.
     * @param prefix the name of the prefix file
     * @param suffix the name of the suffix file
     */
    public NameGenerator(String prefix, String suffix)
        {this(prefix, suffix, new Random());}
    
    /**
     * Invokes the NameGenerator(String, String, Random) constructor with two
     * given file names and a given seed.
     * @param prefix the name of the prefix file
     * @param suffix the name of the suffix file
     * @param seed the seed of the random generator to be used
     */
    public NameGenerator(String prefix, String suffix, long seed)
        {this(prefix, suffix, new Random(seed));}
    
    /**
     * Invokes the NameGenerator(String[], String[], Random) constructor with
     * two given file names and a random generator.
     * @param prefix the name of the prefix file
     * @param suffix the name of the suffix file
     * @param rand the random generator to use
     */
    public NameGenerator(String prefix, String suffix, Random rand)
    {
        this(FileManager.toLineArray(prefix),
                FileManager.toLineArray(suffix), rand);
    }
    
    /**
     * Creates a new NameGenerator with two String arrays for the prefixes and
     * suffixes, and a random generator.
     * @param prefix the array of prefixes
     * @param suffix the array of suffixes
     * @param rand the random generator to use
     */
    public NameGenerator(String[] prefix, String[] suffix, Random rand)
    {
        prefixes = prefix;
        suffixes = suffix;
        random   = rand;
    }
    
    // GETTERS
    
    /**
     * Retrieves a random prefix from the prefixes array.
     * @return a randomly-selected String from the prefixes array
     */
    public String getPrefix()
        {return prefixes[random.nextInt(prefixes.length)];}
    
    /**
     * Retrieves a random suffix from the suffixes array.
     * @return a randomly-selected String from the suffixes array
     */
    public String getSuffix()
        {return suffixes[random.nextInt(suffixes.length)];}
    
    // GENERATION
    
    /**
     * Generates a random name by combining a prefix and suffix.
     * @return a name consisting of an item from both the prefix and suffix
     * arrays
     */
    public String generateName()
        {return getPrefix() + getSuffix();}
    
    /**
     * Combines the provided suffix with a random prefix from the prefixes
     * array.
     * @param suffix the suffix to combine with a random prefix
     * @return a single String consisting of a random prefix and the chosen
     * suffix, concatenated
     */
    public String combineWithPrefix(String suffix)
        {return getPrefix() + suffix;}
    
    /**
     * Combines the provided prefix with a random suffix from the suffixes
     * array.
     * @param prefix the prefix to combine with a random suffix
     * @return a single String consisting of a random suffix and the chosen
     * prefix, concatented
     */
    public String combineWithSuffix(String prefix)
        {return prefix + getSuffix();}
}