import java.util.ArrayList;
import java.util.List;

class Player {
    private String playerName;
    private Ocean playerBoard;
    private Ocean boardOfShots;
    private ArrayList<Ship> listOfShips;
    private boolean isHuman;
   

    Player(boolean isHuman) {
        this.isHuman = isHuman;
        this.boardOfShots = new Ocean(10);
        this.turn = 0;
        this.time = 0;
        this.playerName = createPlayerName();
        this.listOfShips = new ArrayList<Ship>();  
    }

    private Ocean createRandomBoard() {
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
        Game.listOfPlayers.add(userName);
        return userName;
    }

    private Ocean placeManuallyOrRandomly(){
        String userChoice = Engine.gatherInput("Do you want to have your ships placed randomly? [Y/N]");
        if (userChoice.equals("Y")){
            return createRandomBoard();
        } return  createPlayerBoard();
    }

    public long getTime() {
        return this.time;
    }

    public void increaseTime(long turnTime) {
        this.time += turnTime;
    }

    public int getTurn() {
        return this.turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }


    public List<Ship> getListOfShips() {
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

    public int calculateHighScore() {
        int baseNum = 100_000;
        int turnPoints = this.getTurn() * 10;
        int timePoints = Math.toIntExact(this.getTime() / 100_000_000);
        return baseNum - turnPoints - timePoints;
    }
}