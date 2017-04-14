package core.display.screens;

import java.awt.event.KeyEvent;
import core.display.Display;
import core.display.Menu;

public class ExampleScreen extends Screen
{
    private int score;
    private Screen subscreen;
    
    public ExampleScreen(Display d)
    {
        super(d);
        score     = 0;
        subscreen = null;
    }
    
    @Override
    public void displayOutput()
    {
        String[] text = {"Earn points with Enter.",
            "Press Ctrl+T to toggle the terminal.",
            "Press Escape to quit.", "Your Score: " + score};
        Menu.printCenterBoxed(display, text, display.getCharHeight() / 2, 2);
        
        if (subscreen != null)
            subscreen.displayOutput();
    }

    @Override
    public Screen processInput(KeyEvent key)
    {
        switch (key.getKeyCode())
        {
            case KeyEvent.VK_ENTER:
                if (key.isShiftDown())
                    score = (int) Math.pow(score, 2);
                else
                    score++;
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
            case KeyEvent.VK_T:
                if (key.isControlDown())
                {
                    if (subscreen instanceof Terminal)
                    {
                        subscreen = null;
                    }
                    else
                    {
                        subscreen = new Terminal(display, 0,
                                3 * (display.getCharHeight() / 4),
                                "Your Input: ", display.getCharWidth(), true,
                                true);
                    }
                    break;
                }
                // Go to default case if control was not pressed
            default:
                if (subscreen != null)
                    subscreen.processInput(key);
                break;
        }
        
        return this;
    }
}
