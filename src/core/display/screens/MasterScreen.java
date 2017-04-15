package core.display.screens;

import core.display.Border;
import core.display.Window;
import java.awt.event.KeyEvent;
import core.display.Display;

/**
 * 
 */
public class MasterScreen extends Screen
{
    private int    score;
    private Window output;
    private Screen subscreen;
    
    public MasterScreen(Display d)
    {
        super(d);
        score     = 0;
        subscreen = null;
        
        output = new Window(display, 0, display.getCharHeight() / 4, true,
                new Border(2), new core.display.LineBorder(true, 2, 1));
        output.getContents().addAll(java.util.Arrays.asList(new String[]
           {"Earn points with Enter.", "Press Ctrl+T to toggle the terminal.",
            "Press Escape to quit."}));
    }
    
    @Override
    public void displayOutput()
    {
        if (output != null)
            output.displayOutput();
        
        if (subscreen != null)
            subscreen.displayOutput();
    }

    @Override
    public Screen processInput(KeyEvent key)
    {
        if (subscreen != null)
        {
            subscreen = subscreen.processInput(key);
            return this;
        }
        
        switch (key.getKeyCode())
        {
            case KeyEvent.VK_ENTER:
                if (key.isShiftDown())
                    score = (int) Math.pow(score, 2);
                else
                    score++;
                
                String scoreString = "Your Score: " + score;
                if (output.getContents().size() >= 4)
                {
                    output.getContents().set(4, scoreString);
                }
                else
                {
                    output.getContents().add(null);
                    output.getContents().add(scoreString);
                }
                break;
            case KeyEvent.VK_UP:
                output.setY(output.getY() - 1);
                break;
            case KeyEvent.VK_DOWN:
                output.setY(output.getY() + 1);
                break;
            case KeyEvent.VK_LEFT:
                output.setX(output.getX() - 1);
                break;
            case KeyEvent.VK_RIGHT:
                output.setX(output.getX() + 1);
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
            case KeyEvent.VK_T:
                if (key.isControlDown())
                {
                    if (subscreen == null)
                    {
                        subscreen = new Terminal(new Window(display, 0,
                                3 * (display.getCharHeight() / 4), true,
                                new Border(1)), "Your Input: ",
                                display.getCharWidth());
                    }
                    else
                    {
                        subscreen = null;
                    }
                    
                    break;
                }
                // Skip case if control was not pressed
        }
        
        return this;
    }
}
