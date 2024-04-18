package be.kuleuven.candycrush;
import be.kuleuven.candycrush.Candy.Candy;
import be.kuleuven.candycrush.Candy.NormalCandy;
import be.kuleuven.candycrush.Position;
import be.kuleuven.candycrush.Boardsize;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import be.kuleuven.CheckNeighboursInGrid;
import javafx.geometry.Pos;

public class CandycrushModel {
    private Board<Candy> board;
    private String speler;
    private int score;
    private Boardsize boardsize;
    private Position position;
    Function<Position, Candy> cellCreator = position -> {
        // Create a new cell object using the provided position
        return selectRandomCandy(rngNumber());
    };
    public CandycrushModel(String speler, int score, Boardsize boardsize) {
        this.speler = speler;
        this.score = score;
        this.boardsize = boardsize;
        board = new Board<Candy>(this.boardsize);
        board.fill(cellCreator);
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
    public void nieuwSpeelbord(){
        board.fill(cellCreator);
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
        return board.copyTo(new Board<Candy>(this.boardsize));
    }
    public void candyWithIndexSelected(Position posIndex) {
        List<Position> positionsOfSameCandy = (List<Position>) getSameNeighbourPositions(posIndex);
            for (Position element : positionsOfSameCandy) {
                board.replaceCellAt(element, selectRandomCandy(rngNumber()));
                increaseScore();
            }
    }
    Iterable<Position> getSameNeighbourPositions(Position position){
        List<Position> positionNeighbouringList = (List<Position>) position.neighborPositions();
        Candy candyOfIndex = board.getCellAt(position);
        List<Position> result = new ArrayList<>();
        for(Position element : positionNeighbouringList){
            if(candyOfIndex.equals(board.getCellAt(element))){
                result.add(element);
            }
        }
        return result;
    }
    public static Candy selectRandomCandy(int randomNumber) {
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
    public Iterable<Position> getBoardPositionsOfElement(Candy candy){
        return board.getPositionsOfElement(candy);
    }
//    public Set<List<Position>> findAllMatches(){
//        //concatenate longest horizontal match and longest vertical match
//        return
//    }
    private boolean firstTwoHaveCandy(Candy candy, Stream<Position> streamPositions){
        //
        // Get the first Two positions in the stream
        // Filter them based on same candy
        // Add them to a resulting array
        Position[] result = streamPositions
                                        .limit(2)
                        .filter(position -> board.getCellAt(position).equals(candy))
                                        .toArray(Position[]::new);
        // Check how many candies are added to the list
        return result.length == 2;
    }
//    private Stream<Position> horizontalStartingPositions(){
//
//    }
//    private Stream<Position> verticalStartingPositions(){
//
//    }
//    private List<Position> longestMatchToRight(Position position){
//
//    }
//    List<Position> longestMatchDown(Position position){
//
//    }
}


