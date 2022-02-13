package maugrift.apwt.screens;

import asciiPanel.AsciiPanel;
import maugrift.apwt.glyphs.ColorString;
import maugrift.apwt.glyphs.ColorStringObject;

import java.awt.*;

/**
 * A class containing the information in a keybinding, as well as methods to output it.
 *
 * @author Maugrift
 */
public class Keybinding implements ColorStringObject
{
    /**
     * The default color to use when outputting the key's function as a
	 * ColorString.
     */
    public static final Color COLOR_FUNCTION = new Color(192, 192, 192);

    /**
     * The default color to use when outputting the key as a ColorString.
     */
    public static final Color COLOR_KEY = Color.WHITE;

    /**
     * The function that is performed when the key is pressed.
     */
    private String function;

    /**
     * The keys that can trigger the function.
     */
    private String[] keys;

    /**
     * Creates a {@link Keybinding} with an action and keys (as Strings).
     *
     * @param function the {@link Keybinding}'s function
     * @param keys     the {@link Keybinding}'s keys as Strings
     */
    public Keybinding(String function, String... keys)
    {
        this.function = function;
        this.keys = keys;
    }

    /**
     * Creates a {@link Keybinding} with an action and keys (as chars).
     *
     * @param function the {@link Keybinding}'s function
     * @param keys     the {@link Keybinding}'s keys as chars
     */
    public Keybinding(String function, char... keys)
    {
        this(function, charsToStrings(keys));
    }

    /**
     * Converts the given chars to Strings.
     *
     * @param chars the chars to convert into Strings
     * @return the given array of chars as an array of Strings
     */
    private static String[] charsToStrings(char[] chars)
    {
        String[] keyStrings = new String[chars.length];
        for (int i = 0; i < chars.length; i++)
        {
            keyStrings[i] = Character.toString(chars[i]);
        }
        return keyStrings;
    }

    /**
     * Returns this {@link Keybinding}'s function.
     *
     * @return this {@link Keybinding}'s function
     */
    public String getFunction()
    {
        return function;
    }

    /**
     * Returns this {@link Keybinding}'s keys.
     *
     * @return this {@link Keybinding}'s keys
     */
    public String[] getKeys()
    {
        return keys;
    }

	/**
	 * Convert this {@link Keybinding} to a ColorString representation using
	 * the default colors.
	 *
	 * @return a ColorString representing this keybinding
	 */
    @Override
    public ColorString toColorString()
    {
        return toColorString(COLOR_FUNCTION, COLOR_KEY);
    }

	/**
	 * Convert this {@link Keybinding} to a ColorString representation using
	 * the given colors.
	 *
     * @param colorFunction  the color to use for the key's function
     * @param colorKey       the color to use for the key
	 * @return a ColorString representing this keybinding
	 */
    public ColorString toColorString(Color colorFunction, Color colorKey)
    {
        ColorString colorString = new ColorString(keys[0], colorKey);

        if (keys.length > 1)
        {
            for (int i = 1; i < keys.length; i++)
            {
                colorString.add("/").add(new ColorString(keys[i], colorKey));
            }
        }

        colorString.add(": ").add(new ColorString(function, colorFunction));
        return colorString;
    }
}
