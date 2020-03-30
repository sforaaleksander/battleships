import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

class Game {
    private Player player1;
    private Player player2;
    // private String gameMode;
    public static ArrayList<String> listOfPlayers = new ArrayList<>();

    Game(boolean isHuman1, boolean isHuman2) {
        // this.gameMode = "";
        this.player1 = new Player(isHuman1);
        this.player2 = new Player(isHuman2);
    }

    // public String getGameMode() {
    // return this.gameMode;
    // }

    // public void setGameMode(String gameMode) {
    // this.gameMode = gameMode;
    // }

    public Player getPlayerOne() {
        return this.player1;
    }

    public Player getPlayerTwo() {
        return this.player2;
    }

    public void winGameScreen(Player player1, Player player2) {
        System.out.println("\n" + player1.getPlayerName() + " WINS!\n");
        System.out.println(player1.getPlayerName() + "SCORE IS " + player1.calculateHighScore() + "\n");
        System.out.println(player1.getPlayerName() + "'S BOARD");
        System.out.println(player1.getPlayerBoard().toString());
        System.out.println("");
        System.out.println(player2.getPlayerName() + "'S BOARD");
        System.out.println(player2.getPlayerBoard().toString());
        restartGame();
    }

    public void restartGame() {
        String input = Engine.gatherInput("Do you want to start new game? [y/n]");
        if (input.equals("Y")) {
            listOfPlayers.clear();
            Main.newGameMenu();
        }
    }

    public void gamePlay(String gameMode) {
        boolean isAlive = true;
        boolean switchPlayer = false;
        Player currentPlayer = getPlayerOne();
        Player opponentPlayer = getPlayerTwo();

        while (isAlive) {
            if (!switchPlayer) {
                switchPlayer = true;
                currentPlayer = getPlayerOne();
                opponentPlayer = getPlayerTwo();
                currentPlayer.setTurn(currentPlayer.getTurn() + 1);
                opponentPlayer.setTurn(opponentPlayer.getTurn() + 1);
            } else {
                switchPlayer = false;
                currentPlayer = getPlayerTwo();
                opponentPlayer = getPlayerOne();
            }
            switch (gameMode) {
                case "pvpMode":
                    pvpMode(currentPlayer, opponentPlayer);
                    break;
                case "pvcMode":
                    pvcMode(currentPlayer, opponentPlayer);
                    break;
                case "cvcMode":
                    cvcMode(currentPlayer, opponentPlayer, switchPlayer);
                    break;
            }
            isAlive = Engine.arePlayersAlive(currentPlayer, opponentPlayer);
        }
        Engine.saveHighScoreToFile(currentPlayer.getPlayerName(), currentPlayer.calculateHighScore());
        winGameScreen(currentPlayer, opponentPlayer);
    }

    public void pvpMode(Player currentPlayer, Player opponentPlayer) {
        long startTime = System.nanoTime();
        currentPlayer.displayScreen("");
        String message = currentPlayer.launchTheRocket(opponentPlayer);
        currentPlayer.displayScreen(message);
        Engine.gatherEmptyInput("End turn and switch player.");
        long elapsedTime = System.nanoTime() - startTime;
        currentPlayer.increaseTime(elapsedTime);
        Engine.changeHotSeats();
    }

    public void pvcMode(Player currentPlayer, Player opponentPlayer) {
        if (currentPlayer.getIsHuman() == true) {
            long startTime = System.nanoTime();
            currentPlayer.displayScreen("");
            String message = currentPlayer.launchTheRocket(opponentPlayer);
            currentPlayer.displayScreen(message);
            Engine.gatherEmptyInput("End turn and switch player.");
            long elapsedTime = System.nanoTime() - startTime;
            currentPlayer.increaseTime(elapsedTime);
        } else {
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
    }

    public void cvcMode(Player currentPlayer, Player opponentPlayer, boolean switchPlayer) {
        currentPlayer.computerLaunchTheRocket(opponentPlayer);
        currentPlayer.displaySimulationScreen(opponentPlayer, switchPlayer);
        Engine.gatherEmptyInput("End turn and switch player.");
        Engine.clearScreen();

    }
}
