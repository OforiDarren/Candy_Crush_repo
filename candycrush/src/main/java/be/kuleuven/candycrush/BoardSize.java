package be.kuleuven.candycrush;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public record BoardSize(Integer rows, Integer columns) {
    public BoardSize{
        if(columns == null || rows == null || rows <= 0 || columns <= 0){
            throw new IllegalArgumentException("Rijen en/of kolommen zijn nul of minder. Het spel kan niet starten");
        }

    }
    Collection<Position> positions(){
        //0 1 2 3
        //4 5 6 7
        ArrayList<Position> positionArrayList = new ArrayList<>();
        int totalPos = columns*rows;
        for (int i = 0; i < totalPos; i++){
            int rowOfIndex = i / columns;
            int colOfIndex = i % columns;
            positionArrayList.add(new Position(rowOfIndex, colOfIndex, this));
        }

        return positionArrayList;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BoardSize other = (BoardSize) o;
        return Objects.equals(this.rows, other.rows()) && Objects.equals(this.columns, other.columns());
    }
    @Override
    public int hashCode() {
        return Objects.hash(rows, columns);
    }
}
