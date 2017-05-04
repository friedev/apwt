package corelib.display.screens;

import corelib.display.Display;
import java.awt.event.KeyEvent;

/**
 * 
 */
public abstract class ConfirmationScreen extends Screen
{
    public ConfirmationScreen(Display display)
        {super(display);}
    
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
    
    public abstract Screen onConfirm();
    public abstract Screen onCancel();
}
