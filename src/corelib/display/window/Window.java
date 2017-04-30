package corelib.display.window;

import corelib.display.Display;
import java.util.ArrayList;
import java.util.List;

/**
 * Any window used to display a list of contents should extend this class.
 * @param <Content> the type of content the Window will display
 */
public abstract class Window<Content extends CharSequence>
{
    /** The Display on which to print the window. */
    protected Display display;
    /** The contents of the Window, with each ColorSet as a different line. */
    protected List<Content> contents;
    /** The Border surrounding the Window; not displayed if null. */
    protected Border border;
    
    /**
     * Creates a Window with all fields defined.
     * @param display the Window's Display
     * @param border the Window's Border
     */
    public Window(Display display, Border border)
    {
        this.display = display;
        this.border = border;
        this.contents = new ArrayList<>();
    }
    
    /** Prints the Window to its Display using WindowBuilder. */
    public abstract void display();
    
    /**
     * Returns the contents of the Window as a List.
     * @return the contents of the Window as a List
     */
    public List<Content> getContents()
        {return contents;}
    
    /**
     * Returns the Display used by the Window.
     * @return the Display used by the Window
     */
    public Display getDisplay()
        {return display;}
    
    /**
     * Returns the Window's Border.
     * @return the Window's Border
     */
    public Border getBorder()
        {return border;}
    
    /**
     * Returns true if the Window has a Border.
     * @return true if the Window's Border is not null
     */
    public boolean isBordered()
        {return border != null;}
    
    /**
     * Adds the provided content to the Window's contents.
     * @param content the content to add
     */
    public void add(Content content)
        {contents.add(content);}
    
    /**
     * Sets the line of the Window's contents at the index to the provided
     * content.
     * @param index the line of the Window's contents to replace
     * @param content the content that will be set at the line
     */
    public void set(int index, Content content)
        {contents.set(index, content);}
    
    /** Adds a null line, acting as a separator, to the content. */
    public void addSeparator()
        {contents.add(null);}
}