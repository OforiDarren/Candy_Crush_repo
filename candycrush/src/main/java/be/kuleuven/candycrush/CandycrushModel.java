package be.kuleuven.candycrush;
import be.kuleuven.candycrush.Candy.Candy;
import be.kuleuven.candycrush.Candy.NormalCandy;
import be.kuleuven.candycrush.Position;
import be.kuleuven.candycrush.Boardsize;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import be.kuleuven.CheckNeighboursInGrid;
import javafx.geometry.Pos;

public class CandycrushModel {
    /*
              [
              0, 0, 1, 0,
              1, 1, 0, 2,
              2, 0, 2, 3,
              0, 1, 1, 1
              ]
         */
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
        board = new Board<>(this.boardsize);
        board.fill(cellCreator);
        //horizontalStartingPositions().forEach(System.out::println);
        //verticalStartingPositions().forEach(System.out::println);
        findAllMatches();
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
        //horizontalStartingPositions().forEach(System.out::println);
        //verticalStartingPositions().forEach(System.out::println);
        findAllMatches();
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

    public Set<List<Position>> findAllMatches(){
        //concatenate the longest horizontal match and longest vertical match
        // Collect horizontal longest matches into a Set<List<Position>>
        System.out.print("FIND ALL MATCHES\n\r");
        Set<List<Position>> positionSetList = new HashSet<>();

        positionSetList.addAll(
                horizontalStartingPositions()
                        .map(this::longestMatchToRight)
                        .filter(element -> element.size() >= 3)
                        .collect(Collectors.toSet())
        );

// Collect vertical longest matches into a Set<List<Position>>
        positionSetList.addAll(
                verticalStartingPositions()
                        .map(this::longestMatchDown)
                        .filter(element -> element.size() >= 3)
                        .collect(Collectors.toSet())
        );

        positionSetList.stream()
                .flatMap(List::stream)
                .forEach(System.out::println);
        return positionSetList;
    }


    private boolean firstTwoHaveCandy(Candy candy, Stream<Position> streamPositions){
        // Get the first Two positions in the stream
        // Filter them based on same candy
        // Add them to a resulting array
        Position[] result = streamPositions
                                        .limit(2)
                                        .filter(position -> board.getCellAt(position).equals(candy))
                                        .toArray(Position[]::new);
        // Check how many candies are added to the list
        // To make sure both positions were correct
        // Or else give false
        return result.length == 2;
    }


    public Stream<Position> horizontalStartingPositions(){
        //System.out.print("Horizontal starting positions: \n\r");
        Stream<Position> positionStream = boardsize.positions().stream();
        return positionStream
                .filter(position -> !(firstTwoHaveCandy(board.getCellAt(position), position.walkLeft())))
                .sorted(Comparator.comparingInt(Position::rowOfIndex));
    }
    public Stream<Position> verticalStartingPositions(){
        //System.out.print("Vertical starting positions: \r\n");
        Stream<Position> positionStream = boardsize.positions().stream();
        return positionStream
                .filter(position -> !(firstTwoHaveCandy(board.getCellAt(position), position.walkUp())))
                .sorted(Comparator.comparingInt(Position::rowOfIndex));
    }

    public List<Position> longestMatchToRight(Position position){
                    return position
                    .walkRight()
                    .takeWhile(pos -> board.getCellAt(position).equals(board.getCellAt(pos)))
                            .sorted(Comparator.comparingInt(Position::rowOfIndex))
                    .toList();
    }
    public List<Position> longestMatchDown(Position position){
            return position
                    .walkDown()
                    .takeWhile(pos -> board.getCellAt(position).equals(board.getCellAt(pos)))
                    .sorted(Comparator.comparingInt(Position::rowOfIndex))
                    .collect(Collectors.toList());
    }
}


