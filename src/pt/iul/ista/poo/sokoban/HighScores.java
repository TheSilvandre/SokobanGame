package pt.iul.ista.poo.sokoban;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class HighScores {

    private ArrayList<Score> scores;
    private Sokoban sokoban;

    public HighScores(Sokoban sokoban){
        scores = new ArrayList<>();
        this.sokoban = sokoban;
    }

    private void readHighScores(){
        try (final Scanner file = new Scanner(new File("levels/level" + sokoban.getLevel() + "bestscore.txt"))){
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
                    scores.add(new Score(place, steps, time, name));
                }
            }
            return;
        } catch (final FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(0);
        }
    }

    public void addScoreToList(Score score){

    }

    public void manageScores(double timeToComplete) {
        readHighScores();
        Score thisScore = new Score(6, sokoban.getSteps(), timeToComplete, sokoban.getPlayerName());
        if (compareScores(thisScore))
            scores.add(thisScore);
        else if (scores.size()<5) {
            thisScore.setPlace(scores.size()+1);
            scores.add(thisScore);
        }
        organize();
        removeUnwantedScores();
        showMyScore(thisScore);
        writeScores();
    }

    private boolean compareScores(Score thisScore){
        for(int i=0; i<scores.size(); i++){
            if(scores.get(i).getSteps()>thisScore.getSteps() || (scores.get(i).getSteps()==thisScore.getSteps() && scores.get(i).getTime() > thisScore.getTime())){
                thisScore.setPlace(scores.get(i).getPlace());
                for (int a=0; a<scores.size(); a++)
                    if(scores.get(a).getPlace()>=thisScore.getPlace())
                        scores.get(a).setPlace(scores.get(a).getPlace()+1);
                return true;
            }
        }
        return false;
    }

    private void organize(){
        ArrayList<Score> newHighScores = new ArrayList<Score>();
        int countPlace = 1;
        while (countPlace<=scores.size()) {
            for (int i = 0; i < scores.size(); i++)
                if (scores.get(i).getPlace()==countPlace)
                    newHighScores.add(scores.get(i));
            countPlace++;
        }
        scores = newHighScores;
    }

    private void removeUnwantedScores(){
        if(scores.size()>5)
            for (int i = 5; i<scores.size(); i++)
                scores.remove(i);
    }

    private void writeScores(){
        final PrintWriter fileWriter;
        try {
            fileWriter = new PrintWriter("levels/level" + sokoban.getLevel() + "bestscore.txt");
            for(Score s: scores) {
                fileWriter.println('#');
                fileWriter.println(s.getPlace() + " place");
                fileWriter.println(s.getSteps() + " steps");
                fileWriter.println(s.getTime() + " seconds");
                fileWriter.println(s.getName() + " player");
            }
            fileWriter.close();
        } catch (final FileNotFoundException exception) {
            System.out.println("Error opening file.");
        }
    }

    public void showMyScore(Score score){
        JOptionPane pane = new JOptionPane();
        JOptionPane.showMessageDialog(pane, "Your score is: \n" + score.getSteps() + " steps \n" + score.getTime() + " seconds"  );
    }

}
