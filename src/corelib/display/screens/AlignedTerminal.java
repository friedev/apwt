package corelib.display.screens;

import corelib.display.glyphs.ColorSet;
import corelib.display.windows.AlignedWindow;
import corelib.display.glyphs.ColorString;
import java.awt.Color;

/** A {@link Terminal} displayed through an {@link AlignedWindow}. */
public class AlignedTerminal extends ColoredTerminal<AlignedWindow>
{
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
        {super(output, prompt, length, foreground, background);}
    
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
        {super(output, prompt, length, foreground);}
    
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
        {super(output, prompt, length);}
    
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
        {super(output, prompt, length);}
    
    /**
     * Creates an {@link AlignedTerminal} with the given
     * {@link corelib.display.windows.AlignedWindow Window} and maximum length,
     * but no prompt.
     * @param output the {@link Terminal}'s output window
     * @param length the {@link Terminal}'s maximum length, counting Window
     * {@link corelib.display.windows.Border borders}
     */
    public AlignedTerminal(AlignedWindow output, int length)
        {super(output, length);}
    
    /**
     * Creates an {@link AlignedTerminal} with the given
     * {@link corelib.display.windows.AlignedWindow Window} and
     * prompt, using the width of the
     * {@link corelib.display.Display} as the maximum length.
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     */
    public AlignedTerminal(AlignedWindow output, ColorSet prompt)
        {super(output, prompt);}
    
    /**
     * Creates an {@link AlignedTerminal} with the given
     * {@link corelib.display.windows.AlignedWindow Window} and
     * prompt, using the width of the
     * {@link corelib.display.Display} as the maximum length.
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     */
    public AlignedTerminal(AlignedWindow output, String prompt)
        {super(output, prompt);}
    
    /**
     * Creates an {@link AlignedTerminal} with no prompt
     * and the width of the {@link corelib.display.Display} as the maximum
     * length.
     * @param output the {@link Terminal}'s output window
     */
    public AlignedTerminal(AlignedWindow output)
        {super(output);}
    
    @Override
    public void displayOutput()
    {
        getWindow().set(getWindow().getContents().size() - 1,
                new ColorSet(getPrompt()).add(new ColorString(getInput(),
                        getInputForeground(), getInputBackground())));
        getWindow().display();
    }
}