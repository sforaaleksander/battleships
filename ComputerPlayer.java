import java.util.ArrayList;
import java.util.List;

public class ComputerPlayer extends Player {

    private String difficulty;
    private int[][] listOfInitialShots;
    private ArrayList<Square> listOfFieldsNotToShootAt;
    private Square baseShotSquare;
    private List<Integer> initialRows;
    private String direction;
    private int currentX;
    private int currentY;
    private String colour;

    ComputerPlayer() {
        super();
        this.difficulty = chooseDifficultyLvl();
        this.setPlayerBoard(createRandomBoard());
        this.listOfInitialShots = AIhelpers.createListOfInitialShots();
        this.listOfFieldsNotToShootAt = new ArrayList<Square>();
        this.initialRows = new ArrayList<Integer>();
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

    public List<Integer> getInitialRows() {
        return this.initialRows;
    }

    public void clearInitialRows() {
        this.initialRows.clear();
    }

    public int[][] getListOfInitialShots() {
        return this.listOfInitialShots;
    }

    public String getDifficulty() {
        return this.difficulty;
    }

    private String chooseDifficultyLvl() {
        for (int i = 1; i < 4; i++) {
            System.out.println(i + ". " + Engine.computerDifficulty.get(i));
        }
        String compDiff = Engine.computerDifficulty.get(IO.gatherIntInput("Select computer difficulty level: ", 3));
        return compDiff;
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

    public void computerLaunchTheRocket(Player playerBeingShot) {
        if (this.getDifficulty().equals("EASY")) {
            playEasyMode(playerBeingShot);
        } else {
            AIshot.handleShot(this, playerBeingShot);
        }
    }

    public void displaySimulationScreen(Player opponentPlayer, boolean switchPlayer) {
        Engine.clearScreen();
        String boardOne = switchPlayer ? this.getPlayerBoard().toString() : opponentPlayer.getPlayerBoard().toString();
        String boardTwo = switchPlayer ? opponentPlayer.getPlayerBoard().toString() : this.getPlayerBoard().toString();
        String nameOne = switchPlayer ? this.getPlayerName() : opponentPlayer.getPlayerName();
        String nameTwo = switchPlayer ? opponentPlayer.getPlayerName() : this.getPlayerName();
        System.out.println("CURRENT TURN: " + this.getTurn() + "\n");
        System.out.println(nameOne + "'S BOARD");
        System.out.println("");
        System.out.println(boardOne);
        System.out.println("_____________________\n");
        System.out.println(nameTwo + "'S BOARD");
        System.out.println("");
        System.out.println(boardTwo);
    }
}