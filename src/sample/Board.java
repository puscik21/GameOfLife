package sample;

import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Board {

    private GridPane grid;
    private boolean[][] cellsStates;
    private int rows;
    private int cols;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        cellsStates = new boolean[rows][cols];
        initBoard(rows, cols);

        prepareGridLayout();
    }

    private void initBoard(int rows, int cols) {
        grid = new GridPane();
        grid.getChildren().clear();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                StackPane cell = getNewCell();
                grid.add(cell, j, i);
            }
        }
    }

    private StackPane getNewCell() {
        StackPane cell = new StackPane();
        cell.setPrefWidth(1000);
        cell.setPrefHeight(1000);
        return cell;
    }

    private void prepareGridLayout() {
        grid.setGridLinesVisible(true);
        grid.setPadding(new Insets(0, 0, 0, 0));

        AnchorPane.setTopAnchor(grid, 0.0);
        AnchorPane.setBottomAnchor(grid, 0.0);
        AnchorPane.setLeftAnchor(grid, 0.0);
        AnchorPane.setRightAnchor(grid, 0.0);
    }

    // TODO: 01.03.2020 refactor
    public void fillGridWithRandomStates(int percentage) {
        boolean[][] cells = new boolean[rows][cols];
        int allCells = rows * cols;
        Random randGenerator = new Random();
        int cellsToFill = allCells * percentage / 100;

        List<Integer> cellsNumbers = new LinkedList<>();
        for (int i = 0; i < allCells; i++) {
            cellsNumbers.add(i);
        }

        for (int i = 0; i < cellsToFill; i++) {
            int numberIndex = randGenerator.nextInt(cellsNumbers.size());
            int cellNumber = cellsNumbers.get(numberIndex);
            cellsNumbers.remove(numberIndex);
            fillCellFromSingleNumber(cells, cellNumber);
        }
        updateGrid(cells);
    }

    private void fillCellFromSingleNumber(boolean[][] cells, int cellNumber) {
        int row = cellNumber / rows;
        int col = cellNumber % rows;
        cells[row][col] = true;
    }

    public void updateGrid(boolean[][] cells) {
        cellsStates = cells;
        // TODO: 01.03.2020 make it based on cellsStates
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (cellsStates[i][j]) {
                    grid.getChildren().get(10 * i + j).setStyle("-fx-background-color: #333333");
                } else {
                    grid.getChildren().get(10 * i + j).setStyle("-fx-background-color: #ffffff");
                }
            }
        }
    }

    public boolean computeCellState(int row, int col) {
        boolean cellState = getCurrentState(row, col);
        boolean[] neighboursStates = getNeighboursStates(row,  col);
        int sum = 0;
        for (boolean state : neighboursStates) {
            if (state) {
                sum++;
            }
        }
        return computeStateFromSum(cellState, sum);
    }

    public GridPane getGrid() {
        return grid;
    }

    private boolean getCurrentState(int row, int col) {
        return cellsStates[row][col];
    }

    private boolean[] getNeighboursStates(int row, int col) {
        boolean[] states = new boolean[8];
        int count = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }

                try {
                    boolean state = cellsStates[row + i][col + j];
                    states[count] = state;
                } catch (ArrayIndexOutOfBoundsException e) {
                    states[count] = false;
                }
                count++;
            }
        }
        return states;
    }

    private boolean computeStateFromSum(boolean cellState, int sum) {
        if (sum == 3) {
            return true;
        } else {
            return cellState && sum == 2;
        }
    }
}

