package com.codecool.battleship_oop.ship;

import com.codecool.battleship_oop.board.Square;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    private final ShipType shipType;
    private final int posX;
    private final int posY;
    private final String orientation;
    private final List<Square> listOfFields;

    public Ship(ShipType shipType, String orientation, int posX, int posY) {
        this.shipType = shipType;
        this.orientation = orientation.toUpperCase();
        this.posX = posX;
        this.posY = posY;
        this.listOfFields = new ArrayList<>();
    }

    public void addSquareToList(Square field) {
        this.listOfFields.add(field);
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public String getOrientation() {
        return this.orientation;
    }

    public List<Square> getListOfFields() {
        return this.listOfFields;
    }

    public int getLength() {
        return shipType.getLength();
    }
}
