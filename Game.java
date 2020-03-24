class Game {
    private Player player1;
    private Player player2;

    Game(boolean isHuman1, boolean isHuman2){
        this.player1 = new Player(isHuman1);
        this.player2 = new Player(isHuman2);
    }

    public void chooseGameMode(){

        System.out.println("Select Game Mode:");
    }
}
