package be.kuleuven.candycrush;

import be.kuleuven.candycrush.Candy.Candy;
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
    private Random random = new Random();
    Thread thread1, thread2;
    static final int maxCandies = 30;
    @FXML
    public javafx.scene.control.Label loginLabel;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Label Label;

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
    private CandycrushView view;
    private String playerName;
    private int playerScore;
    private int score;
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
            //int candyIndex = view.getIndexOfClicked(me);
            model.candyWithIndexSelected(view.getIndexOfClicked(me));
            update();
    }
    Function<Position, Candy> cellCreator = position -> {
        // Create a new cell object using the provided position
        return CandycrushModel.selectRandomCandy(random.nextInt(maxCandies));
    };
    public void onClickedStartaction(ActionEvent actionEvent) {
        // Thread test on seperate board
        Board<Candy> threadsCandyBoard = new Board<>(new Boardsize(2,2));
        threadsCandyBoard.fill(cellCreator);
        thread1 = new Thread(new CandyPlacer(threadsCandyBoard));
        thread2 = new Thread(new CandyPlacer(threadsCandyBoard));
        thread1.start();
        thread2.start();
        // Thread test on seperate board
        Boardsize boardsize = new Boardsize(5,5);
        if (playerNameTextInput.getText().isEmpty()){
            playerNameTextInput.setText("No name");
        }
        playerName = playerNameTextInput.getText();
        model = new CandycrushModel(playerName, 0, boardsize);
        view = new CandycrushView(model);
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
        thread1.interrupt();
        thread2.interrupt();
        Platform.exit();
    }
}