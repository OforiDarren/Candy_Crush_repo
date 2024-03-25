package be.kuleuven.candycrush;
import be.kuleuven.candycrush.Candy.Candy;
import be.kuleuven.candycrush.Position;
import be.kuleuven.candycrush.Boardsize;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import be.kuleuven.CheckNeighboursInGrid;

public class CandycrushModel {
    private String speler;
    private int score;
    private ArrayList<Candy> speelbord;
    private Boardsize boardsize;
    private Position position;
    private Candy candy;
    public CandycrushModel(String speler, int score, Boardsize boardsize) {
        if (speler == null) {
            speler = "Default";
        }
        this.speler = speler;
        this.score = score;
        this.boardsize = boardsize;
        speelbord = new ArrayList<>();
        for (int i = 0; i < this.boardsize.columns()*this.boardsize.rows(); i++){
            Random random = new Random();
            int randomGetal = random.nextInt(5);
            speelbord.add(generateRandomCandy(randomGetal));
        }
    }
    public void setPosition(int rowOfIndex, int columnOfIndex) {
        this.position = new Position(rowOfIndex,columnOfIndex,boardsize);
    }
    public Position getPosition(){
        return position;
    }
    public Boardsize getBoardsize() {
        return boardsize;
    }
    private void wisSpeelBord(){
        speelbord.clear();
    }
    public void nieuwSpeelbord(){
        wisSpeelBord();
        for (int i = 0; i < this.boardsize.columns()*this.boardsize.rows(); i++){
            Random random = new Random();
            int randomGetal = random.nextInt(5);
            speelbord.add(generateRandomCandy(randomGetal));
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
    public Iterable<Candy> getSpeelbord() {
        return speelbord;
    }

    public void candyWithIndexSelected(Position posIndex) {
        List<Position> positionsOfSameCandy = (List<Position>) getSameNeighbourPositions(posIndex);
            for (Position element : positionsOfSameCandy) {
                Random random = new Random();
                int randomGetal = random.nextInt(5);
                speelbord.set(element.toIndex(), generateRandomCandy(randomGetal));
                score++;
            }
    }
    Iterable<Position> getSameNeighbourPositions(Position position){
        List<Position> positionNeighbouringList = (List<Position>) position.neighborPositions();
        Candy candyOfIndex = speelbord.get(position.toIndex());
        List<Position> result = new ArrayList<>();
        for(Position element : positionNeighbouringList){
            if(candyOfIndex.equals(speelbord.get(element.toIndex()))){
                result.add(element);
            }
        }
        return result;
    }
    public Candy generateRandomCandy(int randomNumber) {
        Random random = new Random();
        return switch (randomNumber) {
            case 0 -> new Candy.NormalCandy(random.nextInt(4)); // Generate NormalCandy with a random color
            case 1 -> new Candy.ZureMat();
            case 2 -> new Candy.Drop();
            case 3 -> new Candy.Zuurtjes();
            case 4 -> new Candy.Spekjes();
            default -> throw new IllegalStateException("Unexpected value: " + randomNumber);
        };
    }

}


