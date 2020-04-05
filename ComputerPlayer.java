import java.util.ArrayList;
import java.util.List;

public class ComputerPlayer extends Player {
 
    
    private String difficulty;
    private int[][] listOfInitialShots;
    private ArrayList<Square> listOfFieldsNotToShootAt;
    private Square baseShotSquare;
    private List<Integer> forbiddenRows;
    private String direction;
    private int currentX;
    private int currentY;
    private String colour;
    

    ComputerPlayer(){
        super();
        this.difficulty = chooseDifficultyLvl();
        this.setPlayerBoard(createRandomBoard());
        this.listOfInitialShots = AIhelpers.createListOfInitialShots();
        this.listOfFieldsNotToShootAt = new ArrayList<Square>();
        this.forbiddenRows = new ArrayList<Integer>();
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

    public List<Integer> getForbiddenRows() {
        return this.forbiddenRows;
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
                field = playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX];
            } while (forbiddenRows.contains(posY) || this.getListOfFieldsNotToShootAt().contains(field)
                    || !AIhelpers.checkColour(posY, posX, this.getColour()));
            forbiddenRows.add(posY);

        } else if (this.getBaseShotSquare() == null && this.getDifficulty().equals("HARD")) {
            do {
                posY = Engine.getRandomNumber(10);
                posX = Engine.getRandomNumber(10);
                field = playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX];
            } while (this.getListOfFieldsNotToShootAt().contains(field) || !AIhelpers.checkColour(posY, posX, this.getColour()));

        } else if (this.getBaseShotSquare() == null) {
            do {
                posY = Engine.getRandomNumber(10);
                posX = Engine.getRandomNumber(10);
                field = playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX];
            } while (this.getListOfFieldsNotToShootAt().contains(field));

        } else {
            int currentPosX = this.getCurrentX();
            int currentPosY = this.getCurrentY();

            if (currentPosX > 0
                    && !this.getListOfFieldsNotToShootAt()
                            .contains(playerBeingShot.getPlayerBoard().getOceanBoard()[currentPosY][currentPosX - 1])
                    && (this.getDirection().equals("LEFT") || this.getDirection().equals(""))) {
                posY = currentPosY;
                posX = currentPosX - 1;
            } else if (currentPosX < 9
                    && !this.getListOfFieldsNotToShootAt()
                            .contains(playerBeingShot.getPlayerBoard().getOceanBoard()[currentPosY][currentPosX + 1])
                    && (this.getDirection().equals("RIGHT") || this.getDirection().equals(""))) {
                posY = currentPosY;
                posX = currentPosX + 1;
            } else if (currentPosY > 0
                    && !this.getListOfFieldsNotToShootAt()
                            .contains(playerBeingShot.getPlayerBoard().getOceanBoard()[currentPosY - 1][currentPosX])
                    && (this.getDirection().equals("UP") || this.getDirection().equals(""))) {
                posY = currentPosY - 1;
                posX = currentPosX;
            } else if (currentPosY < 9
                    && !this.getListOfFieldsNotToShootAt()
                            .contains(playerBeingShot.getPlayerBoard().getOceanBoard()[currentPosY + 1][currentPosX])
                    && (this.getDirection().equals("DOWN") || this.getDirection().equals(""))) {
                posY = currentPosY + 1;
                posX = currentPosX;
            } else {
                this.setDirection(AIhelpers.switchDirection(this.getDirection()));
                AIhelpers.nextPosByDirection(this);
                posX = this.getCurrentX(); 
                posY = this.getCurrentY();                                                           
            }
        }

        field = playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX];

        if (Engine.isFieldAShip(playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX])) {
            playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX].changeStatus("HIT");
            this.getBoardOfShots().getOceanBoard()[posY][posX].changeStatus("HIT");
            if (this.getBaseShotSquare() == null) {
                this.setBaseShotSquare(this.getBoardOfShots().getOceanBoard()[posY][posX]);
            }
            this.setCurrentX(posX);
            this.setCurrentY(posY);
            this.addToListOfFieldsNotToShootAt(field);
            this.setDirection(AIhelpers.findDirection(getBaseShotSquare(), posY, posX));
            if (playerBeingShot.isShipSunk()) {
                this.setDirection("");
                this.setBaseShotSquare(null);
                AIhelpers.addFieldsAroundSunkShip(this, playerBeingShot);
            }
        } else if (playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX].getStatus().equals("EMPTY")) {
            this.getBoardOfShots().getOceanBoard()[posY][posX].changeStatus("MISSED");
            playerBeingShot.getPlayerBoard().getOceanBoard()[posY][posX].changeStatus("MISSED");
                                                                                                 
            this.addToListOfFieldsNotToShootAt(field);
            if (this.getBaseShotSquare() != null) {
                this.setCurrentX(this.getBaseShotSquare().getPosX());
                this.setCurrentY(this.getBaseShotSquare().getPosY());
            }
            this.setDirection(AIhelpers.switchDirection(this.getDirection()));
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

    public void displaySimulationScreen(Player opponentPlayer, boolean switchPlayer){
        Engine.clearScreen();
        String boardOne = switchPlayer ? this.getPlayerBoard().toString() : opponentPlayer.getPlayerBoard().toString();
        String boardTwo = switchPlayer ? opponentPlayer.getPlayerBoard().toString() : this.getPlayerBoard().toString();
        String nameOne = switchPlayer ? this.getPlayerName() : opponentPlayer.getPlayerName();
        String nameTwo = switchPlayer ? opponentPlayer.getPlayerName() : this.getPlayerName();
        System.out.println("CURRENT TURN: " + this.getTurn() + "\n");
        System.out.println( nameOne + "'S BOARD");
        System.out.println("");
        System.out.println(boardOne);
        System.out.println("_____________________\n");
        System.out.println(nameTwo + "'S BOARD");
        System.out.println("");
        System.out.println(boardTwo);
    }
}