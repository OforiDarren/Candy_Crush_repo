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
            speelbord.add(selectRandomCandy(rngNumber()));
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
            speelbord.add(selectRandomCandy(rngNumber()));
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
                speelbord.set(element.toIndex(), selectRandomCandy(rngNumber()));
                increaseScore();
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
    public Candy selectRandomCandy(int randomNumber) {
        Random random = new Random();
        return switch (randomNumber) {
            case 0 -> new Candy.ZureMat();
            case 1 -> new Candy.Drop();
            case 2 -> new Candy.Zuurtjes();
            case 3 -> new Candy.Spekjes();
            default -> new Candy.NormalCandy(random.nextInt(4)); // Generate NormalCandy with a random color
        };
    }
    private int rngNumber(){//create random number from 0 to 7
        Random random = new Random();
        return random.nextInt(30);//0-7
    }
    private void increaseScore(){
        score++;
    }
}


