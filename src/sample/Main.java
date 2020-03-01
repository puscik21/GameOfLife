package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("grid.fxml"));
        AnchorPane container = loader.load();
        primaryStage.setTitle("Conway's game of life");
        primaryStage.setScene(new Scene(container, 600, 600));
        Controller mainController = loader.getController();
        mainController.start(container);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
