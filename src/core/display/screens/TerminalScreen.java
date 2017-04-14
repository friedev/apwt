package core.display.screens;

import asciiPanel.AsciiPanel;
import core.display.Menu;
import java.awt.event.KeyEvent;

/**
 * A basic input screen where the operator can type characters and delete them
 * with backspace.
 */
public class TerminalScreen implements Screen
{
    private StringBuilder input;
    
    public TerminalScreen()
    {
        input = new StringBuilder();
    }
    
    @Override
    public void displayOutput(AsciiPanel terminal)
    {
        int width  = terminal.getWidthInCharacters();
        int height = terminal.getHeightInCharacters();
        
        if (input.length() == 0)
        {
            String[] text = {"Type something! Press Escape when done."};
            
            Menu.printCenterBoxed(terminal, text, 3 * (height / 4));
        }
        else
        {
            if (input.length() >= width)
                input.delete(width, input.length());
            
            String[] text = {input.toString()};
            Menu.printCenterBoxed(terminal, text, 3 * (height / 4));
        }
    }
    
    @Override
    public Screen processInput(KeyEvent key)
    {
        switch (key.getKeyCode())
        {
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
            case KeyEvent.VK_BACK_SPACE:
                if (input.length() > 0)
                    input.deleteCharAt(input.length() - 1);
                break;
            default:
                if (Character.isDefined(key.getKeyChar()))
                    input.append(key.getKeyChar());
                break;
        }
        
        return this;
    }
}