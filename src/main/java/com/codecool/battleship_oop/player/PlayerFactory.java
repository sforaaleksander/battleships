package com.codecool.battleship_oop.player;

import com.codecool.battleship_oop.board.BoardFactory;
import com.codecool.battleship_oop.game.Display;
import com.codecool.battleship_oop.input.Input;
import com.codecool.battleship_oop.player.HumanPlayer;
import com.codecool.battleship_oop.player.computer_player.*;

public class PlayerFactory {
    private final BoardFactory boardFactory;
    private final Input input;
    private final Display display;


    public PlayerFactory(Input input, Display display) {
        this.input = input;
        this.display = display;
        this.boardFactory = new BoardFactory(input);
    }

    public HumanPlayer createHumanPlayer() {
        String name = input.gatherInput("Enter nickname: ");
        HumanPlayer humanPlayer = new HumanPlayer(name);
        humanPlayer.setPlayerBoard(boardFactory.placeManuallyOrRandomly(humanPlayer));
        return humanPlayer;
    }

    public ComputerPlayer createComputerPlayer() {
        String name = input.gatherInput("Enter nickname: ");
        ComputerDifficulty computerDifficulty = chooseDifficultyLvl();
        return computerPlayerSetUp(computerDifficulty, name);
    }

    private ComputerDifficulty chooseDifficultyLvl() {
        display.printComputerDifficulties();
        return ComputerDifficulty.getDifficultyFromNumber(
                input.gatherIntInput("Select computer difficulty level: ", 3));
    }

    private ComputerPlayer computerPlayerSetUp(ComputerDifficulty difficulty, String name) {
        if (difficulty.equals(ComputerDifficulty.EASY)) {
            return computerPlayerWithBoardSetUp(new ComputerPlayerEasy(name));
        } else if (difficulty.equals(ComputerDifficulty.NORMAL)) {
            return computerPlayerWithBoardSetUp(new ComputerPlayerNormal(name));
        }
        return computerPlayerWithBoardSetUp(new ComputerPlayerHard(name));
    }

    private ComputerPlayer computerPlayerWithBoardSetUp(ComputerPlayer computerPlayer) {
        computerPlayer.setPlayerBoard(boardFactory.createRandomBoard(computerPlayer));
        return computerPlayer;
    }
}

