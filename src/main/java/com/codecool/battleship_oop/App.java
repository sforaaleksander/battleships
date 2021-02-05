package com.codecool.battleship_oop;

import com.codecool.battleship_oop.game.Battleship;
import com.codecool.battleship_oop.game.Display;
import com.codecool.battleship_oop.input.Input;

public class App {
    public static void main(String[] args) {
        Input input = new Input();
        Display display = new Display(input);

        Battleship battleshipGame = new Battleship(display, input);
        battleshipGame.run();
    }
}
