
public class ComputerPlayer extends Player {
 
    private int turn;
    private String difficulty;
    private int[][] listOfInitialShots;
    private ArrayList<Square> listOfFieldsNotToShootAt;
    private Square baseShotSquare;
    private List<Integer> forbiddenRows;
    private String direction;
    private int currentX;
    private int currentY;
    private String colour;
    private long time;

    ComputerPlayer(){
        super();
        this.difficulty = chooseDifficultyLvl();
        this.playerBoard = createRandomBoard();
        this.listOfInitialShots = createListOfInitialShots();
        this.listOfFieldsNotToShootAt = new ArrayList<Square>();
        this.forbiddenRows = new ArrayList<Integer>();
        this.baseShotSquare = null;
        this.direction = "";
        this.colour = Engine.getRandomNumber(10) > 5 ? "BLACK" : "WHITE";
    }

    public String getColour() {
        return this.colour;
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

    public int getCurrentY() {
        return this.currentY;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public List<Square> getListOfFieldsNotToShootAt() {
        return this.listOfFieldsNotToShootAt;
    }

    public void addToListOfFieldsNotToShootAt(Square field) {
        this.listOfFieldsNotToShootAt.add(field);
    }

    public Square getBaseShotSquare() {
        return this.baseShotSquare;
    }

    public void setBaseShotSquare(Square baseShotSquare) {
        this.baseShotSquare = baseShotSquare;
    }

    public List<Integer> getForbiddenRows() {
        return this.forbiddenRows;
    }

    public int[][] getListOfInitialShots() {
        return this.listOfInitialShots;
    }

    public String getDifficulty() {
        return this.difficulty;
    }

    private int[][] createListOfInitialShots() {
        int[][] listToReturn = new int[25][2];
        int count = 0;
        for (int y = 2; y < 7; y++) {
            for (int x = 2; x < 7; x++) {
                int[] pair = new int[] { y, x };
                listToReturn[count] = pair;
                count++;
            }
        }
        return listToReturn;
    }


    private String chooseDifficultyLvl() {
        for (int i = 1; i < 4; i++) {
            System.out.println(i + ". " + Engine.computerDifficulty.get(i));
        }
        String compDiff = Engine.computerDifficulty.get(Engine.gatherIntInput("Select computer difficulty level: ", 3));
        return compDiff;
    }


    public boolean isShipSunk() {
        for (Ship element : getListOfShips()) {
            int hitCounter = 0;
            for (int i = 0; i < element.getListOfFields().size(); i++) {
                if (element.getListOfFields().get(i).getStatus().equals("SHIP")) {
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

    public String launchTheRocket(Player playerBeingShot) {
        String userPosition = Engine.gatherPositionInput("Type the field to shoot at. (eg. F3)");
        char userLetter = userPosition.charAt(0);
        int posY = Engine.fromLetterToNum(userLetter);
        int posX = Integer.parseInt(userPosition.substring(1)) - 1;
        if (Engine.isFieldAlreadyHit(this.getBoardOfShots().getOceanBoard()[posY][posX])) {
            return "You have already struck that coordinats! You wasted a missle!";
        }

        if (Engine.isFieldAShip(playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX])) {
            playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX].changeStatus("HIT");
            this.getBoardOfShots().getOceanBoard()[posY][posX].changeStatus("HIT");
            String sunk = playerBeingShot.isShipSunk() ? " AND SUNK!" : "!";
            return "YOU HIT" + sunk;
        } else {
            playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX].changeStatus("MISSED");
            this.getBoardOfShots().getOceanBoard()[posY][posX].changeStatus("MISSED");
            return "YOU MISSED!";
        }
    }

    public void playEasyMode(Player playerBeingShot) {
        int posY = Engine.getRandomNumber(10);
        int posX = Engine.getRandomNumber(10);
        if (Engine.isFieldAlreadyHit(this.getBoardOfShots().getOceanBoard()[posY][posX])) {
        } else if (Engine.isFieldAShip(playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX])) {
            playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX].changeStatus("HIT");
            this.getBoardOfShots().getOceanBoard()[posY][posX].changeStatus("HIT");
            playerBeingShot.isShipSunk();
        } else {
            this.getBoardOfShots().getOceanBoard()[posY][posX].changeStatus("MISSED");
        }
    }

    public void playNormalOrHardMode(Player playerBeingShot) {
        int posY;
        int posX;
        Square field;

        if (this.getTurn() < 9 && this.getBaseShotSquare() == null && this.getDifficulty().equals("HARD")) {
            int[] pair;
            if (getForbiddenRows().size() > 4) {
                forbiddenRows.clear();
            }
            do {
                pair = this.getListOfInitialShots()[Engine.getRandomNumber(25)];
                posY = pair[0];
                posX = pair[1];
                field = playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX];
            } while (forbiddenRows.contains(posY) || this.getListOfFieldsNotToShootAt().contains(field)
                    || !checkColour(posY, posX));
            forbiddenRows.add(posY);

        } else if (this.getBaseShotSquare() == null && this.getDifficulty().equals("HARD")) {
            do {
                posY = Engine.getRandomNumber(10);
                posX = Engine.getRandomNumber(10);
                field = playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX];
            } while (this.getListOfFieldsNotToShootAt().contains(field) || !checkColour(posY, posX));

        } else if (this.getBaseShotSquare() == null) {
            do {
                posY = Engine.getRandomNumber(10);
                posX = Engine.getRandomNumber(10);
                field = playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX];
            } while (this.getListOfFieldsNotToShootAt().contains(field));

        } else {
            int currentPosX = this.getCurrentX();
            int currentPosY = this.getCurrentY();

            if (currentPosX > 0
                    && !this.getListOfFieldsNotToShootAt()
                            .contains(playerBeingShot.getPlayerBoard().getOceanBoard()[currentPosY][currentPosX - 1])
                    && (this.getDirection().equals("LEFT") || this.getDirection().equals(""))) {
                posY = currentPosY;
                posX = currentPosX - 1;
            } else if (currentPosX < 9
                    && !this.getListOfFieldsNotToShootAt()
                            .contains(playerBeingShot.getPlayerBoard().getOceanBoard()[currentPosY][currentPosX + 1])
                    && (this.getDirection().equals("RIGHT") || this.getDirection().equals(""))) {
                posY = currentPosY;
                posX = currentPosX + 1;
            } else if (currentPosY > 0
                    && !this.getListOfFieldsNotToShootAt()
                            .contains(playerBeingShot.getPlayerBoard().getOceanBoard()[currentPosY - 1][currentPosX])
                    && (this.getDirection().equals("UP") || this.getDirection().equals(""))) {
                posY = currentPosY - 1;
                posX = currentPosX;
            } else if (currentPosY < 9
                    && !this.getListOfFieldsNotToShootAt()
                            .contains(playerBeingShot.getPlayerBoard().getOceanBoard()[currentPosY + 1][currentPosX])
                    && (this.getDirection().equals("DOWN") || this.getDirection().equals(""))) {
                posY = currentPosY + 1;
                posX = currentPosX;
            } else {
                this.setDirection(this.switchDirection());
                this.nextPosByDirection();
                posX = this.getCurrentX(); 
                posY = this.getCurrentY();                                                           
            }
        }

        field = playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX];

        if (Engine.isFieldAShip(playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX])) {
            playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX].changeStatus("HIT");
            this.getBoardOfShots().getOceanBoard()[posY][posX].changeStatus("HIT");
            if (this.getBaseShotSquare() == null) {
                this.setBaseShotSquare(this.getBoardOfShots().getOceanBoard()[posY][posX]);
            }
            this.setCurrentX(posX);
            this.setCurrentY(posY);
            this.addToListOfFieldsNotToShootAt(field);
            this.setDirection(this.findDirection(posY, posX));
            if (playerBeingShot.isShipSunk()) {
                this.setDirection("");
                this.setBaseShotSquare(null);
                this.addFieldsAroundSunkShip(playerBeingShot);
            }
        } else if (playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX].getStatus().equals("EMPTY")) {
            this.getBoardOfShots().getOceanBoard()[posY][posX].changeStatus("MISSED");
            playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX].changeStatus("MISSED");
                                                                                                 
            this.addToListOfFieldsNotToShootAt(field);
            if (this.getBaseShotSquare() != null) {
                this.setCurrentX(this.getBaseShotSquare().getPosX());
                this.setCurrentY(this.getBaseShotSquare().getPosY());
            }
            this.setDirection(this.switchDirection());
        }
    }

    public void computerLaunchTheRocket(Player playerBeingShot) {
        if (this.getDifficulty().equals("EASY")) {
            playEasyMode(playerBeingShot);
        } else if (this.getDifficulty().equals("NORMAL")) {
            playNormalOrHardMode(playerBeingShot);
        } else if (this.getDifficulty().equals("HARD")) {
            playNormalOrHardMode(playerBeingShot);

        }
    }


    public void displaySimulationScreen(Player opponentPlayer, boolean switchPlayer){
        Engine.clearScreen();
        String boardOne = switchPlayer ? this.getPlayerBoard().toString() : opponentPlayer.getPlayerBoard().toString();
        String boardTwo = switchPlayer ? opponentPlayer.getPlayerBoard().toString() : this.getPlayerBoard().toString();
        String nameOne = switchPlayer ? this.getPlayerName() : opponentPlayer.getPlayerName();
        String nameTwo = switchPlayer ? opponentPlayer.getPlayerName() : this.getPlayerName();
        System.out.println("CURRENT TURN: " + this.getTurn() + "\n");
        System.out.println( nameOne + "'S BOARD");
        System.out.println("");
        System.out.println(boardOne);
        System.out.println("_____________________\n");
        System.out.println(nameTwo + "'S BOARD");
        System.out.println("");
        System.out.println(boardTwo);
    }

    public String findDirection(int posY, int posX) {
        if (this.getBaseShotSquare().getPosY() > posY) {
            return "UP";
        } else if (this.getBaseShotSquare().getPosY() < posY) {
            return "DOWN";
        } else if (this.getBaseShotSquare().getPosX() > posX) {
            return "LEFT";
        } else if (this.getBaseShotSquare().getPosX() < posX) {
            return "RIGHT";
        }
        return "";
    }

    public String switchDirection() {
        switch (this.getDirection()) {
            case "LEFT":
                return "RIGHT";
            case "RIGHT":
                return "LEFT";
            case "UP":
                return "DOWN";
            case "DOWN":
                return "UP";
        }
        return "";
    }

    public void nextPosByDirection() {
        switch (this.getDirection()) {
            case "LEFT":
                this.setCurrentY(getBaseShotSquare().getPosY());
                this.setCurrentX(getBaseShotSquare().getPosX() - 1);
                break;
            case "RIGHT":
                this.setCurrentY(getBaseShotSquare().getPosY());
                this.setCurrentX(getBaseShotSquare().getPosX() + 1);
                break;
            case "UP":
                this.setCurrentY(getBaseShotSquare().getPosY() - 1);
                this.setCurrentX(getBaseShotSquare().getPosX());
                break;
            case "DOWN":
                this.setCurrentY(getBaseShotSquare().getPosY() + 1);
                this.setCurrentX(getBaseShotSquare().getPosX());
                break;
        }

    }

    public boolean checkColour(int posY, int posX) {
        if (this.getColour().equals("WHITE")) {
            return (posX % 2 == 0 && posY % 2 == 0) || (posX % 2 != 0 && posY % 2 != 0);
        } else
            return (posX % 2 != 0 && posY % 2 == 0) || (posX % 2 == 0 && posY % 2 != 0);
    }

    public void addFieldsAroundSunkShip(Player playerBeingShot) {
        for (Square field : playerBeingShot.getPlayerBoard().getAllSquaresList()) {
            if (field.getStatus().equals("HIT")) {
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        int x = field.getPosX() + j;
                        int y = field.getPosY() + i;
                        if (x >= 0 && x < 10 && y >= 0 && y < 10 && !this.getListOfFieldsNotToShootAt().contains(playerBeingShot.getPlayerBoard().getOceanBoard()[y][x])){
                            this.addToListOfFieldsNotToShootAt(playerBeingShot.getPlayerBoard().getOceanBoard()[y][x]);
                        }
                    }
                }
            }
        }
    }

}