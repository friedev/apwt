package boldorf.apwt.screens;

import boldorf.apwt.Display;
import java.awt.event.KeyEvent;

/**
 * An abstract class through which input is processed and output is displayed. A
 * single application may have many {@link Screen Screens}.
 */
public abstract class Screen
{
    /** *  The {@link boldorf.apwt.Display} through which output is shown. */
    private Display display;
    
    /**
     * Creates a new {@link Screen} on the provided
     * {@link boldorf.apwt.Display}.
     * @param display the {@link boldorf.apwt.Display} on which the
     * {@link Screen} will be shown
     */
    public Screen(Display display)
        {this.display = display;}
    
    /**
     * Returns the {@link #display Display} used by the {@link Screen}.
     * @return the {@link boldorf.apwt.Display} used by the {@link Screen}
     */
    public Display getDisplay()
        {return display;}
    
    /**
     * Sets the {@link #display Display} to the given
     * {@link boldorf.apwt.Display}.
     * @param d the new {@link boldorf.apwt.Display} for the {@link Screen}
     * to use
     */
    public void setDisplay(Display d)
        {display = d;}
    
    /**
     * Displays the {@link Screen}'s output to its
     * {@link boldorf.apwt.Display}.
     */
    public abstract void displayOutput();   
    
    /**
     * Processes the KeyEvent given.
     * @param key the KeyEvent to process
     * @return the new {@link Screen} to display
     */
    public abstract Screen processInput(KeyEvent key);
}
