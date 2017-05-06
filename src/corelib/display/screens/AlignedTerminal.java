package corelib.display.screens;

import corelib.display.glyphs.ColorSet;
import corelib.display.windows.AlignedWindow;
import corelib.display.glyphs.ColorString;
import java.awt.Color;

/** A {@link Terminal} displayed through an {@link AlignedWindow}. */
public class AlignedTerminal extends Terminal<AlignedWindow, ColorSet>
{
    /** The color of inputted text. */
    private Color inputForeground;
    
    /** The background color of inputted text. */
    private Color inputBackground;
    
    /**
     * Creates an {@link AlignedTerminal} with the given
     * {@link corelib.display.windows.AlignedWindow Window},
     * prompt, and maximum length.
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     * @param length the {@link Terminal}'s maximum length, counting Window
     * {@link corelib.display.windows.Border borders} and the
     * prompt
     * @param foreground the color of the input characters
     * @param background the color of the input background
     */
    public AlignedTerminal(AlignedWindow output, ColorSet prompt, int length,
            Color foreground, Color background)
    {
        super(output, prompt, length);
        inputForeground = foreground;
        inputBackground = background;
        output.add(prompt);
    }
    
    /**
     * Creates an {@link AlignedTerminal} with the given
     * {@link corelib.display.windows.AlignedWindow Window},
     * prompt, and maximum length.
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     * @param length the {@link Terminal}'s maximum length, counting Window
     * {@link corelib.display.windows.Border borders} and the
     * prompt
     * @param foreground the color of the input characters
     */
    public AlignedTerminal(AlignedWindow output, ColorSet prompt, int length,
            Color foreground)
        {this(output, prompt, length, foreground, null);}
    
    /**
     * Creates an {@link AlignedTerminal} with the given
     * {@link corelib.display.windows.AlignedWindow Window},
     * prompt, and maximum length.
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     * @param length the {@link Terminal}'s maximum length, counting Window
     * {@link corelib.display.windows.Border borders} and the
     * prompt
     */
    public AlignedTerminal(AlignedWindow output, ColorSet prompt, int length)
        {this(output, prompt, length, null, null);}
    
    /**
     * Creates an {@link AlignedTerminal} with the given
     * {@link corelib.display.windows.AlignedWindow Window},
     * prompt, and maximum length.
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     * @param length the {@link Terminal}'s maximum length, counting Window
     * {@link corelib.display.windows.Border borders} and the
     * prompt
     */
    public AlignedTerminal(AlignedWindow output, String prompt, int length)
        {this(output, new ColorSet(prompt), length, null, null);}
    
    /**
     * Creates an {@link AlignedTerminal} with the given
     * {@link corelib.display.windows.AlignedWindow Window} and maximum length,
     * but no prompt.
     * @param output the {@link Terminal}'s output window
     * @param length the {@link Terminal}'s maximum length, counting Window
     * {@link corelib.display.windows.Border borders}
     */
    public AlignedTerminal(AlignedWindow output, int length)
        {this(output, new ColorSet(""), length, null, null);}
    
    /**
     * Creates an {@link AlignedTerminal} with the given
     * {@link corelib.display.windows.AlignedWindow Window} and
     * prompt, using the width of the
     * {@link corelib.display.Display} as the maximum length.
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     */
    public AlignedTerminal(AlignedWindow output, ColorSet prompt)
        {this(output, prompt, output.getDisplay().getCharWidth(), null, null);}
    
    /**
     * Creates an {@link AlignedTerminal} with the given
     * {@link corelib.display.windows.AlignedWindow Window} and
     * prompt, using the width of the
     * {@link corelib.display.Display} as the maximum length.
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     */
    public AlignedTerminal(AlignedWindow output, String prompt)
        {this(output, new ColorSet(prompt));}
    
    /**
     * Creates an {@link AlignedTerminal} with no prompt
     * and the width of the {@link corelib.display.Display} as the maximum
     * length.
     * @param output the {@link Terminal}'s output window
     */
    public AlignedTerminal(AlignedWindow output)
        {this(output, new ColorSet(""));}
    
    @Override
    public void displayOutput()
    {
        getWindow().set(getWindow().getContents().size() - 1,
                new ColorSet().add(getPrompt()).add(new ColorString(getInput(),
                        inputForeground, inputBackground)));
        getWindow().display();
    }
}