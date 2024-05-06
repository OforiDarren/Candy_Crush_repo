package be.kuleuven.candycrush;

import be.kuleuven.candycrush.Candy.Candy;
import be.kuleuven.candycrush.MultiThreadingCandies.MultithreadingClient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.time.Duration;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.function.Function;

public class CandycrushController {
    @FXML
    public javafx.scene.control.Label loginLabel;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Label Label;
    private int clickCount = 0;
    @FXML
    private Button startButton;

    @FXML
    private AnchorPane paneel;

    @FXML
    private AnchorPane speelbord;

    @FXML
    private TextField playerNameTextInput;
    @FXML
    private Label showPlayerNameLabel;
    private CandycrushModel model;
    public CandycrushView view;
    private String playerName;
    MouseEvent lastMe;
    @FXML
    void initialize() {
        assert Label != null : "fx:id=\"Label\" was not injected: check your FXML file 'Candycrush-view.fxml'.";
        assert startButton != null : "fx:id=\"btn\" was not injected: check your FXML file 'Candycrush-view.fxml'.";
        assert paneel != null : "fx:id=\"paneel\" was not injected: check your FXML file 'Candycrush-view.fxml'.";
        assert speelbord != null : "fx:id=\"speelbord\" was not injected: check your FXML file 'Candycrush-view.fxml'.";
        assert playerNameTextInput != null : "fx:id=\"playerNameTextinput\" was not injected: check your FXML file 'Candycrush-view.fxml'.";
    }
    public void update(){
        view.update();
        updateScore();
    }

    public void onCandyClicked(MouseEvent me){
        model.candyWithIndexSelected2(null, null);
        update();
        // clickCount++;
        if(clickCount == 1){
            lastMe = me;
        }
        else if(clickCount == 2){
           clickCount = 0;


        }
    }
    public static CandycrushModel createBoardFromString(String configuration) {
        var lines = configuration.toLowerCase().lines().toList();
        BoardSize size = new BoardSize(lines.size(), lines.getFirst().length());
        var model = createNewModel(size); // deze moet je zelf voorzien
        for (int row = 0; row < lines.size(); row++)
        {
            var line = lines.get(row);
            for (int col = 0; col < line.length(); col++)
            {
                model.setCandyAt(new Position(row, col, size), characterToCandy(line.charAt(col)));
            }
        }
        return model;
    }
    public static CandycrushModel createNewModel(BoardSize boardsize) {
        return new CandycrushModel("Darren", 0, boardsize);
    }
    private static Candy characterToCandy(char c) {
        return switch(c) {
            case 'o' -> new Candy.NormalCandy(0);
            case '*' -> new Candy.NormalCandy(1);
            case '#' -> new Candy.NormalCandy(2);
            case '@' -> new Candy.NormalCandy(3);
            default -> throw new IllegalArgumentException("Unexpected value: " + c);
        };
    }
    public void onClickedStartaction(ActionEvent actionEvent) {
        // Thread test on seperate board
        MultithreadingClient.main(new String[]{"Threads for candies"});
        // Thread test on seperate board

        if (playerNameTextInput.getText().isEmpty()){
            playerNameTextInput.setText("No name");
        }
        playerName = playerNameTextInput.getText();
        model = createBoardFromString("""
        @@o#
        o*#o
        @@**
        *#@@""");
        // Already get rid of the possible matches that can be found
        view = new CandycrushView(model);
        // Give model the view, controller after recursive updates
        model.setCandyCrushController(this);
        speelbord.getChildren().add(view);
        view.setOnMouseClicked(this::onCandyClicked);
        showPlayerNameLabel.setText(playerName +": "+ model.getScore());
        playerNameTextInput.clear();
        playerNameTextInput.setDisable(true);
        startButton.setDisable(true);
    }
    public void resetGameWithSamePlayer(){
        model.resetScore();
        model.nieuwSpeelbord();
        update();
    }

    public void updateScore(){
        showPlayerNameLabel.setText(playerName +": "+ model.getScore());
    }
    public void onQuitButton(ActionEvent actionEvent) {
        MultithreadingClient.stopCandyThreads();
        Platform.exit();
    }
}