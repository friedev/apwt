package corelib.display.windows;

import corelib.display.glyphs.ColorString;
import java.awt.Color;

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
        copy.resetContents();
        for (ColorString content: getWindow().getContents())
            copy.add(new ColorString(content));
        
        copy.getContents().get(getSelectionIndex())
                .setColors(getForeground(), getBackground());
        return copy;
    }
}