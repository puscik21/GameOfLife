package sample;

import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.Optional;

public class Board {

    private GridPane grid;
    private boolean[][] cellsStates;

    public Board(int rows, int cols) {
        cellsStates = new boolean[rows][cols];
        initBoard(rows, cols);
        grid.setGridLinesVisible(true);
        grid.setPadding(new Insets(0, 0, 0, 0));
//        grid.getChildren().get(5).setStyle("-fx-background-color: #333333");

        AnchorPane.setTopAnchor(grid, 0.0);
        AnchorPane.setBottomAnchor(grid, 0.0);
        AnchorPane.setLeftAnchor(grid, 0.0);
        AnchorPane.setRightAnchor(grid, 0.0);
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

    public GridPane getGrid() {
        return grid;
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

