package corelib.display.windows;

import corelib.display.glyphs.ColorString;
import java.awt.Color;
import java.util.ArrayList;

/**
 * 
 */
public class PopupMenu extends ColoredMenu<ColorString, PopupWindow>
{
    public PopupMenu(PopupWindow window, int initialSelection, Color foreground,
            Color background)
        {super(window, initialSelection, foreground, background);}
    
    public PopupMenu(PopupWindow window, Color foreground, Color background)
        {super(window, foreground, background);}
    
    @Override
    public PopupWindow getOutput()
    {
        PopupWindow copy = new PopupWindow((PopupWindow) getWindow());
        copy.contents = new ArrayList<>();
        for (ColorString content: getWindow().contents)
            copy.add(new ColorString(content));
        
        copy.contents.get(getSelectionIndex())
                .setColors(selectionForeground, selectionBackground);
        return copy;
    }
}