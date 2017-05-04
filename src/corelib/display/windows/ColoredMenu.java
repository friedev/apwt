package corelib.display.windows;

import java.awt.Color;

/**
 * 
 * @param <Content>
 * @param <WindowType>
 */
public abstract class ColoredMenu<Content extends CharSequence,
        WindowType extends Window<Content>> extends Menu<Content, WindowType>
{
    protected Color selectionForeground;
    protected Color selectionBackground;
    
    public ColoredMenu(WindowType window, int initialSelection,
            Color foreground, Color background)
    {
        super(window, initialSelection);
        selectionForeground = foreground;
        selectionBackground = background;
    }
    
    public ColoredMenu(WindowType window, Color foreground, Color background)
        {this(window, 0, foreground, background);}
}