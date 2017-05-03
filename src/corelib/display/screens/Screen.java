package corelib.display.screens;

import corelib.display.Display;

/**
 * An abstract class through which input is processed and output is displayed. A
 * single game may have many Screens.
 */
public abstract class Screen
{
    /** The Display through which output is shown. */
    protected Display display;
    
    /**
     * Creates a new Screen on the provided Display.
     * @param display the Display on which the Screen will be shown
     */
    public Screen(Display display)
        {this.display = display;}
    
    /**
     * Returns the Display used by the Screen.
     * @return the Display used by the Screen
     */
    public Display getDisplay()
        {return display;}
    
    /**
     * Sets the Display to the given Display.
     * @param d the new Display for the Screen to use
     */
    public void setDisplay(Display d) {display = d;}
    
    /** Displays the Screen's output to its Display. */
    public abstract void displayOutput();   
    
    /**
     * Processes the KeyEvent given.
     * @param key the KeyEvent to process
     * @return the new Screen to display
     */
    public abstract Screen processInput(java.awt.event.KeyEvent key);
}
