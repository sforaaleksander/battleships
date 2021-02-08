package com.codecool.battleship_oop.game;

import com.codecool.battleship_oop.input.Input;
import com.codecool.battleship_oop.player.computer_player.ComputerPlayer;

public class GameCvc extends Game {
    public GameCvc(Display display, Input input) {
        super(display, input);
    }

    @Override
    public void play() {
        this.computerPlayer1 = playerFactory.createComputerPlayer();
        display.changeHotSeats();
        this.computerPlayer2 = playerFactory.createComputerPlayer();
        cvcGamePlay();
    }

    public void cvcGamePlay() {
        boolean isAlive = true;
        while (isAlive) {
            isAlive = computerTurn(computerPlayer1, computerPlayer2, false);
            if (!isAlive) break;
            isAlive = computerTurn(computerPlayer2, computerPlayer1, true);
        }
    }

    private void cvcTurn(ComputerPlayer currentPlayer, ComputerPlayer opponentPlayer, boolean switchPlayer) {
        currentPlayer.computerPlay(opponentPlayer);
        display.displaySimulationScreen(currentPlayer, opponentPlayer, switchPlayer);
    }

    private boolean computerTurn(ComputerPlayer player1, ComputerPlayer player2, boolean switchPlayer) {
        boolean isAlive;
        player1.setTurn(player1.getTurn() + 1);
        cvcTurn(player1, player2, switchPlayer);
        isAlive = arePlayersAlive(player1, player2);
        if (!isAlive) {
            input.saveHighScoreToFile(player1.getPlayerName(), player1
                    .calculateHighScore());
            display.winGameScreen(player1, player2);
            return false;
        }
        return true;
    }
}
