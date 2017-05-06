package corelib.display.screens;

import corelib.display.Display;
import java.awt.event.KeyEvent;

/**
 * A {@link Screen} in which Enter and Escape trigger actions for confirmation
 * and cancellation, respectively.
 */
public abstract class ConfirmationScreen extends Screen
{
    /**
     * Creates a new {@link ConfirmationScreen} on the provided {@link Display}.
     * @param display the {@link Display} on which the
     * {@link ConfirmationScreen} will be shown
     */
    public ConfirmationScreen(Display display)
        {super(display);}
    
    /**
     * Checks if the given key confirms or cancels, and runs the associated
     * method.
     * @param key the KeyEvent to check
     * @return the Screen generated through the {@link ConfirmationScreen}'s
     * {@link #onConfirm()} or {@link #onCancel()} methods
     */
    public Screen checkConfirmation(KeyEvent key)
    {
        switch (key.getKeyCode())
        {
            case KeyEvent.VK_ENTER:
                return onConfirm();
            case KeyEvent.VK_ESCAPE:
                return onCancel();
        }
        
        return this;
    }
    
    /**
     * Performs this {@link ConfirmationScreen}'s confirmation actions.
     * @return the new Screen to display
     */
    public abstract Screen onConfirm();
    
    /**
     * Performs this {@link ConfirmationScreen}'s cancellation actions.
     * @return the new {@link Screen} to display
     */
    public abstract Screen onCancel();
}
