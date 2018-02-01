package maugrift.apwt.screens;

import maugrift.apwt.display.AsciiPanelDisplay;

import java.awt.event.KeyEvent;

/**
 * An abstract class through which input is processed and output is displayed. A single application may have many {@link
 * Screen Screens}.
 *
 * @author Maugrift
 */
public abstract class Screen
{
    /**
     * The {@link AsciiPanelDisplay} through which output is shown.
     */
    private AsciiPanelDisplay display;

    /**
     * Creates a new {@link Screen} on the provided {@link AsciiPanelDisplay}.
     *
     * @param display the {@link AsciiPanelDisplay} on which the {@link Screen} will be shown
     */
    public Screen(AsciiPanelDisplay display)
    {
        this.display = display;
    }

    /**
     * Returns the {@link #display Display} used by the {@link Screen}.
     *
     * @return the {@link AsciiPanelDisplay} used by the {@link Screen}
     */
    public AsciiPanelDisplay getDisplay()
    {
        return display;
    }

    /**
     * Sets the {@link #display Display} to the given {@link AsciiPanelDisplay}.
     *
     * @param d the new {@link AsciiPanelDisplay} for the {@link Screen} to use
     */
    public void setDisplay(AsciiPanelDisplay d)
    {
        display = d;
    }

    /**
     * Displays the {@link Screen}'s output to its {@link AsciiPanelDisplay}.
     */
    public abstract void displayOutput();

    /**
     * Processes the KeyEvent given.
     *
     * @param key the KeyEvent to process
     * @return the new {@link Screen} to display
     */
    public abstract Screen processInput(KeyEvent key);
}
