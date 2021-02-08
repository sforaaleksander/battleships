package com.codecool.battleship_oop.game;

import com.codecool.battleship_oop.board.Square;
import com.codecool.battleship_oop.board.SquareStatus;
import com.codecool.battleship_oop.input.Input;
import com.codecool.battleship_oop.player.HumanPlayer;
import com.codecool.battleship_oop.player.Player;
import com.codecool.battleship_oop.player.computer_player.ComputerPlayer;
import com.codecool.battleship_oop.player.PlayerFactory;

public abstract class Game {
    protected final Display display;
    protected final Input input;
    protected final PlayerFactory playerFactory;
    protected HumanPlayer humanPlayer1;
    protected HumanPlayer humanPlayer2;
    protected ComputerPlayer computerPlayer1;
    protected ComputerPlayer computerPlayer2;

    Game(Display display, Input input) {
        this.display = display;
        this.input = input;
        this.playerFactory = new PlayerFactory(input, display);
    }

    public abstract void play();

    protected void humanPlayerTurn(HumanPlayer currentPlayer, Player opponentPlayer) {
        long startTime = System.nanoTime();
        display.displayScreen(currentPlayer, "");
        String userPosition = input.gatherPositionInput("Type the field to shoot at. (eg. F3)");
        String message = currentPlayer.launchTheRocket(userPosition, opponentPlayer);
        display.displayScreen(currentPlayer, message);
        input.gatherEmptyInput("End turn and switch player.");
        long elapsedTime = System.nanoTime() - startTime;
        currentPlayer.increaseTime(elapsedTime);
    }

    protected boolean arePlayersAlive(Player player1, Player player2) {
        int counterPlayer1 = countShipSquares(player1);
        int counterPlayer2 = countShipSquares(player2);
        return (counterPlayer1 > 0 && counterPlayer2 > 0);
    }

    protected int countShipSquares(Player player) {
        int counter = 0;
        for (Square element1 : player.getPlayerBoard().getShipSquaresList()) {
            if (element1.getStatus().equals(SquareStatus.SHIP)) {
                counter++;
            }
        }
        return counter;
    }
}
