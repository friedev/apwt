package maugrift.apwt.screens;

import maugrift.apwt.display.Display;
import maugrift.apwt.glyphs.ColorString;
import maugrift.apwt.windows.AlignedWindow;

import java.awt.*;

/**
 * A {@link Terminal} displayed through an {@link AlignedWindow}.
 *
 * @author Maugrift
 */
public class AlignedTerminal extends ColoredTerminal<AlignedWindow>
{
    /**
     * Creates an {@link AlignedTerminal} with the given {@link maugrift.apwt.windows.AlignedWindow Window}, prompt, and
     * maximum length.
     *
     * @param output     the {@link Terminal}'s output window
     * @param prompt     the {@link Terminal}'s prompt
     * @param length     the {@link Terminal}'s maximum length, counting Window {@link maugrift.apwt.windows.Border
     *                   borders} and the prompt
     * @param foreground the color of the input characters
     * @param background the color of the input background
     */
    public AlignedTerminal(AlignedWindow output, ColorString prompt, int length, Color foreground, Color background)
    {
        super(output, prompt, length, foreground, background);
    }

    /**
     * Creates an {@link AlignedTerminal} with the given {@link maugrift.apwt.windows.AlignedWindow Window}, prompt, and
     * maximum length.
     *
     * @param output     the {@link Terminal}'s output window
     * @param prompt     the {@link Terminal}'s prompt
     * @param length     the {@link Terminal}'s maximum length, counting Window {@link maugrift.apwt.windows.Border
     *                   borders} and the prompt
     * @param foreground the color of the input characters
     */
    public AlignedTerminal(AlignedWindow output, ColorString prompt, int length, Color foreground)
    {
        super(output, prompt, length, foreground);
    }

    /**
     * Creates an {@link AlignedTerminal} with the given {@link maugrift.apwt.windows.AlignedWindow Window}, prompt, and
     * maximum length.
     *
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     * @param length the {@link Terminal}'s maximum length, counting Window {@link maugrift.apwt.windows.Border borders}
     *               and the prompt
     */
    public AlignedTerminal(AlignedWindow output, ColorString prompt, int length)
    {
        super(output, prompt, length);
    }

    /**
     * Creates an {@link AlignedTerminal} with the given {@link maugrift.apwt.windows.AlignedWindow Window}, prompt, and
     * maximum length.
     *
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     * @param length the {@link Terminal}'s maximum length, counting Window {@link maugrift.apwt.windows.Border borders}
     *               and the prompt
     */
    public AlignedTerminal(AlignedWindow output, String prompt, int length)
    {
        super(output, prompt, length);
    }

    /**
     * Creates an {@link AlignedTerminal} with the given {@link maugrift.apwt.windows.AlignedWindow Window} and maximum
     * length, but no prompt.
     *
     * @param output the {@link Terminal}'s output window
     * @param length the {@link Terminal}'s maximum length, counting Window {@link maugrift.apwt.windows.Border
     *               borders}
     */
    public AlignedTerminal(AlignedWindow output, int length)
    {
        super(output, length);
    }

    /**
     * Creates an {@link AlignedTerminal} with the given {@link maugrift.apwt.windows.AlignedWindow Window} and prompt,
     * using the width of the {@link Display} as the maximum length.
     *
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     */
    public AlignedTerminal(AlignedWindow output, ColorString prompt)
    {
        super(output, prompt);
    }

    /**
     * Creates an {@link AlignedTerminal} with the given {@link maugrift.apwt.windows.AlignedWindow Window} and prompt,
     * using the width of the {@link Display} as the maximum length.
     *
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     */
    public AlignedTerminal(AlignedWindow output, String prompt)
    {
        super(output, prompt);
    }

    /**
     * Creates an {@link AlignedTerminal} with no prompt and the width of the {@link Display} as the
     * maximum length.
     *
     * @param output the {@link Terminal}'s output window
     */
    public AlignedTerminal(AlignedWindow output)
    {
        super(output);
    }

    @Override
    public void displayOutput()
    {
        getWindow().getContents().set(getWindow().getContents().size() - 1, new ColorString(getPrompt()).add(
                new ColorString(getInput(), getInputForeground(), getInputBackground())));
        getWindow().display();
    }
}
