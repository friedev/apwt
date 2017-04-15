package core.display.screens;

import java.awt.event.KeyEvent;
import core.display.Display;
import java.util.List;

/**
 * 
 */
public class MasterScreen extends Screen
{
    private int score;
    private List<Window> windows;
    
    public MasterScreen(Display d)
    {
        super(d);
        score   = 0;
        windows = new java.util.ArrayList<>();
        windows.add(new Window(display, 0, display.getCharHeight() / 2, true,
                true));
        windows.get(1).getContents().addAll(java.util.Arrays.asList(new String[]
           {"Earn points with Enter.", "Press Ctrl+T to toggle the terminal.",
            "Press Escape to quit.", "Your Score: " + score}));
    }
    
    @Override
    public void displayOutput()
    {
        if (windows == null || windows.isEmpty())
            return;
        
        for (Window window: windows)
            window.displayOutput();
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
                    if (windows.get(1) instanceof Terminal)
                    {
                        windows.remove(1);
                    }
                    else
                    {
                        windows.add(1, new Terminal(display, 0,
                                3 * (display.getCharHeight() / 4), true, true,
                                "Your Input: ", display.getCharWidth()));
                    }
                    break;
                }
                // Go to default case if control was not pressed
            default:
                // TODO go to windows for input processing
                break;
        }
        
        return this;
    }
}
