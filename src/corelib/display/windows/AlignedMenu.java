package corelib.display.windows;

import corelib.display.glyphs.ColorSet;
import java.awt.Color;

/**
 * 
 */
public class AlignedMenu extends ColoredMenu<ColorSet, AlignedWindow>
{
    public AlignedMenu(AlignedWindow window, int initialSelection,
            Color foreground, Color background)
        {super(window, initialSelection, foreground, background);}
    
    public AlignedMenu(AlignedWindow window, Color foreground, Color background)
        {super(window, foreground, background);}

    @Override
    public AlignedWindow getOutput()
    {
        AlignedWindow copy = new AlignedWindow((AlignedWindow) getWindow());
        copy.resetContents();
        for (ColorSet content: getWindow().getContents())
            copy.add(new ColorSet(content));
        
        copy.getContents().get(getSelectionIndex())
                .setColors(getForeground(), getBackground());
        return copy;
    }
}