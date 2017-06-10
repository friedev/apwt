package corelib.display.windows;

import corelib.display.glyphs.ColorSet;
import java.awt.Color;

/** A {@link Menu} displayed on an {@link AlignedWindow}. */
public class AlignedMenu extends ColoredMenu<AlignedWindow>
{
    /**
     * Creates an {@link AlignedMenu} from an {@link AlignedWindow}, initial
     * selection, and selection colors.
     * @param window the {@link Menu}'s {@link Window}
     * @param initialSelection the item that the {@link Menu} will have selected
     * initially
     * @param foreground the foreground color of selected items
     * @param background the background color of selected items
     */
    public AlignedMenu(AlignedWindow window, int initialSelection,
            Color foreground, Color background)
        {super(window, initialSelection, foreground, background);}
    
    /**
     * Creates an {@link AlignedMenu} from an {@link AlignedWindow} and
     * selection colors, starting with the first item selected.
     * @param window the {@link Menu}'s {@link Window}
     * @param foreground the foreground color of selected items
     * @param background the background color of selected items
     */
    public AlignedMenu(AlignedWindow window, Color foreground, Color background)
        {super(window, foreground, background);}

    @Override
    public AlignedWindow getOutput()
    {
        AlignedWindow copy = new AlignedWindow((AlignedWindow) getWindow());
        copy.getContents().clear();
        for (ColorSet content: getWindow().getContents())
            copy.add(new ColorSet(content));
        
        applySelectionColors(copy.getContents().get(getSelectionIndex()));
        return copy;
    }
}