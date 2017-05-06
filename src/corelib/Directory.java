package corelib;

/**
 * An object that stores a list of other objects or directories in it.
 * @param <Content> the type of content stored in the directory
 */
public interface Directory<Content>
{
    /**
     * Lists all contents of the Directory as an array.
     * @return all of the Directory's contents in an array
     */
    public Content[] listContents();
    
    /**
     * Finds the item in this specific Directory identified by the provided
     * String.
     * @param identifier a String that can be used to identify a Content
     * @return the content in this Directory identified by the provided String
     */
    public Content findContent(String identifier);
    
    /**
     * Finds the item in this directory identified by the provided String,
     * including items in sub-directories.
     * @param identifier a String that can be used to identify a Content
     * @return the content in this Directory or its sub-directories identified
     * by the provided String
     */
    public Content deepSearch(String identifier);
}