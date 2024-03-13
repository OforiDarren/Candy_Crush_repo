package be.kuleuven.candycrush;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CandycrushModelTests {

    @Test
    public void accepteert_Het_Model_Mijn_argumenten_Naam(){
        CandycrushModel model = new CandycrushModel("Darren", 0, 7, 7);
        String result = model.getSpeler();
        assert (result.equals("Darren"));
    }
    @Test
    public void accepteert_Het_Model_Mijn_argumenten_Nummer_Als_Naam(){
        CandycrushModel model = new CandycrushModel("65314", 0, 7, 7);
        String result = model.getSpeler();
        assert (result.equals("65314"));
    }
    @Test
    public void accepteert_Het_Model_Mijn_argumenten_Score(){
        int score = 47;
        CandycrushModel model = new CandycrushModel("Darren", score, 7, 7);
        int result = model.getScore();
        assert (result == score);
    }
    @Test
    public void accepteert_Het_Model_Mijn_argumenten_Width(){
        int width = 7;
        CandycrushModel model = new CandycrushModel("Darren", 0, width, 7);
        int result = model.getWidth();
        assert (result == width);
    }
    @Test
    public void accepteert_Het_Model_Mijn_argumenten_Height(){
        int height = 8;
        CandycrushModel model = new CandycrushModel("Darren", 0, 7, height);
        int result = model.getHeight();
        assert (result == height);
    }
    @Test
    public void accepteert_Het_Model_Mijn_argumenten_Naam_Is_Null(){
        CandycrushModel model = new CandycrushModel(null, 0, 7, 7);
        String result = model.getSpeler();
        assert (result.equals("Default"));
    }
    @Test
    public void accepteert_Het_Model_Mijn_argumenten_WidthIsNul(){
        int value = 2;
        CandycrushModel model = new CandycrushModel("Darren", 0, null, 7);
        int result = model.getWidth();
        assert (result == value);
    }
    @Test
    public void accepteert_Het_Model_Mijn_argumenten_HeightIsNul(){
        int value = 2;
        CandycrushModel model = new CandycrushModel("Darren", 0, 7, null);
        int result = model.getHeight();
        assert (result == value);
    }
    @Test
    public void accepteert_Het_Model_Mijn_argumenten_Paneelbord_is_niet_nul(){
        CandycrushModel model = new CandycrushModel("Darren", 0, null, null);
        assertNotNull(model.getSpeelbord());
    }
    @Test
    public void testSnoepjeMetGeselecteerdeIndex(){
        CandycrushModel model = new CandycrushModel("Darren", 0, 3,3);
        // Assuming you know the index based on your specific test scenario
        int index = 4;

        // Mock a specific grid configuration for testing
        ArrayList<Integer> testSpeelbord = new ArrayList<>(List.of(
                1, 2, 3,
                4, 1, 2,
                1, 4, 1
        ));
        model.testSetSpeelbord(testSpeelbord);
        model.candyWithIndexSelected(index);
        int score = 4;
        // Check if the method behaves as expected
        assert (score == model.getScore()); // Assuming 4 elements (including the selected one) are changed
    }

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