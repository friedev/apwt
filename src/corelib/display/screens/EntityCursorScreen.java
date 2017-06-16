package corelib.display.screens;

import corelib.Utility;
import corelib.display.Display;
import corelib.map.Entity;
import java.awt.event.KeyEvent;
import squidpony.squidgrid.Direction;
import squidpony.squidmath.Coord;

/**
 * A {@link CursorScreen} that controls an {@link corelib.map.Entity} acting as
 * a cursor. This can be used to select tiles on a
 * {@link corelib.map.TileMap map}.
 */
public abstract class EntityCursorScreen extends ConfirmationScreen
        implements CursorScreen
{
    /** The {@link corelib.map.Entity} acting as a cursor. */
    private Entity cursor;
    
    /**
     * Creates a new {@link CursorScreen} on the given
     * {@link corelib.display.Display}, with the given
     * {@link corelib.map.Entity} as a cursor.
     * @param display the {@link corelib.display.Display} on which the
     * {@link Screen} will be shown
     * @param cursor the {@link corelib.map.Entity} to use as a cursor
     */
    public EntityCursorScreen(Display display, Entity cursor)
    {
        super(display);
        this.cursor = cursor;
        cursor.addToMap();
    }

    @Override
    public void displayOutput()
        {return;}

    @Override
    public Screen processInput(KeyEvent key)
    {
        Direction direction = Utility.keyToDirection(key);
        if (direction != null)
        {
            cursor.changeLocation(direction);
            return this;
        }
        
        return super.processInput(key);
    }
    
    /**
     * Returns the {@link EntityCursorScreen Screen's} {@link #cursor cursor}.
     * @return the {@link EntityCursorScreen Screen's} {@link #cursor cursor}
     */
    @Override
    public Coord getCursor()
        {return cursor.getLocation();}
    
    @Override
    public Screen onConfirm()
    {
        cursor.removeFromMap();
        return null;
    }
    
    @Override
    public Screen onCancel()
    {
        cursor.removeFromMap();
        cursor = null;
        return null;
    }
}