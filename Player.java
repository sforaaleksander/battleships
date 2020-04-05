import java.util.ArrayList;
import java.util.List;

class Player {
    private String playerName;
    private Ocean playerBoard;
    private Ocean boardOfShots;
    private ArrayList<Ship> listOfShips;
    private int turn;
    private long time;

    Player() {
        this.boardOfShots = new Ocean(10);
        this.turn = 0;
        this.time = 0;
        this.playerName = IO.gatherInput("Enter nickname: ");;
        this.listOfShips = new ArrayList<Ship>();
    }

    protected Ocean createRandomBoard() {
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

    // public boolean getIsHuman() {
    //     return this.isHuman;
    // }

    public int calculateHighScore() {
        int baseNum = 100_000;
        int turnPoints = this.getTurn() * 10;
        int timePoints = Math.toIntExact(this.getTime() / 100_000_000);
        return baseNum - turnPoints - timePoints;
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
}