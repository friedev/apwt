package core.display.screens;

import core.display.Display;
import java.awt.event.KeyEvent;

/**
 * A basic window where the operator can type characters and delete them with
 * backspace.
 */
public class Terminal extends Window
{
    private StringBuilder input;
    private String prompt;
    private int maxInputLength;
    
    public Terminal(Display d, int xx, int yy, boolean c, boolean b, String p,
            int l)
    {
        super(d, xx, yy, c, b);
        input          = new StringBuilder();
        x              = xx;
        y              = yy;
        prompt         = p;
        maxInputLength = l;
        centered       = c;
        bordered       = b;
        
        if (prompt != null)
            maxInputLength -= prompt.length();
        
        if (bordered)
            maxInputLength -= 2;
        
        getContents().add(prompt);
    }
    
    // TODO add more intermediate TerminalScreen constructors
    
    public Terminal(Display d, int xx, int yy)
        {this(d, xx, yy, false, false, "", d.getCharWidth());}
    
    @Override
    public Screen processInput(KeyEvent key)
    {
        switch (key.getKeyCode())
        {
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
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
        
        getContents().set(0, prompt + input.toString());
        return this;
    }
}