package com.codecool.battleship.game;

import com.codecool.battleship.input.Input;
import com.codecool.battleship.player.HumanPlayer;
import com.codecool.battleship.player.computer_player.ComputerPlayer;

import java.util.concurrent.TimeUnit;

public class GamePvc extends Game {
    public GamePvc(Display display, Input input) {
        super(display, input);
    }

    @Override
    public void play() {
        this.humanPlayer1 = playerFactory.createHumanPlayer();
        display.changeHotSeats();
        this.computerPlayer1 = playerFactory.createComputerPlayer();
        pvcGamePlay();
    }

    private void pvcGamePlay() {
        boolean isAlive = true;
        while (isAlive) {
            this.humanPlayer1.setTurn(humanPlayer1.getTurn() + 1);
            humanPlayerTurn(this.humanPlayer1, this.computerPlayer1);
            isAlive = arePlayersAlive(this.humanPlayer1, this.computerPlayer1);
            if (!isAlive) {
                input.saveHighScoreToFile(this.humanPlayer1.getPlayerName(), this.humanPlayer1.calculateHighScore());
                display.winGameScreen(this.humanPlayer1, this.computerPlayer1);
            }
            this.computerPlayer1.setTurn(computerPlayer1.getTurn() + 1);
            pvcModeComputerAttacks(this.computerPlayer1, this.humanPlayer1);
            isAlive = arePlayersAlive(this.computerPlayer1, this.humanPlayer1);
            if (!isAlive) {
                input.saveHighScoreToFile(this.computerPlayer1.getPlayerName(), this.computerPlayer1
                        .calculateHighScore());
                display.winGameScreen(this.computerPlayer1, this.humanPlayer1);
            }
        }
    }

    private void pvcModeComputerAttacks(ComputerPlayer currentPlayer, HumanPlayer opponentPlayer) {
        display.clearScreen();
        System.out.println("\n\n\nComputer is now taking the shot...");
        currentPlayer.computerPlay(opponentPlayer);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        display.clearScreen();
    }
}
