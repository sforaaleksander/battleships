import java.util.ArrayList;

class Player {
    private String playerName;
    private Ocean playerBoard;
    private Ocean boardOfShots;
    // private boolean turn; ?
    private Ship[] listOfShips;
    private boolean isHuman;

    Player(boolean isHuman) {
        this.playerName = createPlayerName();
        this.isHuman = isHuman;
        this.playerBoard = createPlayerBoard();
        this.boardOfShots = new Ocean(10);
        // listofships?
    }

    private String createPlayerName() {
        String userName = Engine.gatherInput("Type in your name: ");
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
                    System.out.println("Type [h] for horizontal or [v] for vertical and [letter][number] for position.");
                }
                String userOrientation = Engine
                        .gatherInput("Type [h] for horizontal or [v] for vertical for your ship placement.");
                String userPosition = Engine.gatherInput("Type the position. (eg. F3)");
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
        return ocean;
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
        String userPosition = Engine.gatherInput("Type the field to shoot at. (eg. F3)");
        char userLetter = userPosition.charAt(0);
        int posY = Engine.fromLetterToNum(userLetter);
        int posX = Integer.parseInt(userPosition.substring(1)) - 1;

        if (Engine.isFieldAShip(posX, posY, playerBeingShot.getPlayerBoard().getOceanBoard())) {
            playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX].changeStatus("HIT");
            this.getBoardOfShots().getOceanBoard()[posY][posX].changeStatus("HIT");
            return "YOU HIT";
        } else {
            this.getBoardOfShots().getOceanBoard()[posY][posX].changeStatus("MISSED");
            return "YOU MISSED";
        }

    }

    public void displayScreen(String message) {
        String playerBoard = this.getPlayerBoard().toString();
        String hitsBoard = this.getBoardOfShots().toString();
        System.out.println(playerBoard);
        System.out.println("\n\n\n");
        System.out.println(hitsBoard);
        System.out.println("\n");
        System.out.println(message);

    }

}