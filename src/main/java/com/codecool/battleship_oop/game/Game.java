package com.codecool.battleship_oop.game;

import com.codecool.battleship_oop.board.Square;
import com.codecool.battleship_oop.board.SquareStatus;
import com.codecool.battleship_oop.input.Input;
import com.codecool.battleship_oop.player.*;
import com.codecool.battleship_oop.player.computer_player.ComputerDifficulty;
import com.codecool.battleship_oop.player.computer_player.ComputerPlayer;
import com.codecool.battleship_oop.player.computer_player.ComputerPlayerFactory;

import java.util.concurrent.TimeUnit;

class Game {
    private final Display display;
    private final Input input;
    private HumanPlayer humanPlayer1;
    private HumanPlayer humanPlayer2;
    private ComputerPlayer computerPlayer1;
    private ComputerPlayer computerPlayer2;
    private final ComputerPlayerFactory computerPlayerFactory;

    Game(GameMode gameMode, Display display, Input input) {
        this.display = display;
        this.input = input;
        this.computerPlayerFactory = new ComputerPlayerFactory();
        if (gameMode.equals(GameMode.PVP)) {
            pvpSetUp();
        } else if (gameMode.equals(GameMode.PVC)) {
            pvcSetUp();
        } else {
            cvcSetUp();
        }
    }

    private void pvpSetUp() {
        this.humanPlayer1 = createHumanPlayer();
        display.changeHotSeats();
        this.humanPlayer2 = createHumanPlayer();
        pvpGamePlay();
    }

    private void pvcSetUp() {
        this.humanPlayer1 = createHumanPlayer();
        display.changeHotSeats();
        this.computerPlayer1 = createComputerPlayer();
        pvcGamePlay();
    }

    private HumanPlayer createHumanPlayer() {
        String name = input.gatherInput("Enter nickname: ");
        String userChoice = input.gatherInput("Do you want to have your ships placed randomly? [Y/N]");
        HumanPlayer humanPlayer = new HumanPlayer(name);
        humanPlayer.setInput(input);
        humanPlayer.setPlayerBoard(humanPlayer.placeManuallyOrRandomly(userChoice));
        return humanPlayer;
    }

    private ComputerPlayer createComputerPlayer() {
        String name = input.gatherInput("Enter nickname: ");
        ComputerDifficulty computerDifficulty = chooseDifficultyLvl();
        return computerPlayerFactory.createComputerPlayer(computerDifficulty, name);
    }

    private void cvcSetUp() {
        this.computerPlayer1 = createComputerPlayer();
        display.changeHotSeats();
        this.computerPlayer2 = createComputerPlayer();
        cvcGamePlay();
    }

    private ComputerDifficulty chooseDifficultyLvl() {
        display.printComputerDifficulties();
        return ComputerDifficulty.getDifficultyFromNumber(
                input.gatherIntInput("Select computer difficulty level: ", 3));
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

    public void pvcGamePlay() {
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

    public void cvcGamePlay() {
        boolean isAlive = true;
        while (isAlive) {
            isAlive = computerTurn(computerPlayer1, computerPlayer2, false);
            if (!isAlive) break;
            isAlive = computerTurn(computerPlayer2, computerPlayer1, true);
        }
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

    public void humanPlayerTurn(HumanPlayer currentPlayer, Player opponentPlayer) {
        long startTime = System.nanoTime();
        display.displayScreen(currentPlayer, "");
        String userPosition = input.gatherPositionInput("Type the field to shoot at. (eg. F3)");
        String message = currentPlayer.launchTheRocket(userPosition, opponentPlayer);
        display.displayScreen(currentPlayer, message);
        input.gatherEmptyInput("End turn and switch player.");
        long elapsedTime = System.nanoTime() - startTime;
        currentPlayer.increaseTime(elapsedTime);
    }

    public void pvcModeComputerAttacks(ComputerPlayer currentPlayer, HumanPlayer opponentPlayer) {
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

    public void cvcTurn(ComputerPlayer currentPlayer, ComputerPlayer opponentPlayer, boolean switchPlayer) {
        currentPlayer.computerPlay(opponentPlayer);
        display.displaySimulationScreen(currentPlayer, opponentPlayer, switchPlayer);
    }

    public boolean arePlayersAlive(Player player1, Player player2) {
        int counterPlayer1 = countShipSquares(player1);
        int counterPlayer2 = countShipSquares(player2);
        return (counterPlayer1 > 0 && counterPlayer2 > 0);
    }

    private int countShipSquares(Player player) {
        int counter = 0;
        for (Square element1 : player.getPlayerBoard().getShipSquaresList()) {
            if (element1.getStatus().equals(SquareStatus.SHIP)) {
                counter++;
            }
        }
        return counter;
    }
}
