package com.codecool.battleship_oop.player.computer_player;

import com.codecool.battleship_oop.board.Square;
import com.codecool.battleship_oop.player.Player;

import java.util.HashSet;
import java.util.Set;

public abstract class ComputerPlayer extends Player {
    protected final Set<Square> fieldsNotToShootAt;
    protected int posY;
    protected int posX;
    protected Square field;

    public ComputerPlayer(String name) {
        super(name);
        this.setPlayerBoard(createRandomBoard());
        this.fieldsNotToShootAt = new HashSet<>();
    }

    public Set<Square> getFieldsNotToShootAt() {
        return this.fieldsNotToShootAt;
    }

    public void addToFieldsNotToShootAt(Square field) {
        this.fieldsNotToShootAt.add(field);
    }

    public abstract void computerPlay(Player playerBeingShot);
}
