package corelib.display.screens;

import corelib.display.Display;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

/**
 * A {@link Screen} in which Enter and Escape trigger actions for confirmation
 * and cancellation, respectively.
 */
public abstract class ConfirmationScreen extends Screen
{
    private List<Integer> confirmCodes;
    private List<Integer> denyCodes;
    private List<Integer> cancelCodes;
    
    public ConfirmationScreen(Display display, List<Integer> confirmCodes,
            List<Integer> denyCodes, List<Integer> cancelCodes)
    {
        super(display);
        this.confirmCodes = confirmCodes;
        this.denyCodes    = denyCodes;
        this.cancelCodes  = cancelCodes;
    }
    
    public ConfirmationScreen(Display display, Integer[] confirmCodes,
            Integer[] denyCodes, Integer[] cancelCodes)
    {
        this(display, Arrays.asList(confirmCodes), Arrays.asList(denyCodes),
                Arrays.asList(cancelCodes));
    }
    
    /**
     * Creates a new {@link ConfirmationScreen} on the provided {@link Display}.
     * @param display the {@link Display} on which the
     * {@link ConfirmationScreen} will be shown
     */
    public ConfirmationScreen(Display display)
    {
        this(display,
                new Integer[] {KeyEvent.VK_ENTER, KeyEvent.VK_Y},
                new Integer[] {KeyEvent.VK_N},
                new Integer[] {KeyEvent.VK_ESCAPE, KeyEvent.VK_Q});
    }
    
    /**
     * Checks if the given key confirms or cancels, and runs the associated
     * method.
     * @param key the KeyEvent to check
     * @return the Screen generated through the {@link ConfirmationScreen}'s
     * {@link #onConfirm()} or {@link #onCancel()} methods
     */
    @Override
    public Screen processInput(KeyEvent key)
    {
        if (confirmCodes.contains(key.getKeyCode()))
            return onConfirm();
        
        if (denyCodes.contains(key.getKeyCode()))
            return onDeny();
        
        if (cancelCodes.contains(key.getKeyCode()))
            return onCancel();
        
        return this;
    }
    
    /**
     * Performs this {@link ConfirmationScreen}'s confirmation actions. By
     * default, performs the same actions as {@link #onCancel()}.
     * @return the new {@link Screen} to display
     */
    public Screen onConfirm()
        {return onCancel();}
    
    /**
     * Performs this {@link ConfirmationScreen}'s denial actions. By default,
     * performs the same actions as {@link #onCancel()}.
     * @return the new {@link Screen} to display
     */
    public Screen onDeny()
        {return onCancel();}
    
    /**
     * Performs this {@link ConfirmationScreen}'s cancellation actions. By
     * default, returns null.
     * @return the new {@link Screen} to display
     */
    public Screen onCancel()
        {return null;}
}
