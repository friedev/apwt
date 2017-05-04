package corelib.display.screens;

import corelib.display.Display;
import corelib.display.windows.Menu;
import java.awt.event.KeyEvent;

/**
 * 
 */
public class MenuScreen extends ConfirmationScreen
{
    private Menu menu;
    
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
        
        return checkConfirmation(key);
    }

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