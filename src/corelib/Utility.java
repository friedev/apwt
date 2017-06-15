package corelib;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import squidpony.squidgrid.Direction;
import squidpony.squidmath.RNG;

/**
 * A container for various static utility methods.
 */
public abstract class Utility
{
    /**
     * Converts the given KeyEvent into a Direction, referring only to the arrow
     * keys.
     * @param key the KeyEvent to convert into a Direction
     * @return the Direction corresponding to the provided KeyEvent; will be
     * orthogonal
     */
    public static Direction keyToDirectionRestricted(KeyEvent key)
    {
        if (key == null)
            return null;
        
        switch (key.getKeyCode())
        {
            case KeyEvent.VK_UP:    return Direction.UP;
            case KeyEvent.VK_DOWN:  return Direction.DOWN;
            case KeyEvent.VK_LEFT:  return Direction.LEFT;
            case KeyEvent.VK_RIGHT: return Direction.RIGHT;
        }
        
        return null;
    }
    
    /**
     * Converts the given KeyEvent into a Direction, referring to many different
     * sets of directional keys. These include the arrow keys, WASD, the VI
     * keys, and the number pad.
     * @param key the KeyEvent to convert into a Direction
     * @return the Direction corresponding to the provided KeyEvent; may be
     * orthogonal or diagonal
     */
    public static Direction keyToDirection(KeyEvent key)
    {
        if (key == null)
            return null;
        
        Direction restricted = keyToDirectionRestricted(key);
        if (restricted != null)
            return restricted;
        
        switch (key.getKeyCode())
        {
            case KeyEvent.VK_K: case KeyEvent.VK_W: case KeyEvent.VK_NUMPAD8:
                return Direction.UP;
            case KeyEvent.VK_J: case KeyEvent.VK_S: case KeyEvent.VK_NUMPAD2:
                return Direction.DOWN;
            case KeyEvent.VK_H: case KeyEvent.VK_A: case KeyEvent.VK_NUMPAD4:
                return Direction.LEFT;
            case KeyEvent.VK_L: case KeyEvent.VK_D: case KeyEvent.VK_NUMPAD6:
                return Direction.RIGHT;
            case KeyEvent.VK_Y: case KeyEvent.VK_NUMPAD7:
                return Direction.UP_LEFT;
            case KeyEvent.VK_U: case KeyEvent.VK_NUMPAD9:
                return Direction.UP_RIGHT;
            case KeyEvent.VK_B: case KeyEvent.VK_NUMPAD1:
                return Direction.DOWN_LEFT;
            case KeyEvent.VK_N: case KeyEvent.VK_NUMPAD3:
                return Direction.DOWN_RIGHT;
        }
        
        return null;
    }
    
    /**
     * Generates a random outcome with the chance given as the probability of it
     * happening, returning true if it did and false if it didn't.
     * @param rng the RNG with which to find the chance
     * @param chance the chance out of 1 of the event happening
     * @return true if a double less than one is less than the chance
     */
    public static boolean getChance(RNG rng, double chance)
        {return rng.nextDouble() <= chance;}
    
    /**
     * Selects a random element of an array.
     * @param rng the RNG with which to randomly select items
     * @param choices the choices from which to randomly select one
     * @return a random element of the given array
     */
    public static Object select(RNG rng, Object[] choices)
    {
        if (choices == null || choices.length == 0)
            return null;
        return choices[rng.nextInt(choices.length)];
    }
    
    /**
     * Selects a random element of an array, given a parallel array of
     * probabilities.
     * @param rng the RNG with which to randomly select items
     * @param choices the choices from which to randomly select one
     * @param probabilities the parallel list of probabilities for each
     * corresponding choice
     * @return a random element of the given array
     */
    public static Object select(RNG rng, Object[] choices,
            double[] probabilities)
    {
        if (choices == null || choices.length == 0)
            return null;
        
        if (probabilities == null || choices.length != probabilities.length)
            return select(rng, choices);
        
        double maxProb  = 0;
        double selector = rng.nextDouble();
        
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
     * @param rng the RNG with which to randomly select items
     * @param choices the choices from which to randomly select one
     * @param probabilities the parallel list of probabilities for each
     * corresponding choice
     * @param amount the amount of elements to return 
     * @return the specified amount of random elements from the given array
     */
    public static Object[] select(RNG rng, Object[] choices,
            double[] probabilities, int amount)
    {
        if (choices == null || choices.length == 0 || amount < choices.length)
            return null;
        
        if (amount == choices.length)
            return choices;
        
        if (probabilities == null || choices.length != probabilities.length)
        {
            ArrayList<Object> results = new ArrayList<>();
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
        ArrayList<Object> results = new ArrayList<>();
        
        for (int n = 0; n < amount; n++)
        {
            boolean reselect = false;
            maxProb = 0;
            selector = rng.nextDouble();
            
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