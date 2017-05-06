package corelib.display.windows;

import corelib.display.Display;
import java.awt.event.KeyEvent;
import squidpony.squidgrid.Direction;

/**
 * A {@link Window} with navigable contents.
 * @param <Content> the type of content displayed by the {@link Menu}'s
 * {@link Window}
 * @param <WindowType> the type of {@link Window} used by the {@link Menu}
 */
public abstract class Menu<Content extends CharSequence,
        WindowType extends Window<Content>>
{
    /** The {@link Window} through which the {@link Menu} will be displayed. */
    private WindowType window;
    
    /** The index of the currently selected item. */
    private int selection;
    
    /**
     * Creates a {@link Menu} from a {@link Window} and an initial selection.
     * @param window the {@link Menu}'s {@link Window}
     * @param initialSelection the item that the {@link Menu} will have selected
     * initially
     */
    public Menu(WindowType window, int initialSelection)
    {
        this.window = window;
        selection = Math.max(0, Math.min(window.getContents().size() - 1,
                initialSelection));
    }
    
    /**
     * Creates a {@link Menu} from a {@link Window}, starting with the first
     * item selected.
     * @param window the {@link Menu}'s {@link Window}
     */
    public Menu(WindowType window)
        {this(window, 0);}
    
    /**
     * Returns the {@link Menu}'s {@link Window}.
     * @return the {@link Menu}'s {@link Window}
     */
    public WindowType getWindow()
        {return window;}
    
    /**
     * Returns the {@link Menu}'s {@link Window}, modified to show which item is
     * selected.
     * @return the {@link Menu}'s {@link Window}, modified to show which item is
     * selected
     */
    public abstract WindowType getOutput();
    
    /**
     * Returns the index of the currently selected item.
     * @return the index of the currently selected item
     */
    public int getSelectionIndex()
        {return selection;}
    
    /**
     * Returns true if the given value is a valid index of the {@link Window}'s
     * contents.
     * @param index the value to check for validity as an index
     * @return true if there is an item {@link Window}'s contents at the given
     * index
     */
    private boolean contentsContains(int index)
        {return index < window.getContents().size() && index >= 0;}
    
    /**
     * Sets the current selection index to the provided value.
     * @param index the new value of the selection index
     * @return true if the selection index was updated
     */
    public boolean setSelectionIndex(int index)
    {
        if (contentsContains(index))
        {
            selection = index;
            return true;
        }
        
        return false;
    }
    
    /**
     * Gets the currently selected item.
     * @return the currently selected item
     */
    public Content getSelection()
        {return window.getContents().get(selection);}
    
    /**
     * Modifies the selection index by the provided amount until it is either
     * out of bounds or is not at a separator.
     * @param change the amount by which to modify the selection index at each
     * iteration
     * @return the final value of the selection index
     */
    private int bypassSeparators(int change)
    {
        if (change == 0)
            return selection;
        
        int nextSelection = selection;
        while (contentsContains(nextSelection + change) &&
                getWindow().getContents().get(nextSelection + change) == null)
            nextSelection += change;
        return nextSelection + change;
    }
    
    /**
     * Increments the selection index by the given amount, wrapping to the front
     * or back if bounds are exceeded. Intermediate separators will not be
     * selected.
     * @param change the amount by which to modify the selection index at each
     * iteration
     * @return true if the selection index was updated
     */
    private boolean select(int change)
    {
        if (change == 0)
            return false;
        
        int nextSelection = bypassSeparators(change);
        int fallback = change > 0 ? 0 : getWindow().getContents().size() - 1;
        return contentsContains(nextSelection) ?
                setSelectionIndex(nextSelection) : setSelectionIndex(fallback);
    }
    
    /**
     * Increments the selection index by one.
     * @return true if the selection index was incremented
     */
    public boolean incrementSelection()
        {return select(1);}
    
    /**
     * Decrements the selection index by one.
     * @return true if the selection index was decremented
     */
    public boolean decrementSelection()
        {return select(-1);}
    
    /**
     * Modifies the selection index by the direction corresponding to the given
     * KeyEvent.
     * @param key the KeyEvent to process
     * @return true if the selection index was updated
     */
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
    
    /**
     * Increments the selection if the up arrow is pressed, and decrements it if
     * the down arrow is pressed.
     * @param key the KeyEvent to process
     * @return true if the selection index was updated
     */
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