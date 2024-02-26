package be.kuleuven;
import java.util.*;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("Hello and welcome to task 5!");
        // Create a 1D array to represent a 2D grid
        List<Integer> grid = Arrays.asList(0, 0, 1, 0, 1, 1, 0, 2, 2, 0, 1, 3, 0, 1, 1, 1);
        /*
              [ 0, 0, 1, 0,
                1, 1, 0, 2,
                2, 0, 1, 3,
                0, 1, 1, 1 ]
         */
        Iterable<Integer> result = CheckNeighboursInGrid.getSameNeighboursIds(grid, 4, 4, 7);
        System.out.print("Result: ");
        for (Integer value : result) {
            System.out.print(value + " ");
        }
    }
}