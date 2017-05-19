package corelib.display.windows;

import corelib.display.Display;
import java.util.ArrayList;
import java.util.List;

/**
 * Any window used to display a list of contents should extend this class.
 * @param <Content> the type of content the {@link Window} will display
 */
public abstract class Window<Content extends CharSequence>
{
    /**
     * The {@link corelib.display.Display} on which to print the {@link Window}.
     */
    private Display display;
    
    /**
     * The {@link Border} surrounding the {@link Window}. If null, the
     * {@link Window} will be borderless.
     */
    private Border border;
    
    /**
     * The contents of the {@link Window}, with each Content as a different
     * line.
     */
    private List<Content> contents;
    
    /**
     * Creates a {@link Window} with all fields defined.
     * @param display the {@link Window}'s {@link corelib.display.Display}
     * @param border the {@link Window}'s {@link Border}
     * @param contents the {@link Window}'s initial contents
     */
    public Window(Display display, Border border, List<Content> contents)
    {
        this.display = display;
        this.border = border;
        this.contents = contents;
    }
    /**
     * Creates a {@link Window} with no contents.
     * @param display the {@link Window}'s {@link corelib.display.Display}
     * @param border the {@link Window}'s {@link Border}
     */
    public Window(Display display, Border border)
        {this(display, border, new ArrayList<>());}
    
    /**
     * Prints the {@link Window} to its {@link corelib.display.Display} using
     * {@link WindowBuilder}.
     */
    public abstract void display();
    
    /**
     * Returns the contents of the {@link Window} as a List.
     * @return the contents of the {@link Window} as a List
     */
    public List<Content> getContents()
        {return contents;}
    
    /**
     * Returns the {@link corelib.display.Display} used by the {@link Window}.
     * @return the {@link corelib.display.Display} used by the {@link Window}
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
    
    /**
     * Adds the provided content to the {@link Window}'s contents.
     * @param content the content to add
     * @return this for convenient chaining
     */
    public Window add(Content content)
        {contents.add(content); return this;}
    
    /**
     * Sets the line of the {@link Window}'s contents at the index to the
     * provided content.
     * @param index the line of the {@link Window}'s contents to replace
     * @param content the content that will be set at the line
     */
    public void set(int index, Content content)
        {contents.set(index, content);}
    
    /**
     * Adds a null line, acting as a separator, to the content.
     * @return this for convenient chaining
     */
    public Window addSeparator()
        {contents.add(null); return this;}
}