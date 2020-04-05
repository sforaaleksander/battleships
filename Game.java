import java.util.concurrent.TimeUnit;

class Game {
    private HumanPlayer humanPlayer1;
    private HumanPlayer humanPlayer2;
    private ComputerPlayer computerPlayer1;
    private ComputerPlayer computerPlayer2;
    private String gameMode;

    Game(String gameMode) {
        this.gameMode = gameMode;
        if (this.gameMode.equals("pvp")) {
            this.humanPlayer1 = new HumanPlayer();
            this.humanPlayer2 = new HumanPlayer();
            pvpGamePlay();
        } else if (this.gameMode.equals("pvc")) {
            this.humanPlayer1 = new HumanPlayer();
            this.computerPlayer1 = new ComputerPlayer();
            pvcGamePlay();
        } else
            this.computerPlayer1 = new ComputerPlayer();
            this.computerPlayer2 = new ComputerPlayer();
            cvcGamePlay();
    }

    public void winGameScreen(Player player1, Player player2) {
        System.out.println("\n" + player1.getPlayerName() + " WINS!\n");
        System.out.println(player1.getPlayerName() + "'S SCORE IS " + player1.calculateHighScore() + "\n");
        System.out.println(player1.getPlayerName() + "'S BOARD");
        System.out.println(player1.getPlayerBoard().toString());
        System.out.println("");
        System.out.println(player2.getPlayerName() + "'S BOARD");
        System.out.println(player2.getPlayerBoard().toString());
        restartGame();
    }

    public void restartGame() {
        String input = IO.gatherInput("Do you want to start new game? [y/n]");
        if (input.equals("Y")) {
            Main.newGameMenu();
        }
    }

    public void pvpGamePlay(){
        boolean isAlive = true;
        while (isAlive) {
            this.humanPlayer1.setTurn(humanPlayer1.getTurn() + 1); 
            pvpTurn(this.humanPlayer1, this.humanPlayer2);
            isAlive = Engine.arePlayersAlive(this.humanPlayer1, this.humanPlayer2);
            if (!isAlive){
                IO.saveHighScoreToFile(this.humanPlayer1.getPlayerName(), this.humanPlayer1.calculateHighScore());
                winGameScreen(this.humanPlayer1, this.humanPlayer2);
            }
            this.humanPlayer2.setTurn(humanPlayer2.getTurn() + 1); 
            pvpTurn(this.humanPlayer2, this.humanPlayer1);
            isAlive = Engine.arePlayersAlive(this.humanPlayer2, this.humanPlayer1);
            if (!isAlive){
                IO.saveHighScoreToFile(this.humanPlayer1.getPlayerName(), this.humanPlayer1.calculateHighScore());
                winGameScreen(this.humanPlayer1, this.humanPlayer2);
            }
        }
    }

    public void pvcGamePlay(){
        boolean isAlive = true;
        while (isAlive) {
            this.humanPlayer1.setTurn(humanPlayer1.getTurn() + 1); 
            pvcModeHumanAttacks(this.humanPlayer1, this.computerPlayer1);
            isAlive = Engine.arePlayersAlive(this.humanPlayer1, this.computerPlayer1);
            if (!isAlive){
                IO.saveHighScoreToFile(this.humanPlayer1.getPlayerName(), this.humanPlayer1.calculateHighScore());
                winGameScreen(this.humanPlayer1, this.computerPlayer1);
            }
            this.computerPlayer1.setTurn(computerPlayer1.getTurn() + 1); 
            pvcModeComputerAttacks(this.computerPlayer1, this.humanPlayer1);
            isAlive = Engine.arePlayersAlive(this.computerPlayer1, this.humanPlayer1);
            if (!isAlive){
                IO.saveHighScoreToFile(this.computerPlayer1.getPlayerName(), this.computerPlayer1.calculateHighScore());
                winGameScreen(this.computerPlayer1, this.humanPlayer1);
            }
        }
    }

    public void cvcGamePlay(){
        boolean isAlive = true;
        while (isAlive) {
            this.computerPlayer1.setTurn(computerPlayer1.getTurn() + 1); 
            cvcTurn(this.computerPlayer1, this.computerPlayer2, false);
            isAlive = Engine.arePlayersAlive(this.computerPlayer1, this.computerPlayer1);
            if (!isAlive){
                IO.saveHighScoreToFile(this.computerPlayer1.getPlayerName(), this.computerPlayer1.calculateHighScore());
                winGameScreen(this.computerPlayer1, this.computerPlayer1);
            }
            this.computerPlayer2.setTurn(computerPlayer2.getTurn() + 1); 
            cvcTurn(this.computerPlayer2, this.computerPlayer1, true);
            isAlive = Engine.arePlayersAlive(this.computerPlayer1, this.computerPlayer1);
            if (!isAlive){
                IO.saveHighScoreToFile(this.computerPlayer1.getPlayerName(), this.computerPlayer1.calculateHighScore());
                winGameScreen(this.computerPlayer2, this.computerPlayer1);
            }
        }
    }

    public void pvpTurn(HumanPlayer currentPlayer, HumanPlayer opponentPlayer) {
        long startTime = System.nanoTime();
        currentPlayer.displayScreen("");
        String message = currentPlayer.launchTheRocket(opponentPlayer);
        currentPlayer.displayScreen(message);
        IO.gatherEmptyInput("End turn and switch player.");
        long elapsedTime = System.nanoTime() - startTime;
        currentPlayer.increaseTime(elapsedTime);
        Engine.changeHotSeats();
    }

    public void pvcModeHumanAttacks(HumanPlayer currentPlayer, ComputerPlayer opponentPlayer) {
        long startTime = System.nanoTime();
        currentPlayer.displayScreen("");
        String message = currentPlayer.launchTheRocket(opponentPlayer);
        currentPlayer.displayScreen(message);
        IO.gatherEmptyInput("End turn and switch player.");
        long elapsedTime = System.nanoTime() - startTime;
        currentPlayer.increaseTime(elapsedTime);

    }

    public void pvcModeComputerAttacks(ComputerPlayer currentPlayer, HumanPlayer opponentPlayer) {
        Engine.clearScreen();
        System.out.println("\n\n\nComputer is now taking the shot...");
        currentPlayer.computerLaunchTheRocket(opponentPlayer);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        Engine.clearScreen();
    }

    public void cvcTurn(ComputerPlayer currentPlayer, ComputerPlayer opponentPlayer, boolean switchPlayer) {
        currentPlayer.computerLaunchTheRocket(opponentPlayer);
        currentPlayer.displaySimulationScreen(opponentPlayer, switchPlayer);
        IO.gatherEmptyInput("End turn and switch player.");
        Engine.clearScreen();

    }
}
