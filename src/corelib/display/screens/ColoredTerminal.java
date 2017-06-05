package corelib.display.screens;

import corelib.display.glyphs.ColorSet;
import corelib.display.windows.Window;
import java.awt.Color;

/**
 * A {@link Terminal} that uses color to differentiate the prompt from operator
 * input.
 * @param <Output> the type of {@link corelib.display.windows.Window} used by
 * the {@link Terminal}
 */
public abstract class ColoredTerminal<Output extends Window>
        extends Terminal<Output>
{
    /** The color of inputted text. */
    private Color inputForeground;
    
    /** The background color of inputted text. */
    private Color inputBackground;
    
    /**
     * Creates an {@link ColoredTerminal} with the given
     * {@link corelib.display.windows.Window Window},
     * prompt, and maximum length.
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     * @param length the {@link Terminal}'s maximum length, counting Window
     * {@link corelib.display.windows.Border borders} and the
     * prompt
     * @param foreground the color of the input characters
     * @param background the color of the input background
     */
    public ColoredTerminal(Output output, ColorSet prompt, int length,
            Color foreground, Color background)
    {
        super(output, prompt, length);
        inputForeground = foreground;
        inputBackground = background;
    }
    
    /**
     * Creates an {@link ColoredTerminal} with the given
     * {@link corelib.display.windows.Window Window},
     * prompt, and maximum length.
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     * @param length the {@link Terminal}'s maximum length, counting Window
     * {@link corelib.display.windows.Border borders} and the
     * prompt
     * @param foreground the color of the input characters
     */
    public ColoredTerminal(Output output, ColorSet prompt, int length,
            Color foreground)
        {this(output, prompt, length, foreground, null);}
    
    /**
     * Creates an {@link ColoredTerminal} with the given
     * {@link corelib.display.windows.Window Window},
     * prompt, and maximum length.
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     * @param length the {@link Terminal}'s maximum length, counting Window
     * {@link corelib.display.windows.Border borders} and the
     * prompt
     */
    public ColoredTerminal(Output output, ColorSet prompt, int length)
        {this(output, prompt, length, null, null);}
    
    /**
     * Creates an {@link ColoredTerminal} with the given
     * {@link corelib.display.windows.Window Window},
     * prompt, and maximum length.
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     * @param length the {@link Terminal}'s maximum length, counting Window
     * {@link corelib.display.windows.Border borders} and the
     * prompt
     */
    public ColoredTerminal(Output output, String prompt, int length)
        {this(output, new ColorSet(prompt), length, null, null);}
    
    /**
     * Creates an {@link ColoredTerminal} with the given
     * {@link corelib.display.windows.Window Window} and maximum length,
     * but no prompt.
     * @param output the {@link Terminal}'s output window
     * @param length the {@link Terminal}'s maximum length, counting Window
     * {@link corelib.display.windows.Border borders}
     */
    public ColoredTerminal(Output output, int length)
        {this(output, new ColorSet(""), length, null, null);}
    
    /**
     * Creates an {@link ColoredTerminal} with the given
     * {@link corelib.display.windows.Window Window} and
     * prompt, using the width of the
     * {@link corelib.display.Display} as the maximum length.
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     */
    public ColoredTerminal(Output output, ColorSet prompt)
        {this(output, prompt, output.getDisplay().getCharWidth(), null, null);}
    
    /**
     * Creates an {@link ColoredTerminal} with the given
     * {@link corelib.display.windows.Window Window} and
     * prompt, using the width of the
     * {@link corelib.display.Display} as the maximum length.
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     */
    public ColoredTerminal(Output output, String prompt)
        {this(output, new ColorSet(prompt));}
    
    /**
     * Creates an {@link ColoredTerminal} with no prompt
     * and the width of the {@link corelib.display.Display} as the maximum
     * length.
     * @param output the {@link Terminal}'s output window
     */
    public ColoredTerminal(Output output)
        {this(output, new ColorSet(""));}
    
    /**
     * Returns the color of inputted text.
     * @return the color of inputted text
     */
    public Color getInputForeground()
        {return inputForeground;}
    
    /**
     * Returns the background color of inputted text.
     * @return the background color of inputted text
     */
    public Color getInputBackground()
        {return inputBackground;}
}