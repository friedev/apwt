package core;

import java.util.Random;

/** A container for a random generator and its seed, used to manage both. */
public class SeededRandom
{
    private Random random;
    private long   seed;
    
    public SeededRandom()
    {
        random = new Random();
        seed   = random.nextLong();
        random = new Random(seed);
    }
    
    public SeededRandom(long s)
    {
        seed   = s;
        random = new Random(seed);
    }
    
    public Random get()   {return random;}
    public long getSeed() {return seed;  }
    
    /**
     * Sets the seed to a given value and recreates the generator.
     * @param s the new seed to be used in generation
     */
    public void setSeed(long s)
    {
        seed = s;
        random = new Random(seed);
    }
    
    /**
     * Generates a new random seed by creating a long and assigning it to the
     * generator.
     */
    public void generateSeed() {setSeed(random.nextLong());}
    
    /**
     * Selects a random element of a String array.
     * @param choices the choices from which to randomly select one
     * @return a random element of the given array
     */
    public String select(String[] choices)
    {
        if (choices == null)
            return null;
        return choices[random.nextInt(choices.length)];
    }
    
    /**
     * Selects a random element of a String array, given a parallel array of
     * probabilities.
     * @param choices the choices from which to randomly select one
     * @param probabilities the parallel list of probabilities for each
     * corresponding choice
     * @return a random element of the given array
     */
    public String select(String[] choices, double[] probabilities)
    {
        if (choices == null)
            return null;
        
        if (probabilities == null || choices.length != probabilities.length)
            return select(choices);
        
        double maxProb = 0;
        double selector = random.nextDouble();
        
        for (int i = 0; i < choices.length; i++)
        {
            if (selector <= maxProb + probabilities[i])
                return choices[i];
            maxProb %= probabilities[i];
        }
        
        return null;
    }
}