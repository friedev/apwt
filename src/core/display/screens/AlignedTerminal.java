package core.display.screens;

import core.display.ColorSet;
import core.display.AlignedWindow;
import core.display.ColorString;
import java.awt.Color;

/** A Terminal displayed as an AlignedWindow. */
public class AlignedTerminal extends Terminal<AlignedWindow, ColorSet>
{
    private Color inputForeground;
    private Color inputBackground;
    
    /**
     * Creates a Terminal with the given Window, prompt, and maximum length.
     * @param output the Terminal's output window
     * @param prompt the Terminal's prompt
     * @param length the Terminal's maximum length, counting Window borders and
     * the prompt
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
     * Creates a Terminal with the given Window, prompt, and maximum length.
     * @param output the Terminal's output window
     * @param prompt the Terminal's prompt
     * @param length the Terminal's maximum length, counting Window borders and
     * the prompt
     * @param foreground the color of the input characters
     */
    public AlignedTerminal(AlignedWindow output, ColorSet prompt, int length,
            Color foreground)
        {this(output, prompt, length, foreground, null);}
    
    /**
     * Creates a Terminal with the given Window, prompt, and maximum length.
     * @param output the Terminal's output window
     * @param prompt the Terminal's prompt
     * @param length the Terminal's maximum length, counting Window borders and
     * the prompt
     */
    public AlignedTerminal(AlignedWindow output, ColorSet prompt, int length)
        {this(output, prompt, length, null, null);}
    
    /**
     * Creates a Terminal with the given Window, prompt, and maximum length.
     * @param output the Terminal's output window
     * @param prompt the Terminal's prompt
     * @param length the Terminal's maximum length, counting Window borders and
     * the prompt
     */
    public AlignedTerminal(AlignedWindow output, String prompt, int length)
        {this(output, new ColorSet(prompt), length, null, null);}
    
    /**
     * Creates a Terminal with the given Window and maximum length, but no
     * prompt.
     * @param output the Terminal's output window
     * @param length the Terminal's maximum length, counting Window borders
     */
    public AlignedTerminal(AlignedWindow output, int length)
        {this(output, new ColorSet(""), length, null, null);}
    
    /**
     * Creates a Terminal with the given Window and prompt, using the width of
     * the Display as the maximum length.
     * @param output the Terminal's output window
     * @param prompt the Terminal's prompt
     */
    public AlignedTerminal(AlignedWindow output, ColorSet prompt)
        {this(output, prompt, output.getDisplay().getCharWidth(), null, null);}
    
    /**
     * Creates a Terminal with the given Window and prompt, using the width of
     * the Display as the maximum length.
     * @param output the Terminal's output window
     * @param prompt the Terminal's prompt
     */
    public AlignedTerminal(AlignedWindow output, String prompt)
        {this(output, new ColorSet(prompt));}
    
    /**
     * Creates a Terminal with no prompt and the width of the Display as the
     * maximum length.
     * @param output the Terminal's output window
     */
    public AlignedTerminal(AlignedWindow output)
        {this(output, new ColorSet(""));}
    
    @Override
    public void displayOutput()
    {
        output.set(output.getContents().size() - 1,
                new ColorSet().add(prompt).add(new ColorString(input.toString(),
                        inputForeground, inputBackground)));
        output.display();
    }
}