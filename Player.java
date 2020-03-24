class Player {
    private String playerName;
    private Ocean playerBoard;
    private Ocean boardOfShots;
    // private boolean turn; ?
    private Ship[] listOfShips;
    private boolean isHuman;

    Player(boolean isHuman) {
        this.playerName = "Player";
        this.isHuman = isHuman;
        this.playerBoard = createPlayerBoard();
        this.boardOfShots = new Ocean(10);
        // listofships?
    }

    private static Ocean createPlayerBoard(){
        int oceanSize = 10;
        Ocean ocean = new Ocean(oceanSize);
        for (String key : Main.ships.keySet()) {
            System.out.println("Place the " + key + "ship on your board!");
            String userOrientation = Engine.gatherInput("Type [horizontal] or [vertical] for your ship placement.");
            int posX = Engine.gatherIntInput("Type in the X position.");
            int posY = Engine.gatherIntInput("Type in the Y position.");
            int length = Main.ships.get(key);
            Ship newShip = new Ship(length, userOrientation, posX, posY);
            ocean.placeOnTable(newShip);
            ocean.setFieldsUnavailable();
        }
        return ocean;
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
    public boolean getIsHuman(){
        return this.isHuman;
    }

    public void launchTheRocket(Player playerBeingShot){


    }
    

}