import java.util.ArrayList;
import java.util.function.IntFunction;

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
        ArrayList<Ship> list = new ArrayList<>();
        Ocean ocean = new Ocean(oceanSize);
        for (String key : Main.ships.keySet()) {
            System.out.println(ocean.getOceanBoard().toString());
            System.out.println("Place the " + key + "ship on your board!");
            String userOrientation = Engine.gatherInput("Type [horizontal] or [vertical] for your ship placement.");
            String userLetter = Engine.gatherInput("Type in the [letter] for Y position.");
            int posY = Engine.fromLetterToNum(userLetter);
            int posX = Engine.gatherIntInput("Type in the [number] for X position.");
            int length = Main.ships.get(key);
            Ship newShip = new Ship(length, userOrientation, posX, posY);
            list.add(newShip);
            ocean.placeOnTable(newShip);
            ocean.setFieldsUnavailable();
        }
        this.listOfShips = list.toArray(new Ship[list.size()]);
        System.out.println(ocean.getOceanBoard().toString());
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

    public void launchTheRocket(Player playerBeingShot){
        String userLetter = Engine.gatherInput("Type in the letter field you want to hit.");
        int posY = Engine.fromLetterToNum(userLetter);
        int posX = Engine.gatherIntInput("Type in the number of the field.") - 1;

        if (Engine.isFieldAShip(posX, posY, playerBeingShot.getPlayerBoard().getOceanBoard())){
            playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX].changeStatus("SHOT");
            this.getBoardOfShots().getOceanBoard()[posY][posX].changeStatus("SHOT");
        } else{
            this.getBoardOfShots().getOceanBoard()[posY][posX].changeStatus("MISSED");
        }
        
    }
    

}