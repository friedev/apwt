package corelib.display.windows;

import corelib.display.Display;
import java.awt.event.KeyEvent;
import squidpony.squidgrid.Direction;

/**
 * 
 * @param <Content>
 * @param <WindowType>
 */
public abstract class Menu<Content extends CharSequence,
        WindowType extends Window<Content>>
{
    private WindowType window;
    private int selection;
    
    public Menu(WindowType window, int initialSelection)
    {
        this.window = window;
        selection = Math.max(0, Math.min(window.contents.size() - 1,
                initialSelection));
    }
    
    public Menu(WindowType window)
        {this(window, 0);}
    
    public WindowType getWindow()
        {return window;}
    
    public abstract WindowType getOutput();
    
    public int getSelectionIndex()
        {return selection;}
    
    private boolean contentsContains(int index)
        {return index < window.contents.size() && index >= 0;}
    
    public boolean setSelectionIndex(int index)
    {
        if (contentsContains(index))
        {
            selection = index;
            return true;
        }
        
        return false;
    }
    
    public Content getSelection()
        {return window.contents.get(selection);}
    
    private int bypassSeparators(int change)
    {
        if (change == 0)
            return selection;
        
        int nextSelection = selection;
        while (contentsContains(nextSelection + change) &&
                getWindow().contents.get(nextSelection + change) == null)
            nextSelection++;
        return nextSelection + change;
    }
    
    private boolean select(int change)
    {
        if (change == 0)
            return false;
        
        int nextSelection = bypassSeparators(change);
        int fallback = change > 0 ? 0 : getWindow().contents.size() - 1;
        return contentsContains(nextSelection) ?
                setSelectionIndex(nextSelection) : setSelectionIndex(fallback);
    }
    
    public boolean incrementSelection()
        {return select(1);}
    
    public boolean decrementSelection()
        {return select(-1);}
    
    public boolean updateSelection(KeyEvent key)
    {
        Direction direction = Display.keyToDirection(key);
        
        if (direction == null)
            return false;
        
        if (direction.hasUp())
            return decrementSelection();
        
        if (direction.hasDown())
            return incrementSelection();
        
        return false;
    }
    
    public boolean updateSelectionRestricted(KeyEvent key)
    {
        switch (key.getKeyCode())
        {
            case KeyEvent.VK_UP:   return incrementSelection();
            case KeyEvent.VK_DOWN: return decrementSelection();
        }
        
        return false;
    }
}