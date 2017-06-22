package boldorf.apwt.windows;

import boldorf.util.Utility;
import boldorf.apwt.glyphs.ColorString;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import squidpony.squidgrid.Direction;

/**
 * A {@link Window} with navigable contents.
 * @param <WindowType> the type of {@link Window} used by the {@link Menu}
 */
public abstract class Menu<WindowType extends Window>
{
    /** The {@link Window} through which the {@link Menu} will be displayed. */
    private WindowType window;
    
    /** The index of the currently selected item. */
    private int selection;
    
    /**
     * Designates which indices of the {@link Window} can be traversed as a
     * menu. If empty or null, all of the window may be navigated. Otherwise,
     * only indices specified in this list may be selected.
     */
    private List<Integer> restrictions;
    
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
        restrictions = new ArrayList<>();
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
     * Returns a list of allowed selection indices in the menu.
     * @return a list of allowed selection indices in the menu
     */
    public List<Integer> getRestrictions()
        {return restrictions;}
    
    /**
     * Returns true if the selection range of this {@link Menu} is restricted.
     * @return true if the selection range of this {@link Menu} is restricted
     */
    public boolean hasRestrictions()
        {return restrictions != null && !restrictions.isEmpty();}
    
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
    public ColorString getSelection()
        {return window.getContents().get(selection);}
    
    /**
     * Modifies the selection index by the provided amount until it is either
     * out of bounds, or not at a separator or restricted value.
     * @param change the amount by which to modify the selection index at each
     * iteration
     * @return the final value of the selection index
     */
    private int bypassRestrictions(int change)
    {
        if (change == 0)
            return selection;
        
        int nextSelection = selection;
        boolean loop;
        do
        {
            if (contentsContains(nextSelection + change))
            {
                nextSelection += change;
            }
            else
            {
                nextSelection = change > 0 ?
                        0 : getWindow().getContents().size() - 1;
            }
            
            loop = getWindow().getContents().get(nextSelection) == null
                    || (hasRestrictions() &&
                    !restrictions.contains(nextSelection));
            
        } while (loop);
        
        return nextSelection;
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
        return change == 0 ?
                false : setSelectionIndex(bypassRestrictions(change));
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
        Direction direction = Utility.keyToDirection(key);
        
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
            case KeyEvent.VK_UP:   return decrementSelection();
            case KeyEvent.VK_DOWN: return incrementSelection();
        }
        
        return false;
    }
}