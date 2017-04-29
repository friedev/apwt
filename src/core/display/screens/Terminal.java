package core.display.screens;

import core.display.ColorString;
import core.display.PopupWindow;
import java.awt.event.KeyEvent;

/**
 * A basic window where the operator can type characters and delete them with
 * backspace.
 */
public class Terminal extends Screen
{
    /** The StringBuilder where all keypresses are added. */
    private StringBuilder input;
    /** The PopupWindow through which all output is displayed. */
    private PopupWindow output;
    /** The String printed before the entered input is shown to the user. */
    private String prompt;
    /** The input length at which the Terminal will not accept any new input. */
    private int maxInputLength;
    
    /**
     * Creates a Terminal with the given PopupWindow, prompt, and maximum
     * length.
     * @param w the Terminal's output PopupWindow
     * @param p the Terminal's prompt
     * @param l the Terminal's maximum length, counting Window borders and the
     * prompt
     */
    public Terminal(PopupWindow w, String p, int l)
    {
        super(w.getDisplay());
        input  = new StringBuilder();
        output = w;
        prompt = p;
        maxInputLength = l;
        
        if (prompt != null)
            maxInputLength -= prompt.length();
        
        if (output.isBordered())
            maxInputLength -= 2;
        
        output.add(new ColorString(prompt));
    }
    
    /**
     * Creates a Terminal with the given PopupWindow and maximum length, but no
     * prompt.
     * @param w the Terminal's output PopupWindow
     * @param l the Terminal's maximum length, counting Window borders
     */
    public Terminal(PopupWindow w, int l)
        {this(w, "", l);}
    
    /**
     * Creates a Terminal with the given PopupWindow and prompt, using the width
     * of the Display as the maximum length.
     * @param w the Terminal's output PopupWindow
     * @param p the Terminal's prompt
     */
    public Terminal(PopupWindow w, String p)
        {this(w, p, w.getDisplay().getCharWidth());}
    
    /**
     * Creates a Terminal with no prompt and the width of the Display as the
     * maximum length.
     * @param w the Terminal's output PopupWindow
     */
    public Terminal(PopupWindow w)
        {this(w, "", w.getDisplay().getCharWidth());}
    
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
}