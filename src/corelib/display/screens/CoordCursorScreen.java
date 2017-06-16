package corelib.display.screens;

import corelib.Utility;
import corelib.display.Display;
import corelib.display.glyphs.ColorChar;
import java.awt.event.KeyEvent;
import squidpony.squidgrid.Direction;
import squidpony.squidmath.Coord;

/**
 * A {@link CoordScreen} designed to select locations on the
 * {@link corelib.display.Display}.
 */
public abstract class CoordCursorScreen extends ConfirmationScreen
        implements CursorScreen
{
    private Coord cursor;
    private ColorChar cursorOutput;
    
    public CoordCursorScreen(Display display, Coord cursor,
            ColorChar cursorOutput)
    {
        super(display);
        this.cursor = cursor;
        this.cursorOutput = cursorOutput;
    }
    
    public CoordCursorScreen(Display display, Coord cursor)
        {this(display, cursor, null);}

    @Override
    public void displayOutput()
    {
        if (getDisplay().contains(cursor) && cursorOutput != null)
            getDisplay().write(cursor, cursorOutput);
    }

    @Override
    public Screen processInput(KeyEvent key)
    {
        Direction direction = Utility.keyToDirection(key);
        if (direction != null)
        {
            cursor = cursor.translate(direction);
            return this;
        }
        
        return super.processInput(key);
    }

    @Override
    public Coord getCursor()
        {return cursor;}
}