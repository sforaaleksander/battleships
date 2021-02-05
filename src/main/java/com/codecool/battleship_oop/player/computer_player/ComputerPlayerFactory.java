package com.codecool.battleship_oop.player.computer_player;

public class ComputerPlayerFactory {

    public ComputerPlayer createComputerPlayer(ComputerDifficulty difficulty, String name) {
        if (difficulty.equals(ComputerDifficulty.EASY)) {
            return new ComputerPlayerEasy(name);
        } else if (difficulty.equals(ComputerDifficulty.NORMAL)) {
            return new ComputerPlayerNormal(name);
        }
        return new ComputerPlayerHard(name);
    }
}

