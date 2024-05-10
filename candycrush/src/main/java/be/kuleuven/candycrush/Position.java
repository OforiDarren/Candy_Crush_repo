package be.kuleuven.candycrush;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public record Position(int rowOfIndex, int colOfIndex, BoardSize boardsize) {
    public Position{
        if(boardsize.columns()-1 < colOfIndex || boardsize.rows()-1 < rowOfIndex || colOfIndex < 0 || rowOfIndex < 0){
            throw new IllegalArgumentException("Position is out of bounds!");
        }
    }
    int toIndex(){
        return (rowOfIndex * boardsize.columns()) + colOfIndex;
    }
    static Position fromIndex(int index, BoardSize boardsize){
        int totalPositions = boardsize.columns() * boardsize.rows();

        if(index < 0 || index >= (totalPositions)){
            //throw exception if index not possible
            throw new IllegalArgumentException("Index doesn't fit in boardsize!");
        }
        else {
            int rowOfIndex = index / boardsize.columns();
            int colOfIndex = index % boardsize.columns();
            return new Position(rowOfIndex, colOfIndex, boardsize);
        }

    }
    Iterable<Position> neighborPositions(){
        ArrayList<Position> positionArrayList = new ArrayList<>();

        int rowbegin = Math.max(0, rowOfIndex-1);
        int rowend = Math.min(boardsize.rows()-1, rowOfIndex+1);

        int colbegin = Math.max(0, colOfIndex-1);
        int colend = Math.min(boardsize.columns()-1, colOfIndex+1);
        for (int i = rowbegin; i <= rowend; i++){
            for (int j = colbegin; j <= colend; j++){
                positionArrayList.add(new Position(i,j,boardsize));
            }
        }
        return positionArrayList;
    }
    @Override
    public String toString() {
        return "Position: (r: " + (rowOfIndex) + ", c: " + (colOfIndex) + ")";
    }
    public boolean isLastColumn(){//is positie laatste in een rij
        return ((colOfIndex+1) % boardsize.columns()) == 0;
    }
    public boolean isLastRow() {return ((rowOfIndex+1) % boardsize.rows()) == 0;}
    public Stream<Position> walkLeft(){
        long num = this.colOfIndex+1;//because row 0 is still a valid row and limit(0) doesn't work
        final int[] indexCol = {this.colOfIndex};
        return Stream.iterate(this,
                position -> {
                        indexCol[0]--;
                        return new Position(this.rowOfIndex,indexCol[0],this.boardsize);})
                .limit(num);
    }
    public Stream<Position> walkRight(){
        long num = this.boardsize.columns()- this.colOfIndex;//because row 0 is still a valid row and limit(0) doesn't work
        final int[] indexCol = {this.colOfIndex};
        return Stream.iterate(this,
                position -> {
                    indexCol[0]++;
                    return new Position(this.rowOfIndex,indexCol[0],this.boardsize);
                })
                .limit(num);
    }
    public Stream<Position> walkUp(){
        long num = this.rowOfIndex+1;//because row 0 is still a valid row and limit(0) doesn't work
        final int[] indexRow = {this.rowOfIndex};
        return Stream.iterate(this,
                position -> {
                        indexRow[0]--;
                return new Position(indexRow[0], this.colOfIndex,this.boardsize);
                })
                .limit(num);
    }
    public Stream<Position> walkDown(){
        long num = boardsize().rows() - this.rowOfIndex;//total amount of rows minus the first position
        final int[] indexRow = {this.rowOfIndex};
        return Stream.iterate(this,
                position -> {
                        indexRow[0]++;
                        return new Position(indexRow[0], this.colOfIndex, this.boardsize);
                })
                .limit(num);
    }
    @Override
    public boolean equals(Object object){
        if(object == null || getClass()!= object.getClass()){
            return false;
        }
        Position other = (Position) object;
        return this.boardsize == other.boardsize &&
                this.rowOfIndex == other.rowOfIndex &&
                this.colOfIndex == other.colOfIndex
                ;
    }
}
