import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

class Player {
    private String playerName;
    private Ocean playerBoard;
    private Ocean boardOfShots;
    // private boolean turn; ?
    private Ship[] listOfShips;
    private boolean isHuman;
    private String difficulty;

    Player(boolean isHuman) {
        this.isHuman = isHuman;
        this.boardOfShots = new Ocean(10);
        this.playerName = createPlayerName();
        if (isHuman) {
            this.playerBoard = createPlayerBoard();
        } else {
            this.playerBoard = computerCreatesOwnBoard();
            this.difficulty = "";
        }
        // listofships?
    }

    private Ocean computerCreatesOwnBoard() {
        int oceanSize = 10;
        boolean isPlaceOK = false;
        ArrayList<Ship> list = new ArrayList<>();
        Ocean ocean = new Ocean(oceanSize);
        for (String key : Main.ships.keySet()) {
            do {

                // System.out.println(ocean.toString());
                // Engine.gatherInput("dupaaaa");
                // System.out.println(
                // "Place the " + key + " ship on your board. The ship's length is " +
                // Main.ships.get(key) + ".");
                // if (!isPlaceOK) {
                // System.out.println("The ships must fit on board and may not touch each
                // other.");
                // }
                String computerOrientation = Engine.getRandomNumber() < 5 ? "H" : "V";
                int posY = Engine.getRandomNumber();
                int posX = Engine.getRandomNumber();
                int length = Main.ships.get(key);
                Ship newShip = new Ship(length, computerOrientation, posX, posY);
                list.add(newShip);
                isPlaceOK = ocean.placeOnTable(newShip);
                System.out.println(ocean.toString());
                Engine.gatherInput("dupaaaa");
            } while (!isPlaceOK);
            ocean.setFieldsUnavailable();
        }
        this.listOfShips = list.toArray(new Ship[list.size()]);
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
                Ship newShip = new Ship(length, userOrientation, posX, posY);
                list.add(newShip);
                isPlaceOK = ocean.placeOnTable(newShip);
            } while (!isPlaceOK);
            ocean.setFieldsUnavailable();
        }
        this.listOfShips = list.toArray(new Ship[list.size()]);
        System.out.println(ocean.toString());
        Engine.changeHotSeats();
        return ocean;
    }

    public String getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Ship[] getListOfShips() {
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

    public String launchTheRocket(Player playerBeingShot) {
        String userPosition = Engine.gatherPositionInput("Type the field to shoot at. (eg. F3)");
        char userLetter = userPosition.charAt(0);
        int posY = Engine.fromLetterToNum(userLetter);
        int posX = Integer.parseInt(userPosition.substring(1)) - 1;
        if (Engine.isFieldAlreadyHit(posX, posY, this.getBoardOfShots().getOceanBoard())) {
            return "You dummy! You have already struck that coordinats!\nYOU WASTED A MISSLE";
        }

        if (Engine.isFieldAShip(posX, posY, playerBeingShot.getPlayerBoard().getOceanBoard())) {
            playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX].changeStatus("HIT");
            this.getBoardOfShots().getOceanBoard()[posY][posX].changeStatus("HIT");
            return "YOU HIT";
        } else {
            this.getBoardOfShots().getOceanBoard()[posY][posX].changeStatus("MISSED");
            return "YOU MISSED";
        }

    }

    public void computerLaunchTheRocket(Player playerBeingShot) {
        if (this.getDifficulty().equals("EASY")) {
            int posY = Engine.getRandomNumber();
            int posX = Engine.getRandomNumber();
            if (Engine.isFieldAlreadyHit(posX, posY, this.getBoardOfShots().getOceanBoard())) {
                // -points
            } else if (Engine.isFieldAShip(posX, posY, playerBeingShot.getPlayerBoard().getOceanBoard())) {
                playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX].changeStatus("HIT");
                this.getBoardOfShots().getOceanBoard()[posY][posX].changeStatus("HIT");
            } else {
                this.getBoardOfShots().getOceanBoard()[posY][posX].changeStatus("MISSED");
            }

        } else if (this.getDifficulty().equals("NORMAL")) {

        } else if (this.getDifficulty().equals("HARD")) {

        }

    }

    public void displayScreen(String message, String playerName, int turnNo) {
        Engine.clearScreen();
        System.out.println("CURRENT PLAYER: " + playerName);
        System.out.println("CURRENT TURN: " + turnNo);
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

}