package core.display.screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
import core.display.Menu;
import map.Point;

public class ExampleScreen implements Screen
{
    private int score = 0;
    private StringBuilder input = new StringBuilder("");
    
    @Override
    public void displayOutput(AsciiPanel terminal)
    {
        if (score == 0 && input.length() == 0)
        {
            String instructions = "Earn points with Enter and type messages "
                    + "with your keyboard."; // 60 chars
            String quit = "Press Escape to quit.";
            String[] text = {instructions, quit};
            
            Menu.printCenterBoxed(terminal, text,
                    terminal.getHeightInCharacters() / 2);
            
            /*
            int line1height = terminal.getHeightInCharacters() / 2;
            int line2height = line1height + 1;
            
            double length = Math.max(instructions.length(), quit.length());
            int center = terminal.getWidthInCharacters() / 2;
            int offsetLeft, offsetRight;
            double offset = length / 2.0;
            if (offset % 1.0 == 0.0)
            {
                offsetLeft = (int) length / 2;
                offsetRight = offsetLeft;
            }
            else
            {
                offsetLeft = (int) length / 2 + 1;
                offsetRight = (int) length / 2;
            }
            
            terminal.writeCenter(instructions, line1height);
            terminal.writeCenter(quit, line2height);
            Menu.draw(terminal, new Point(center - offsetLeft, line1height - 1),
                    new Point(center + offsetRight, line2height + 1), 1);
            */
        }
        else
        {
            if (input.length() >= terminal.getWidthInCharacters())
            {
                input.delete(terminal.getWidthInCharacters(),
                        input.length());
            }
            
            String[] text = {"Your Score: " + score, input.toString()};
            Menu.printCenterBoxed(terminal, text,
                    terminal.getHeightInCharacters() / 2);
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
