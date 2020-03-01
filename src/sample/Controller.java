package sample;

import javafx.scene.layout.AnchorPane;

public class Controller {

    private Board board;

    public void start(AnchorPane container) {
        board = new Board(10, 10);
        container.getChildren().add(board.getGrid());

        new Thread(this::startLife).start();
//        new Thread(this::testCompute).start();
    }

    private void startLife() {
//        System.out.println(board.getGrid().getChildren().size()); //101?
//        for (int i = 0; i < board.getGrid().getChildren().size(); i++) {
        boolean[][] cells = new boolean[10][10];

        //
        cells[1][3] = true;
        cells[0][4] = true;
        cells[1][5] = true;
        board.updateGrid(cells);
        //

        for (int i = 0; i < 3; i++) {
            cells = prepareBoardStates();

            // TODO: 01.03.2020 wait some time method
            long neededTime = 1000;
            long elapsedTime = 0;
            long startTime = System.currentTimeMillis();
            while (neededTime > elapsedTime) {
                elapsedTime = System.currentTimeMillis() - startTime;
            }
            board.updateGrid(cells);
        }
    }

    private boolean[][] prepareBoardStates() {
        boolean[][] newCells = new boolean[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                newCells[i][j] = board.computeCellState(i, j);
            }
        }
        return newCells;
    }

    private void firstStartLife() {
//        System.out.println(board.getGrid().getChildren().size()); //101?
//        for (int i = 0; i < board.getGrid().getChildren().size(); i++) {
        boolean[][] cells = new boolean[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                cells[i][j] = true;
                board.updateGrid(cells);


                long neededTime = 50;
                long elapsedTime = 0;
                long startTime = System.currentTimeMillis();
                while (neededTime > elapsedTime) {
                    elapsedTime = System.currentTimeMillis() - startTime;
                }
            }
        }
    }

    private void testCompute() {
        boolean[][] cells = new boolean[10][10];
        cells[1][3] = true;
        cells[0][4] = true;
        cells[1][5] = true;
        board.updateGrid(cells);
        boolean result = board.computeCellState(9,8);
        System.out.println(result);
    }
}
