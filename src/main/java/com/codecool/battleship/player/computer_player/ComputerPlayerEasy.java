package com.codecool.battleship.player.computer_player;

import com.codecool.battleship.board.SquareStatus;
import com.codecool.battleship.player.Player;

import java.util.concurrent.ThreadLocalRandom;

public class ComputerPlayerEasy extends ComputerPlayer {

    public ComputerPlayerEasy(String name) {
        super(name);
    }

    @Override
    public void computerPlay(Player playerBeingShot) {
        randomCoords(playerBeingShot);
        shootPosition(playerBeingShot, posY, posX);
    }

    protected void shootPosition(Player playerBeingShot, int posY, int posX) {
        if (isFieldAShip(playerBeingShot.getBoardSquares()[posY][posX])) {
            playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX].changeStatus(SquareStatus.HIT);
            this.getShotsBoardSquares()[posY][posX].changeStatus(SquareStatus.HIT);
            playerBeingShot.getBoardSquares()[posY][posX].changeStatus(SquareStatus.HIT);
        } else {
            this.getShotsBoardSquares()[posY][posX].changeStatus(SquareStatus.MISSED);
            playerBeingShot.getBoardSquares()[posY][posX].changeStatus(SquareStatus.MISSED);
        }
        this.addToFieldsNotToShootAt(field);
    }

    protected void randomCoords(Player playerBeingShot) {
        do {
            posY = ThreadLocalRandom.current().nextInt(10);
            posX = ThreadLocalRandom.current().nextInt(10);
            field = playerBeingShot.getBoardSquares()[posY][posX];
        } while (this.getFieldsNotToShootAt().contains(field));
    }
}
