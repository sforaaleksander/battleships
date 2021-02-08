package com.codecool.battleship.player.computer_player;

import com.codecool.battleship.board.Square;
import com.codecool.battleship.board.SquareStatus;
import com.codecool.battleship.player.Player;

public class ComputerPlayerNormal extends ComputerPlayerEasy implements Ai {
    protected Square baseShotSquare;
    protected String direction;
    protected int currentX;
    protected int currentY;

    public ComputerPlayerNormal(String name) {
        super(name);
        this.baseShotSquare = null;
        this.direction = "";
    }

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getCurrentX() {
        return this.currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return this.currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public Square getBaseShotSquare() {
        return this.baseShotSquare;
    }

    public void setBaseShotSquare(Square baseShotSquare) {
        this.baseShotSquare = baseShotSquare;
    }

    @Override
    public void computerPlay(Player playerBeingShot) {
        if (this.getBaseShotSquare() == null) {
            randomCoords(playerBeingShot);
        } else {
            calculatedCoors(this, playerBeingShot);
        }
        fireShot(playerBeingShot);
    }

    protected void fireShot(Player playerBeingShot) {
        field = playerBeingShot.getBoardSquares()[posY][posX];
        if (isFieldAShip(playerBeingShot.getBoardSquares()[posY][posX])) {
            gotHit(this, playerBeingShot);
        } else if (playerBeingShot.getBoardSquares()[posY][posX].getStatus().equals(SquareStatus.EMPTY)) {
            missed(this, playerBeingShot);
        }
        this.addToFieldsNotToShootAt(field);
    }

    protected void calculatedCoors(ComputerPlayerNormal playerShooting, Player playerBeingShot) {
        int currentPosX = playerShooting.getCurrentX();
        int currentPosY = playerShooting.getCurrentY();

        if (currentPosX > 0
                && !playerShooting.getFieldsNotToShootAt()
                                  .contains(playerBeingShot.getBoardSquares()[currentPosY][currentPosX - 1])
                && (playerShooting.getDirection().equals("LEFT") || playerShooting.getDirection().equals(""))) {
            posY = currentPosY;
            posX = currentPosX - 1;
        } else if (currentPosX < 9
                && !playerShooting.getFieldsNotToShootAt()
                                  .contains(playerBeingShot.getBoardSquares()[currentPosY][currentPosX + 1])
                && (playerShooting.getDirection().equals("RIGHT") || playerShooting.getDirection().equals(""))) {
            posY = currentPosY;
            posX = currentPosX + 1;
        } else if (currentPosY > 0
                && !playerShooting.getFieldsNotToShootAt()
                                  .contains(playerBeingShot.getBoardSquares()[currentPosY - 1][currentPosX])
                && (playerShooting.getDirection().equals("UP") || playerShooting.getDirection().equals(""))) {
            posY = currentPosY - 1;
            posX = currentPosX;
        } else if (currentPosY < 9
                && !playerShooting.getFieldsNotToShootAt()
                                  .contains(playerBeingShot.getBoardSquares()[currentPosY + 1][currentPosX])
                && (playerShooting.getDirection().equals("DOWN") || playerShooting.getDirection().equals(""))) {
            posY = currentPosY + 1;
            posX = currentPosX;
        } else {
            playerShooting.setDirection(switchDirection(playerShooting.getDirection()));
            nextPosByDirection(playerShooting);
            posX = playerShooting.getCurrentX();
            posY = playerShooting.getCurrentY();
        }
    }

    protected void gotHit(ComputerPlayerNormal playerShooting, Player playerBeingShot) {
        playerBeingShot.getBoardSquares()[posY][posX].changeStatus(SquareStatus.HIT);
        playerShooting.getShotsBoardSquares()[posY][posX].changeStatus(SquareStatus.HIT);
        if (playerShooting.getBaseShotSquare() == null) {
            playerShooting.setBaseShotSquare(playerShooting.getShotsBoardSquares()[posY][posX]);
        }
        playerShooting.setCurrentX(posX);
        playerShooting.setCurrentY(posY);
        playerShooting.setDirection(findDirection(playerShooting.getBaseShotSquare(), posY, posX));
        if (playerBeingShot.isShipSunk()) {
            playerShooting.setDirection("");
            playerShooting.setBaseShotSquare(null);
            addFieldsAroundSunkShip(playerShooting, playerBeingShot);
        }
    }

    protected void missed(ComputerPlayerNormal playerShooting, Player playerBeingShot) {
        playerShooting.getShotsBoardSquares()[posY][posX].changeStatus(SquareStatus.MISSED);
        playerBeingShot.getBoardSquares()[posY][posX].changeStatus(SquareStatus.MISSED);

        if (playerShooting.getBaseShotSquare() != null) {
            playerShooting.setCurrentX(playerShooting.getBaseShotSquare().getPosX());
            playerShooting.setCurrentY(playerShooting.getBaseShotSquare().getPosY());
        }
        playerShooting.setDirection(switchDirection(playerShooting.getDirection()));
    }
}
