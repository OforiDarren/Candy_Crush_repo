package be.kuleuven.candycrush;

import java.util.*;
import java.util.function.Function;

public class Board<T> {
    private ArrayList<T> typeList;
    private Boardsize  boardSize;
    private Position position;
    public Board(Iterable<T> iter, Boardsize boardsize){//iterable of your (Candy) object types that represents each cell's content
        typeList = (ArrayList<T>) iter;
        this.boardSize = boardsize;
    }
    public Boardsize getBoardsize(){
        return boardSize;
    }
    public T getCellAt(Position position){//om de cel op een gegeven positie van het bord op te vragen.
        return typeList.get(position.toIndex());
    }
    public void replaceCellAt(Position position,T newCell){//om de cel op een gegeven positie te vervangen door een meegegeven object.
        typeList.set(position.toIndex(), newCell);
    }
    public void fill(Function<Position, T> cellCreator){//om het hele bord te vullen. De fill-functie heeft als parameter een Function-object (cellCreator) die, gegeven een Positie-object, een nieuw cel-object teruggeeft.
        typeList.clear();
        for (int i = 0; i < this.boardSize.columns()*this.boardSize.rows(); i++){
            T cellContent = cellCreator.apply(position);
            typeList.add(cellContent);
        }
    }
    public void copyTo(Board<T> otherBoard){//die alle cellen van het huidige bord kopieert naar het meegegeven bord. Als het meegegeven bord niet dezelfde afmetingen heeft, gooi je een exception.
        //gooi exception als het lukt
        if(!Objects.equals(boardSize.columns(), otherBoard.boardSize.columns()) && !Objects.equals(boardSize.rows(), otherBoard.boardSize.rows())){
            throw new IllegalArgumentException("De boardsizes komen niet overeen.\r\nFunction -> copyTo");
        }
        else {
            for (int i = 0; i < boardSize.rows()* boardSize.columns(); i++) {
                T cell = typeList.get(i);
                otherBoard.replaceCellAt(Position.fromIndex(i, boardSize), cell);
            }
        }

    }
}
