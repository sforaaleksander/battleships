
public class ComputerPlayer extends Player {
 
    private int turn;
    private String difficulty;
    private int[][] listOfInitialShots;
    private ArrayList<Square> listOfFieldsNotToShootAt;
    private Square baseShotSquare;
    private List<Integer> forbiddenRows;
    private String direction;
    private int currentX;
    private int currentY;
    private String colour;
    private long time;

    ComputerPlayer(){
        super();
        this.difficulty = chooseDifficultyLvl();
        this.playerBoard = createRandomBoard();
        this.listOfInitialShots = createListOfInitialShots();
        this.listOfFieldsNotToShootAt = new ArrayList<Square>();
        this.forbiddenRows = new ArrayList<Integer>();
        this.baseShotSquare = null;
        this.direction = "";
        this.colour = Engine.getRandomNumber(10) > 5 ? "BLACK" : "WHITE";
    }
}