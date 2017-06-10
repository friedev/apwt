package corelib.display.screens;

import corelib.display.Display;
import corelib.display.windows.Menu;
import java.awt.event.KeyEvent;

/**
 * A {@link Screen} used for traversing a {@link corelib.display.windows.Menu}
 * and selecting items on it.
 */
public class MenuScreen extends ConfirmationScreen
{
    /**
     * The {@link corelib.display.windows.Menu} displayed and navigated by the
     * {@link MenuScreen}.
     */
    private Menu menu;
    
    /**
     * Creates a {@link MenuScreen} on the given {@link corelib.display.Display}
     * for the given {@link corelib.display.windows.Menu}.
     * @param display the {@link corelib.display.Display} on which the
     * {@link Screen} will be shown
     * @param menu the {@link corelib.display.windows.Menu} to create a
     * {@link MenuScreen} for
     */
    public MenuScreen(Display display, Menu menu)
    {
        super(display);
        this.menu = menu;
    }

    @Override
    public void displayOutput()
        {menu.getOutput().display();}

    @Override
    public Screen processInput(KeyEvent key)
    {
        if (menu.updateSelection(key))
            return this;
        
        return super.processInput(key);
    }
    
    /**
     * Returns the {@link MenuScreen Screen's} {@link #menu menu}.
     * @return the {@link MenuScreen Screen's} {@link #menu menu}
     */
    public Menu getMenu()
        {return menu;}
    
    @Override
    public Screen onConfirm()
        {return null;}

    @Override
    public Screen onCancel()
    {
        menu = null;
        return null;
    }
}