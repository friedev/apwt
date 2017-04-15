package core.display.screens;

import java.awt.event.KeyEvent;

/**
 * A basic window where the operator can type characters and delete them with
 * backspace.
 */
public class Terminal extends Screen
{
    private StringBuilder input;
    private Window output;
    private String prompt;
    private int    maxInputLength;
    
    public Terminal(Window w, String p, int l)
    {
        super(w.display);
        input  = new StringBuilder();
        output = w;
        prompt = p;
        maxInputLength = l;
        
        if (prompt != null)
            maxInputLength -= prompt.length();
        
        if (output.bordered)
            maxInputLength -= 2;
        
        output.getContents().add(prompt);
    }
    
    // TODO add more intermediate TerminalScreen constructors
    
    public Terminal(Window w)
        {this(w, "", w.display.getCharWidth());}
    
    @Override
    public void displayOutput()
        {output.displayOutput();}
    
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
        
        output.getContents().set(0, prompt + input.toString());
        return this;
    }
}