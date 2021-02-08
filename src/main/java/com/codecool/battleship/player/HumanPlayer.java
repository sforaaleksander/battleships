package com.codecool.battleship.player;

import com.codecool.battleship.board.LettersAJ;
import com.codecool.battleship.board.SquareStatus;

public class HumanPlayer extends Player {

    public HumanPlayer(String name) {
        super(name);
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
