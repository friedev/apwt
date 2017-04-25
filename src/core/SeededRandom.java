package core;

import java.util.Random;

/** A container for a random generator and its seed, used to manage both. */
public class SeededRandom
{
    // FIELDS
    
    /** The random number generator. */
    private Random random;
    
    /** The stored seed of the random number generator. */
    private long seed;
    
    // CONSTRUCTORS
    
    /**
     * Creates a SeededRandom with the given seed.
     * @param s the seed to use in the random generator
     */
    public SeededRandom(long s)
    {
        seed   = s;
        random = new Random(seed);
    }
    
    /**
     * Creates a SeededRandom with a random seed, but saves the seed for further
     * use.
     */
    public SeededRandom()
        {this(new Random().nextLong());}
    
    // GETTERS
    
    /**
     * Returns the random number generator.
     * @return the random number generator
     */
    public Random get() {return random;}
    
    /**
     * Returns the seed of the random number generator.
     * @return the seed of the random number generator
     */
    public long getSeed() {return seed;}
    
    // SETTERS
    
    /**
     * Sets the seed to a given value and recreates the generator.
     * @param s the new seed to be used in generation
     */
    public void setSeed(long s)
    {
        seed = s;
        random = new Random(seed);
    }
    
    // GENERATORS
    
    /**
     * Generates a new random seed by creating a long and assigning it to the
     * generator.
     */
    public void generateSeed()
        {setSeed(random.nextLong());}
    
    /**
     * Generates a random outcome with the chance given as the probability of it
     * happening, returning true if it did and false if it didn't.
     * @param chance the chance out of 1 of the event happening
     * @return true if a double less than one is less than the chance
     */
    public boolean getChance(double chance)
        {return random.nextDouble() <= chance;}
    
    /**
     * Selects a random element of an array.
     * @param choices the choices from which to randomly select one
     * @return a random element of the given array
     */
    public Object select(Object[] choices)
    {
        if (choices == null || choices.length == 0)
            return null;
        return choices[random.nextInt(choices.length)];
    }
    
    /**
     * Selects a random element of an array, given a parallel array of
     * probabilities.
     * @param choices the choices from which to randomly select one
     * @param probabilities the parallel list of probabilities for each
     * corresponding choice
     * @return a random element of the given array
     */
    public Object select(Object[] choices, double[] probabilities)
    {
        // TODO upgrade select to be able to use sums of any amount
        
        if (choices == null || choices.length == 0)
            return null;
        
        if (probabilities == null || choices.length != probabilities.length)
            return select(choices);
        
        double maxProb  = 0;
        double selector = random.nextDouble();
        
        for (int i = 0; i < choices.length; i++)
        {
            if (selector <= maxProb + probabilities[i])
                return choices[i];
            maxProb += probabilities[i];
        }
        
        return null;
    }
    
    /**
     * Selects a specified amount of random elements from an array, given a
     * parallel array of probabilities.
     * @param choices the choices from which to randomly select one
     * @param probabilities the parallel list of probabilities for each
     * corresponding choice
     * @param amount the amount of elements to return 
     * @return the specified amount of random elements from the given array
     */
    public Object[] select(Object[] choices, double[] probabilities, int amount)
    {
        // TODO test multi-select
        
        if (choices == null || choices.length == 0 || amount < choices.length)
            return null;
        
        if (amount == choices.length)
            return choices;
        
        if (probabilities == null || choices.length != probabilities.length)
        {
            java.util.ArrayList<Object> results = new java.util.ArrayList<>();
            for (int i = 0; i < amount; i++)
            {
                if (results.contains(choices[i]))
                    i--;
                else
                    results.add(choices[i]);
            }
        }
        
        double maxProb;
        double selector;
        java.util.ArrayList<Object> results = new java.util.ArrayList<>();
        
        for (int n = 0; n < amount; n++)
        {
            boolean reselect = false;
            maxProb = 0;
            selector = random.nextDouble();
            
            for (int i = 0; i < choices.length; i++)
            {
                if (selector <= maxProb + probabilities[i])
                {
                    if (results.contains(choices[i]))
                    {
                        reselect = true;
                        break;
                    }
                    results.add(choices[i]);
                }
                maxProb += probabilities[i];
            }
            
            if (reselect)
                n--;
        }
        
        Object[] resultsArray = new Object[results.size()];
        return results.toArray(resultsArray);
    }
}