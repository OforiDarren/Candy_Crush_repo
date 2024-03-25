package be.kuleuven.candycrush;

import java.util.ArrayList;

public record Boardsize(Integer rows, Integer columns) {
    public Boardsize{
        if(columns == null || rows == null || rows <= 0 || columns <= 0){
            throw new IllegalArgumentException("Rijen en/of kolommen zijn nul of minder. Argumenten zijn de default waarden 7 en 7.");
        }

    }
    Iterable<Position> positions(){
        //0 1 2 3
        //4 5 6 7
        // ex: 6
        ArrayList<Position> positionArrayList = new ArrayList<>();
        int totalPos = columns*rows;
        for (int i = 0; i < totalPos; i++){
            int rowOfIndex = i / columns;
            int colOfIndex = i % columns;
            positionArrayList.add(new Position(rowOfIndex, colOfIndex, this));
        }

        return positionArrayList;
    }

}
