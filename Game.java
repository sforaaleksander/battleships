import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.HashSet;

class Game {
    private Player player1;
    private Player player2;
    public static Set<String> listOfPlayers = new HashSet<>();

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

        // TODO
        // show highscore
        restartGame();
    }

    public void restartGame() {
        String input = Engine.gatherInput("Do you want to start new game? [y/n]");
        if (input.equals("Y")) {
            listOfPlayers.clear();
            Main.newGameMenu();
        } else {
            Main.exitGame();
        }
    }

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
                currentPlayer.setTurn(currentPlayer.getTurn()+1);
                opponentPlayer.setTurn(opponentPlayer.getTurn()+1);
            } else {
                switchPlayer = false;
                currentPlayer = getPlayerTwo();
                opponentPlayer = getPlayerOne();
            }
            currentPlayer.displayScreen("", currentPlayer.getPlayerName());
            String message = currentPlayer.launchTheRocket(opponentPlayer);
            currentPlayer.displayScreen(message, currentPlayer.getPlayerName());
            Engine.gatherEmptyInput("End turn and switch player.");
            Engine.changeHotSeats();
            isAlive = Engine.arePlayersAlive(currentPlayer, opponentPlayer);
            if (!isAlive) {
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
                currentPlayer.setTurn(currentPlayer.getTurn()+1);
                opponentPlayer.setTurn(opponentPlayer.getTurn()+1);
            } else {
                switchPlayer = false;
                currentPlayer = getPlayerTwo();
                opponentPlayer = getPlayerOne();
            }
            if (currentPlayer.getIsHuman() == true) {
                currentPlayer.displayScreen("", currentPlayer.getPlayerName());
                String message = currentPlayer.launchTheRocket(opponentPlayer);
                currentPlayer.displayScreen(message, currentPlayer.getPlayerName());
                Engine.gatherInput("End turn and switch player.");
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
                winGameScreen(currentPlayer, opponentPlayer);
            }
        }
    }
}