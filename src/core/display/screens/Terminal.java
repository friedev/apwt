package core.display.screens;

import core.display.ColorString;
import core.display.Window;
import java.awt.event.KeyEvent;

/**
 * A basic window where the operator can type characters and delete them with
 * backspace.
 */
public class Terminal extends Screen
{
    /** The StringBuilder where all keypresses are added. */
    private StringBuilder input;
    /** The Window through which all output is displayed. */
    private Window output;
    /** The String printed before the entered input is shown to the user. */
    private String prompt;
    /** The input length at which the Terminal will not accept any new input. */
    private int maxInputLength;
    
    /**
     * Creates a Terminal with the given Window, prompt, and maximum length.
     * @param output the Terminal's output window
     * @param prompt the Terminal's prompt
     * @param length the Terminal's maximum length, counting Window borders and
     * the prompt
     */
    public Terminal(Window output, String prompt, int length)
    {
        super(output.getDisplay());
        input  = new StringBuilder();
        this.output = output;
        this.prompt = prompt;
        maxInputLength = length;
        
        if (prompt != null)
            maxInputLength -= prompt.length();
        
        if (output.isBordered())
            maxInputLength -= 2;
        
        output.add(prompt);
    }
    
    /**
     * Creates a Terminal with the given Window and maximum length, but no
     * prompt.
     * @param output the Terminal's output window
     * @param length the Terminal's maximum length, counting Window borders
     */
    public Terminal(Window output, int length)
        {this(output, "", length);}
    
    /**
     * Creates a Terminal with the given Window and prompt, using the width of
     * the Display as the maximum length.
     * @param output the Terminal's output window
     * @param prompt the Terminal's prompt
     */
    public Terminal(Window output, String prompt)
        {this(output, prompt, output.getDisplay().getCharWidth());}
    
    /**
     * Creates a Terminal with no prompt and the width of the Display as the
     * maximum length.
     * @param window the Terminal's output window
     */
    public Terminal(Window window)
        {this(window, "", window.getDisplay().getCharWidth());}
    
    @Override
    public void displayOutput()
        {output.display();}
    
    @Override
    public Screen processInput(KeyEvent key)
    {
        switch (key.getKeyCode())
        {
            case KeyEvent.VK_ESCAPE:
                return null;
            case KeyEvent.VK_BACK_SPACE:
                if (input.length() > 0)
                {
                    if (key.isControlDown())
                        input.delete(0, input.length());
                    else
                        input.deleteCharAt(input.length() - 1);
                }
                break;
            case KeyEvent.VK_T:
                if (key.isControlDown())
                    return null;
            default:
                if (Character.isDefined(key.getKeyChar()))
                    input.append(key.getKeyChar());
                break;
        }
        
        if (input.length() >= maxInputLength)
            input.delete(maxInputLength, input.length());
        
        output.set(0, new ColorString(prompt + input.toString()));
        return this;
    }
    
    public Window getWindow()
        {return output;}
}