import java.util.Set;
import java.util.HashSet;

class Game {
    private Player player1;
    private Player player2;
    private int turn;
    public static Set<String> listOfPlayers = new HashSet<>();

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

    public void winGameScreen(Player player) {
        System.out.println("\n" + player.getPlayerName() + " WINS!\n");
        // TODO
        // show highscore 
        restartGame();
    }

    public void restartGame(){
        String input = Engine.gatherInput("Do you want to start new game? [y/n]");
        if(input.equals("Y")){
            listOfPlayers.clear();
            Main.newGameMenu();
        } else {
            Main.exitGame();
        }
    }



    public void pvpMode() {
        boolean isAlive = true;
        int turn = getTurn();
        boolean switchPlayer = false;
        Player currentPlayer;
        Player opponentPlayer;

        while (isAlive) {
            if (!switchPlayer) {
                switchPlayer = true;
                currentPlayer = getPlayerOne();
                opponentPlayer = getPlayerTwo();
                turn = turn + 1;
            } else {
                switchPlayer = false;
                currentPlayer = getPlayerTwo();
                opponentPlayer = getPlayerOne();
            }

            currentPlayer.displayScreen("");
            String message = currentPlayer.launchTheRocket(opponentPlayer);
            currentPlayer.displayScreen(message);
            isAlive = Engine.areBothPlayersAlive(currentPlayer, opponentPlayer);
            if (!isAlive) {
                winGameScreen(currentPlayer);
            }
        }
    }
}

// ChooseNameScreen();

// function with 2 inputs and assigning to :
//
// syso some text like "Enter Player1 name:"
// playerInput = inputFunction
// newGame.getPlayerOne().setPlayerName(playerInput)
// same for second

// printWelcomeScreen(newGame);

// function with just screen like

// WOJTEK
// VS
// RAFAŁ

// press enter to start game...

// newGame.getPlayerOne().getPlayerName();

// shipPlacement(newGame);
// function allowing player1 and player2 set their ships on board.
// between them should be function switchHotSeatScreen() used to inform player
// to switch
// same shit screen like that
// PLACEING SHIPS TURN
//
// Player: RAFAŁ

// press enter to start ...