package be.kuleuven.candycrush;

import be.kuleuven.candycrush.Candy.Candy;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class CandycrushApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CandycrushApplication.class.getResource("Candycrush-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setTitle("Candy Crush!");//title
        stage.setScene(scene);
        stage.show();//test
    }

    public static void main(String[] args) {
        launch();
    }
}