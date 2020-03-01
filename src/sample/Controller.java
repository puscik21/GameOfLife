package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Window;

public class Controller {

    @FXML
    private TextField rowsField;

    @FXML
    private TextField colsField;

    @FXML
    private TextField populationField;

    @FXML
    private Button startButton;

    @FXML
    private Button clearButton;

    private AnchorPane rootContainer;
    private Board board;

    private Thread simulationThread;

    private int rows;
    private int cols;
    private boolean isSimulationStarted = false;

    public void start(AnchorPane container) {
        this.rootContainer = container;
        startButton.setOnAction(e -> startLife());
        clearButton.setOnAction(e -> clearBoard());
    }

    private void clearBoard() {
        simulationThread.interrupt();
    }

    private void startLife() {
        if (rowsField.getText().isEmpty() || colsField.getText().isEmpty() || populationField.getText().isEmpty() || isSimulationStarted) {
            return;
        }
        isSimulationStarted = true;
        rows = Integer.parseInt(rowsField.getText());
        cols = Integer.parseInt(colsField.getText());
        int population = Integer.parseInt(populationField.getText());

        board = new Board(rows, cols);
        BorderPane centerContainer = (BorderPane) rootContainer.getChildren().get(0);
        AnchorPane gridContainer = (AnchorPane) centerContainer.getCenter();
        gridContainer.getChildren().clear();
        gridContainer.getChildren().add(board.getGrid());
        board.fillGridWithRandomStates(population);

        simulationThread = new Thread(this::startSimulation);
        simulationThread.start();
    }

    private void startSimulation() {
        boolean[][] grid = null;
        while (true) {
            if (Thread.currentThread().isInterrupted()) {
                board.clear();
                isSimulationStarted = false;
                return;
            }

            boolean[][] newGrid = prepareStatesForUpdate();
            if (compareGridsValues(grid, newGrid)) {
                Platform.runLater(this::showEndMessage);
                isSimulationStarted = false;
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
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] != newGrid[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean[][] prepareStatesForUpdate() {
        boolean[][] newCells = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                newCells[i][j] = board.computeCellState(i, j);
            }
        }
        return newCells;
    }

    private void showEndMessage() {
        Alert message = new Alert(Alert.AlertType.NONE);
        message.setHeaderText("No more moves");
        Window messageWindow = message.getDialogPane().getScene().getWindow();
        messageWindow.setOnCloseRequest(e -> messageWindow.hide());
        message.showAndWait();
    }

    private void waitTimeInMillis(int timeToWait) {
        long elapsedTime = 0;
        long startTime = System.currentTimeMillis();
        while (timeToWait > elapsedTime) {
            elapsedTime = System.currentTimeMillis() - startTime;
        }
    }
}
