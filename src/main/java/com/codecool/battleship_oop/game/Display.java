package com.codecool.battleship_oop.game;

import com.codecool.battleship_oop.input.Input;
import com.codecool.battleship_oop.player.computer_player.ComputerDifficulty;
import com.codecool.battleship_oop.player.Player;

public class Display {
    private final Input input;
    private final String[] mainMenuList = new String[]{"PLAY NEW GAME", "ABOUT", "SHOW HIGHSCORES", "EXIT"};
    private final String[] newGameMenuList = new String[]{"PLAYER VS. PLAYER", "PLAYER VS. COMPUTER",
            "COMPUTER VS. COMPUTER"};

    public Display(Input input) {
        this.input = input;
    }

    public void printMainMenu() {
        clearScreen();
        System.out.println("THE BATTLESHIP GAME\n");
        for (int i = 0; i < mainMenuList.length; i++) {
            System.out.println(i + 1 + ". " + mainMenuList[i]);
        }
    }

    public void printNewGameMenu() {
        clearScreen();
        System.out.println("PLAY NEW GAME\n");
        for (int i = 0; i < newGameMenuList.length; i++) {
            System.out.println(i + 1 + ". " + newGameMenuList[i]);
        }
    }

    public void changeHotSeats() {
        clearScreen();
        input.gatherEmptyInput("\n\n\nPress any key when ready.");
        clearScreen();
    }

    public void clearScreen() {
        try {
            new ProcessBuilder("clear").inheritIO().start().waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printComputerDifficulties() {
        int i = 1;
        for (ComputerDifficulty difficulty : ComputerDifficulty.values()) {
            System.out.println(i + ". " + difficulty.getName());
            i++;
        }
    }

    public void displayScreen(Player player, String message) {
        clearScreen();
        System.out.println("CURRENT PLAYER: " + player.getPlayerName());
        System.out.println("CURRENT TURN: " + player.getTurn());
        System.out.println();
        String playerBoard = player.getPlayerBoard().toString();
        String hitsBoard = player.getBoardOfShots().toString();
        System.out.println("YOUR SHIPS");
        System.out.println(playerBoard);
        System.out.println("_____________________\n");
        System.out.println("BOARD OF SHOTS");
        System.out.println(hitsBoard);
        System.out.println(message);
    }

    public void displaySimulationScreen(Player currentPlayer, Player opponentPlayer, boolean switchPlayer) {
        clearScreen();
        String boardOne = switchPlayer ? currentPlayer.getPlayerBoard().toString() : opponentPlayer.getPlayerBoard()
                                                                                                   .toString();
        String boardTwo = switchPlayer ? opponentPlayer.getPlayerBoard().toString() : currentPlayer.getPlayerBoard()
                                                                                                   .toString();
        String nameOne = switchPlayer ? currentPlayer.getPlayerName() : opponentPlayer.getPlayerName();
        String nameTwo = switchPlayer ? opponentPlayer.getPlayerName() : currentPlayer.getPlayerName();
        System.out.println("CURRENT TURN: " + currentPlayer.getTurn() + "\n");
        System.out.println(nameOne + "'S BOARD");
        System.out.println();
        System.out.println(boardOne);
        System.out.println("_____________________\n");
        System.out.println(nameTwo + "'S BOARD");
        System.out.println();
        System.out.println(boardTwo);
        input.gatherEmptyInput("End turn and switch player.");
        clearScreen();
    }

    public void winGameScreen(Player player1, Player player2) {
        clearScreen();
        System.out.println("\n" + player1.getPlayerName() + " WINS!\n");
        System.out.println(player1.getPlayerName() + "'S SCORE IS " + player1.calculateHighScore() + "\n");
        System.out.println(player1.getPlayerName() + "'S BOARD");
        System.out.println(player1.getPlayerBoard().toString());
        System.out.println();
        System.out.println(player2.getPlayerName() + "'S BOARD");
        System.out.println(player2.getPlayerBoard().toString());
        input.gatherEmptyInput("Press any key.");
    }

    public void highScoresDisplay() {
        clearScreen();
        System.out.println("HIGH SCORES BOARD\n");
        System.out.println(input.loadHighScores());
        input.gatherEmptyInput("");
    }

    public void printAbout() {
        clearScreen();
        System.out.println("ABOUT\n");
        System.out.println("THIS BATTLESHIP GAME WAS MADE BY ");
        System.out.println("RAFAL WYPASEK : rafal.zawodowiec@gmail.com ");
        System.out.println("AND");
        System.out.println("ALEKSANDER JEDNASZEWSKI : sforaaleksander@gmail.com ");
        System.out.println("DURING JAVA MODULE IN CODECOOL COURSE");
        System.out.println("ANNO DOMINI 2020.");
        input.gatherEmptyInput("");
    }
}
