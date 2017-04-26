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
    private int[][] heightmap;

    public MapScreen(Display display)
    {
        super(display);
        smoothness = SMOOTHNESS;
        showSmoothness = false;
        updateMap(false);
    }
    
    @Override
    public void displayOutput()
    {
        if (heightmap == null)
        {
            map.display(display, new core.Point(0, 0));
            if (showSmoothness)
            {
                display.write("Smoothness: " + smoothness,
                        new core.Point(0, 0));
            }
        }
        else
        {
            for (int y = 0; y < heightmap.length; y++)
                for (int x = 0; x < heightmap[y].length; x++)
                    display.write(new core.display.ColorChar(
                            core.display.ExtChars.BLOCK,
                            new java.awt.Color(heightmap[y][x], heightmap[y][x],
                                    heightmap[y][x])), new core.Point(y, x));
        }
    }

    @Override
    public Screen processInput(KeyEvent key)
    {
        switch (key.getKeyCode())
        {
            case KeyEvent.VK_UP:
                smoothness++;
                updateMap(false);
                break;
            case KeyEvent.VK_DOWN:
                smoothness = Math.max(smoothness - 1, 0);
                updateMap(false);
                break;
            case KeyEvent.VK_S:
                showSmoothness = !showSmoothness;
                break;
            case KeyEvent.VK_H:
                updateMap(heightmap == null);
                break;
            case KeyEvent.VK_ENTER:
                updateMap(heightmap != null);
                break;
            case KeyEvent.VK_ESCAPE:
                return null;
        }
        
        return this;
    }
    
    private void updateMap(boolean height)
    {
        if (height)
        {
            heightmap = Map.generateHeightmap(48, 128, 2.5);
            map = null;
        }
        else
        {
            map = Map.generateCave(Math.min(display.getCharHeight(),
                    display.getCharWidth()), smoothness,
                    TileType.FLOOR, TileType.WALL);
            heightmap = null;
        }
    }
}