package core.display.screens;

import core.display.ColorChar;
import core.display.ColorSet;
import core.display.Display;
import java.awt.Color;
import java.awt.event.KeyEvent;
import map.Entity;
import map.Map;
import map.TileProperty;
import map.TileType;
import squidpony.squidgrid.Direction;
import squidpony.squidmath.Coord;

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
    private Entity player;
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
        char[][] testMap = new squidpony.squidgrid.mapping.SpillWorldMap(48, 48, "Earth").generate(3);
        ColorChar[][] glyphMap = new ColorChar[testMap.length][testMap[0].length];
        for (int y = 0; y < testMap.length; y++)
        {
            for (int x = 0; x < testMap[y].length; x++)
            {
                glyphMap[y][x] = new ColorChar(testMap[y][x]);
                /*
                switch (testMap[y][x])
                {
                    case '~':
                        glyphMap[y][x].setColors(AsciiPanel.brightBlue, AsciiPanel.blue);
                        break;
                    case '%':
                        glyphMap[y][x].setColors(AsciiPanel.green, AsciiPanel.green);
                        break;
                    case 'A':
                        glyphMap[y][x].setColors(AsciiPanel.brightRed, AsciiPanel.green);
                        break;
                    case 'B':
                        glyphMap[y][x].setColors(AsciiPanel.brightYellow, AsciiPanel.green);
                        break;
                    case 'C':
                        glyphMap[y][x].setColors(AsciiPanel.brightGreen, AsciiPanel.green);
                        break;
                }
                */
            }
        }
        display.write(ColorSet.toColorSetArray(glyphMap), Coord.get(0, 0));
        
        /*
        if (heightmap == null)
        {
            display.write(ColorSet.toColorSetArray(map.toGlyphs(
                    player.getLocation(), display)), new Point(0, 0));
            
            if (showUI)
                display.write("Smoothness: " + smoothness, new Point(0, 0));
        }
        else
        {
            for (int y = 0; y < heightmap.length; y++)
                for (int x = 0; x < heightmap[y].length; x++)
                    display.write(new ColorChar(ExtChars.BLOCK,
                            new Color(heightmap[y][x], heightmap[y][x],
                                    heightmap[y][x])), new Point(y, x));
            
            if (showUI)
            {
                display.write("Variation Exponent: " + variationExponent,
                        new Point(0, 0));
            }
        }
        */
    }

    @Override
    public Screen processInput(KeyEvent key)
    {
        switch (key.getKeyCode())
        {
            case KeyEvent.VK_UP:
                if (key.isControlDown())
                {
                    if (heightmap == null)
                        smoothness++;
                    else
                        variationExponent += 0.1;
                    updateMap();
                    break;
                }
            case KeyEvent.VK_K: case KeyEvent.VK_W: case KeyEvent.VK_NUMPAD8:
                player.changeLocation(Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
                if (key.isControlDown())
                {
                    if (heightmap == null)
                        smoothness = Math.max(smoothness - 1, 0);
                    else
                        variationExponent -= 0.1;
                    updateMap();
                    break;
                }
            case KeyEvent.VK_J: case KeyEvent.VK_S: case KeyEvent.VK_NUMPAD2:
                player.changeLocation(Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT: case KeyEvent.VK_H: case KeyEvent.VK_A: 
            case KeyEvent.VK_NUMPAD4:
                player.changeLocation(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT: case KeyEvent.VK_L: case KeyEvent.VK_D:
            case KeyEvent.VK_NUMPAD6:
                player.changeLocation(Direction.RIGHT);
                break;
            case KeyEvent.VK_Y: case KeyEvent.VK_NUMPAD7:
                player.changeLocation(Direction.UP_LEFT);
                break;
            case KeyEvent.VK_U: case KeyEvent.VK_NUMPAD9:
                player.changeLocation(Direction.UP_RIGHT);
                break;
            case KeyEvent.VK_B: case KeyEvent.VK_NUMPAD1:
                player.changeLocation(Direction.DOWN_LEFT);
                break;
            case KeyEvent.VK_N: case KeyEvent.VK_NUMPAD3:
                player.changeLocation(Direction.DOWN_RIGHT);
                break;
            case KeyEvent.VK_X:
                showUI = !showUI;
                break;
            case KeyEvent.VK_M:
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
            map = Map.generateCave(301, smoothness, TileType.FLOOR,
                    TileType.WALL);
            player = new Entity("Player", map,
                    map.findTileWithProperty(TileProperty.OPEN),
                    new ColorChar('@', Color.white));
            map.addEntity(player);
            heightmap = null;
        }
    }
    
    private void updateMap()
        {updateMap(heightmap != null);}
}