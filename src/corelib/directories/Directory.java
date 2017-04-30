package corelib.directories;

/** An object that stores a list of other objects in it. */
public interface Directory extends Printable
{
    public String listContents();
    public Content findContent(String name);
    public Content deepSearch(String name);
}