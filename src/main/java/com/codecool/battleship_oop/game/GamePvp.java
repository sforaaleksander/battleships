package com.codecool.battleship_oop.game;

import com.codecool.battleship_oop.input.Input;
import com.codecool.battleship_oop.player.HumanPlayer;

public class GamePvp extends Game {
    GamePvp(Display display, Input input) {
        super(display, input);
    }

    @Override
    public void play() {
        this.humanPlayer1 = playerFactory.createHumanPlayer();
        display.changeHotSeats();
        this.humanPlayer2 = playerFactory.createHumanPlayer();
        pvpGamePlay();
    }

    public void pvpGamePlay() {
        boolean isAlive = true;
        while (isAlive) {
            isAlive = pvpTurn(humanPlayer1, humanPlayer2);
            if (!isAlive) break;
            isAlive = pvpTurn(humanPlayer2, humanPlayer1);
        }
    }

    private boolean pvpTurn(HumanPlayer player1, HumanPlayer player2) {
        boolean isAlive;
        player1.setTurn(player1.getTurn() + 1);
        humanPlayerTurn(player1, player2);
        isAlive = arePlayersAlive(player1, player2);
        if (!isAlive) {
            input.saveHighScoreToFile(player1.getPlayerName(), player1.calculateHighScore());
            display.winGameScreen(player1, player2);
            return false;
        }
        return true;
    }
}

