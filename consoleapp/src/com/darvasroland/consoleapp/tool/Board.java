package com.darvasroland.consoleapp.tool;

import com.darvasroland.consoleapp.dao.BoardInit;

/**
 * @author darvasr
 */
public final class Board {

    private final int width;

    private final int height;

    private int[] playerOnePosition;

    private int[] playerTwoPosition;


    public Board(BoardInit boardInit) {
        this.width = boardInit.getWidth();
        this.height = boardInit.getHeight();
        this.playerOnePosition = boardInit.getPlayerOnePosition();
        this.playerTwoPosition = boardInit.getPlayerTwoPosition();
        createBoard();
    }

    private void createBoard() {
        System.out.println("");
        for (int k = 0; k < width + 2; k++) {
            System.out.print("#" + "\t");
        }
        System.out.println("");
        for (int row = 0; row < height; row++) {
            System.out.print("#" + "\t");
            for (int column = 0; column < width; column++) {
                printCharacters(row + 1, column + 1);
            }
            System.out.print("#");
            System.out.println("");
        }
        for (int k = 0; k < width + 2; k++) {
            System.out.print("#" + "\t");
        }
        System.out.println("");
    }

    private void printCharacters(int row, int column) {
        if (row == playerOnePosition[0] && column == playerOnePosition[1]) {
            System.out.print("A" + "\t");
        } else if (row == playerTwoPosition[0] && column == playerTwoPosition[1]) {
            System.out.print("B" + "\t");
        } else {
            System.out.print("." + "\t");
        }
    }

    public void updateBoard(int[] playerOnePosition, int[] playerTwoPosition) {
        setPlayerOnePosition(playerOnePosition);
        setPlayerTwoPosition(playerTwoPosition);
        createBoard();
    }

    public void getBoardState(){
        createBoard();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getPlayerOnePosition() {
        return playerOnePosition;
    }

    public int[] getPlayerTwoPosition() {
        return playerTwoPosition;
    }

    public void setPlayerOnePosition(int[] playerOnePosition) {
        this.playerOnePosition = playerOnePosition;
    }

    public void setPlayerTwoPosition(int[] playerTwoPosition) {
        this.playerTwoPosition = playerTwoPosition;
    }


}
