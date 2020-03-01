package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

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

//        Dialog<String> dialog = new Dialog<>();
//        Window window = dialog.getDialogPane().getScene().getWindow();
//        window.setOnCloseRequest(e -> window.hide());
//        dialog.showAndWait();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
