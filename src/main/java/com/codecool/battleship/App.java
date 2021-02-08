package com.codecool.battleship;

import com.codecool.battleship.game.Battleship;
import com.codecool.battleship.game.Display;
import com.codecool.battleship.input.Input;

public class App {
    public static void main(String[] args) {
        Input input = new Input();
        Display display = new Display(input);

        Battleship battleshipGame = new Battleship(display, input);
        battleshipGame.run();
    }
}
