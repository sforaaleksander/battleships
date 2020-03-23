class Player{
    private String playerName;
    private Square[][] playerBoard;
    private Square[][] boardOfShots;
    // private boolean turn; ?
    private Ship[] listOfShips;
    private boolean isHuman;

    Player(String playerName, boolean isHuman){
        this.playerName = playerName;
        this.isHuman = isHuman;
        this.playerBoard = new Ocean(10).getTable();
        this.boardOfShots = new Ocean(10).getTable();
    }
}