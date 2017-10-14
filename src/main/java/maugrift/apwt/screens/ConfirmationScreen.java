package maugrift.apwt.screens;

import maugrift.apwt.Display;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A {@link Screen} in which Enter and Escape trigger actions for confirmation and cancellation, respectively.
 *
 * @author Maugrift
 */
public abstract class ConfirmationScreen extends Screen
{
    /**
     * The default confirmation keycodes.
     */
    public static final List<Integer> CONFIRM_CODES = Arrays.asList(new Integer[]{KeyEvent.VK_ENTER, KeyEvent.VK_Y});

    /**
     * The default denial keycodes.
     */
    public static final List<Integer> DENY_CODES = Arrays.asList(new Integer[]{KeyEvent.VK_N});

    /**
     * The default cancellation keycodes.
     */
    public static final List<Integer> CANCEL_CODES = Arrays.asList(new Integer[]{KeyEvent.VK_ESCAPE, KeyEvent.VK_Q});

    /**
     * The keycodes that will trigger {@link #onConfirm()}.
     */
    private List<Integer> confirmCodes;

    /**
     * The keycodes that will trigger {@link #onDeny()}.
     */
    private List<Integer> denyCodes;

    /**
     * The keycodes that will trigger {@link #onCancel()}.
     */
    private List<Integer> cancelCodes;

    /**
     * Creates a new {@link ConfirmationScreen} on the provided {@link Display} with the given Lists of keycodes.
     *
     * @param display      the {@link Display} on which the {@link ConfirmationScreen} will be shown
     * @param confirmCodes the keycodes that will trigger {@link #onConfirm()}
     * @param denyCodes    the keycodes that will trigger {@link #onDeny()}
     * @param cancelCodes  the keycodes that will trigger {@link #onCancel()}
     */
    public ConfirmationScreen(Display display, List<Integer> confirmCodes, List<Integer> denyCodes,
                              List<Integer> cancelCodes)
    {
        super(display);
        this.confirmCodes = confirmCodes;
        this.denyCodes = denyCodes;
        this.cancelCodes = cancelCodes;
    }

    /**
     * Creates a new {@link ConfirmationScreen} on the provided {@link Display} with the given arrays of keycodes.
     *
     * @param display      the {@link Display} on which the {@link ConfirmationScreen} will be shown
     * @param confirmCodes the keycodes that will trigger {@link #onConfirm()}
     * @param denyCodes    the keycodes that will trigger {@link #onDeny()}
     * @param cancelCodes  the keycodes that will trigger {@link #onCancel()}
     */
    public ConfirmationScreen(Display display, Integer[] confirmCodes, Integer[] denyCodes, Integer[] cancelCodes)
    {
        this(display, new ArrayList<>(Arrays.asList(confirmCodes)), new ArrayList<>(Arrays.asList(denyCodes)),
                new ArrayList<>(Arrays.asList(cancelCodes)));
    }

    /**
     * Creates a new {@link ConfirmationScreen} on the provided {@link Display}. Uses the default keycodes.
     *
     * @param display the {@link Display} on which the {@link ConfirmationScreen} will be shown
     */
    public ConfirmationScreen(Display display)
    {
        this(display, new ArrayList<>(CONFIRM_CODES), new ArrayList<>(DENY_CODES), new ArrayList<>(CANCEL_CODES));
    }

    /**
     * Checks if the given key confirms or cancels, and runs the associated method.
     *
     * @param key the KeyEvent to check
     * @return the Screen generated through the {@link ConfirmationScreen}'s {@link #onConfirm()} or {@link #onCancel()}
     * methods
     */
    @Override
    public Screen processInput(KeyEvent key)
    {
        if (confirmCodes.contains(key.getKeyCode()))
        {
            return onConfirm();
        }

        if (denyCodes.contains(key.getKeyCode()))
        {
            return onDeny();
        }

        if (cancelCodes.contains(key.getKeyCode()))
        {
            return onCancel();
        }

        return this;
    }

    /**
     * Returns this {@link ConfirmationScreen}'s confirmation keycodes.
     *
     * @return this {@link ConfirmationScreen}'s confirmation keycodes
     */
    public List<Integer> getConfirmCodes()
    {
        return confirmCodes;
    }

    /**
     * Returns this {@link ConfirmationScreen}'s denial keycodes.
     *
     * @return this {@link ConfirmationScreen}'s denial keycodes
     */
    public List<Integer> getDenyCodes()
    {
        return denyCodes;
    }

    /**
     * Returns this {@link ConfirmationScreen}'s cancellation keycodes.
     *
     * @return this {@link ConfirmationScreen}'s cancellation keycodes
     */
    public List<Integer> getCancelCodes()
    {
        return cancelCodes;
    }

    /**
     * Performs this {@link ConfirmationScreen}'s confirmation actions. By default, performs the same actions as {@link
     * #onCancel()}.
     *
     * @return the new {@link Screen} to display
     */
    public Screen onConfirm()
    {
        return onCancel();
    }

    /**
     * Performs this {@link ConfirmationScreen}'s denial actions. By default, performs the same actions as {@link
     * #onCancel()}.
     *
     * @return the new {@link Screen} to display
     */
    public Screen onDeny()
    {
        return onCancel();
    }

    /**
     * Performs this {@link ConfirmationScreen}'s cancellation actions. By default, returns null.
     *
     * @return the new {@link Screen} to display
     */
    public Screen onCancel()
    {
        return null;
    }
}
