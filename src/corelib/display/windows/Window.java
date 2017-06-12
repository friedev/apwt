package corelib.display.windows;

import corelib.display.Display;
import corelib.display.glyphs.ColorChar;
import corelib.display.glyphs.ColorSet;
import corelib.display.glyphs.ColorString;
import java.util.ArrayList;
import java.util.List;

/** Any window used to display a list of contents. */
public abstract class Window
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
     * The contents of the {@link Window}, with each
     * {@link corelib.display.glyphs.ColorSet} as a different line.
     */
    private List<ColorSet> contents;
    
    /**
     * Creates a {@link Window} with all fields defined.
     * @param display the {@link Window}'s {@link corelib.display.Display}
     * @param border the {@link Window}'s {@link Border}
     * @param contents the {@link Window}'s initial contents
     */
    public Window(Display display, Border border, List<ColorSet> contents)
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
     * Creates a borderless {@link Window}.
     * @param display the {@link Window}'s {@link corelib.display.Display}
     * @param contents the {@link Window}'s initial contents
     */
    public Window(Display display, List<ColorSet> contents)
        {this(display, null, contents);}
    
    /**
     * Creates a borderless {@link Window} with no contents.
     * @param display the {@link Window}'s {@link corelib.display.Display}
     */
    public Window(Display display)
        {this(display, null, new ArrayList<>());}
    
    /**
     * Prints the {@link Window} to its {@link corelib.display.Display} using
     * {@link WindowBuilder}.
     */
    public abstract void display();
    
    /**
     * Returns the contents of the {@link Window} as a List.
     * @return the contents of the {@link Window} as a List
     */
    public List<ColorSet> getContents()
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
     * Converts the provided {@link corelib.display.glyphs.ColorChar} into a
     * {@link corelib.display.glyphs.ColorSet} and adds it to the
     * {@link Window}'s contents.
     * @param content content the {@link corelib.display.glyphs.ColorChar} to
     * add
     * @return this for convenient chaining
     */
    public Window add(ColorChar content)
        {contents.add(new ColorSet(content)); return this;}
    
    /**
     * Converts the provided String into a
     * {@link corelib.display.glyphs.ColorSet} and adds it to the
     * {@link Window}'s contents.
     * @param content content the String to add
     * @return this for convenient chaining
     */
    public Window add(String content)
        {contents.add(new ColorSet(content)); return this;}
    
    /**
     * Converts the provided ColorString into a
     * {@link corelib.display.glyphs.ColorSet} and adds it to the
     * {@link Window}'s contents.
     * @param content the {@link corelib.display.glyphs.ColorString} to add
     * @return this for convenient chaining
     */
    public Window add(ColorString content)
        {contents.add(new ColorSet(content)); return this;}
    
    /**
     * Converts the provided array of ColorChars into a
     * {@link corelib.display.glyphs.ColorSet} and adds it to the
     * {@link Window}'s contents.
     * @param content the array of
     * {@link corelib.display.glyphs.ColorChar ColorChars} to add
     * @return this for convenient chaining
     */
    public Window add(ColorChar[] content)
        {contents.add(new ColorSet(content)); return this;}
    
    /**
     * Converts the provided array of
     * {@link corelib.display.glyphs.ColorString ColorStrings} into a
     * {@link corelib.display.glyphs.ColorSet} and adds it to the
     * {@link Window}'s contents.
     * @param content the array of
     * {@link corelib.display.glyphs.ColorString ColorStrings} to add
     * @return this for convenient chaining
     */
    public Window add(ColorString[] content)
        {contents.add(ColorSet.toColorSet(content)); return this;}
    
    /**
     * Adds the provided content to the {@link Window}'s contents.
     * @param content the content to add
     * @return this for convenient chaining
     */
    public Window add(ColorSet content)
        {contents.add(content); return this;}
    
    /**
     * Sets the line of the {@link Window}'s contents at the index to the
     * provided String, converted into a
     * {@link corelib.display.glyphs.ColorSet}.
     * @param index the line of the {@link Window}'s contents to replace
     * @param content the String that will be set at the line
     */
    public void set(int index, String content)
        {contents.set(index, new ColorSet(content));}
    
    /**
     * Sets the line of the {@link Window}'s contents at the index to the
     * provided {@link corelib.display.glyphs.ColorString}, converted into a
     * {@link corelib.display.glyphs.ColorSet}.
     * @param index the line of the {@link Window}'s contents to replace
     * @param content the {@link corelib.display.glyphs.ColorString} that will
     * be set at the line
     */
    public void set(int index, ColorString content)
        {contents.set(index, new ColorSet(content));}
    
    /**
     * Sets the line of the {@link Window}'s contents at the index to the
     * provided array of {@link corelib.display.glyphs.ColorChar ColorChars},
     * converted into a {@link corelib.display.glyphs.ColorSet}.
     * @param index the line of the {@link Window}'s contents to replace
     * @param content the array of
     * {@link corelib.display.glyphs.ColorChar ColorChars} that will be set at
     * the line
     */
    public void set(int index, ColorChar[] content)
        {contents.set(index, new ColorSet(content));}
    
    /**
     * Sets the line of the {@link Window}'s contents at the index to the
     * provided array of
     * {@link corelib.display.glyphs.ColorString ColorStrings}, converted into a
     * {@link corelib.display.glyphs.ColorSet}.
     * @param index the line of the {@link Window}'s contents to replace
     * @param content the array of
     * {@link corelib.display.glyphs.ColorString ColorStrings} that will be set
     * at the line
     */
    public void set(int index, ColorString[] content)
        {contents.set(index, ColorSet.toColorSet(content));}
    
    /**
     * Sets the line of the {@link Window}'s contents at the index to the
     * provided content.
     * @param index the line of the {@link Window}'s contents to replace
     * @param content the content that will be set at the line
     */
    public void set(int index, ColorSet content)
        {contents.set(index, content);}
    
    /**
     * Inserts the given String into the given index of the
     * {@link Window}'s contents.
     * @param index the index at which to insert the String
     * @param content the String to insert
     */
    public void insert(int index, String content)
        {insert(index, new ColorSet(content));}
    
    /**
     * Inserts the given {@link corelib.display.glyphs.ColorString} into the
     * given index of the {@link Window}'s contents.
     * @param index the index at which to insert the
     * {@link corelib.display.glyphs.ColorString}
     * @param content the {@link corelib.display.glyphs.ColorString} to insert
     */
    public void insert(int index, ColorString content)
        {insert(index, new ColorSet(content));}
    
    /**
     * Inserts the given array of
     * {@link corelib.display.glyphs.ColorChar ColorChars} into the given index
     * of the {@link Window}'s contents.
     * @param index the index at which to insert the array of
     * {@link corelib.display.glyphs.ColorChar ColorChars}
     * @param content the array of
     * {@link corelib.display.glyphs.ColorChar ColorChars} to insert
     */
    public void insert(int index, ColorChar[] content)
        {insert(index, new ColorSet(content));}
    
    /**
     * Inserts the given {@link corelib.display.glyphs.ColorSet} into the given
     * index of the {@link Window}'s contents.
     * @param index the index at which to insert the
     * {@link corelib.display.glyphs.ColorSet}
     * @param content the * {@link corelib.display.glyphs.ColorSet} to insert
     */
    public void insert(int index, ColorSet content)
        {contents.add(index, content);}
    
    /**
     * Adds a null line, acting as a separator, to the content.
     * @return this for convenient chaining
     */
    public Window addSeparator()
        {contents.add(null); return this;}
}