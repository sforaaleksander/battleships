package com.codecool.battleship.player.computer_player;

public enum ComputerDifficulty {
    EASY("EASY", 1), NORMAL("NORMAL", 2), HARD("HARD", 3);

    private final String name;
    private final int no;

    ComputerDifficulty(String name, int no) {
        this.name = name;
        this.no = no;
    }

    public static ComputerDifficulty getDifficultyFromNumber(int no) {
        for (ComputerDifficulty difficulty : ComputerDifficulty.values()) {
            if (difficulty.getNo() == no) {
                return difficulty;
            }
        }
        throw new IllegalArgumentException();
    }

    public int getNo() {
        return no;
    }

    public String getName() {
        return name;
    }
}
