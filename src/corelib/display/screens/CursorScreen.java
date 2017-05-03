package corelib.display.screens;

import corelib.display.Display;
import corelib.map.Cursor;
import java.awt.event.KeyEvent;
import squidpony.squidgrid.Direction;
import squidpony.squidmath.Coord;

/**
 * 
 */
public class CursorScreen extends Screen
{
    private Cursor cursor;
    
    public CursorScreen(Display display, Cursor cursor, Coord offset)
    {
        super(display);
        this.cursor = cursor;
        cursor.addToMap();
    }
    
    public CursorScreen(Display display, Cursor cursor)
        {this(display, cursor, Coord.get(0, 0));}

    @Override
    public void displayOutput()
        {return;}

    @Override
    public Screen processInput(KeyEvent key)
    {
        Direction direction = Display.keyToDirection(key);
        if (direction != null)
        {
            cursor.changeLocation(direction);
            return this;
        }
        
        switch (key.getKeyCode())
        {
            case KeyEvent.VK_ENTER:
                return onConfirm();
            case KeyEvent.VK_ESCAPE:
                return onCancel();
        }
        
        return this;
    }
    
    public Cursor getCursor()
        {return cursor;}
    
    public Screen onConfirm()
    {
        cursor.removeFromMap();
        return null;
    }
    
    public Screen onCancel()
    {
        cursor.removeFromMap();
        cursor = null;
        return null;
    }
}