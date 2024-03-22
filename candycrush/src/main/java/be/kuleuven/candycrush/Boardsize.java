package be.kuleuven.candycrush;

public record Boardsize(int rows, int columns) {
    public Boardsize{
        if(rows == 0 || columns == 0){
            throw new IllegalArgumentException("Rijen en/of kolommen zijn nul");
        }
        Iterable<Position> positions(){

        }
    }

}
