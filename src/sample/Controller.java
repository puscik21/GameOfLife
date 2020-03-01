package sample;

import javafx.scene.layout.AnchorPane;

public class Controller {

    private Board board;

    public void start(AnchorPane container) {
        board = new Board(10, 10);
        container.getChildren().add(board.getGrid());

        new Thread(this::startLife).start();
    }

    private void startLife() {
//        System.out.println(board.getGrid().getChildren().size()); //101?
//        for (int i = 0; i < board.getGrid().getChildren().size(); i++) {

        board.fillGridWithRandomStates(25);

        // TODO: 01.03.2020 while checking if nothing else to do
        for (int i = 0; i < 3; i++) {
            boolean[][] cells = prepareStatesForUpdate();
            waitTimeInMillis(1000);
            board.updateGrid(cells);
        }
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
