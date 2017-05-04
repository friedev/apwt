package corelib.display.windows;

import corelib.display.glyphs.ColorSet;
import java.awt.Color;
import java.util.ArrayList;

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
        copy.contents = new ArrayList<>();
        for (ColorSet content: getWindow().contents)
            copy.add(new ColorSet(content));
        
        copy.contents.get(getSelectionIndex())
                .setColors(selectionForeground, selectionBackground);
        return copy;
    }
}