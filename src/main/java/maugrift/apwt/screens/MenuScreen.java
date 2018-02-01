package maugrift.apwt.screens;

import maugrift.apwt.display.AsciiPanelDisplay;
import maugrift.apwt.windows.Menu;

import java.awt.event.KeyEvent;

/**
 * A {@link Screen} used for traversing a {@link maugrift.apwt.windows.Menu} and selecting items on it.
 *
 * @param <MenuType> the type of {@link Menu} used in the {@link Screen}
 * @author Maugrift
 */
public abstract class MenuScreen<MenuType extends Menu> extends ConfirmationScreen
{
    /**
     * The {@link maugrift.apwt.windows.Menu} displayed and navigated by the {@link MenuScreen}.
     */
    private MenuType menu;

    /**
     * Creates a {@link MenuScreen} on the given {@link AsciiPanelDisplay} for the given {@link
     * maugrift.apwt.windows.Menu}.
     *
     * @param menu the {@link maugrift.apwt.windows.Menu} to create a {@link MenuScreen} for
     */
    public MenuScreen(MenuType menu)
    {
        super(menu.getWindow().getDisplay());
        this.menu = menu;
    }

    @Override
    public void displayOutput()
    {
        menu.getOutput().display();
    }

    @Override
    public Screen processInput(KeyEvent key)
    {
        switch (key.getKeyCode())
        {
            case KeyEvent.VK_UP:
                if (menu.select(-1))
                {
                    return this;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (menu.select(1))
                {
                    return this;
                }
                break;
        }

        return super.processInput(key);
    }

    /**
     * Returns the {@link MenuScreen Screen's} {@link #menu menu}.
     *
     * @return the {@link MenuScreen Screen's} {@link #menu menu}
     */
    public MenuType getMenu()
    {
        return menu;
    }
}