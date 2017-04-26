package core.display.screens;

import core.display.Border;
import core.display.ColorSet;
import core.display.ColorString;
import java.awt.event.KeyEvent;
import core.display.Display;
import core.display.Line;
import core.display.PopupWindow;
import core.display.Window;
import java.awt.Color;
import java.util.ArrayList;

/** A sample Screen used as the main screen of an application. */
public class MasterScreen extends Screen
{
    /** A sample gameplay mechanic. */
    private int score;
    /** The Window through which output is displayed. */
    private Window output;
    /**
     * A Screen that will be processed and displayed in front of this Screen.
     */
    private Screen subscreen;
    
    /**
     * Creates a MasterScreen on the provided Display.
     * @param d the Display on which the MasterScreen will be shown
     */
    public MasterScreen(Display d)
    {
        super(d);
        score = 0;
        subscreen = null;
        output = new core.display.Window(display, 10, display.getCharHeight() / 4,
                new Border(2), new ArrayList<>());
        
        output.add("Earn points with Enter.");
        output.add("Press Ctrl+T to toggle the terminal.");
        output.add("Press Ctrl+M to toggle the map.");
        output.add("Press Escape to quit.");
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
                if (key.isShiftDown() && score > 0)
                {
                    score = (int) Math.pow(score, 2);
                    output.addSeparator(new Line(true, 2, 1));
                    output.setSeparator(0, new Line(false, 2, 1, 1));
                    output.add(new ColorString("The score has been hacked!",
                            Color.RED));
                }
                else
                {
                    score++;
                }
                
                ColorSet scoreOutput = ColorSet.toColorSet(new ColorString[]
                    {new ColorString("Your Score: "),
                    new ColorString(Integer.toString(score), Color.YELLOW)});
                
                if (output.getContents().size() >= 5)
                {
                    output.set(5, scoreOutput);
                }
                else
                {
                    output.addSeparator(new Line(false, 2, 1));
                    output.add(scoreOutput);
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
                    subscreen = new Terminal(new PopupWindow(display,
                            3 * (display.getCharHeight() / 4),
                            new Border(1, Color.RED, Color.BLUE)),
                            "Your Input: ", display.getCharWidth());
                    break;
                }
                // Skip case if control was not pressed
            case KeyEvent.VK_M:
                if (key.isControlDown())
                {
                    subscreen = new MapScreen(display);
                    break;
                }
                // Skip case if control was not pressed
        }
        
        return this;
    }
}
