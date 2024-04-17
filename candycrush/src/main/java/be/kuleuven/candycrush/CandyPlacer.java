package be.kuleuven.candycrush;

import be.kuleuven.candycrush.Candy.Candy;

import java.util.Random;

import be.kuleuven.candycrush.CandycrushModel;

public class CandyPlacer implements Runnable{

    private final Board<Candy> board;
    private final Random random;
    private final int maxCandies = 4;

        public CandyPlacer(Board<Candy> board) {
            this.board = board;
            this.random = new Random();
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                // Generate random position
                int x = random.nextInt(board.getBoardsize().columns());
                int y = random.nextInt(board.getBoardsize().rows());

                // Generate random candy
                Candy candy = CandycrushModel.selectRandomCandy(random.nextInt(maxCandies));

                // Place the random candy at the random position on the board
                board.replaceCellAt(new Position(x, y, board.getBoardsize()), candy);

                // sleep and then replace cell again
                try { //busy waiting
                    Thread.sleep(500);//only interrupted exception thrown during sleep
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                    // Restore the interrupted status because the catch will,
                    // Reset the status of the interrupt for the while loop condition (so it stops)
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
}
