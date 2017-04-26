package core.display.screens;

import core.display.Display;
import java.awt.event.KeyEvent;
import map.Map;
import map.TileType;

/**
 * 
 */
public class MapScreen extends Screen
{
    public static final int SMOOTHNESS = 8;
    
    private boolean showSmoothness;
    private int smoothness;
    private Map map;

    public MapScreen(Display display)
    {
        super(display);
        smoothness = SMOOTHNESS;
        showSmoothness = false;
        updateMap();
    }
    
    @Override
    public void displayOutput()
    {
        map.display(display, new core.Point(0, 0));
        if (showSmoothness)
            display.write("Smoothness: " + smoothness, new core.Point(0, 0));
    }

    @Override
    public Screen processInput(KeyEvent key)
    {
        switch (key.getKeyCode())
        {
            case KeyEvent.VK_UP:
                smoothness++;
                updateMap();
                break;
            case KeyEvent.VK_DOWN:
                smoothness = Math.max(smoothness - 1, 0);
                updateMap();
                break;
            case KeyEvent.VK_S:
                showSmoothness = !showSmoothness;
                break;
            case KeyEvent.VK_ENTER:
                updateMap();
                break;
            case KeyEvent.VK_ESCAPE:
                return null;
        }
        
        return this;
    }
    
    private void updateMap()
    {
        map = Map.generateCave(Math.min(display.getCharHeight(),
                display.getCharWidth()), smoothness,
                TileType.FLOOR, TileType.WALL);
    }
}