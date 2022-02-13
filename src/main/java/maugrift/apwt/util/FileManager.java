package maugrift.apwt.util;

import squidpony.squidmath.RNG;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

/**
 * A class to manage multiple aspects of files, including finding the file path, saving and loading properties files,
 * retrieving lines of text files, and playing audio files.
 *
 * @author Maugrift
 */
public abstract class FileManager
{
    /**
     * The maximum integer volume allowed in {@link #setVolume(Clip, int)}.
     */
    public static final int MAX_VOLUME = 100;

    /**
     * The path to the data folder.
     */
    private static String path = findPath();

    /**
     * Returns the path to the data folder.
     *
     * @return the path to the data folder as a String
     */
    public static String getPath()
    {
        return path;
    }

    /**
     * Sets the data folder path to the String provided.
     *
     * @param newPath the String to be set as the new path
     */
    public void setPath(String newPath)
    {
        path = newPath;
    }

    /**
     * Changes the data folder path to the folder containing it.
     */
    public static void movePathUp()
    {
        // Must be done as two statements since both modify and refer to path
        path = path.substring(0, path.length() - 2);
        path = path.substring(0, path.lastIndexOf("/") + 1);
    }

    /**
     * Moves the path up multiple times.
     *
     * @param times the number of times to move the path up
     */
    public static void movePathUp(int times)
    {
        for (int i = 0; i < times; i++)
        {
            movePathUp();
        }
    }

    /**
     * Adds the given folders to the path.
     *
     * @param folders the folders in the current path to move to
     */
    public static void addToPath(String folders)
    {
        path += folders;
    }

    /**
     * Finds the path to the jar and its directory, returning the latter.
     *
     * @return the path to the jar file's directory
     */
    public static String findPath()
    {
        String jarPath = FileManager.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        return jarPath.substring(0, jarPath.lastIndexOf("/") + 1);
    }

    /**
     * Returns true if the specified file name exists.
     *
     * @param target the name of the file to check existence of
     * @return true if the file with the given name exists
     */
    public static boolean checkExistence(String target)
    {
        return new File(path + target).exists();
    }

    /**
     * Deletes a specified file if it exists.
     *
     * @param target the name of the file to delete
     */
    public static void delete(String target)
    {
        File file = new File(path + target);
        if (file.exists())
        {
            file.delete();
        }
    }

    /**
     * Returns a list of file names in the given directory.
     *
     * @param folderPath the relative path of the directory, must be a directory
     * @return a list of relative paths to files in the given directory
     */
    public static List<String> getFilesInFolder(String folderPath)
    {
        File folder = new File(path + folderPath);
        if (!folder.isDirectory())
        {
            throw new IllegalArgumentException("Given path does not point to a directory");
        }

        List<String> paths = new ArrayList<>();
        for (File file : folder.listFiles())
        {
            if (!file.isDirectory())
            {
                paths.add(folderPath + file.getName());
            }
        }
        return paths;
    }

    /**
     * Creates all folders containing the target file or folder, using recursion up to the base directory specified by
     * the path field.
     *
     * @param target the path to the file or folder to create containing folders for
     */
    public static void createContainingFolders(String target)
    {
        if (target == null)
        {
            throw new NullPointerException("Target file path may not be null");
        }

        if (target.contains("/"))
        {
            String folder = target.substring(0, target.lastIndexOf("/"));
            createContainingFolders(folder);
            new File(path + folder).mkdir();
        }
        else if (!target.contains("."))
        {
            new File(path + target).mkdir();
        }
    }

    /**
     * Loads a Properties object from a file, if it exists.
     *
     * @param target the name of the file to load from
     * @return the Properties object loaded from the file
     * @throws FileNotFoundException if the file is not found
     * @throws IOException           if there is an error processing the file
     */
    public static Properties load(String target) throws IOException
    {
        FileInputStream reader = new FileInputStream(new File(path + target));
        Properties properties = new Properties();
        properties.load(reader);
        reader.close();
        return properties;
    }

    /**
     * Saves a Properties object to the specified file.
     *
     * @param properties the list of properties to save
     * @param target     the name of the file to save to
     * @throws FileNotFoundException if the file is not found; should not be thrown since a file will be created
     *                                       if none exists
     * @throws IOException           if there is an error processing the file
     */
    public static void save(Properties properties, String target) throws IOException
    {
        if (target == null)
        {
            throw new NullPointerException("File saving destination may " + "not be null");
        }

        File file = new File(path + target);
        if (!file.exists())
        {
            createContainingFolders(target);
            file.createNewFile();
        }

        FileOutputStream writer = new FileOutputStream(file);
        properties.store(writer, "Saved game properties.");
        writer.close();
    }

    /**
     * Prints the contents of a file.
     *
     * @param target the file to print the contents of
     */
    public static void print(String target)
    {
        try
        {
            Scanner reader = new Scanner(new File(path + target));
            while (reader.hasNextLine())
            {
                System.out.println(reader.nextLine());
            }
            reader.close();
        }
        catch (FileNotFoundException fnf)
        {
            System.out.println("File not found at " + path + target);
        }
    }

    /**
     * Creates an array of Strings, where each item is a line of the file.
     *
     * @param target the file to create an array from
     * @return an array of Strings, where each item is a line of the file
     * @throws FileNotFoundException if the file is not found
     */
    public static String[] toLineArray(String target) throws FileNotFoundException
    {
        List<String> lineList = new ArrayList<>();
        Scanner reader = new Scanner(new File(path + target));
        while (reader.hasNextLine())
        {
            lineList.add(reader.nextLine());
        }
        reader.close();
        return lineList.toArray(new String[lineList.size()]);
    }

    /**
     * Writes the given lines of text to the given file; will overwrite existing lines or create the file if necessary.
     *
     * @param target the file to write to, must be non-null
     * @param lines  the lines to write
     * @throws IOException if there is an error creating the file
     */
    public static void writeToFile(String target, String... lines) throws IOException
    {
        if (target == null)
        {
            throw new NullPointerException("Target file path may not be null");
        }

        if (lines == null || lines.length == 0)
        {
            return;
        }

        File file = new File(path + target);
        if (!file.exists())
        {
            createContainingFolders(target);
            file.createNewFile();
        }

        PrintWriter writer = new PrintWriter(file);

        for (String line : lines)
        {
            writer.println(line);
        }

        writer.close();
    }

    /**
     * Writes the given List of text lines to the given file; will overwrite existing lines or create the file if
     * necessary.
     *
     * @param target the file to write to, must be non-null
     * @param lines  the lines to write
     * @throws IOException if there is an error creating the file
     */
    public static void writeToFile(String target, List<String> lines) throws IOException
    {
        writeToFile(target, lines.toArray(new String[lines.size()]));
    }

    /**
     * Returns the line at the specified index from the given file.
     *
     * @param target the name of the file to return a line from, must exist, be non-null, and have at least one line in
     *               it
     * @param line   the index of the line in the file to return (the first line is 0), must be non-negative
     * @return the line from the file, null if any of the above conditions are not met
     * @throws FileNotFoundException if the file is not found
     */
    public static String getLine(String target, int line) throws FileNotFoundException
    {
        if (target == null)
        {
            throw new NullPointerException("Target file path may not be null");
        }

        if (line < 0)
        {
            throw new IndexOutOfBoundsException("Line number must be >= 0");
        }

        File file = new File(path + target);

        Scanner reader = new Scanner(file);
        for (int i = 0; i < line; i++)
        {
            if (reader.hasNextLine())
            {
                reader.nextLine();
            }
            else
            {
                reader.close();
                return null;
            }
        }

        String lineString = reader.nextLine();
        reader.close();
        return lineString;
    }

    /**
     * Returns a random line from the designated file.
     *
     * @param target the name of the file to return a line from, must exist, be non-null, and have at least one line in
     *               it
     * @param rng    the random number generator with which to select a line
     * @return a random line from the file, null if any of the above conditions are not met
     * @throws FileNotFoundException if the file is not found
     */
    public static String getRandomLine(String target, RNG rng) throws FileNotFoundException
    {
        if (target == null)
        {
            throw new NullPointerException("Target file path may not be null");
        }

        File file = new File(path + target);

        // Count the lines of the file
        Scanner reader = new Scanner(file);
        int lineCounter = 0;
        while (reader.hasNextLine())
        {
            reader.nextLine();
            lineCounter++;
        }

        // The file has no lines and thus is empty, so return null
        if (lineCounter == 0)
        {
            reader.close();
            return null;
        }

        return getLine(target, rng.nextInt(lineCounter));
    }

    /**
     * Writes the specified text to the specified file.
     *
     * @param target the name of the file to write to, must be non-null
     * @param text   the text to write to the file, must be non-null and have characters in it
     * @throws FileNotFoundException if the file is not found
     * @throws IOException           if there is an error processing the file
     */
    public static void append(String target, String text) throws IOException
    {
        if (target == null)
        {
            throw new NullPointerException("Target file path may not be null");
        }

        if (text == null || "".equals(text))
        {
            return;
        }

        File file = new File(path + target);
        if (!file.exists())
        {
            createContainingFolders(target);
            file.createNewFile();
        }

        String[] lines = toLineArray(target);

        PrintWriter writer = new PrintWriter(file);

        for (String line : lines)
        {
            writer.println(line);
        }

        writer.println(text);
        writer.close();
    }

    /**
     * Loads the specified image from the file at the given path.
     *
     * @param target the name of the image file, must be non-null and exist
     * @return the Image read at the given path
     * @throws IOException if there is an error processing the file
     */
    public static Image loadImage(String target) throws IOException
    {
        File imageFile = new File(path + target);
        return imageFile.exists() ? ImageIO.read(imageFile) : null;
    }

    /**
     * Plays the given sound file, looping it for the given number of times.
     *
     * @param target the sound file to play
     * @param times  the number of times to play the sound file
     * @return the Clip that will play
     * @throws Exception if the file is not found, or if there is an error playing the file
     */
    public static Clip loopAudio(String target, int times) throws Exception
    {
        Clip clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(new File(path + target)));
        clip.loop(times);
        return clip;
    }

    /**
     * Loops the given sound file until stopped.
     *
     * @param target the sound file to loop
     * @return the Clip that will play
     * @throws Exception if the file is not found, or if there is an error playing the file
     */
    public static Clip loopAudio(String target) throws Exception
    {
        return loopAudio(target, Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Plays the given sound file.
     *
     * @param target the sound file to play
     * @return the Clip that will play
     * @throws Exception if the file is not found, or if there is an error playing the file
     */
    public static Clip playAudio(String target) throws Exception
    {
        return loopAudio(target, 0);
    }

    /**
     * Sets the volume of the given clip to the given float value.
     *
     * @param clip   the clip to adjust the volume of
     * @param volume the volume to set the clip at, with {@code 1.0f} being full volume and {@code 0.0f} being silence
     */
    public static void setVolume(Clip clip, float volume)
    {
        FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float range = control.getMaximum() - control.getMinimum();
        float gain = (range * volume) + control.getMinimum();
        control.setValue(gain);
    }

    /**
     * Sets the volume of the given clip to the given int value.
     *
     * @param clip   the clip to adjust the volume of
     * @param volume the volume to set the clip at, with {@code 100} being full volume and {@code 0} being silence
     */
    public static void setVolume(Clip clip, int volume)
    {
        setVolume(clip, Math.max(0.0f, Math.min(1.0f, ((float) volume) / ((float) MAX_VOLUME))));
    }
}
