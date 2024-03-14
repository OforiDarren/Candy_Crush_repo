package be.kuleuven.candycrush;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import be.kuleuven.CheckNeighboursInGrid;

public class CandycrushModel {
    private String speler;
    private int score;
    private ArrayList<Integer> speelbord;
    private int width;
    private int height;

    public CandycrushModel(String speler, int score, Integer width, Integer height) {
        this.speler = (speler != null) ? speler : "Default";
        this.score = score;
        speelbord = new ArrayList<>();
        this.width = (width != null) ? width : 2; // Set default width to 2 if it's null
        this.height = (height != null) ? height : 2;

        for (int i = 0; i < this.width*this.height; i++){
            Random random = new Random();
            int randomGetal = random.nextInt(5) + 1;
            speelbord.add(randomGetal);
        }
    }
    private void wisSpeelBord(){
        speelbord.clear();
    }
    public void nieuwSpeelbord(){
        wisSpeelBord();
        for (int i = 0; i < width*height; i++){
            Random random = new Random();
            int randomGetal = random.nextInt(5) + 1;
            speelbord.add(randomGetal);
        }
    }
    public String getSpeler() {
        return speler;
    }

    public int getScore(){
        return score;
    }
    public void resetScore(){
        score = 0;
    }
    public ArrayList<Integer> getSpeelbord() {
        return speelbord;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void candyWithIndexSelected(int index) {
        //TODO: update method so it also changes direct neighbours of same type and updates score
        if (index != -1) {
            List<Integer> grid = (List<Integer>) CheckNeighboursInGrid.getSameNeighboursIds(getSpeelbord(), getWidth(), getHeight(), index);
            int count = 0;

            for (Integer element : grid) {
                count++;
                if (count >= 3) {
                    System.out.println("The grid has more than three elements.");
                    break;
                }
            }
            if (count >= 3) {
                grid.add(index);

                for (Integer neighbourIndex : grid) {
                    Random random = new Random();
                    int randomGetal = random.nextInt(5) + 1;
                    speelbord.set(neighbourIndex, randomGetal);
                    score++;
                }
            } else {
                System.out.println("The grid has three or fewer elements.");
            }
        } else {
            System.out.println("model:candyWithIndexSelected:indexWasMinusOne");
        }
    }

    public int getIndexFromRowColumn ( int row, int column){
        return column + row * width;
    }
    public void testSetSpeelbord(ArrayList<Integer> testSpeelbord) {
        speelbord = testSpeelbord;
    }
}


