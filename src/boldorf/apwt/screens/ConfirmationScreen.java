package boldorf.apwt.screens;

import boldorf.apwt.Display;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A {@link Screen} in which Enter and Escape trigger actions for confirmation
 * and cancellation, respectively.
 */
public abstract class ConfirmationScreen extends Screen
{
    public static final List<Integer> CONFIRM_CODES =
            Arrays.asList(new Integer[]{KeyEvent.VK_ENTER, KeyEvent.VK_Y});
    public static final List<Integer> DENY_CODES =
            Arrays.asList(new Integer[]{KeyEvent.VK_N});
    public static final List<Integer> CANCEL_CODES =
            Arrays.asList(new Integer[]{KeyEvent.VK_ESCAPE, KeyEvent.VK_Q});
    
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
        this(display, new ArrayList<>(Arrays.asList(confirmCodes)),
                new ArrayList<>(Arrays.asList(denyCodes)),
                new ArrayList<>(Arrays.asList(cancelCodes)));
    }
    
    /**
     * Creates a new {@link ConfirmationScreen} on the provided {@link Display}.
     * @param display the {@link Display} on which the
     * {@link ConfirmationScreen} will be shown
     */
    public ConfirmationScreen(Display display)
    {
        this(display, new ArrayList<>(CONFIRM_CODES),
                new ArrayList<>(DENY_CODES), new ArrayList<>(CANCEL_CODES));
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
    
    public List<Integer> getConfirmCodes()
        {return confirmCodes;}
    
    public List<Integer> getDenyCodes()
        {return denyCodes;}
    
    public List<Integer> getCancelCodes()
        {return cancelCodes;}
    
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
