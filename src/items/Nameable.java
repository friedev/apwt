package items;

/** An object or other entity that can be named and given a nickname. */
public class Nameable
{
    private String name;
    private String nickname;
    
    public Nameable(String name) {this(name, null);}
    
    public Nameable(String name, String nickname)
    {
        this.name = name;
        this.nickname = nickname;
    }
    
    @Override
    public String toString()
    {
        if (nickname != null && !"".equals(nickname))
            return name + " \"" + nickname + "\"";
        
        return name;
    }
    
    public String getName()          {return name;              }
    public String getNickname()      {return nickname;          }
    public String getLowerCaseName() {return name.toLowerCase();}
    public char   getFirstLetter()   {return name.charAt(0);    }
    
    /**
     * Changes the name to n, and must be final so that sector change change it.
     * @param n the name to change the current name to
     */
    public final void setName(String n) {name = n;}
    
    /**
     * Changes the nickname to nn, and must be final for the same reason as
     * setName().
     * @param nn the name to change the current nickname to
     */
    public final void setNickname(String nn) {nickname = nn;}
    
    /**
     * Returns true if a given string is equal to the name or its shortened
     * form.
     * @param s the string to compare with the name
     * @return true if the string has the same characters, ignoring case, as the
     * name itself or its last word
     */
    public boolean isName(String s)
    {
        String lastWord = name.substring(name.lastIndexOf(" ") + 1);
        
        return s.equalsIgnoreCase(name) || s.equalsIgnoreCase(lastWord);
    }
    
    /**
     * Returns the full name of a String in lower case, meaning that either 'a'
     * or 'an' will be attached to the front.
     * @param name the String to generate a full name for
     * @return the full name of the String as described above
     */
    public static String getFullName(String name)
    {
        char firstLetter = name.charAt(0);
        
        if (firstLetter == 'A' || firstLetter == 'E' || firstLetter == 'I' ||
                                  firstLetter == 'O' || firstLetter == 'U')
            return "an " + name.toLowerCase();
        
        return "a " + name.toLowerCase();
    }
    
    /**
     * Performs the same function as the normal full name method, except the 'a'
     * or 'an' will be capitalized, making it 'A' or 'An.'
     * @param name the String to generate a full name for
     * @return the full name of the String with the first letter capitalized
     */
    public static String getFullNameCapitalized(String name)
    {
        char firstLetter = name.charAt(0);
        
        if (firstLetter == 'A' || firstLetter == 'E' || firstLetter == 'I' ||
                                  firstLetter == 'O' || firstLetter == 'U')
            return "An " + name.toLowerCase();
        
        return "A " + name.toLowerCase();
    }
    
    /**
     * Returns the full name of the item in lower case, meaning that either 'a'
     * or 'an' will be attached to the front.
     * @return the full name of the item as described above
     */
    public String getFullName() {return getFullName(name);}
    
    /**
     * Performs the same function as the normal full name method, except the 'a'
     * or 'an' will be capitalized, making it 'A' or 'An.'
     * @return the full name of the item with the first letter capitalized
     */
    public String getFullNameCapitalized()
        {return getFullNameCapitalized(name);}
}