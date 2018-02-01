package maugrift.apwt.windows;

import maugrift.apwt.display.AsciiPanelDisplay;
import maugrift.apwt.glyphs.ColorString;

import java.util.ArrayList;
import java.util.List;

/**
 * Any window used to display a list of contents.
 *
 * @author Maugrift
 */
public abstract class Window
{
    /**
     * The {@link AsciiPanelDisplay} on which to print the {@link Window}.
     */
    private AsciiPanelDisplay display;

    /**
     * The {@link Border} surrounding the {@link Window}. If null, the {@link Window} will be borderless.
     */
    private Border border;

    /**
     * The contents of the {@link Window}, with each {@link maugrift.apwt.glyphs.ColorString} as a different line.
     */
    private List<ColorString> contents;

    /**
     * Creates a {@link Window} with all fields defined.
     *
     * @param display  the {@link Window}'s {@link AsciiPanelDisplay}
     * @param border   the {@link Window}'s {@link Border}
     * @param contents the {@link Window}'s initial contents
     */
    public Window(AsciiPanelDisplay display, Border border, List<ColorString> contents)
    {
        this.display = display;
        this.border = border;
        this.contents = contents;
    }

    /**
     * Creates a {@link Window} with no contents.
     *
     * @param display the {@link Window}'s {@link AsciiPanelDisplay}
     * @param border  the {@link Window}'s {@link Border}
     */
    public Window(AsciiPanelDisplay display, Border border)
    {
        this(display, border, new ArrayList<>());
    }

    /**
     * Creates a borderless {@link Window}.
     *
     * @param display  the {@link Window}'s {@link AsciiPanelDisplay}
     * @param contents the {@link Window}'s initial contents
     */
    public Window(AsciiPanelDisplay display, List<ColorString> contents)
    {
        this(display, null, contents);
    }

    /**
     * Creates a borderless {@link Window} with no contents.
     *
     * @param display the {@link Window}'s {@link AsciiPanelDisplay}
     */
    public Window(AsciiPanelDisplay display)
    {
        this(display, null, new ArrayList<>());
    }

    /**
     * Prints the {@link Window} to its {@link AsciiPanelDisplay}.
     */
    public abstract void display();

    /**
     * Returns the contents of the {@link Window} as a List.
     *
     * @return the contents of the {@link Window} as a List
     */
    public List<ColorString> getContents()
    {
        return contents;
    }

    /**
     * Returns the {@link AsciiPanelDisplay} used by the {@link Window}.
     *
     * @return the {@link AsciiPanelDisplay} used by the {@link Window}
     */
    public AsciiPanelDisplay getDisplay()
    {
        return display;
    }

    /**
     * Returns the {@link Window}'s {@link Border}.
     *
     * @return the {@link Window}'s {@link Border}
     */
    public Border getBorder()
    {
        return border;
    }

    /**
     * Returns true if the {@link Window} has a {@link Border}.
     *
     * @return true if the {@link Window}'s {@link Border} is not null
     */
    public boolean isBordered()
    {
        return border != null;
    }

    /**
     * Adds a null line, acting as a separator, to the content.
     */
    public void addSeparator()
    {
        contents.add(null);
    }
}