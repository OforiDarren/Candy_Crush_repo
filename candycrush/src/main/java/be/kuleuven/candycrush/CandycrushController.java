package be.kuleuven.candycrush;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;

public class CandycrushController {
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

    public void onClickedStartaction(ActionEvent actionEvent) {
//        if(playerNameTextinput.getText().isEmpty()){
//            // Show an alert indicating that the input is empty
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("Input Error");
//            alert.setHeaderText("Player Name is Empty");
//            alert.setContentText("Please enter a player name.");
//            alert.showAndWait();
//        }else {
//            showPlayerNameLabel.setText(playerNameTextinput.getText() + ": " + model.getScore());
//            playerNameTextinput.clear();
//            playerNameTextinput.setDisable(true);
//            model.nieuwSpeelbord();
//            speelbord.getChildren().add(view);
//            loginButton.setDisable(true);
//        }
        Boardsize boardsize = new Boardsize(8,8);
        if (playerNameTextInput == null){
            playerNameTextInput = new TextField("No name");
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
        Platform.exit();
    }
}