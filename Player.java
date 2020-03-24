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

    private String createPlayerName(){
        String userName = Engine.gatherInput("Type in your name: ");
        return userName;
    }

    private Ocean createPlayerBoard(){
        int oceanSize = 10;
        boolean isPlaceOK = false;
        ArrayList<Ship> list = new ArrayList<>();
        Ocean ocean = new Ocean(oceanSize);
        for (String key : Main.ships.keySet()) {
            do{
            System.out.println(ocean.toString());
            System.out.println("Place the " + key + "ship on your board. The ships may not touch each other.");
            String userOrientation = Engine.gatherInput("Type [horizontal] or [vertical] for your ship placement.");
            String userLetter = Engine.gatherInput("Type in the [letter] for Y position.");
            int posY = Engine.fromLetterToNum(userLetter);
            int posX = Engine.gatherIntInput("Type in the [number] for X position.") - 1;
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

    public Ship[] getListOfShips(){
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
    public boolean getIsHuman(){
        return this.isHuman;
    }

    public boolean launchTheRocket(Player playerBeingShot){
        String userLetter = Engine.gatherInput("Type in the letter field you want to hit.");
        int posY = Engine.fromLetterToNum(userLetter);
        int posX = Engine.gatherIntInput("Type in the number of the field.") - 1;

        if (Engine.isFieldAShip(posX, posY, playerBeingShot.getPlayerBoard().getOceanBoard())){
            playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX].changeStatus("SHOT");
            this.getBoardOfShots().getOceanBoard()[posY][posX].changeStatus("SHOT");
            return true;
        } else{
            this.getBoardOfShots().getOceanBoard()[posY][posX].changeStatus("MISSED");
            return false;
        }
        
    }

    public void displayScreen(){
        String playerBoard = this.getPlayerBoard().toString();
        String hitsBoard = this.getBoardOfShots().toString();
        System.out.println(playerBoard);
        System.out.println("\n\n\n");
        System.out.println(hitsBoard);

    }

    

}