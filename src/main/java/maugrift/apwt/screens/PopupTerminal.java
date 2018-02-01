package maugrift.apwt.screens;

import maugrift.apwt.display.AsciiPanelDisplay;
import maugrift.apwt.glyphs.ColorString;
import maugrift.apwt.windows.PopupWindow;

import java.awt.*;

/**
 * A {@link Terminal} displayed through a {@link maugrift.apwt.windows.PopupWindow}.
 *
 * @author Maugrift
 */
public class PopupTerminal extends ColoredTerminal<PopupWindow>
{
    /**
     * Creates an {@link PopupTerminal} with the given {@link maugrift.apwt.windows.PopupWindow Window}, prompt, and
     * maximum length.
     *
     * @param output     the {@link Terminal}'s output window
     * @param prompt     the {@link Terminal}'s prompt
     * @param length     the {@link Terminal}'s maximum length, counting Window {@link maugrift.apwt.windows.Border
     *                   borders} and the prompt
     * @param foreground the color of the input characters
     * @param background the color of the input background
     */
    public PopupTerminal(PopupWindow output, ColorString prompt, int length, Color foreground, Color background)
    {
        super(output, prompt, length, foreground, background);
    }

    /**
     * Creates an {@link PopupTerminal} with the given {@link maugrift.apwt.windows.PopupWindow Window}, prompt, and
     * maximum length.
     *
     * @param output     the {@link Terminal}'s output window
     * @param prompt     the {@link Terminal}'s prompt
     * @param length     the {@link Terminal}'s maximum length, counting Window {@link maugrift.apwt.windows.Border
     *                   borders} and the prompt
     * @param foreground the color of the input characters
     */
    public PopupTerminal(PopupWindow output, ColorString prompt, int length, Color foreground)
    {
        super(output, prompt, length, foreground);
    }

    /**
     * Creates an {@link PopupTerminal} with the given {@link maugrift.apwt.windows.PopupWindow Window}, prompt, and
     * maximum length.
     *
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     * @param length the {@link Terminal}'s maximum length, counting Window {@link maugrift.apwt.windows.Border borders}
     *               and the prompt
     */
    public PopupTerminal(PopupWindow output, ColorString prompt, int length)
    {
        super(output, prompt, length);
    }

    /**
     * Creates an {@link PopupTerminal} with the given {@link maugrift.apwt.windows.PopupWindow Window}, prompt, and
     * maximum length.
     *
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     * @param length the {@link Terminal}'s maximum length, counting Window {@link maugrift.apwt.windows.Border borders}
     *               and the prompt
     */
    public PopupTerminal(PopupWindow output, String prompt, int length)
    {
        super(output, prompt, length);
    }

    /**
     * Creates an {@link PopupTerminal} with the given {@link maugrift.apwt.windows.PopupWindow Window} and maximum
     * length, but no prompt.
     *
     * @param output the {@link Terminal}'s output window
     * @param length the {@link Terminal}'s maximum length, counting Window {@link maugrift.apwt.windows.Border
     *               borders}
     */
    public PopupTerminal(PopupWindow output, int length)
    {
        super(output, length);
    }

    /**
     * Creates an {@link PopupTerminal} with the given {@link maugrift.apwt.windows.PopupWindow Window} and prompt,
     * using the width of the {@link AsciiPanelDisplay} as the maximum length.
     *
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     */
    public PopupTerminal(PopupWindow output, ColorString prompt)
    {
        super(output, prompt);
    }

    /**
     * Creates an {@link PopupTerminal} with the given {@link maugrift.apwt.windows.PopupWindow Window} and prompt,
     * using the width of the {@link AsciiPanelDisplay} as the maximum length.
     *
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     */
    public PopupTerminal(PopupWindow output, String prompt)
    {
        super(output, prompt);
    }

    /**
     * Creates an {@link PopupTerminal} with no prompt and the width of the {@link AsciiPanelDisplay} as the maximum
     * length.
     *
     * @param output the {@link Terminal}'s output window
     */
    public PopupTerminal(PopupWindow output)
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