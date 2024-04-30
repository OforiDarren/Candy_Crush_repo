package be.kuleuven.candycrush.MultiThreadingCandies;

import be.kuleuven.candycrush.Board;
import be.kuleuven.candycrush.BoardSize;
import be.kuleuven.candycrush.Candy.Candy;
import be.kuleuven.candycrush.CandycrushModel;
import be.kuleuven.candycrush.Position;

import java.util.Random;
import java.util.function.Function;

public class MultithreadingClient {
    private static Thread thread1,thread2;
    public static void main(String[] args) {

        final int maxCandies = 4;
        Random random = new Random();

        Function<Position, Candy> cellCreator = position -> {
            // Create a new cell object using the provided position
            return CandycrushModel.selectRandomCandy(random.nextInt(maxCandies));
        };
        // Maak een bord-object
        Board<Candy> threadsCandyBoard = new Board<>(new BoardSize(2, 2));
        threadsCandyBoard.fill(cellCreator);
        // Start twee threads om continu replaceCellAt te gebruiken
        thread1 = new Thread(new CandyPlacer(threadsCandyBoard));
        thread2 = new Thread(new CandyPlacer(threadsCandyBoard));
        thread1.start();
        thread2.start();

    }

    public static void stopCandyThreads(){
        thread1.interrupt();
        thread2.interrupt();
    }
}
