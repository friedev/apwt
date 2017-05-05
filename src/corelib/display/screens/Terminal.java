package corelib.display.screens;

import corelib.display.windows.Window;
import java.awt.event.KeyEvent;

/**
 * A basic window where the operator can type characters and delete them with
 * backspace.
 * @param <Output> the type of Window used by the Terminal
 * @param <Content> the type of content displayed by the Terminal's Window
 */
public abstract class Terminal<Output extends Window,
        Content extends CharSequence> extends ConfirmationScreen
{
    /** The StringBuilder where all keypresses are added. */
    protected StringBuilder input;
    /** The Window through which all output is displayed. */
    protected Output output;
    /** The String printed before the entered input is shown to the user. */
    protected Content prompt;
    /** The input length at which the Terminal will not accept any new input. */
    protected int maxInputLength;
    
    /**
     * Creates a Terminal with the given Window, prompt, and maximum length.
     * @param output the Terminal's output window
     * @param prompt the Terminal's prompt
     * @param length the Terminal's maximum length, counting Window borders and
     * the prompt
     */
    public Terminal(Output output, Content prompt, int length)
    {
        super(output.getDisplay());
        input = new StringBuilder();
        this.output = output;
        this.prompt = prompt;
        maxInputLength = length;
        
        if (prompt != null)
            maxInputLength -= prompt.length();
        
        if (output.isBordered())
            maxInputLength -= 2;
    }
    
    /**
     * Adds the typed key to the input field.
     * @param key the KeyEvent to process
     * @return the new Screen to display
     */
    @Override
    public Screen processInput(KeyEvent key)
    {
        switch (key.getKeyCode())
        {
            case KeyEvent.VK_BACK_SPACE:
                if (input.length() > 0)
                {
                    if (key.isControlDown())
                        input.delete(0, input.length());
                    else
                        input.deleteCharAt(input.length() - 1);
                }
                break;
            default:
                if (Character.isDefined(key.getKeyChar()))
                    input.append(key.getKeyChar());
                break;
        }
        
        if (input.length() >= maxInputLength)
            input.delete(maxInputLength, input.length());
        
        return checkConfirmation(key);
    }
    
    public Window getWindow()
        {return output;}
    
    public String getInput()
    {
        if (input == null)
            return null;
        
        return input.toString();
    }
    
    @Override
    public Screen onConfirm()
    {
        input.deleteCharAt(input.length() - 1);
        return null;
    }
    
    @Override
    public Screen onCancel()
    {
        input = null;
        return null;
    }
}