package sample;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;
import java.util.concurrent.ExecutionException;

public class Controller {

    private Board board;

    // TODO: 01.03.2020 take rows and cols from gui
    public void start(AnchorPane container) {
        board = new Board(10, 10);
        container.getChildren().add(board.getGrid());
        new Thread(this::startLife).start();
    }

    private void startLife() {
        board.fillGridWithRandomStates(10);
        boolean[][] grid = null;
        while (true) {
            boolean[][] newGrid = prepareStatesForUpdate();
            if (compareGridsValues(grid, newGrid)) {
                Platform.runLater(this::showEndMessage);
                return;
            }
            grid = newGrid;
            waitTimeInMillis(500);
            board.updateGrid(grid);
        }
    }

    private boolean compareGridsValues(boolean[][] grid, boolean[][] newGrid) {
        if (grid == null) {
            return false;
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (grid[i][j] != newGrid[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void showEndMessage() {
        Alert message = new Alert(Alert.AlertType.NONE);
        message.setHeaderText("No more moves");
        Window messageWindow = message.getDialogPane().getScene().getWindow();
        messageWindow.setOnCloseRequest(e -> messageWindow.hide());
        message.showAndWait();
    }

    private boolean[][] prepareStatesForUpdate() {
        boolean[][] newCells = new boolean[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                newCells[i][j] = board.computeCellState(i, j);
            }
        }
        return newCells;
    }

    private void waitTimeInMillis(int timeToWait) {
        long elapsedTime = 0;
        long startTime = System.currentTimeMillis();
        while (timeToWait > elapsedTime) {
            elapsedTime = System.currentTimeMillis() - startTime;
        }
    }
}
