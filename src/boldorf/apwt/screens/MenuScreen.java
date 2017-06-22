package boldorf.apwt.screens;

import boldorf.apwt.windows.Menu;
import java.awt.event.KeyEvent;

/**
 * A {@link Screen} used for traversing a {@link boldorf.apwt.windows.Menu}
 * and selecting items on it.
 * @param <MenuType> the type of {@link Menu} used in the {@link Screen}
 */
public abstract class MenuScreen<MenuType extends Menu>
        extends ConfirmationScreen
{
    /**
     * The {@link boldorf.apwt.windows.Menu} displayed and navigated by the
     * {@link MenuScreen}.
     */
    private MenuType menu;
    
    /**
     * Creates a {@link MenuScreen} on the given {@link boldorf.apwt.Display}
     * for the given {@link boldorf.apwt.windows.Menu}.
     * @param menu the {@link boldorf.apwt.windows.Menu} to create a
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
}