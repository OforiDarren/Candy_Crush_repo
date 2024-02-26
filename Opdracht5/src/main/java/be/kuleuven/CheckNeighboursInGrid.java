package be.kuleuven;
import java.util.*;

public class CheckNeighboursInGrid {
    private static Integer[] iterableToArray(Iterable<Integer> iterable) {
        // Convert iterable to list
        List<Integer> list = new ArrayList<>();
        for (Integer element : iterable) {
            list.add(element);
        }

        // Convert list to array
        return list.toArray(new Integer[0]);
    }
    private static int getValueAtIndex(Iterable<Integer> iterable, int targetIndex) {
        Iterator<Integer> iterator = iterable.iterator();

        int currentIndex = 0;

        while (iterator.hasNext()) {
            int currentValue = iterator.next();

            if (currentIndex == targetIndex) {
                return currentValue;
            }

            currentIndex++;
        }

        throw new IndexOutOfBoundsException("Index " + targetIndex + " is out of bounds");
    }
    public static Iterable<Integer> getSameNeighboursIds(Iterable<Integer> grid, int width, int height, int indexToCheck){
        Integer []gridInArray = iterableToArray(grid);
        /*
                [ 0, 0, 1, 0,
                1, 1, 0, 2,
                2, 0, 1, 3,
                0, 1, 1, 1 ]
                */
        //result iterable int
        List<Integer> result = new ArrayList<>();
        //Represent 1D array as 2D grid with grid and height
        //width;
        //height;
        //use index to calculate which integer you should check
        //indexToCheck;
        //check for row above and below
        int rowofindextoCheck = indexToCheck / 4;
        int columnofindextoCheck = indexToCheck-(rowofindextoCheck * 4);
        //check for equal numbers in these rows (use for each loop)
        //don't go out of bounds
        //have value of index ready to check for other equals
        int valofindextoCheck = getValueAtIndex(grid, indexToCheck);
        //write functions to know where index is at (edge or not)

        //check only 3x3 matrix surrounding the given index
        //rows + columns
        //establish borders of 3x3
        int rowbegin = Math.max(0, rowofindextoCheck-1);
        int rowend = Math.min(height-1, rowofindextoCheck+1);

        int colbegin = Math.max(0, columnofindextoCheck-1);
        int colend = Math.min(width-1, columnofindextoCheck+1);
        /*
              [ 0, 0, 1, 0,
                1, 1, 0, 2,
                2, 0, 1, 3,
                0, 1, 1, 1 ]
         */
        for (int i = rowbegin; i <= rowend; i++){
            for (int j = colbegin; j <= colend; j++){
                int currentIndex = i * width + j; //navigating through 3x3
                int tempVal = gridInArray[currentIndex];
                if (tempVal == valofindextoCheck && currentIndex != indexToCheck) {//als de waarde gelijk is aan het getal dat gecheckt moet worden en het is niet het getal zelf
                    result.add(currentIndex);//voeg het toe in de lijst
                }
            }
        }
        //return the index of the numbers that are equal to it
        return result;
    }
}