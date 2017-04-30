package demo;

import corelib.Console;
import corelib.NameGenerator;
import corelib.display.Display;
import corelib.items.ContainerItem;
import squidpony.WeightedLetterNamegen;

/**
 * A sample main class that contains the basic components of the library to get
 * started quickly.
 */
public class Main
{
    /**
     * Launches a new display for testing.
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        new Display().init();
        testDefine();
        testNameGenerator();
        testWeightedLetterNamegen();
    }
    
    public static void testDefine()
    {
        for (String line: new ContainerItem("The Container",
                "The one container to rule them all.", 10000, 25, 100).define())
            Console.println(line);
    }
    
    public static void testNameGenerator()
    {
        NameGenerator generator = new NameGenerator(new String[]
                {"names/syl1.txt", "names/syl2.txt", "names/syl3.txt"});
        
        for (int i = 0; i < 10; i++)
            Console.println(generator.generateRandomLengthName(2));
    }
    
    public static void testWeightedLetterNamegen()
    {
        WeightedLetterNamegen generator = new WeightedLetterNamegen(
                new String[]{"Boldorf", "Dalian", "Renovhir", "Islix",
                    "Galandor", "Keltzquin", "Mogrithe"});
        for (int i = 0; i < 50; i++)
            Console.println(generator.generate()[0]);
    }
}