
public class AIshot {
    private static int posY;
    private static int posX;
    private static int[] pair;
    private static Square field;

    private static void initialCoords(ComputerPlayer playerShooting, Player playerBeingShot) {
        if (playerShooting.getInitialRows().size() > 4) {
            playerShooting.clearInitialRows();
        }
        do {
            pair = playerShooting.getListOfInitialShots()[Engine.getRandomNumber(25)];
            posY = pair[0];
            posX = pair[1];
            field = playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX];
        } while (playerShooting.getInitialRows().contains(posY)
                || playerShooting.getListOfFieldsNotToShootAt().contains(field)
                || !AIhelpers.checkColour(posY, posX, playerShooting.getColour()));
        playerShooting.getInitialRows().add(posY);
    }

    private static void randomDiagonalCoords(ComputerPlayer playerShooting, Player playerBeingShot) {
        do {
            posY = Engine.getRandomNumber(10);
            posX = Engine.getRandomNumber(10);
            field = playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX];
        } while (playerShooting.getListOfFieldsNotToShootAt().contains(field)
                || !AIhelpers.checkColour(posY, posX, playerShooting.getColour()));
    }

    private static void randomCoords(ComputerPlayer playerShooting, Player playerBeingShot) {
        do {
            posY = Engine.getRandomNumber(10);
            posX = Engine.getRandomNumber(10);
            field = playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX];
        } while (playerShooting.getListOfFieldsNotToShootAt().contains(field));
    }

    private static void calculatedCoors(ComputerPlayer playerShooting, Player playerBeingShot) {
        int currentPosX = playerShooting.getCurrentX();
        int currentPosY = playerShooting.getCurrentY();

        if (currentPosX > 0
                && !playerShooting.getListOfFieldsNotToShootAt()
                        .contains(playerBeingShot.getPlayerBoard().getOceanBoard()[currentPosY][currentPosX - 1])
                && (playerShooting.getDirection().equals("LEFT") || playerShooting.getDirection().equals(""))) {
            posY = currentPosY;
            posX = currentPosX - 1;
        } else if (currentPosX < 9
                && !playerShooting.getListOfFieldsNotToShootAt()
                        .contains(playerBeingShot.getPlayerBoard().getOceanBoard()[currentPosY][currentPosX + 1])
                && (playerShooting.getDirection().equals("RIGHT") || playerShooting.getDirection().equals(""))) {
            posY = currentPosY;
            posX = currentPosX + 1;
        } else if (currentPosY > 0
                && !playerShooting.getListOfFieldsNotToShootAt()
                        .contains(playerBeingShot.getPlayerBoard().getOceanBoard()[currentPosY - 1][currentPosX])
                && (playerShooting.getDirection().equals("UP") || playerShooting.getDirection().equals(""))) {
            posY = currentPosY - 1;
            posX = currentPosX;
        } else if (currentPosY < 9
                && !playerShooting.getListOfFieldsNotToShootAt()
                        .contains(playerBeingShot.getPlayerBoard().getOceanBoard()[currentPosY + 1][currentPosX])
                && (playerShooting.getDirection().equals("DOWN") || playerShooting.getDirection().equals(""))) {
            posY = currentPosY + 1;
            posX = currentPosX;
        } else {
            playerShooting.setDirection(AIhelpers.switchDirection(playerShooting.getDirection()));
            AIhelpers.nextPosByDirection(playerShooting);
            posX = playerShooting.getCurrentX();
            posY = playerShooting.getCurrentY();
        }
    }

    private static void gotHit(ComputerPlayer playerShooting, Player playerBeingShot){
        playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX].changeStatus("HIT");
        playerShooting.getBoardOfShots().getOceanBoard()[posY][posX].changeStatus("HIT");
        if (playerShooting.getBaseShotSquare() == null) {
            playerShooting.setBaseShotSquare(playerShooting.getBoardOfShots().getOceanBoard()[posY][posX]);
        }
        playerShooting.setCurrentX(posX);
        playerShooting.setCurrentY(posY);
        playerShooting.addToListOfFieldsNotToShootAt(field);
        playerShooting.setDirection(AIhelpers.findDirection(playerShooting.getBaseShotSquare(), posY, posX));
        if (playerBeingShot.isShipSunk()) {
            playerShooting.setDirection("");
            playerShooting.setBaseShotSquare(null);
            AIhelpers.addFieldsAroundSunkShip(playerShooting, playerBeingShot);
        }
    }

    private static void missed(ComputerPlayer playerShooting, Player playerBeingShot){
        playerShooting.getBoardOfShots().getOceanBoard()[posY][posX].changeStatus("MISSED");
            playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX].changeStatus("MISSED");

            playerShooting.addToListOfFieldsNotToShootAt(field);
            if (playerShooting.getBaseShotSquare() != null) {
                playerShooting.setCurrentX(playerShooting.getBaseShotSquare().getPosX());
                playerShooting.setCurrentY(playerShooting.getBaseShotSquare().getPosY());
            }
            playerShooting.setDirection(AIhelpers.switchDirection(playerShooting.getDirection()));
    }

    public static void handleShot(ComputerPlayer playerShooting, Player playerBeingShot) {
        if (playerShooting.getTurn() < 9 && playerShooting.getBaseShotSquare() == null
                && playerShooting.getDifficulty().equals("HARD")) {
            initialCoords(playerShooting, playerBeingShot);
        } else if (playerShooting.getBaseShotSquare() == null && playerShooting.getDifficulty().equals("HARD")) {
            randomDiagonalCoords(playerShooting, playerBeingShot);
        } else if (playerShooting.getBaseShotSquare() == null) {
            randomCoords(playerShooting, playerBeingShot);
        } else {
            calculatedCoors(playerShooting, playerBeingShot);
        }

        field = playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX];

        if (Engine.isFieldAShip(playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX])) {
            gotHit(playerShooting, playerBeingShot);
        } else if (playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX].getStatus().equals("EMPTY")) {
            missed(playerShooting, playerBeingShot);
        }
    }
}