package com.codecool.battleship.player;

import com.codecool.battleship.board.Ocean;
import com.codecool.battleship.board.Square;
import com.codecool.battleship.board.SquareStatus;
import com.codecool.battleship.ship.Ship;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    private final String playerName;
    private final Ocean boardOfShots;
    private final List<Ship> listOfShips;
    private Ocean playerBoard;
    private int turn;
    private long time;

    protected Player(String name) {
        this.boardOfShots = new Ocean(10);
        this.turn = 0;
        this.time = 0;
        this.playerName = name;
        this.listOfShips = new ArrayList<>();
    }

    public long getTime() {
        return this.time;
    }

    public void increaseTime(long turnTime) {
        this.time += turnTime;
    }

    public int getTurn() {
        return this.turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public List<Ship> getListOfShips() {
        return this.listOfShips;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public Ocean getPlayerBoard() {
        return this.playerBoard;
    }

    public void setPlayerBoard(Ocean board) {
        this.playerBoard = board;
    }

    public Square[][] getBoardSquares() {
        return playerBoard.getOceanBoard();
    }

    public Ocean getBoardOfShots() {
        return this.boardOfShots;
    }

    public Square[][] getShotsBoardSquares() {
        return boardOfShots.getOceanBoard();
    }

    public int calculateHighScore() {
        int baseNum = 100_000;
        int turnPoints = this.getTurn() * 10;
        int timePoints = Math.toIntExact(this.getTime() / 100_000_000);
        return baseNum - turnPoints - timePoints;
    }

    public boolean isShipSunk() {
        for (Ship element : getListOfShips()) {
            int hitCounter = 0;
            for (int i = 0; i < element.getListOfFields().size(); i++) {
                if (element.getListOfFields().get(i).getStatus().equals(SquareStatus.SHIP)) {
                    break;
                } else {
                    hitCounter = hitCounter + 1;
                    if (hitCounter == element.getLength()) {
                        getListOfShips().remove(element);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isFieldAShip(Square field) {
        return field.getStatus().equals(SquareStatus.SHIP);
    }

    public boolean isFieldAlreadyHit(Square field) {
        return field.getStatus().equals(SquareStatus.HIT) || field.getStatus().equals(SquareStatus.MISSED);
    }
}
