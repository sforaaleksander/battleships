package com.codecool.battleship_oop.player;

import com.codecool.battleship_oop.board.LettersAJ;
import com.codecool.battleship_oop.board.Ocean;
import com.codecool.battleship_oop.board.SquareStatus;
import com.codecool.battleship_oop.input.Input;
import com.codecool.battleship_oop.ship.Ship;
import com.codecool.battleship_oop.ship.ShipType;

public class HumanPlayer extends Player {
    private Input input;

    public HumanPlayer(String name) {
        super(name);
    }

    public void setInput(Input input) {
        this.input = input;
    }

    public Ocean placeManuallyOrRandomly(String userChoice) {
        if (userChoice.equals("Y")) {
            return createRandomBoard();
        }
        return createPlayerBoard();
    }

    private Ocean createPlayerBoard() {
        int oceanSize = 10;
        boolean isPlaceOK = true;
        Ocean ocean = new Ocean(oceanSize);
        for (ShipType shipType : ShipType.values()) {
            Ship newShip;
            do {
                System.out.println(ocean.toString());
                System.out.println("Place the " + shipType.getName() + " ship on your board. The ship's length is "
                        + shipType.getLength() + ".");
                if (!isPlaceOK) {
                    System.out.println("The ships must fit on board and may not touch each other.");
                }
                String userOrientation = input
                        .gatherVOrHInput("Type [h] for horizontal or [v] for vertical for your ship placement.");
                String userPosition = input.gatherPositionInput("Type the position. (eg. F3)");
                char userLetter = userPosition.charAt(0);
                int posY = LettersAJ.getNo(userLetter);
                int posX = Integer.parseInt(userPosition.substring(1)) - 1;
                newShip = new Ship(shipType, userOrientation, posX, posY);
                isPlaceOK = ocean.placeOnTable(newShip);
            } while (!isPlaceOK);
            this.getListOfShips().add(newShip);
        }
        System.out.println(ocean.toString());
        return ocean;
    }

    public String launchTheRocket(String userPosition, Player playerBeingShot) {
        char userLetter = userPosition.charAt(0);
        int posY = LettersAJ.getNo(userLetter);
        int posX = Integer.parseInt(userPosition.substring(1)) - 1;
        if (isFieldAlreadyHit(this.getBoardOfShots().getOceanBoard()[posY][posX])) {
            return "You have already struck that coordinats! You wasted a missle!";
        }

        if (isFieldAShip(playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX])) {
            playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX].changeStatus(SquareStatus.HIT);
            this.getBoardOfShots().getOceanBoard()[posY][posX].changeStatus(SquareStatus.HIT);
            String sunk = playerBeingShot.isShipSunk() ? " AND SUNK!" : "!";
            return "YOU HIT" + sunk;
        } else {
            playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX].changeStatus(SquareStatus.MISSED);
            this.getBoardOfShots().getOceanBoard()[posY][posX].changeStatus(SquareStatus.MISSED);
            return "YOU MISSED!";
        }
    }
}
