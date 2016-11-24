package directories;

/** A directory that contains other directories. */
public interface SuperDirectory extends Directory
{
    public Content deepFind(String name);
}
