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
    public static final double VARIATION_EXPONENT = 2.5;
    
    private boolean showUI;
    private int smoothness;
    private double variationExponent;
    private Map map;
    private int[][] heightmap;

    public MapScreen(Display display)
    {
        super(display);
        smoothness = SMOOTHNESS;
        variationExponent = VARIATION_EXPONENT;
        showUI = false;
        updateMap(false);
    }
    
    @Override
    public void displayOutput()
    {
        if (heightmap == null)
        {
            map.display(display, new core.Point(0, 0));
            if (showUI)
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
            
            if (showUI)
            {
                display.write("Variation Exponent: " + variationExponent,
                        new core.Point(0, 0));
            }
        }
    }

    @Override
    public Screen processInput(KeyEvent key)
    {
        switch (key.getKeyCode())
        {
            case KeyEvent.VK_UP:
                if (heightmap == null)
                    smoothness++;
                else
                    variationExponent += 0.1;
                updateMap();
                break;
            case KeyEvent.VK_DOWN:
                if (heightmap == null)
                    Math.max(smoothness - 1, 0);
                else
                    variationExponent -= 0.1;
                updateMap();
                break;
            case KeyEvent.VK_S:
                showUI = !showUI;
                break;
            case KeyEvent.VK_H:
                updateMap(heightmap == null);
                break;
            case KeyEvent.VK_ENTER:
                updateMap();
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
            heightmap = Map.generateHeightmap(Math.min(display.getCharHeight(),
                    display.getCharWidth()), 128, variationExponent);
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
    
    private void updateMap()
        {updateMap(heightmap != null);}
}