import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

class Player {
    private String playerName;
    private Ocean playerBoard;
    private Ocean boardOfShots;
    // private boolean turn; ?
    private ArrayList<Ship> listOfShips;
    private boolean isHuman;
    private int turn;
    private String difficulty;
    private int[][] listOfInitialShots;
    private ArrayList<Square> listOfFieldsNotToShootAt;
    private Square lastShotSquare;
    private Square baseShotSquare;
    private Square nextShotSquare;
    // private ArrayList<Square> listOfShots;
    private List<Integer> forbiddenRows;

    Player(boolean isHuman) {
        this.isHuman = isHuman;
        this.boardOfShots = new Ocean(10);
        this.difficulty = "x";
        this.turn = 1;
        this.playerName = createPlayerName();
        // this.listOfShots= new ArrayList<Square>();
        this.listOfShips = new ArrayList<Ship>();
        if (isHuman) {
            this.playerBoard = createPlayerBoard();
        } else {
            this.playerBoard = computerCreatesOwnBoard();
            this.listOfInitialShots = createListOfInitialShots();
            this.listOfFieldsNotToShootAt = new ArrayList<Square>();
            this.forbiddenRows = new ArrayList<Integer>();
            this.baseShotSquare = null;
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
        for (String key : Main.ships.keySet()) {
            Ship newShip;
            do {
                String computerOrientation = Engine.getRandomNumber(10) < 5 ? "H" : "V";
                int posY = Engine.getRandomNumber(10);
                int posX = Engine.getRandomNumber(10);
                int length = Main.ships.get(key);
                newShip = new Ship(length, computerOrientation, posX, posY, key);
                list.add(newShip);
                isPlaceOK = ocean.placeOnTable(newShip);
                // System.out.println(ocean.toString());
                // Engine.gatherInput("cheat");
            } while (!isPlaceOK);
            ocean.setFieldsUnavailable();
            this.getListOfShips().add(newShip);
        }
        // this.listOfShips = list.toArray(new Ship[list.size()]);
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
            Map<Integer, String> computerDifficulty = new HashMap<>();
            computerDifficulty.put(1, "EASY");
            computerDifficulty.put(2, "NORMAL");
            computerDifficulty.put(3, "HARD");
            for (int i = 1; i < 4; i++) {
                System.out.println(i + ". " + computerDifficulty.get(i));
            }
            String compDiff = computerDifficulty.get(Engine.gatherIntInput("Select Computer Difficulty Level: ", 3));
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
        for (String key : Main.ships.keySet()) {
            Ship newShip;
            do {
                System.out.println(ocean.toString());
                System.out.println(
                        "Place the " + key + " ship on your board. The ship's length is " + Main.ships.get(key) + ".");
                if (!isPlaceOK) {
                    System.out.println("The ships must fit on board and may not touch each other.");
                }
                String userOrientation = Engine
                        .gatherVOrHInput("Type [h] for horizontal or [v] for vertical for your ship placement.");
                String userPosition = Engine.gatherPositionInput("Type the position. (eg. F3)");
                char userLetter = userPosition.charAt(0);
                int posY = Engine.fromLetterToNum(userLetter);
                int posX = Integer.parseInt(userPosition.substring(1)) - 1;
                int length = Main.ships.get(key);
                newShip = new Ship(length, userOrientation, posX, posY, key);
                list.add(newShip);
                isPlaceOK = ocean.placeOnTable(newShip);
            } while (!isPlaceOK);
            this.getListOfShips().add(newShip);
            ocean.setFieldsUnavailable();
        }
        // this.listOfShips = list.toArray(new Ship[list.size()]);
        System.out.println(ocean.toString());
        Engine.changeHotSeats();
        return ocean;
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

    public Square getNextShotSquare() {
        return this.nextShotSquare;
    }

    public void setNextShotSquare(Square nextShotSquare) {
        this.nextShotSquare = nextShotSquare;
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

    // public List<Square> getListOfShots() {
    // return this.listOfShots;
    // }

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
            return "You dummy! You have already struck that coordinats!\nYOU WASTED A MISSLE";
        }

        if (Engine.isFieldAShip(playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX])) {
            playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX].changeStatus("HIT");
            this.getBoardOfShots().getOceanBoard()[posY][posX].changeStatus("HIT");
            String sunk = playerBeingShot.isShipSunk() ? " AND SUNK!" : "!";
            return "YOU HIT" + sunk;
        } else {
            this.getBoardOfShots().getOceanBoard()[posY][posX].changeStatus("MISSED");
            return "YOU MISSED";
        }
    }

    public void playEasyMode(Player playerBeingShot) {
        int posY = Engine.getRandomNumber(10);
        int posX = Engine.getRandomNumber(10);
        if (Engine.isFieldAlreadyHit(this.getBoardOfShots().getOceanBoard()[posY][posX])) {
            // -points
        } else if (Engine.isFieldAShip(playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX])) {
            playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX].changeStatus("HIT");
            this.getBoardOfShots().getOceanBoard()[posY][posX].changeStatus("HIT");
            playerBeingShot.isShipSunk();
        } else {
            this.getBoardOfShots().getOceanBoard()[posY][posX].changeStatus("MISSED");
        }
    }

    public void playHardMode(Player playerBeingShot) {
        int posY;
        int posX;
        Square field;

        if (this.getTurn() < 9 && this.getBaseShotSquare() == null) {
            int[] pair;
            if (getForbiddenRows().size() > 4) {
                forbiddenRows.clear();
            }
            do {
                pair = this.getListOfInitialShots()[Engine.getRandomNumber(25)];
                posY = pair[0];
                posX = pair[1];
                field = this.getPlayerBoard().getOceanBoard()[posY][posX];
            } while (forbiddenRows.contains(posY) || this.getListOfFieldsNotToShootAt().contains(field));
            forbiddenRows.add(posY);

        } else if (this.getBaseShotSquare() == null) {
            do {
                posY = Engine.getRandomNumber(10);
                posX = Engine.getRandomNumber(10);
                field = this.getPlayerBoard().getOceanBoard()[posY][posX];
            } while (this.getListOfFieldsNotToShootAt().contains(field));
        } else {
            int basePosY = this.getBaseShotSquare().getPosY();
            int basePosX = this.getBaseShotSquare().getPosX();
            int nextPosY;
            int nextPosX;
            if (this.getNextShotSquare() == null) {
                // this.setNextShotSquare(this.getBaseShotSquare());
                nextPosY = basePosY;
                nextPosX = basePosX;
            } else{
                nextPosY = this.getNextShotSquare().getPosY();
                nextPosX = this.getNextShotSquare().getPosX();
            }

            // going around shot attempt
            if (nextPosX > 0 && !this.getListOfFieldsNotToShootAt()
                    .contains(this.getPlayerBoard().getOceanBoard()[nextPosY][nextPosX - 1])) {
                posY = nextPosY;
                posX = nextPosX - 1;
                field = this.getPlayerBoard().getOceanBoard()[posY][posX];
            } else if (nextPosX < 9 && !this.getListOfFieldsNotToShootAt()
                    .contains(this.getPlayerBoard().getOceanBoard()[nextPosY][nextPosX + 1])) {
                posY = nextPosY;
                posX = nextPosX + 1;
                field = this.getPlayerBoard().getOceanBoard()[posY][posX];
            } else if (nextPosY > 0 && !this.getListOfFieldsNotToShootAt()
                    .contains(this.getPlayerBoard().getOceanBoard()[nextPosY - 1][nextPosX])) {
                posY = nextPosY - 1;
                posX = nextPosX;
                field = this.getPlayerBoard().getOceanBoard()[posY][posX];
            } else {
                posY = nextPosY + 1;
                posX = nextPosX;
                field = this.getPlayerBoard().getOceanBoard()[posY][posX];
            }
        }

        if (Engine.isFieldAShip(playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX])) {
            playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX].changeStatus("HIT");
            this.getBoardOfShots().getOceanBoard()[posY][posX].changeStatus("HIT");
            this.setBaseShotSquare(this.getPlayerBoard().getOceanBoard()[posY][posX]);
            this.addToListOfFieldsNotToShootAt(field);
            this.setNextShotSquare(this.findNextShotSquare(posY, posX));
        } else {
            this.getBoardOfShots().getOceanBoard()[posY][posX].changeStatus("MISSED");
            this.addToListOfFieldsNotToShootAt(field);
            this.setNextShotSquare(this.getBaseShotSquare());
        }
    }

    public void computerLaunchTheRocket(Player playerBeingShot) {
        if (this.getDifficulty().equals("EASY")) {
            playEasyMode(playerBeingShot);
        } else if (this.getDifficulty().equals("NORMAL")) {
        } else if (this.getDifficulty().equals("HARD")) {
            playHardMode(playerBeingShot);

        }
    }

    public void displayScreen(String message, String playerName) {
        Engine.clearScreen();
        System.out.println("CURRENT PLAYER: " + playerName);
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

    public Square findNextShotSquare(int posY, int posX) {
        if (this.getBaseShotSquare().getPosY() > posY) {
            return this.getPlayerBoard().getOceanBoard()[posY - 1][posX];
        } else if (this.getBaseShotSquare().getPosY() < posY) {
            return this.getPlayerBoard().getOceanBoard()[posY + 1][posX];
        } else if (this.getBaseShotSquare().getPosX() > posX) {
            return this.getPlayerBoard().getOceanBoard()[posY][posX - 1];
        } else
            return this.getPlayerBoard().getOceanBoard()[posY][posX + 1];
    }
}