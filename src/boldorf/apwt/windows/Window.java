package boldorf.apwt.windows;

import boldorf.apwt.Display;
import boldorf.apwt.glyphs.ColorString;
import java.util.ArrayList;
import java.util.List;

/** Any window used to display a list of contents. */
public abstract class Window
{
    /**
     * The {@link boldorf.apwt.Display} on which to print the {@link Window}.
     */
    private Display display;
    
    /**
     * The {@link Border} surrounding the {@link Window}. If null, the
     * {@link Window} will be borderless.
     */
    private Border border;
    
    /**
     * The contents of the {@link Window}, with each
     * {@link boldorf.apwt.glyphs.ColorString} as a different line.
     */
    private List<ColorString> contents;
    
    /**
     * Creates a {@link Window} with all fields defined.
     * @param display the {@link Window}'s {@link boldorf.apwt.Display}
     * @param border the {@link Window}'s {@link Border}
     * @param contents the {@link Window}'s initial contents
     */
    public Window(Display display, Border border, List<ColorString> contents)
    {
        this.display = display;
        this.border = border;
        this.contents = contents;
    }
    
    /**
     * Creates a {@link Window} with no contents.
     * @param display the {@link Window}'s {@link boldorf.apwt.Display}
     * @param border the {@link Window}'s {@link Border}
     */
    public Window(Display display, Border border)
        {this(display, border, new ArrayList<>());}
    
    /**
     * Creates a borderless {@link Window}.
     * @param display the {@link Window}'s {@link boldorf.apwt.Display}
     * @param contents the {@link Window}'s initial contents
     */
    public Window(Display display, List<ColorString> contents)
        {this(display, null, contents);}
    
    /**
     * Creates a borderless {@link Window} with no contents.
     * @param display the {@link Window}'s {@link boldorf.apwt.Display}
     */
    public Window(Display display)
        {this(display, null, new ArrayList<>());}
    
    /**
     * Prints the {@link Window} to its {@link boldorf.apwt.Display} using
     * {@link WindowBuilder}.
     */
    public abstract void display();
    
    /**
     * Returns the contents of the {@link Window} as a List.
     * @return the contents of the {@link Window} as a List
     */
    public List<ColorString> getContents()
        {return contents;}
    
    /**
     * Returns the {@link boldorf.apwt.Display} used by the {@link Window}.
     * @return the {@link boldorf.apwt.Display} used by the {@link Window}
     */
    public Display getDisplay()
        {return display;}
    
    /**
     * Returns the {@link Window}'s {@link Border}.
     * @return the {@link Window}'s {@link Border}
     */
    public Border getBorder()
        {return border;}
    
    /**
     * Returns true if the {@link Window} has a {@link Border}.
     * @return true if the {@link Window}'s {@link Border} is not null
     */
    public boolean isBordered()
        {return border != null;}
    
    /** Adds a null line, acting as a separator, to the content. */
    public void addSeparator()
        {contents.add(null);}
}