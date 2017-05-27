package pt.iul.ista.poo.sokoban;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;


public class JUnitTest {

    Sokoban s = new Sokoban("Test");
    HighScores highScores = new HighScores(s);
    Score addedScore = new Score(1, 50, 100, "Test");

    @Test
    public void testScores(){   //Sempre que se corre este teste, o programa escreve de novo um score, consequentemente, vai rebentar sempre que há mais que duas entries no ficheiro.
        s.setLevel(5);
        s.setSteps(50);
        highScores.manageScores(100);
        try (final Scanner file = new Scanner(new File("levels/level" + s.getLevel() + "bestscore.txt"))){
            file.useLocale(Locale.US);
            while(file.hasNextLine()) {
                String line = file.nextLine();
                if(!(line.isEmpty()) && line.charAt(0)=='#'){
                    int place = file.nextInt();
                    file.nextLine();
                    int steps = file.nextInt();
                    file.nextLine();
                    double time = file.nextDouble();
                    file.nextLine();
                    String name = file.next();
                    file.nextLine();
                    Score score = new Score(place, steps, time, name);
                    assertEquals(score, addedScore);
                }
            }
        } catch (final FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(0);
        }
    }

    @AfterEach
    public void tearDown(){
        final PrintWriter fileWriter;
        try {
            fileWriter = new PrintWriter("levels/level" + s.getLevel() + "bestscore.txt");
            fileWriter.print("");
            fileWriter.close();
        } catch (final FileNotFoundException exception) {
            System.out.println("Error opening file.");
        }
    }
}
