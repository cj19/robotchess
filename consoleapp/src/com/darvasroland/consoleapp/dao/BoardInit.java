package com.darvasroland.consoleapp.dao;

/**
 * @author darvasr
 */
public class BoardInit {

    private final int width;

    private final int height;

    private int[] playerOnePosition = new int[2];

    private int[] playerTwoPosition = new int[2];

    public BoardInit(int width, int height, int[] playerOnePosition, int[] playerTwoPosition) {
        this.width = width;
        this.height = height;
        this.playerOnePosition = playerOnePosition;
        this.playerTwoPosition = playerTwoPosition;
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
}
