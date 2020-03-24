class Game {
    private Player player1;
    private Player player2;
    private int turn;

    public void init(boolean isHuman1, boolean isHuman2){
        this.player1 = new Player(isHuman1);
        this.player2 = new Player(isHuman2);
        this.turn = 0;
    }

    Game(boolean isHuman1, boolean isHuman2) {
        this.player1 = new Player(isHuman1);
        this.player2 = new Player(isHuman2);
        this.turn = 0;
    }

    public Player getPlayerOne() {
        return this.player1;
    }

    public Player getPlayerTwo() {
        return this.player2;
    }

    public int getTurn() {
        return this.turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void chooseGameMode() {

        System.out.println("Select Game Mode:");
    }

    public void pvpMode(){
        init(true,true);
        ChooseNameScreen();

        // function with 2 inputs and assigning to :
        // 
        //syso some text like "Enter Player1 name:"
        //playerInput = inputFunction
        //newGame.getPlayerOne().setPlayerName(playerInput)
        //same for second

        printWelcomeScreen();

        // function with just  screen like 

        //              WOJTEK
        //                VS
        //              RAFAŁ

        //       press enter to start game...

        //newGame.getPlayerOne().getPlayerName();

        shipPlacement();
        //function allowing player1 and player2 set their ships on board. 
        //between them should be function switchHotSeatScreen() used to inform player to switch 
        // same shit screen like that 
        //           PLACEING SHIPS TURN
        //                
        //              Player: RAFAŁ

        //       press enter to start ...

        mainGamePlay(){
            boolean isAlive=true; //function checking if any player have 0 ships or all ships are destoryed
            
            isAlive= isAnyPlayerAlive();

            int turn = newGame.getTurn();
            boolean switchPlayer = false;
            Player currentPlayer;
            Player opponentPlayer;

            while(isAlive){

                if(switchPlayer == false){
                    switchPlayer =true;
                    currentPlayer = newGame.getPlayerOne();
                    opponentPlayer = newGame.getPlayerTwo();
                    turn++;
                } else {
                    switchPlayer = false;
                    currentPlayer = newGame.getPlayerTwo();
                    opponentPlayer = newGame.getPlayerOne();
                }
                


            }
        }

    }

}
