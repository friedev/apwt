package core.display.screens;

import core.display.Display;
import core.display.Menu;
import java.awt.event.KeyEvent;

/**
 * A basic input screen where the operator can type characters and delete them
 * with backspace.
 */
public class Terminal extends Screen
{
    private StringBuilder input;
    private int           x;
    private int           y;
    private String        prompt;
    private int           maxInputLength;
    private boolean       centered;
    private boolean       bordered;
    
    public Terminal(Display d, int xx, int yy, String p, int l, boolean c,
            boolean b)
    {
        super(d);
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
    }
    
    // TODO add more intermediate TerminalScreen constructors
    
    public Terminal(Display d, int xx, int yy)
        {this(d, xx, yy, "", d.getCharWidth(), false, false);}
    
    @Override
    public void displayOutput()
    {
        if (input.length() >= maxInputLength)
            input.delete(maxInputLength, input.length());

        String output = prompt + input.toString();
        
        if (bordered)
        {
            if (centered)
                Menu.printCenterBoxed(display, new String[] {output}, y);
            else
                Menu.printBoxed(display, new String[] {output}, x, y);
        }
        else
        {
            if (centered)
                display.get().writeCenter(output, y);
            else
                display.get().write(output, x, y);
        }
    }
    
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
        
        return this;
    }
}