package com.codecool.battleship_oop.game;

import com.codecool.battleship_oop.input.Input;

import java.util.HashMap;
import java.util.Map;

public class Battleship {
    private final Display display;
    private final Input input;
    private boolean isRunning;
    private Map<Integer, Runnable> mainMenu;
    private Map<Integer, Runnable> playGameMenu;


    public Battleship(Display display, Input input) {
        this.display = display;
        this.input = input;
        initialize();
    }

    public void run() {
        initialize();
        while (isRunning) {
            mainMenu();
        }
        input.getScan().close();
    }

    private void initialize() {
        display.clearScreen();
        isRunning = true;

        mainMenu = new HashMap<>();
        mainMenu.put(1, this::newGameMenu);
        mainMenu.put(2, this::aboutDisplay);
        mainMenu.put(3, display::highScoresDisplay);
        mainMenu.put(4, this::exitGame);

        playGameMenu = new HashMap<>();
        playGameMenu.put(1, () -> new Game(GameMode.PVP, display, input));
        playGameMenu.put(2, () -> new Game(GameMode.PVC, display, input));
        playGameMenu.put(3, () -> new Game(GameMode.CVC, display, input));
    }

    public void mainMenu() {
        display.printMainMenu();
        int userChoice = input.gatherIntInput("", 4);
        display.clearScreen();
        mainMenu.get(userChoice).run();
    }

    public void newGameMenu() {
        display.printNewGameMenu();
        int userChoice = input.gatherIntInput("", 3);
        display.clearScreen();
        playGameMenu.get(userChoice).run();
    }

    public void aboutDisplay() {
        display.printAbout();
    }

    public void exitGame() {
        isRunning = false;
    }
}
