package be.kuleuven.candycrush;
import be.kuleuven.candycrush.Candy.Candy;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.concurrent.TimeUnit;
public class CandycrushModel {
    private static Board<Candy> board;
    private List<Position> bestMovesList = new ArrayList<>();
    private String speler;
    private int score, bestScore;
    private BoardSize boardsize;
    private Position position;
    Board<Candy> originalBoard;
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
    public void clearBoard(){
        board.emptyBoard();
    }
    public void setPosition(int rowOfIndex, int columnOfIndex) {
        this.position = new Position(rowOfIndex,columnOfIndex,boardsize);
    }
    public Position getPosition(){
        return position;
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
        Board<Candy> tempBoard = new Board<>(this.boardsize);
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
        //System.out.print("FIND ALL MATCHES\n\r");
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
//        positionSetList.stream()
//                .flatMap(List::stream)
//                .forEach(System.out::println);
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
        // See if the positions are one apart, horizontally or vertically
        // Check if arguments are valid position swaps
        if ((posIndex == null || posIndex2 == null) || posIndex == posIndex2) return false;

        if (((Math.abs(posIndex.colOfIndex() - posIndex2.colOfIndex()) == 1) && (posIndex.rowOfIndex() == posIndex2.rowOfIndex()))
            || ((Math.abs(posIndex.rowOfIndex() - posIndex2.rowOfIndex())) == 1 && (posIndex.colOfIndex() == posIndex2.colOfIndex())))
        {   // Horizontal or vertical swap is possible
            if(board.getCellAt(posIndex) == null || board.getCellAt(posIndex2) == null){
                return false;
            }
            else{
                Candy temp = board.getCellAt(posIndex);
                board.replaceCellAt(posIndex, board.getCellAt(posIndex2));
                board.replaceCellAt(posIndex2, temp);
                return true; // Horizontal swap is possible and executed
            }
        }
        return false;
    }
    private boolean checkBoardIfNoMatches(){
        // Check the board swap the candies and check for a comination
        // If there is none return true
        List<Position> matchHoriz, matchVert;
        for (int i = 0; i < boardsize.rows(); i++)
        {
            for (int j = 0; j < boardsize.columns(); j++)
            {
                Position currentPosition = new Position(i,j,boardsize);
                Position positionBelow;
                Position positionToTheRight;
                if(currentPosition.isLastColumn()) positionToTheRight  = currentPosition;
                else positionToTheRight  = new Position(i    ,1+j    , boardsize);
                if(currentPosition.isLastRow()) positionBelow = new Position(i,j,boardsize);
                else positionBelow = new Position(i+1   ,j      , boardsize);


                swapOnePosition(currentPosition, positionToTheRight);
                matchHoriz = new ArrayList<>(findAllMatches().stream().flatMap(List::stream).toList());
                swapOnePosition(currentPosition, positionToTheRight);

                swapOnePosition(currentPosition, positionBelow);
                matchVert = new ArrayList<>(findAllMatches().stream().flatMap(List::stream).toList());
                swapOnePosition(currentPosition, positionBelow);

                if (!matchHoriz.isEmpty() || !matchVert.isEmpty()){
                    return false;
                }
            }
        }
        return true;
    }

    void findBestMove(){
        // This function will check every swappable move in the board and provide the maximum amount of points you can get
        // Base case
        if(checkBoardIfNoMatches()) {
            // If no matches then track back!
            if (bestScore < score) {
                // If a high score was achieved then save it and print out the position swaps to get the most points momentarily
                bestScore = score;
                System.out.print("Beste score mogelijk in dit spel tot nu toe: "+bestScore+"\n" +
                                "De volgende wissels werden uitgevoerd (aantal:"+bestMovesList.size()/2+")\n");
                for(int i = 0; i < bestMovesList.size(); i++){
                    // Format for tidiness
                    if(i % 2 == 0) System.out.println();
                    System.out.println(bestMovesList.get(i));
                }
            }
            return;
        }
        // Door de clearmatches in het begin kan de speler al een score hebben

        for (int i = 0;i < boardsize.rows(); i++)
        {
            for (int j = 0; j < boardsize.columns(); j++)
            {
                // This function swaps all the candies on the board one by one to get the most points possible
                Position currentPosition = new Position(i,j,boardsize);
                Position positionBelow;
                Position positionToTheRight;
                // If we are at the border of our board don't create positions outside of it
                if(currentPosition.isLastColumn()) positionToTheRight = new Position(i,j,boardsize);
                else positionToTheRight = new Position(i,1+j,boardsize);
                if(currentPosition.isLastRow()) positionBelow = new Position(i,j,boardsize);
                else positionBelow = new Position(i+1   ,j      , boardsize);

                // New swap
                Board<Candy> tempBoard = new Board<>(boardsize);
                swapOnePosition(currentPosition, positionToTheRight);
                board.copyTo(tempBoard);
                int tempScore = score;
                // After a match is found save the position swap and try to find another match in that reduced board
                if (matchAfterSwitch())
                {
                    bestMovesList.add(currentPosition);bestMovesList.add(positionToTheRight);
                    findBestMove();
                    bestMovesList.removeLast();bestMovesList.removeLast();
                    score = tempScore;
                    tempBoard.copyTo(board);
                }
                // Unswap
                swapOnePosition(currentPosition, positionToTheRight);


                // New swap
                swapOnePosition(currentPosition, positionBelow);
                board.copyTo(tempBoard);
                tempScore = score;
                if (matchAfterSwitch())
                {
                    bestMovesList.add(currentPosition);bestMovesList.add(positionBelow);
                    findBestMove();
                    bestMovesList.removeLast();bestMovesList.removeLast();
                    score = tempScore;
                    tempBoard.copyTo(board);
                }
                // Unswap
                swapOnePosition(currentPosition, positionBelow);

            }
        }
    }
    public void maximizeScore(){
        // Solve the board for the maximum amount of points
        board.copyTo(originalBoard);
        findBestMove();
        resetScore();
        originalBoard.copyTo(board);
        bestMovesList.clear();
    }
    public void candyWithIndexSelected2(Position posIndex, Position posIndex2) {
        // Perform the swap on the board
        // If one of the two candies are zero then just return
        if(!swapOnePosition(posIndex, posIndex2)) return;
        // Make the swap slower so you can see what happens
        try{
            TimeUnit.SECONDS.sleep(1);
        }
        catch(InterruptedException ignored){}
        // No match after the swap? Unswap the candies!
        if(!matchAfterSwitch()) swapOnePosition(posIndex, posIndex2);
        // Else save the position swaps in the list
        else bestMovesList.add(posIndex);bestMovesList.add(posIndex2);
        // Update the GUI
        candycrushController.update();
    }

    public void clearMatch(List<Position> match){
        // Base case if no more match positions given
        if(match.isEmpty()) return;
        // Make candy null
        board.replaceCellAt(match.getLast(), null);
        clearMatch(match.subList(0, match.size()-1));
    }
    public void fallDownTo(Position position){
        // Base case
        if(position.rowOfIndex() == 0) return;

        int i = position.rowOfIndex();
        Position oneRowAbove = new Position(--i, position.colOfIndex(),boardsize);

        if(board.getCellAt(position) == null)
        {
            board.replaceCellAt(position, board.getCellAt(oneRowAbove));
            board.replaceCellAt(oneRowAbove, null);
        }
        fallDownTo(oneRowAbove);
    }
    public boolean updateBoard(){
        // If there is no match left then return false
        List<Position> match = new ArrayList<>(findAllMatches().stream().flatMap(List::stream).toList());
        match.sort(Comparator.comparingInt(Position::rowOfIndex));
        match.reversed();
        // It's already a guarantee that a match is minimum 3 long
        if(!match.isEmpty())
        {
            score+=match.size();
            //System.out.print("Huidige score: "+ score+"\n");
            clearMatch(match);
            for(Position pos : match){
                fallDownTo(pos);
            }
            // If there were matches call the function again to see if there are more
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
    1. Swap candies: Swap every candy with its neighboring candy horizontally and vertically.
    2. See if there was a match with matchafterswitch()
    3. (updateBoard 3.) Clear matches: Clear all matched candies and make them fall down.
    4. a (Inside step 4) Check for new matches: Call updateBoard() again to see if new matches are formed due to candies falling down.
    5. If there are no new matches, backtrack by undoing the previous swap and try the next possible swap.
    Repeat steps 1-5: Repeat steps 1 to 5 until you find the best move. (remember the one with the best score)
     */
}


