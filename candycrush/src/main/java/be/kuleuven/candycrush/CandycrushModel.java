package be.kuleuven.candycrush;
import be.kuleuven.candycrush.Candy.Candy;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CandycrushModel {
    /*
              [
              0, 0, 1, 0,
              1, 1, 0, 2,
              2, 0, 2, 3,
              0, 1, 1, 1
              ]
         */
    private static Board<Candy> board;
    private Board<Candy> originalBoard;
    private String speler;
    private int score, bestScore;
    private BoardSize boardsize;
    private Position position;
    private CandycrushController candycrushController;
    Function<Position, Candy> cellCreator = position -> {
        // Create a new cell object using the provided position
        return selectRandomCandy(rngNumber());
    };
    public void setCandyAt(Position position, Candy candy){
        board.replaceCellAt(position, candy);
    }
    public CandycrushModel(String speler, int score, BoardSize boardsize) {
        this.speler = speler;
        this.score = score;
        this.boardsize = boardsize;
        board = new Board<>(this.boardsize);
        originalBoard = new Board<>(this.boardsize);
    }
    public void setPosition(int rowOfIndex, int columnOfIndex) {
        this.position = new Position(rowOfIndex,columnOfIndex,boardsize);
    }
    public Position getPosition(){
        return position;
    }
    public BoardSize getBoardsize() {
        return boardsize;
    }
    public void nieuwSpeelbord(){
        board.fill(cellCreator);
        updateBoard();
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
    public Board<Candy> getSpeelbord() {
        Board<Candy> tempBoard = new Board<Candy>(this.boardsize);
        board.copyTo(tempBoard);
        return tempBoard;
    }
    public void candyWithIndexSelected(Position posIndex) {
        List<Position> positionsOfSameCandy = (List<Position>) getSameNeighbourPositions(posIndex);
            for (Position element : positionsOfSameCandy) {
                //board.replaceCellAt(element, selectRandomCandy(rngNumber()));
                board.replaceCellAt(element, null);
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
                                        .filter(position -> board.getCellAt(position) != null && board.getCellAt(position).equals(candy))
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
                    .takeWhile(pos -> board.getCellAt(pos) != null &&
                            board.getCellAt(position) != null &&
                            board.getCellAt(position).equals(board.getCellAt(pos))
                    )
                            .sorted(Comparator.comparingInt(Position::rowOfIndex))
                    .toList();
    }
    public List<Position> longestMatchDown(Position position){
            return position
                    .walkDown()
                    .takeWhile(pos -> board.getCellAt(position) != null &&
                            board.getCellAt(pos) != null &&
                            board.getCellAt(position).equals(board.getCellAt(pos)))
                    .sorted(Comparator.comparingInt(Position::rowOfIndex))
                    .collect(Collectors.toList());
    }
    private boolean swapOnePosition(Position posIndex, Position posIndex2){
        // See if the positions are one apart
        // Check if the positions are one off horizontally
        if (Math.abs(posIndex.colOfIndex() - posIndex2.colOfIndex()) == 1 && posIndex.rowOfIndex() == posIndex2.rowOfIndex()
            || Math.abs(posIndex.rowOfIndex() - posIndex2.rowOfIndex()) == 1 && posIndex.colOfIndex() == posIndex2.colOfIndex())
        {   // Horizontal or vertical swap is possible
            if(board.getCellAt(posIndex) == null || board.getCellAt(posIndex2) == null){
                return false;
            }
            else{
                Candy temp = board.getCellAt(posIndex);
                board.replaceCellAt(posIndex, board.getCellAt(posIndex2));
                board.replaceCellAt(posIndex2, temp);
                return true; // Horizontal swap is possible
            }
        }
        return false;
    }
    // This function will check every swappable move in the board and provide the maximum amount of points you can get
    void findBestMove(){

        // Door de clearmatches in het begin kan de speler al een score hebben

        for (int i = 0;i < boardsize.rows()-1; i++){
            for (int j = 0; j < boardsize.columns()-1; j++)
            {
                // This function changes candies on the original board and gives them to a function which then swaps
                // All the candies on that board to get the most points of that.
                Position currentPosition = new Position(i,j,boardsize);
                Position positionToTheRight = new Position(i    ,1+j    , boardsize);
                Position positionBelow = new Position(i+1   ,j      , boardsize);
                // Swapped 2 candies in board.
                // Find out if it is a match
                // originalBoard = board;
                simulateSwap(currentPosition, positionToTheRight);
                simulateSwap(currentPosition, positionBelow);
            }
        }
    }
    public void simulateSwap(Position position1, Position position2){
        if(swapOnePosition(position1, position2)){
            // originalBoard = board;
            // Go over every possible swap inside a swap, save the swap that brought you the most amount of points
            if (matchAfterSwitch())
            {
                // After a match in the board
                // Find the best move again for this (temporary) board
                // Save the bestScore if score is higher (furthest solution in the game yet)
                if(score > bestScore) bestScore = score;
                System.out.print("Beste score mogelijk in dit spel tot nu toe: "+bestScore+"\n");
                // Find best move in the board after the swap and cleared board
                findBestMove();
            }
            // Undo the swap
            // board = originalBoard;
            swapOnePosition(position1, position2);

        }
    }
    public void candyWithIndexSelected2(Position posIndex, Position posIndex2) {
        // Solve the board for the maximum amount of points
        updateBoard();
        bestScore = score;
        board.copyTo(originalBoard);
        // Find best move with given board
        findBestMove();
        // System.out.print("Beste score mogelijk in dit spel: "+bestScore);
        // If one of the two candies are zero then just return
//        if(board.getCellAt(posIndex) == null || board.getCellAt(posIndex2) == null) return;
//        if(!swapOnePosition(posIndex, posIndex2)) return;
//        // Copy the board (maybe swap doesn't lead to any combo's)
//        Board<Candy> originalBoard = board;
//        // Perform the swap on the board because you don't know if it's doable
//        Candy temp = board.getCellAt(posIndex);
//        board.replaceCellAt(posIndex, board.getCellAt(posIndex2));
//        board.replaceCellAt(posIndex2, temp);
//        updateBoard();
    }

    public void clearMatch(List<Position> match){
        if(match.isEmpty()) return;
        board.replaceCellAt(match.getLast(), null);
        clearMatch(match.subList(0, match.size()-1));
    }
    public void fallDownTo(Position position){
        if(position.rowOfIndex() == 0) return;

        int i = position.rowOfIndex();
        Position oneRowAbove = new Position(--i, position.colOfIndex(),boardsize);

        if(board.getCellAt(position) == null){
            board.replaceCellAt(position, board.getCellAt(oneRowAbove));
            board.replaceCellAt(oneRowAbove, null);
        }
        fallDownTo(oneRowAbove);
    }
    public boolean updateBoard(){
        // If there is no match left then return
        List<Position> match = new ArrayList<>(findAllMatches().stream().flatMap(List::stream).toList());
        match.sort(Comparator.comparingInt(Position::rowOfIndex));
        match.reversed();
        // It's already a guarantee that a match is minimum 3 long
        if(!match.isEmpty()){
            score+=match.size();
            //System.out.print("Huidige score: "+ score+"\n");
            clearMatch(match);
            for(Position pos : match){
                fallDownTo(pos);
            }
            // If there were matches call the function again to see if there are more
            candycrushController.update();
            updateBoard();
            return true;
        }
        return false;
    }
    public void setCandyCrushController(CandycrushController controller){ this.candycrushController = controller;}
    private boolean matchAfterSwitch(){
        return updateBoard();
    }
    /*
    1. Clear all mathes and let htem fall down
    2. Swap candies: Swap every candy with its neighboring candy horizontally and vertically.
    3. Update the board: Call the updateBoard() function to find all matches on the board after a single swap
    4. (updateBoard 3.) Clear matches: Clear all matched candies and make them fall down.
    4. a (Inside step 4) Check for new matches: Call updateBoard() again to see if new matches are formed due to candies falling down.
    5. If there are no new matches, backtrack by undoing the previous swap and try the next possible swap.
    Repeat steps 1-5: Repeat steps 1 to 5 until you find the best move. (remember the one with the ebst score)
     */
}


