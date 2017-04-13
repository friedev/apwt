package core.display;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;

public class ExampleScreen implements Screen
{
    private int score = 0;
    private StringBuilder input = new StringBuilder("");
    
    @Override
    public void displayOutput(AsciiPanel terminal)
    {
        if (score == 0 && input.length() == 0)
        {
            terminal.writeCenter("Earn points with Enter and type messages "
                    + "with your keyboard.",
                    terminal.getHeightInCharacters() / 2);
            terminal.writeCenter("Press Escape to quit.",
                    terminal.getHeightInCharacters() / 2 + 1);
        }
        else
        {
            terminal.writeCenter("Your Score: " + score,
                    terminal.getHeightInCharacters() / 2);
            
            if (input.length() >= terminal.getWidthInCharacters())
            {
                input.delete(terminal.getWidthInCharacters(),
                        input.length());
            }
            
            terminal.writeCenter(input.toString(),
                    terminal.getHeightInCharacters() / 2 + 1);
        }
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
