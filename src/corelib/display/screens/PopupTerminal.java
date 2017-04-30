package corelib.display.screens;

import corelib.display.glyphs.ColorString;
import corelib.display.windows.PopupWindow;

/** A Terminal displayed as a PopupWindow. */
public class PopupTerminal extends Terminal<PopupWindow, ColorString>
{
    /**
     * Creates a Terminal with the given Window, prompt, and maximum length.
     * @param output the Terminal's output window
     * @param prompt the Terminal's prompt
     * @param length the Terminal's maximum length, counting Window borders and
     * the prompt
     */
    public PopupTerminal(PopupWindow output, ColorString prompt, int length)
    {
        super(output, prompt, length);
        output.add(prompt);
    }
    
    /**
     * Creates a Terminal with the given Window, prompt, and maximum length.
     * @param output the Terminal's output window
     * @param prompt the Terminal's prompt
     * @param length the Terminal's maximum length, counting Window borders and
     * the prompt
     */
    public PopupTerminal(PopupWindow output, String prompt, int length)
        {this(output, new ColorString(prompt), length);}
    
    /**
     * Creates a Terminal with the given Window and maximum length, but no
     * prompt.
     * @param output the Terminal's output window
     * @param length the Terminal's maximum length, counting Window borders
     */
    public PopupTerminal(PopupWindow output, int length)
        {this(output, new ColorString(""), length);}
    
    /**
     * Creates a Terminal with the given Window and prompt, using the width of
     * the Display as the maximum length.
     * @param output the Terminal's output window
     * @param prompt the Terminal's prompt
     */
    public PopupTerminal(PopupWindow output, ColorString prompt)
        {this(output, prompt, output.getDisplay().getCharWidth());}
    
    /**
     * Creates a Terminal with the given Window and prompt, using the width of
     * the Display as the maximum length.
     * @param output the Terminal's output window
     * @param prompt the Terminal's prompt
     */
    public PopupTerminal(PopupWindow output, String prompt)
        {this(output, new ColorString(prompt));}
    
    /**
     * Creates a Terminal with no prompt and the width of the Display as the
     * maximum length.
     * @param output the Terminal's output window
     */
    public PopupTerminal(PopupWindow output)
        {this(output, new ColorString(""), output.getDisplay().getCharWidth());}
    
    @Override
    public void displayOutput()
    {
        output.set(output.getContents().size() - 1,
                new ColorString(prompt + input.toString(), prompt.foreground,
                prompt.background));
        output.display();
    }
}