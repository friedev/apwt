package corelib.display.screens;

import corelib.display.glyphs.ColorString;
import corelib.display.windows.PopupWindow;

/**
 * A {@link Terminal} displayed through a
 * {@link corelib.display.windows.PopupWindow}.
 */
public class PopupTerminal extends Terminal<PopupWindow, ColorString>
{
    /**
     * Creates a {@link PopupTerminal} with the given
     * {@link corelib.display.windows.PopupWindow Window}, prompt, and maximum
     * length.
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     * @param length the {@link Terminal}'s maximum length, counting Window
     * {@link corelib.display.windows.Border borders}
     */
    public PopupTerminal(PopupWindow output, ColorString prompt, int length)
    {
        super(output, prompt, length);
        output.add(prompt);
    }
    
    /**
     * Creates a {@link PopupTerminal} with the given
     * {@link corelib.display.windows.PopupWindow Window}, prompt, and maximum
     * length.
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     * @param length the {@link Terminal}'s maximum length, counting Window
     * {@link corelib.display.windows.Border borders}
     */
    public PopupTerminal(PopupWindow output, String prompt, int length)
        {this(output, new ColorString(prompt), length);}
    
    /**
     * Creates a {@link PopupTerminal} with the given
     * {@link corelib.display.windows.PopupWindow Window} and maximum length,
     * but no prompt.
     * @param output the {@link Terminal}'s output window
     * @param length the {@link Terminal}'s maximum length, counting Window
     * {@link corelib.display.windows.Border borders}
     */
    public PopupTerminal(PopupWindow output, int length)
        {this(output, new ColorString(""), length);}
    
    /**
     * Creates a {@link PopupTerminal} with the given
     * {@link corelib.display.windows.PopupWindow Window} and prompt, using the
     * width of the {@link corelib.display.Display} as the maximum length.
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     */
    public PopupTerminal(PopupWindow output, ColorString prompt)
        {this(output, prompt, output.getDisplay().getCharWidth());}
    
    /**
     * Creates a {@link PopupTerminal} with the given
     * {@link corelib.display.windows.PopupWindow Window} and prompt, using the
     * width of the {@link corelib.display.Display} as the maximum length.
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     */
    public PopupTerminal(PopupWindow output, String prompt)
        {this(output, new ColorString(prompt));}
    
    /**
     * Creates a {@link PopupTerminal} with no prompt and the width of the
     * {@link corelib.display.Display} as the maximum length.
     * @param output the {@link Terminal}'s output window
     */
    public PopupTerminal(PopupWindow output)
        {this(output, new ColorString(""), output.getDisplay().getCharWidth());}
    
    @Override
    public void displayOutput()
    {
        getWindow().set(getWindow().getContents().size() - 1,
                new ColorString(getPrompt() + getInput(),
                        getPrompt().getForeground(),
                        getPrompt().getBackground()));
        getWindow().display();
    }
}