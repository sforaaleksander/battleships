import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

class Player {
    private String playerName;
    private Ocean playerBoard;
    private Ocean boardOfShots;
    private ArrayList<Ship> listOfShips;
    private boolean isHuman;
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

    Player(boolean isHuman) {
        this.isHuman = isHuman;
        this.boardOfShots = new Ocean(10);
        this.turn = 0;
        this.time = 0;
        this.difficulty = "x";
        this.playerName = createPlayerName();
        this.listOfShips = new ArrayList<Ship>();
        if (isHuman) {
            this.playerBoard = createPlayerBoard();
        } else {
            
            this.colour = Engine.getRandomNumber(10) > 5 ? "BLACK" : "WHITE";
            System.out.println(this.getColour());
            this.playerBoard = computerCreatesOwnBoard();
            this.listOfInitialShots = createListOfInitialShots();
            this.listOfFieldsNotToShootAt = new ArrayList<Square>();
            this.forbiddenRows = new ArrayList<Integer>();
            this.baseShotSquare = null;
            this.direction = "";
        }

    }

    private int[][] createListOfInitialShots() {
        List<int[]> listArr = new ArrayList<>();
        for (int y = 2; y < 7; y++) {
            for (int x = 2; x < 7; x++) {
                int[] pair = new int[] { y, x };
                listArr.add(pair);
            }
        }
        int[][] listToReturn = listArr.toArray(new int[25][2]);
        return listToReturn;
    }

    private Ocean computerCreatesOwnBoard() {
        int oceanSize = 10;
        boolean isPlaceOK = false;
        ArrayList<Ship> list = new ArrayList<>();
        Ocean ocean = new Ocean(oceanSize);
        for (String key : Engine.shipsNameLength.keySet()) {
            Ship newShip;
            do {
                String computerOrientation = Engine.getRandomNumber(10) < 5 ? "H" : "V";
                int posY = Engine.getRandomNumber(10);
                int posX = Engine.getRandomNumber(10);
                int length = Engine.shipsNameLength.get(key);
                newShip = new Ship(length, computerOrientation, posX, posY, key);
                list.add(newShip);
                isPlaceOK = ocean.placeOnTable(newShip);
            } while (!isPlaceOK);
            ocean.setFieldsUnavailable();
            this.getListOfShips().add(newShip);
        }
        System.out.println(ocean.toString());
        Engine.changeHotSeats();
        return ocean;
    }

    private String createPlayerName() {
        String humanOrComputer = isHuman ? "Player" : "Computer";
        int numberOfPlayer = Game.listOfPlayers.size() + 1;
        String userName = Engine.gatherInput("Enter nickname for " + humanOrComputer + numberOfPlayer + ": ");
        if (Game.listOfPlayers.contains(userName)) {
            System.out.println("Name already taken, choose other one: ");
            createPlayerName();
        }
        if (humanOrComputer.equals("Computer")) {         
            for (int i = 1; i < 4; i++) {
                System.out.println(i + ". " + Engine.computerDifficulty.get(i));
            }
            String compDiff = Engine.computerDifficulty.get(Engine.gatherIntInput("Select computer difficulty level: ", 3));
            this.setDifficulty(compDiff);
        }
        Game.listOfPlayers.add(userName);
        return userName;

    }

    private Ocean createPlayerBoard() {
        int oceanSize = 10;
        boolean isPlaceOK = true;
        ArrayList<Ship> list = new ArrayList<>();
        Ocean ocean = new Ocean(oceanSize);
        for (String key : Engine.shipsNameLength.keySet()) {
            Ship newShip;
            do {
                System.out.println(ocean.toString());
                System.out.println(
                        "Place the " + key + " ship on your board. The ship's length is " + Engine.shipsNameLength.get(key) + ".");
                if (!isPlaceOK) {
                    System.out.println("The ships must fit on board and may not touch each other.");
                }
                String userOrientation = Engine
                        .gatherVOrHInput("Type [h] for horizontal or [v] for vertical for your ship placement.");
                String userPosition = Engine.gatherPositionInput("Type the position. (eg. F3)");
                char userLetter = userPosition.charAt(0);
                int posY = Engine.fromLetterToNum(userLetter);
                int posX = Integer.parseInt(userPosition.substring(1)) - 1;
                int length = Engine.shipsNameLength.get(key);
                newShip = new Ship(length, userOrientation, posX, posY, key);
                list.add(newShip);
                isPlaceOK = ocean.placeOnTable(newShip);
            } while (!isPlaceOK);
            this.getListOfShips().add(newShip);
            ocean.setFieldsUnavailable();
        }
        System.out.println(ocean.toString());
        Engine.changeHotSeats();
        return ocean;
    }

    public String getColour() {
        return this.colour;
    }

    public long getTime() {
        return this.time;
    }

    public void increaseTime(long turnTime) {
        this.time += turnTime;
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

    public ArrayList<Square> getListOfFieldsNotToShootAt() {
        return this.listOfFieldsNotToShootAt;
    }

    public void addToListOfFieldsNotToShootAt(Square field) {
        this.listOfFieldsNotToShootAt.add(field);
    }

    public int getTurn() {
        return this.turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
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

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public ArrayList<Ship> getListOfShips() {
        return this.listOfShips;
    }

    public void setPlayerName(String name) {
        this.playerName = name;
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

    public Ocean getBoardOfShots() {
        return this.boardOfShots;
    }

    public void setBoardOfShots(Ocean board) {
        this.boardOfShots = board;
    }

    public boolean getIsHuman() {
        return this.isHuman;
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
                        this.setBaseShotSquare(null);
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
                field = this.getPlayerBoard().getOceanBoard()[posY][posX];
            } while (forbiddenRows.contains(posY) || this.getListOfFieldsNotToShootAt().contains(field) || !checkColour(posY, posX));
            forbiddenRows.add(posY);

        } else if (this.getBaseShotSquare() == null && this.getDifficulty().equals("HARD")) {
            do {
                posY = Engine.getRandomNumber(10);
                posX = Engine.getRandomNumber(10);
                field = this.getPlayerBoard().getOceanBoard()[posY][posX];
            } while (this.getListOfFieldsNotToShootAt().contains(field) || !checkColour(posY, posX));
        
        } else if (this.getBaseShotSquare() == null) {
            do {
                posY = Engine.getRandomNumber(10);
                posX = Engine.getRandomNumber(10);
                field = this.getPlayerBoard().getOceanBoard()[posY][posX];
            } while (this.getListOfFieldsNotToShootAt().contains(field));
            
        } else {
            int currentPosX = this.getCurrentX();
            int currentPosY = this.getCurrentY();

            if (currentPosX > 0
                    && !this.getListOfFieldsNotToShootAt()
                            .contains(this.getPlayerBoard().getOceanBoard()[currentPosY][currentPosX - 1])
                    && (this.getDirection().equals("LEFT") || this.getDirection().equals(""))) {
                posY = currentPosY;
                posX = currentPosX - 1;
                field = this.getPlayerBoard().getOceanBoard()[posY][posX];
            } else if (currentPosX < 9
                    && !this.getListOfFieldsNotToShootAt()
                            .contains(this.getPlayerBoard().getOceanBoard()[currentPosY][currentPosX + 1])
                    && (this.getDirection().equals("RIGHT") || this.getDirection().equals(""))) {
                posY = currentPosY;
                posX = currentPosX + 1;
                field = this.getPlayerBoard().getOceanBoard()[posY][posX];
            } else if (currentPosY > 0
                    && !this.getListOfFieldsNotToShootAt()
                            .contains(this.getPlayerBoard().getOceanBoard()[currentPosY - 1][currentPosX])
                    && (this.getDirection().equals("UP") || this.getDirection().equals(""))) {
                posY = currentPosY - 1;
                posX = currentPosX;
                field = this.getPlayerBoard().getOceanBoard()[posY][posX];
            } else if (currentPosY < 9
                    && !this.getListOfFieldsNotToShootAt()
                            .contains(this.getPlayerBoard().getOceanBoard()[currentPosY + 1][currentPosX])
                    && (this.getDirection().equals("DOWN") || this.getDirection().equals(""))) {
                posY = currentPosY + 1;
                posX = currentPosX;
                field = this.getPlayerBoard().getOceanBoard()[posY][posX];
            } else {
                this.setDirection(this.switchDirection(this.getDirection())); //
                this.nextPosByDirection();
                posX = this.getCurrentX(); // trying to fix confusion when
                posY = this.getCurrentY(); // can not hit the next field
                field = this.getPlayerBoard().getOceanBoard()[posY][posX]; //
                                                                           //
            }
        }

        if (Engine.isFieldAShip(playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX])) {
            playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX].changeStatus("HIT");
            this.getBoardOfShots().getOceanBoard()[posY][posX].changeStatus("HIT");
            if (this.getBaseShotSquare() == null) {
                this.setBaseShotSquare(this.getPlayerBoard().getOceanBoard()[posY][posX]);
            }
            this.setCurrentX(posX);
            this.setCurrentY(posY);
            this.addToListOfFieldsNotToShootAt(field);
            this.setDirection(this.findDirection(posY, posX));
            if (playerBeingShot.isShipSunk()) {
                this.setDirection("");
                this.setBaseShotSquare(null);
            }
        } else if (playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX].getStatus().equals("EMPTY")) {
            this.getBoardOfShots().getOceanBoard()[posY][posX].changeStatus("MISSED");
            playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX].changeStatus("MISSED"); // display PC shots for
                                                                                                 // debug
            this.addToListOfFieldsNotToShootAt(field);
            if (this.getBaseShotSquare() != null) {
                this.setCurrentX(this.getBaseShotSquare().getPosX());
                this.setCurrentY(this.getBaseShotSquare().getPosY());
            }
            this.setDirection(this.switchDirection(this.getDirection()));
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

    public void displayScreen(String message) {
        Engine.clearScreen();
        System.out.println("CURRENT PLAYER: " + this.getPlayerName());
        System.out.println("CURRENT TURN: " + this.getTurn());
        System.out.println("");
        String playerBoard = this.getPlayerBoard().toString();
        String hitsBoard = this.getBoardOfShots().toString();
        System.out.println("YOUR SHIPS");
        System.out.println(playerBoard);
        System.out.println("_____________________\n");
        System.out.println("BOARD OF SHOTS");
        System.out.println(hitsBoard);
        System.out.println(message);

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

    public String switchDirection(String wrongDirection) {
        switch (wrongDirection) {
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

    public int calculateHighScore() {
        int baseNum = 100_000;
        int turnPoints = this.getTurn() * 10;
        int timePoints = Math.toIntExact(this.getTime() / 100_000_000);
        return baseNum - turnPoints - timePoints;
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

    public boolean checkColour(int posY, int posX){
        if (this.getColour().equals("WHITE")){
            return (posX % 2 == 0 && posY % 2 == 0) || (posX % 2 != 0 && posY % 2 != 0);
        } else 
        return (posX % 2 != 0 && posY % 2 == 0) || (posX % 2 == 0 && posY % 2 != 0);
    }
}