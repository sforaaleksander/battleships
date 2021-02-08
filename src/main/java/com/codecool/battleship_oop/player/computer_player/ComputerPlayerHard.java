package com.codecool.battleship_oop.player.computer_player;

import com.codecool.battleship_oop.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ComputerPlayerHard extends ComputerPlayerNormal {
    private final int[][] listOfInitialShots;
    private final String colour;
    private final List<Integer> initialRows;

    public ComputerPlayerHard(String name) {
        super(name);
        this.listOfInitialShots = createListOfInitialShots();
        this.colour = ThreadLocalRandom.current().nextInt(10) > 5 ? "BLACK" : "WHITE";
        this.initialRows = new ArrayList<>();
    }

    public String getColour() {
        return this.colour;
    }

    public List<Integer> getInitialRows() {
        return this.initialRows;
    }

    public void clearInitialRows() {
        this.initialRows.clear();
    }

    public int[][] getListOfInitialShots() {
        return this.listOfInitialShots;
    }

    public void computerPlay(Player playerBeingShot) {
        if (this.getTurn() < 9 && this.getBaseShotSquare() == null) {
            initialCoords(this, playerBeingShot);
        } else if (this.getBaseShotSquare() == null) {
            randomDiagonalCoords(this, playerBeingShot);
        } else {
            calculatedCoors(this, playerBeingShot);
        }
        fireShot(playerBeingShot);
    }

    private void initialCoords(ComputerPlayerHard playerShooting, Player playerBeingShot) {
        if (playerShooting.getInitialRows().size() > 4) {
            playerShooting.clearInitialRows();
        }
        do {
            int[] pair = playerShooting.getListOfInitialShots()[ThreadLocalRandom.current().nextInt(25)];
            posY = pair[0];
            posX = pair[1];
            field = playerBeingShot.getBoardSquares()[posY][posX];
        } while (playerShooting.getInitialRows().contains(posY)
                || playerShooting.getFieldsNotToShootAt().contains(field)
                || !checkColour(posY, posX, playerShooting.getColour()));
        playerShooting.getInitialRows().add(posY);
    }

    private void randomDiagonalCoords(ComputerPlayerHard playerShooting, Player playerBeingShot) {
        do {
            posY = ThreadLocalRandom.current().nextInt(10);
            posX = ThreadLocalRandom.current().nextInt(10);
            field = playerBeingShot.getBoardSquares()[posY][posX];
        } while (playerShooting.getFieldsNotToShootAt().contains(field)
                || !checkColour(posY, posX, playerShooting.getColour()));
    }

    private boolean checkColour(int posY, int posX, String colour) {
        if (colour.equals("WHITE")) {
            return (posX % 2 == 0 && posY % 2 == 0) || (posX % 2 != 0 && posY % 2 != 0);
        } else
            return (posX % 2 != 0 && posY % 2 == 0) || (posX % 2 == 0 && posY % 2 != 0);
    }

    private int[][] createListOfInitialShots() {
        int[][] listToReturn = new int[25][2];
        int count = 0;
        for (int y = 2; y < 7; y++) {
            for (int x = 2; x < 7; x++) {
                int[] pair = new int[]{y, x};
                listToReturn[count] = pair;
                count++;
            }
        }
        return listToReturn;
    }
}
