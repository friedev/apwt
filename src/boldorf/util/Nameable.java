package boldorf.util;

import java.util.Properties;

/** An object or other entity that can be named and given a nickname. */
public abstract class Nameable
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
    
    /**
     * Creates a Nameable from a Properties object that should contain the
     * fields "name" and optionally "nickname".
     * @param properties the Properties object to be used in the constructor
     */
    public Nameable(Properties properties)
    {
        // null properties will cause an exception to be thrown
        name = properties.getProperty("name");
        nickname = properties.getProperty("nickname");
    }
    
    @Override
    public String toString()
    {
        return nickname != null && !nickname.isEmpty() ?
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
     * Returns true if the given letter is a vowel.
     * @param letter the character to check
     * @return true if the letter is a vowel
     */
    public static boolean isVowel(char letter)
    {
        return letter == 'a' || letter == 'e' || letter == 'i' ||
               letter == 'o' || letter == 'u' || letter == 'A' ||
               letter == 'E' || letter == 'I' || letter == 'O' || letter == 'U';
    }
    
    /**
     * Capitalizes the first letter of a String.
     * @param name the String to capitalize
     * @return a new String identical to the first, but with its first letter
     * capitalized
     */
    public static String capitalize(String name)
    {
        return name == null ? null :
                Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
    
    /**
     * Capitalizes every word in a String.
     * @param name the String to capitalize
     * @return a new String identical to the first, but with the first letter of
     * every word capitalized
     */
    public static String capitalizeAllWords(String name)
    {
        if (name == null)
            return null;
        
        String[] words = name.split(" ");
        
        if (words.length == 1)
            return capitalize(name);
        
        for (int word = 0; word < words.length; word++)
            words[word] = capitalize(words[word]);
        
        return reconstruct(words);
    }
    
    /**
     * Capitalizes every character in a String.
     * @param name the String to capitalize
     * @return a new String identical to the first, but with every lowercase
     * letter capitalized
     */
    public static String capitalizeAll(String name)
    {
        if (name == null)
            return null;
        
        char[] array = new char[name.length()];
        
        for (int letter = 0; letter < name.length(); letter++)
            array[letter] = Character.toUpperCase(name.charAt(letter));
        
        return new String(array);
    }
    
    /**
     * Makes the given String plural.
     * @param name the String to make plural
     * @return the name with an "s" appended to it
     */
    public static String makePlural(String name)
        {return name + "s";}
    
    /**
     * Makes the given String plural if the amount given is considered more than
     * one (including 0).
     * @param name the String to make plural
     * @param amount the amount to check before changing the name
     * @return the name with an "s" appended to it, if the amount is not 1
     */
    public static String makePlural(String name, int amount)
        {return amount == 1 ? name : name + "s";}
    
    /**
     * Returns the indefinite article that would precede this name.
     * @param name the String to generate an article for
     * @return the indefinite article that would precede this name
     */
    public static String getArticle(String name)
        {return isVowel(name.charAt(0)) ? "an" : "a";}
    
    /**
     * Returns the full name of a String in lower case, preceded by an
     * indefinite article.
     * @param name the String to generate a full name for
     * @return the full name of the String as described above
     */
    public static String getFullName(String name)
        {return getArticle(name) + " " + name.toLowerCase();}
    
    /**
     * Performs the same function as the normal full name method, except the 'a'
     * or 'an' will be capitalized, making it 'A' or 'An.'
     * @param name the String to generate a full name for
     * @return the full name of the String with the first letter capitalized
     */
    public static String getFullNameCapitalized(String name)
    {
        return isVowel(name.charAt(0)) ?
                "An " + name.toLowerCase() : "A " + name.toLowerCase();
    }
    
    /**
     * Capitalizes the first letter of the item's name.
     * @return a new String identical to the item's name, but with the first
     * letter capitalized
     */
    public String capitalize()
        {return capitalize(name);}
    
    /**
     * Capitalizes the first letter of every word in the item's name.
     * @return a new String identical to the item's name, but with the first
     * letter of every word capitalized
     */
    public String capitalizeAllWords()
        {return capitalizeAllWords(name);}
    
    /**
     * Capitalizes every character in the item's name.
     * @return a new String identical to the item's name, but with every
     * lowercase letter capitalized
     */
    public String capitalizeAll()
        {return capitalizeAll(name);}
    
    /**
     * Makes the item's name plural.
     * @return the item's name with an "s" appended to it
     */
    public String makePlural()
        {return makePlural(name);}
    
    /**
     * Makes the item's name plural if the amount given is considered more than
     * one (including 0).
     * @param amount the amount to check before changing the name
     * @return the item's name with an "s" appended to it, if the amount is not
     * 1
     */
    public String makePlural(int amount)
        {return makePlural(name, amount);}
    
    /**
     * Returns the indefinite article that would precede this item's name.
     * @return the indefinite article that would precede this item's name
     */
    public String getArticle()
        {return getArticle(name);}
    
    /**
     * Returns the full name of the item in lower case, preceded by an
     * indefinite article.
     * @return the item's name as described above
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
    
    /**
     * Reconstructs a String from an array of Strings, separating Strings
     * between the start and end indices with spaces.
     * @param array the array to reconstruct, must be non-null
     * @param start the index to start reconstructing at, must be greater than
     * or equal to zero
     * @param end the index to stop <i>before</i>, must be no larger than the
     * length of the array
     * @return every String between the start and end indices of the array as
     * one String, separating each individual String with spaces, null if the
     * operation cannot be performed
     */
    public static String reconstruct(String[] array, int start, int end)
    {
        if (array == null || start < 0 || start > array.length ||
                             end   < 0 || end   > array.length)
            return null;
        
        StringBuilder builder = new StringBuilder();
        for (int i = start; i < end; i++)
            builder.append(array[i]).append(" ");
        
        if (builder.length() > 0)
            builder.delete(builder.length() - 1, builder.length());
        
        return builder.toString();
    }
    
    /**
     * Reconstructs a String from an array of Strings, separating Strings
     * between the start index and the end of the array with spaces.
     * @param array the array to reconstruct, must be non-null
     * @param start the index to start reconstructing at, must be greater than
     * or equal to zero
     * @return every String between the start index and the end of the array as
     * one String, separating each individual String with spaces, null if the
     * operation cannot be performed
     */
    public static String reconstruct(String[] array, int start)
        {return array == null ? null : reconstruct(array, 0, array.length);}
    
    /**
     * Reconstructs a String from an entire array of Strings, separating Strings
     * with spaces.
     * @param array the array to reconstruct, must be non-null
     * @return every String in the array as on String, separating each
     * individual String with spaces, null if the operation cannot be performed
     */
    public static String reconstruct(String[] array)
        {return array == null ? null : reconstruct(array, 0, array.length);}
}