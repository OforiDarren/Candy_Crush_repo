package be.kuleuven.candycrush;

import javafx.geometry.Pos;
import org.junit.jupiter.api.Test;

import be.kuleuven.candycrush.Boardsize;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CandycrushModelTests {
    Boardsize boardsize = new Boardsize(7,7);
   /*
    @Test
    public void accepteert_Het_Model_Mijn_argumenten_Naam(){
        CandycrushModel model = new CandycrushModel("Darren", 0, boardsize);
        String result = model.getSpeler();
        assert (result.equals("Darren"));
    }
    @Test
    public void accepteert_Het_Model_Mijn_argumenten_Nummer_Als_Naam(){
        CandycrushModel model = new CandycrushModel("65314", 0, boardsize);
        String result = model.getSpeler();
        assert (result.equals("65314"));
    }
    @Test
    public void accepteert_Het_Model_Mijn_argumenten_Score(){
        int score = 47;
        CandycrushModel model = new CandycrushModel("Darren", score, boardsize);
        int result = model.getScore();
        assert (result == score);
    }
    @Test
    public void accepteert_Het_Model_Mijn_argumenten_Width(){
        int width = 7;
        CandycrushModel model = new CandycrushModel("Darren", 0, boardsize);
        int result = model.getBoardsize().columns();
        assert (result == width);
    }
    @Test
    public void accepteert_Het_Model_Mijn_argumenten_Height(){
        int height = 7;
        CandycrushModel model = new CandycrushModel("Darren", 0, new Boardsize(7,8));
        int result = model.getBoardsize().rows();
        assert (result == height);
    }
    @Test
    public void accepteert_Het_Model_Mijn_argumenten_Naam_Is_Null(){
        CandycrushModel model = new CandycrushModel(null, 0, boardsize);
        String result = model.getSpeler();
        assert (result.equals("Default"));
    }
//    @Test
//    public void accepteert_Het_Model_Mijn_argumenten_WidthIsNul(){
//        Integer value = null;
//        CandycrushModel model = new CandycrushModel("Darren", 0, new Boardsize(null, 7));
//        Integer result = model.getBoardsize().rows();
//        assert (result == value);
//    }
//    @Test
//    public void accepteert_Het_Model_Mijn_argumenten_HeightIsNul(){
//        int value = 2;
//        CandycrushModel model = new CandycrushModel("Darren", 0, 7, null);
//        int result = model.getHeight();
//        assert (result == value);
//    }
//    @Test
//    public void accepteert_Het_Model_Mijn_argumenten_Paneelbord_is_niet_nul(){
//        CandycrushModel model = new CandycrushModel("Darren", 0, null);
//        assertNotNull(model.getSpeelbord());
//    }
//    @Test
//    public void testSnoepjeMetGeselecteerdeIndex(){
//        CandycrushModel model = new CandycrushModel("Darren", 0, 3,3);
//        // Assuming you know the index based on your specific test scenario
//        int index = 4;
//
//        // Mock a specific grid configuration for testing
//        ArrayList<Integer> testSpeelbord = new ArrayList<>(List.of(
//                1, 2, 3,
//                4, 1, 2,
//                1, 4, 1
//        ));
//        model.testSetSpeelbord(testSpeelbord);
//        model.candyWithIndexSelected(index);
//        int score = 4;
//        // Check if the method behaves as expected
//        assert (score == model.getScore()); // Assuming 4 elements (including the selected one) are changed
//        //Snel iets toegevoegd om ci te testenfdbkdbvkjbfdkjnkfvbkjbjebkvbfejk
//    }
//    @Test
//    public void boardSizeKanNietNulZijn(){
//
//        assertThrows(IllegalArgumentException.class, () -> {
//            Boardsize boardsize = new Boardsize(0,0);
//        });
//    }
//    @Test
//    public void positionMoetGeldigeIndexHebben(){
//        Boardsize boardsize = new Boardsize(1,4);
//
//        assertThrows(IllegalArgumentException.class, () -> {
//            Position position = new Position(4,4, boardsize);
//        });
//    }
//    @Test
//    public void positionFunctieToIndex(){
//        int checkIndex = 15;
//        Boardsize boardsize = new Boardsize(4,4);
//        Position position = new Position(4,4, boardsize);
//        assert(checkIndex == position.toIndex());
//
//    }
//
//    @Test
//    public void positionFromIndexGeeftHetDeJuistePositieTerug(){
//        assert (Position.fromIndex(3, new Boardsize(4, 4)).equals(new Position(0, 3, new Boardsize(4, 4))));
//    }
//    @Test
//    public void positionNeighbouringIdsWerktDeFunctieZoalsHetHoort(){
//        /*
//              [ 0, 0, 1, 0,
//                1, 1, 0, 2,
//                2, 0, 1, 3,
//                0, 1, 1, 1 ]
//         */
//        Boardsize boardsize = new Boardsize(4,4);
//        Position position = new Position(0,3, boardsize);
//        ArrayList<Position> p2 = (ArrayList<Position>) position.neighborPositions();
//        ArrayList<Position> position1 = new ArrayList<>();
//        position1.add(new Position(0,2,boardsize));
//        position1.add(new Position(0, 3, boardsize));
//        position1.add(new Position(1,2,boardsize));
//        position1.add(new Position(1,3,boardsize));
//        assert(position1.equals(p2));
//    }
//    @Test
//    public void isLastColumnGeeftHetDeJuisteIndexTerug(){
//        Boardsize boardsize = new Boardsize(4,4);
//        Position position = new Position(0,3, boardsize);
//        assert(position.isLastColumn());
//    }
//    @Test
//    public void GiveBackEveryPositionFromTheBoard(){
//        /*
//              [ 0, 0, 1, 0,
//                1, 1, 0, 2,
//                2, 0, 1, 3,
//                0, 1, 1, 1 ]
//         */
//        Boardsize boardsize = new Boardsize(4,4);
//        ArrayList<Position> positionArrayListcompare = (ArrayList<Position>) boardsize.positions();
//        ArrayList<Position> positionArrayList = new ArrayList<>();
//        for (int i = 0; i < boardsize.columns()* boardsize.rows(); i++){
//            int rowOfIndex = i / boardsize.columns();
//            int colOfIndex = i % boardsize.columns();
//            positionArrayList.add(new Position(rowOfIndex, colOfIndex, boardsize));
//        }
//        assert (positionArrayListcompare.equals(positionArrayList));
//    }
/*
    @Test
    public void Geeft_CheckNeighBourInGrid_de_juiste_resultaten_terug(){
        CandycrushModel model = new CandycrushModel(null, 0, 7, 7);
            int i = 1;

            Iterator<Integer> iter = model.getSpeelbord().iterator();
            while(iter.hasNext()){
                int candy = iter.next();
                System.out.print(candy);
                if(i% model.getWidth()==0){
                    System.out.print("\n");
                    i = 1;
                }
                i++;
            }
            System.out.print("\n");
        }
        assert (result.equals("Default"));
    }
*/
}