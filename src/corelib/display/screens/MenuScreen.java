package corelib.display.screens;

import corelib.display.windows.Menu;
import java.awt.event.KeyEvent;

/**
 * A {@link Screen} used for traversing a {@link corelib.display.windows.Menu}
 * and selecting items on it.
 * @param <MenuType> the type of {@link Menu} used in the {@link Screen}
 */
public class MenuScreen<MenuType extends Menu> extends ConfirmationScreen
{
    /**
     * The {@link corelib.display.windows.Menu} displayed and navigated by the
     * {@link MenuScreen}.
     */
    private MenuType menu;
    
    /**
     * Creates a {@link MenuScreen} on the given {@link corelib.display.Display}
     * for the given {@link corelib.display.windows.Menu}.
     * @param menu the {@link corelib.display.windows.Menu} to create a
     * {@link MenuScreen} for
     */
    public MenuScreen(MenuType menu)
    {
        super(menu.getWindow().getDisplay());
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
    public MenuType getMenu()
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