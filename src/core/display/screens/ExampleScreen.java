package core.display.screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
import core.display.Menu;

public class ExampleScreen implements Screen
{
    private int score;
    private Screen subscreen;
    
    public ExampleScreen()
    {
        score = 0;
        subscreen = null;
    }
    
    @Override
    public void displayOutput(AsciiPanel terminal)
    {
        String[] text = {"Earn points with Enter.",
            "Press Control+T to toggle the terminal.",
            "Press Escape to quit.", "Your Score: " + score};
        Menu.printCenterBoxed(terminal, text,
                terminal.getHeightInCharacters() / 2);
        
        if (subscreen != null)
            subscreen.displayOutput(terminal);
    }

    @Override
    public Screen processInput(KeyEvent key)
    {
        switch (key.getKeyCode())
        {
            case KeyEvent.VK_ENTER:
                score++;
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
            case KeyEvent.VK_T:
                if (key.isControlDown())
                {
                    if (subscreen instanceof TerminalScreen)
                        subscreen = null;
                    else
                        subscreen = new TerminalScreen();
                }
                break;
            default:
                if (subscreen != null)
                    subscreen.processInput(key);
                break;
        }
        
        return this;
    }
}
