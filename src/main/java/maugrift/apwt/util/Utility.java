package maugrift.apwt.util;

import squidpony.squidgrid.Direction;
import squidpony.squidmath.Coord;
import squidpony.squidmath.RNG;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A container for various static utility methods.
 *
 * @author Maugrift
 */
public abstract class Utility
{
    /**
     * Compares Coords based first on their y position, then on their x position if equal.
     */
    public static final Comparator<Coord> COORD_COMPARATOR = (Coord coord1, Coord coord2) -> Double.compare(coord1.y,
            coord2.y) == 0 ? Double.compare(coord1.x, coord2.x) : 0;

    /**
     * Identical to the {@link #COORD_COMPARATOR}, but y comparisons are inverted: higher y values are considered less
     * than lower y values.
     */
    public static final Comparator<Coord> CARTESIAN_COORD_COMPARATOR = (Coord coord1, Coord coord2) ->
    {
        int yComparison = Double.compare(coord1.y, coord2.y);
        return yComparison == 0 ? Double.compare(coord1.x, coord2.x) : -yComparison;
    };

    /**
     * Returns true if the given letter is a vowel.
     *
     * @param letter the character to check
     * @return true if the letter is a vowel
     */
    public static boolean isVowel(char letter)
    {
        return letter == 'a' || letter == 'e' || letter == 'i' || letter == 'o' || letter == 'u' || letter == 'A' ||
               letter == 'E' || letter == 'I' || letter == 'O' || letter == 'U';
    }

    /**
     * Capitalizes the first letter of a String.
     *
     * @param string the String to capitalize
     * @return a new String identical to the first, but with its first letter capitalized
     */
    public static String capitalize(String string)
    {
        return string == null ? null : Character.toUpperCase(string.charAt(0)) + string.substring(1);
    }

    /**
     * Capitalizes every word in a String.
     *
     * @param string the String to capitalize
     * @return a new String identical to the first, but with the first letter of every word capitalized
     */
    public static String capitalizeAllWords(String string)
    {
        if (string == null)
        {
            return null;
        }

        String[] words = string.split(" ");

        if (words.length == 1)
        {
            return capitalize(string);
        }

        for (int word = 0; word < words.length; word++)
        {
            words[word] = capitalize(words[word]);
        }

        return reconstruct(words);
    }

    /**
     * Makes the given String plural if the amount given is considered more than one (including 0).
     *
     * @param string the String to make plural
     * @param amount the amount to check before changing the name
     * @return the name with an "s" appended to it, if the amount is not 1
     */
    public static String makePlural(String string, int amount)
    {
        return amount == 1 ? string : string + "s";
    }

    /**
     * Returns the indefinite article that would precede this String.
     *
     * @param string the String to generate an article for
     * @return the indefinite article that would precede this name
     */
    public static String getArticle(String string)
    {
        return isVowel(string.charAt(0)) ? "an" : "a";
    }

    /**
     * Returns the String in lower case, preceded by an indefinite article.
     *
     * @param string the String to generate a full name for
     * @return the full name of the String as described above
     */
    public static String addArticle(String string)
    {
        return getArticle(string) + " " + string.toLowerCase();
    }

    /**
     * Performs the same function as the addArticle() method, except the "a" or "an" will be capitalized, making it "A"
     * or "An."
     *
     * @param string the String to generate a full name for
     * @return the full name of the String with the first letter capitalized
     */
    public static String addCapitalizedArticle(String string)
    {
        return capitalize(addArticle(string));
    }

    /**
     * Reconstructs a String from an array of Strings, separating Strings between the start and end indices with
     * spaces.
     *
     * @param array the array to reconstruct, must be non-null
     * @param start the index to start reconstructing at, must be greater than or equal to zero
     * @param end   the index to stop <i>before</i>, must be no larger than the length of the array
     * @return every String between the start and end indices of the array as one String, separating each individual
     * String with spaces, null if the operation cannot be performed
     */
    public static String reconstruct(String[] array, int start, int end)
    {
        if (array == null || start < 0 || start > array.length || end < 0 || end > array.length)
        {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = start; i < end; i++)
        {
            builder.append(array[i]).append(" ");
        }

        if (builder.length() > 0)
        {
            builder.delete(builder.length() - 1, builder.length());
        }

        return builder.toString();
    }

    /**
     * Reconstructs a String from an array of Strings, separating Strings between the start index and the end of the
     * array with spaces.
     *
     * @param array the array to reconstruct, must be non-null
     * @param start the index to start reconstructing at, must be greater than or equal to zero
     * @return every String between the start index and the end of the array as one String, separating each individual
     * String with spaces, null if the operation cannot be performed
     */
    public static String reconstruct(String[] array, int start)
    {
        return array == null ? null : reconstruct(array, 0, array.length);
    }

    /**
     * Reconstructs a String from an entire array of Strings, separating Strings with spaces.
     *
     * @param array the array to reconstruct, must be non-null
     * @return every String in the array as on String, separating each individual String with spaces, null if the
     * operation cannot be performed
     */
    public static String reconstruct(String[] array)
    {
        return array == null ? null : reconstruct(array, 0, array.length);
    }

    /**
     * A modified version of Integer.parseInt() that will return null instead of throwing a NumberFormatException.
     *
     * @param intString the String to parse for integers
     * @return an Integer read from the given String; null if none were found
     */
    public static Integer parseInt(String intString)
    {
        try
        {
            return Integer.parseInt(intString);
        }
        catch (NumberFormatException nf)
        {
            return null;
        }
    }

    /**
     * A modified version of Integer.parseInt() that will return the default value instead of throwing a
     * NumberFormatException.
     *
     * @param intString    the String to parse for integers
     * @param defaultValue the value to return if the parsing fails
     * @return an int read from the given String; the defaultValue if none were found
     */
    public static int parseInt(String intString, int defaultValue)
    {
        Integer parsedInt = parseInt(intString);
        return parsedInt == null ? defaultValue : parsedInt;
    }

    /**
     * Parses a Coord from the given ordered pair as a String.
     *
     * @param orderedPair the ordered pair to parse, must be in the format {@code (x,y)}
     * @return the Coord parsed from the given String
     */
    public static Coord parseCoord(String orderedPair)
    {
        orderedPair = orderedPair.replace("(", "");
        orderedPair = orderedPair.replace(")", "");
        String[] split = orderedPair.split(",");
        return Coord.get(parseInt(split[0], 0), parseInt(split[1], 0));
    }

    /**
     * Encodes a Coord as String in ordered pair format.
     *
     * @param coord the Coord to encode
     * @return the Coord in the format {@code (x,y)}
     */
    public static String coordToOrderedPair(Coord coord)
    {
        return ("(" + coord.x + "," + coord.y + ")");
    }

    /**
     * Performs the same function as {@link Coord#toGoTo(Coord)}, but works over any distance and returns only cardinal
     * directions.
     *
     * @param from the starting coordinate
     * @param to   the destination coordinate
     * @return a Direction that, if the from coordinate was translated by it, would decrease the distance between the
     * points
     */
    public static Direction toGoToCardinal(Coord from, Coord to)
    {
        return Direction.getCardinalDirection(to.x - from.x, to.y - from.y);
    }

    /**
     * Returns a String containing the number of spaces specified.
     *
     * @param spaces the number of spaces to include in the String
     * @return a String consisting of the number of space characters provided
     */
    public static String getSpaces(int spaces)
    {
        if (spaces <= 0)
        {
            return "";
        }

        StringBuilder indentedOutput = new StringBuilder();
        for (int i = 0; i < spaces; i++)
        {
            indentedOutput.append(" ");
        }
        return indentedOutput.toString();
    }


    /**
     * Converts the given KeyEvent into a Direction, referring only to the arrow keys.
     *
     * @param key the KeyEvent to convert into a Direction
     * @return the Direction corresponding to the provided KeyEvent; will be orthogonal
     */
    public static Direction keyToDirectionRestricted(KeyEvent key)
    {
        if (key == null)
        {
            return null;
        }

        switch (key.getKeyCode())
        {
            case KeyEvent.VK_UP:
                return Direction.UP;
            case KeyEvent.VK_DOWN:
                return Direction.DOWN;
            case KeyEvent.VK_LEFT:
                return Direction.LEFT;
            case KeyEvent.VK_RIGHT:
                return Direction.RIGHT;
        }

        return null;
    }

    /**
     * Converts the given KeyEvent into a Direction, referring to many different sets of directional keys. These include
     * the arrow keys, WASD, the VI keys, and the number pad.
     *
     * @param key the KeyEvent to convert into a Direction
     * @return the Direction corresponding to the provided KeyEvent; may be orthogonal or diagonal
     */
    public static Direction keyToDirection(KeyEvent key)
    {
        if (key == null)
        {
            return null;
        }

        Direction restricted = keyToDirectionRestricted(key);
        if (restricted != null)
        {
            return restricted;
        }

        switch (key.getKeyCode())
        {
            case KeyEvent.VK_K:
            case KeyEvent.VK_W:
            case KeyEvent.VK_NUMPAD8:
                return Direction.UP;
            case KeyEvent.VK_J:
            case KeyEvent.VK_S:
            case KeyEvent.VK_NUMPAD2:
                return Direction.DOWN;
            case KeyEvent.VK_H:
            case KeyEvent.VK_A:
            case KeyEvent.VK_NUMPAD4:
                return Direction.LEFT;
            case KeyEvent.VK_L:
            case KeyEvent.VK_D:
            case KeyEvent.VK_NUMPAD6:
                return Direction.RIGHT;
            case KeyEvent.VK_Y:
            case KeyEvent.VK_NUMPAD7:
                return Direction.UP_LEFT;
            case KeyEvent.VK_U:
            case KeyEvent.VK_NUMPAD9:
                return Direction.UP_RIGHT;
            case KeyEvent.VK_B:
            case KeyEvent.VK_NUMPAD1:
                return Direction.DOWN_LEFT;
            case KeyEvent.VK_N:
            case KeyEvent.VK_NUMPAD3:
                return Direction.DOWN_RIGHT;
        }

        return null;
    }

    /**
     * Generates a random outcome with the chance given as the probability of it happening, returning true if it did and
     * false if it didn't.
     *
     * @param rng    the RNG with which to find the chance
     * @param chance the chance out of 1 of the event happening
     * @return true if a double less than one is less than the chance
     */
    public static boolean getChance(RNG rng, double chance)
    {
        return rng.nextDouble() <= chance;
    }

    /**
     * Selects a random element of an array.
     *
     * @param rng     the RNG with which to randomly select items
     * @param choices the choices from which to randomly select one
     * @return a random element of the given array
     */
    public static Object select(RNG rng, Object[] choices)
    {
        return choices == null || choices.length == 0 ? null : choices[rng.nextInt(choices.length)];
    }

    /**
     * Selects a random element of an array, given a parallel array of probabilities.
     *
     * @param rng           the RNG with which to randomly select items
     * @param choices       the choices from which to randomly select one
     * @param probabilities the parallel list of probabilities for each corresponding choice
     * @return a random element of the given array
     */
    public static Object select(RNG rng, Object[] choices, double[] probabilities)
    {
        if (choices == null || choices.length == 0)
        {
            return null;
        }

        if (probabilities == null || choices.length != probabilities.length)
        {
            return select(rng, choices);
        }

        double maxProb = 0;
        double selector = rng.nextDouble();

        for (int i = 0; i < choices.length; i++)
        {
            if (selector <= maxProb + probabilities[i])
            {
                return choices[i];
            }
            maxProb += probabilities[i];
        }

        return null;
    }

    /**
     * Selects a specified amount of random elements from an array, given a parallel array of probabilities.
     *
     * @param rng           the RNG with which to randomly select items
     * @param choices       the choices from which to randomly select one
     * @param probabilities the parallel list of probabilities for each corresponding choice
     * @param amount        the amount of elements to return
     * @return the specified amount of random elements from the given array
     */
    public static Object[] select(RNG rng, Object[] choices, double[] probabilities, int amount)
    {
        if (choices == null || choices.length == 0 || amount < choices.length)
        {
            return null;
        }

        if (amount == choices.length)
        {
            return choices;
        }

        if (probabilities == null || choices.length != probabilities.length)
        {
            List<Object> results = new ArrayList<>();
            for (int i = 0; i < amount; i++)
            {
                if (results.contains(choices[i]))
                {
                    i--;
                }
                else
                {
                    results.add(choices[i]);
                }
            }
        }

        double maxProb;
        double selector;
        List<Object> results = new ArrayList<>();

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
            {
                n--;
            }
        }

        Object[] resultsArray = new Object[results.size()];
        return results.toArray(resultsArray);
    }

    public static Comparator<Coord> createDistanceComparator(Coord center)
    {
        return Comparator.comparingDouble(coord -> coord.distanceSq(center));
    }
}