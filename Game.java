import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

class Game {
    private Player player1;
    private Player player2;
    public static ArrayList<String> listOfPlayers = new ArrayList<>();

    Game(boolean isHuman1, boolean isHuman2) {
        this.player1 = new Player(isHuman1);
        this.player2 = new Player(isHuman2);
    }

    public Player getPlayerOne() {
        return this.player1;
    }

    public Player getPlayerTwo() {
        return this.player2;
    }

    public void winGameScreen(Player player1, Player player2) {
        System.out.println("\n" + player1.getPlayerName() + " WINS!\n");
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
    }}

    public void pvpMode() {
        boolean isAlive = true;
        boolean switchPlayer = false;
        Player currentPlayer;
        Player opponentPlayer;

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
            long startTime = System.nanoTime();
            currentPlayer.displayScreen("");
            String message = currentPlayer.launchTheRocket(opponentPlayer);
            currentPlayer.displayScreen(message);
            Engine.gatherEmptyInput("End turn and switch player.");
            long elapsedTime = System.nanoTime() - startTime;
            currentPlayer.increaseTime(elapsedTime);
            Engine.changeHotSeats();
            isAlive = Engine.arePlayersAlive(currentPlayer, opponentPlayer);
            if (!isAlive) {
                Engine.saveHighScoreToFile(currentPlayer.getPlayerName(), currentPlayer.calculateHighScore());
                winGameScreen(currentPlayer, opponentPlayer);
            }
        }
    }

    public void pvcMode() {
        boolean isAlive = true;
        boolean switchPlayer = false;
        Player currentPlayer;
        Player opponentPlayer;

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
            if (currentPlayer.getIsHuman() == true) {
                long startTime = System.nanoTime();
                currentPlayer.displayScreen("");
                String message = currentPlayer.launchTheRocket(opponentPlayer);
                System.out.println(opponentPlayer.getPlayerBoard().toString()); //for tests
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
            isAlive = Engine.arePlayersAlive(currentPlayer, opponentPlayer);
            if (!isAlive) {
                Engine.saveHighScoreToFile(currentPlayer.getPlayerName(), currentPlayer.calculateHighScore());
                winGameScreen(currentPlayer, opponentPlayer);

            }
        }
    }

    public void cvcMode() {
        boolean isAlive = true;
        boolean switchPlayer = false;
        Player currentPlayer;
        Player opponentPlayer;

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

            currentPlayer.computerLaunchTheRocket(opponentPlayer);
            currentPlayer.displayScreen("");
            Engine.gatherEmptyInput("End turn and switch player.");
            Engine.clearScreen();
            isAlive = Engine.arePlayersAlive(currentPlayer, opponentPlayer);
            if (!isAlive) {
                winGameScreen(currentPlayer, opponentPlayer);
            }
        }
    }
}
