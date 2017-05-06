package corelib.display.screens;

import corelib.display.windows.Window;
import java.awt.event.KeyEvent;

/**
 * A basic {@link corelib.display.windows.Window} where the operator can type
 * characters and delete them with backspace.
 * @param <Output> the type of {@link corelib.display.windows.Window} used by
 * the {@link Terminal}
 * @param <Content> the type of content displayed by the {@link Terminal}'s
 * {@link corelib.display.windows.Window}
 */
public abstract class Terminal<Output extends Window,
        Content extends CharSequence> extends ConfirmationScreen
{
    /** The StringBuilder where all keypresses are added. */
    private StringBuilder input;
    
    /**
     * The {@link corelib.display.windows.Window} through which all output is
     * displayed.
     */
    private Output output;
    
    /** The String printed before the entered input is shown to the user. */
    private Content prompt;
    
    /**
     * The input length at which the {@link Terminal} will not accept any new
     * input.
     */
    private int maxInputLength;
    
    /**
     * Creates a {@link Terminal} with the given
     * {@link corelib.display.windows.Window}, prompt, and maximum length.
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     * @param length the {@link Terminal}'s maximum length, counting Window
     * {@link corelib.display.windows.Border borders}
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
     * @return the new {@link Screen} to display
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
    
    /**
     * Returns this {@link Terminal}'s output
     * {@link corelib.display.windows.Window}.
     * @return this {@link Terminal}'s output
     * {@link corelib.display.windows.Window}
     */
    public Window getWindow()
        {return output;}
    
    /**
     * Returns this {@link Terminal}'s prompt.
     * @return this {@link Terminal}'s prompt
     */
    public Content getPrompt()
        {return prompt;}
    
    /**
     * Returns the input entered into the {@link Terminal} as a String.
     * @return the input entered into the {@link Terminal} as a String
     */
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