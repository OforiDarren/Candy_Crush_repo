package be.kuleuven.candycrush;

import be.kuleuven.candycrush.Candy.Candy;

import java.util.*;
import java.util.function.Function;

public class Board<T> {
    private volatile Map<Position,T>  map;
    private volatile Map<T, List<Position>> reverseMap;
   private volatile BoardSize  boardsize;
    public Board(BoardSize boardsize){
        this.boardsize = boardsize;
        map = new HashMap<>(boardsize.columns()* boardsize.rows());//map for your cells content
        reverseMap = new HashMap<>(boardsize.columns()* boardsize.rows());
    }
    public BoardSize getBoardsize(){
        return boardsize;
    }
    public synchronized T getCellAt(Position position){//om de cel op een gegeven positie van het bord op te vragen.
        return this.map.get(position);
        //return typeList.get(position.toIndex());
    }
    public synchronized void replaceCellAt(Position position, T newCell) {
        T oldCellContent = map.get(position);
        //if(newCell != null) {
            map.put(position, newCell);
            List<Position> positions = reverseMap.get(oldCellContent);
            if (positions != null) {
                positions.remove(position);
                if (positions.isEmpty()) {
                    reverseMap.remove(oldCellContent);
                }
            }
            reverseMap.computeIfAbsent(newCell, k -> new ArrayList<>()).add(position);
        //}
    }
    public synchronized void fill(Function<Position, T> cellCreator){//om het hele bord te vullen. De fill-functie heeft als parameter een Function-object (cellCreator) die, gegeven een Positie-object, een nieuw cel-object teruggeeft.
        map.clear();
        reverseMap.clear();
        for (int i = 0; i < this.boardsize.columns()*this.boardsize.rows(); i++){
            Position positionIndex = Position.fromIndex(i,this.boardsize);
            T cellContent = cellCreator.apply(positionIndex);
            map.put(positionIndex, cellContent);
            reverseMap.computeIfAbsent(cellContent, k -> new ArrayList<>()).add(positionIndex);
        }
    }
    public synchronized void copyTo(Board<T> otherBoard)
    {   // Die alle cellen van het huidige bord kopieert naar het meegegeven bord. Als het meegegeven bord niet dezelfde afmetingen heeft, gooit het een exception.
        // Gooi exception als het lukt
        if(!Objects.equals(boardsize.columns(), otherBoard.boardsize.columns()) && !Objects.equals(boardsize.rows(), otherBoard.boardsize.rows()))
        {
            throw new IllegalArgumentException("De boardsizes komen niet overeen.\r\nFunction -> copyTo");
        }
        else
        {
            for (Map.Entry<Position, T> entry : map.entrySet()) {
                Position position = entry.getKey();
                T value = entry.getValue();
                otherBoard.map.put(new Position(position.rowOfIndex(), position.colOfIndex(), otherBoard.boardsize), value);
            }
        }
    }
    // Methode om alle posities van een element (cel) op te halen
    public synchronized List<Position> getPositionsOfElement(T element) {
        return reverseMap.getOrDefault(element, Collections.emptyList());
    }
    public void emptyBoard(){
        map.clear();
    }
}