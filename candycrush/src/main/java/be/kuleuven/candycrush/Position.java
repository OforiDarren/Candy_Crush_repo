package be.kuleuven.candycrush;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public record Position(int rowOfIndex, int colOfIndex, Boardsize boardsize) {
    public Position{
        if(boardsize.columns() < colOfIndex || boardsize.rows() < rowOfIndex || colOfIndex < 0 || rowOfIndex < 0){
            throw new IllegalArgumentException("Index is out of bounds!");
        }
    }
    int toIndex(){
        return (rowOfIndex * boardsize.columns()) + colOfIndex;
        //0 1 2 3
        //4 5 6 7
        //8 9 10 11//27
        //12 13 14 15
    }
    static Position fromIndex(int index, Boardsize boardsize){
        int totalPositions = boardsize.columns() * boardsize.rows();

        if(index < 0 || index >= (totalPositions)){
            //throw exception if index not possible
            throw new IllegalArgumentException("Index doesn't fit in boardsize!");
        }else{
            //0 1 2 3
            //4 5 6 7
            int rowOfIndex = index / boardsize.columns();
            int colOfIndex = index % boardsize.columns();
            return new Position(rowOfIndex, colOfIndex, boardsize);
        }

    }
    Iterable<Position> neighborPositions(){
        //0 1 2 3
        //4 5 6 7
        // ex: 6
        ArrayList<Position> positionArrayList = new ArrayList<>();

        int rowbegin = Math.max(0, rowOfIndex-1);
        int rowend = Math.min(boardsize.rows()-1, rowOfIndex+1);

        int colbegin = Math.max(0, colOfIndex-1);
        int colend = Math.min(boardsize.columns()-1, colOfIndex+1);
        /*
              [ 0, 0, 1, 0,
                1, 1, 0, 2,
                2, 0, 1, 3,
                0, 1, 1, 1 ]
         */
        for (int i = rowbegin; i <= rowend; i++){
            for (int j = colbegin; j <= colend; j++){
                positionArrayList.add(new Position(i,j,boardsize));
//                if(i == rowOfIndex && j ==  colOfIndex){
//
//                }else{
//                    positionArrayList.add(new Position(i,j,boardsize));
//                }
            }
        }


        return positionArrayList;
    }
    @Override
    public String toString() {
        return "Position: (r: " + rowOfIndex + ", c: " + colOfIndex + ")";
    }
    boolean isLastColumn(){//is positie laatste in een rij
        return ((colOfIndex+1) % boardsize.columns()) == 0;
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
