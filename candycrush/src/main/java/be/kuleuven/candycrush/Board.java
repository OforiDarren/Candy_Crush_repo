package be.kuleuven.candycrush;

import be.kuleuven.candycrush.Candy.Candy;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class Board<T> {
    private ArrayList<T> typeList;
    private Boardsize  boardsize;
    private Position position;
    private Candy candy;
    public Board(Iterable<T> iter, Boardsize boardsize){//iterable of your (Candy) object types that represents each cell's content
        typeList = (ArrayList<T>) iter;
        this.boardsize = boardsize;
    }
    public void setPosition(Position position) {
        this.position = position;
    }
    public T getCellAt(Position position){//om de cel op een gegeven positie van het bord op te vragen.
        return typeList.get(position.toIndex());
    }
    public void replaceCellAt(Position position,T newCell){//om de cel op een gegeven positie te vervangen door een meegegeven object.
        typeList.set(position.toIndex(), newCell);
    }
    public void fill(Function<Position, T> cellCreator){//om het hele bord te vullen. De fill-functie heeft als parameter een Function-object (cellCreator) die, gegeven een Positie-object, een nieuw cel-object teruggeeft.
        typeList.clear();
        for (int i = 0; i < this.boardsize.columns()*this.boardsize.rows(); i++){
            T cellContent = cellCreator.apply(position);
            typeList.add(cellContent);
        }
    }
    public Iterable<T> copyTo(Board<T> otherBoard){//die alle cellen van het huidige bord kopieert naar het meegegeven bord. Als het meegegeven bord niet dezelfde afmetingen heeft, gooi je een exception.
        //gooi exception als het lukt
        if(!Objects.equals(boardsize.columns(), otherBoard.boardsize.columns()) && !Objects.equals(boardsize.rows(), otherBoard.boardsize.rows())){
            throw new IllegalArgumentException("De boardsizes komen niet overeen.\r\nFunction -> copyTo");
        }
        else {
            return typeList;
        }

    }
}
