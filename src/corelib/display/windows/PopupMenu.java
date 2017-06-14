package corelib.display.windows;

import corelib.display.glyphs.ColorString;
import java.awt.Color;

/** A {@link Menu} displayed on a {@link PopupWindow}. */
public class PopupMenu extends ColoredMenu
{
    /**
     * Creates a {@link PopupMenu} from a {@link PopupWindow}, initial
     * selection, and selection colors.
     * @param window the {@link Menu}'s {@link Window}
     * @param initialSelection the item that the {@link Menu} will have selected
     * initially
     * @param foreground the foreground color of selected items
     * @param background the background color of selected items
     */
    public PopupMenu(PopupWindow window, int initialSelection, Color foreground,
            Color background)
        {super(window, initialSelection, foreground, background);}
    
    /**
     * Creates a {@link PopupMenu} from a {@link PopupWindow} and selection
     * colors, starting with the first item selected.
     * @param window the {@link Menu}'s {@link Window}
     * @param foreground the foreground color of selected items
     * @param background the background color of selected items
     */
    public PopupMenu(PopupWindow window, Color foreground, Color background)
        {super(window, foreground, background);}
    
    @Override
    public PopupWindow getOutput()
    {
        PopupWindow copy = new PopupWindow((PopupWindow) getWindow());
        copy.getContents().clear();
        for (ColorString content: getWindow().getContents())
            copy.add(content == null ? null : new ColorString(content));
        
        applySelectionColors(copy.getContents().get(getSelectionIndex()));
        return copy;
    }
}