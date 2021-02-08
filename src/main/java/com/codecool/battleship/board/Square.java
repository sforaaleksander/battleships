package com.codecool.battleship.board;


public class Square {
    private final int posX;
    private final int posY;
    private SquareStatus status;

    Square(int posY, int posX) {
        this.status = SquareStatus.EMPTY;
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public SquareStatus getStatus() {
        return this.status;
    }

    public void changeStatus(SquareStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status.getGraphic();
    }
}
