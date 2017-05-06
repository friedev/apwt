package corelib.items;

/** An object or other entity that can be named and given a nickname. */
public class Nameable
{
    /** The name of the object. */
    private String name;
    
    /**
     * The nickname of the object; to be expressed in quotation marks right of
     * the name.
     */
    private String nickname;
    
    /**
     * Creates a {@link Nameable} with a name and nickname.
     * @param name the {@link Nameable}'s name
     * @param nickname the {@link Nameable}'s nickname
     */
    public Nameable(String name, String nickname)
    {
        this.name = name;
        this.nickname = nickname;
    }
    
    /**
     * Creates a {@link Nameable} with a name and no nickname.
     * @param name the {@link Nameable}'s name
     */
    public Nameable(String name)
        {this(name, null);}
    
    @Override
    public String toString()
    {
        return nickname != null && !"".equals(nickname) ?
            name + " \"" + nickname + "\"" : name;
    }
    
    /**
     * Returns this {@link Nameable}'s name.
     * @return this {@link Nameable}'s name
     */
    public String getName()
        {return name;}
    
    /**
     * Returns this {@link Nameable}'s nickname.
     * @return this {@link Nameable}'s nickname
     */
    public String getNickname()
        {return nickname;}
    
    /**
     * Returns this {@link Nameable}'s name in lower case.
     * @return this {@link Nameable}'s name in lower case
     */
    public String getLowerCaseName()
        {return name.toLowerCase();}
    
    /**
     * Returns the first letter of the {@link Nameable}'s name.
     * @return the first letter of the {@link Nameable}'s name
     */
    public char getFirstLetter()
        {return name.charAt(0);}
    
    /**
     * Changes {@link Nameable}'s name.
     * @param name the {@link Nameable}'s new name
     */
    public final void setName(String name)
        {this.name = name;}
    
    /**
     * Changes {@link Nameable}'s nickname.
     * @param nickname the {@link Nameable}'s new nickname
     */
    public final void setNickname(String nickname)
        {this.nickname = nickname;}
    
    /**
     * Returns true if a given String is equal to the name or its shortened
     * form.
     * @param s the String to compare with the name
     * @return true if the String has the same characters, ignoring case, as the
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
        
        return firstLetter == 'A' || firstLetter == 'E' || firstLetter == 'I' ||
                                  firstLetter == 'O' || firstLetter == 'U' ?
            "an " + name.toLowerCase() : "a " + name.toLowerCase();
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
        
        return firstLetter == 'A' || firstLetter == 'E' || firstLetter == 'I' ||
                                  firstLetter == 'O' || firstLetter == 'U' ?
            "An " + name.toLowerCase() : "A " + name.toLowerCase();
    }
    
    /**
     * Returns the full name of the item in lower case, meaning that either 'a'
     * or 'an' will be attached to the front.
     * @return the full name of the item as described above
     */
    public String getFullName()
     {return getFullName(name);}
    
    /**
     * Performs the same function as the normal full name method, except the 'a'
     * or 'an' will be capitalized, making it 'A' or 'An.'
     * @return the full name of the item with the first letter capitalized
     */
    public String getFullNameCapitalized()
        {return getFullNameCapitalized(name);}
}