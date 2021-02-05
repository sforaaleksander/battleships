package com.codecool.battleship_oop.board;

import com.codecool.battleship_oop.ship.Ship;

import java.util.ArrayList;
import java.util.List;

public class Ocean {
    private final int oceanSize;
    private final List<Square> allSquaresList;
    private Square[][] oceanBoard;
    private List<Square> ShipSquaresList;

    public Ocean(int oceanSize) {
        this.oceanSize = oceanSize;
        this.allSquaresList = new ArrayList<>();
        this.oceanBoard = initializeOceanBoard(oceanSize);
    }

    public Square[][] initializeOceanBoard(int oceanSize) {
        oceanBoard = new Square[oceanSize][oceanSize];
        ShipSquaresList = new ArrayList<>();
        for (int i = 0; i < oceanSize; i++) {
            for (int j = 0; j < oceanSize; j++) {
                Square field = new Square(i, j);
                oceanBoard[i][j] = field;
                allSquaresList.add(field);
            }
        }
        return oceanBoard;
    }

    public List<Square> getAllSquaresList() {
        return this.allSquaresList;
    }

    public Square[][] getOceanBoard() {
        return this.oceanBoard;
    }

    public Square getSquareByPos(int posY, int posX) {
        return this.getOceanBoard()[posY][posX];
    }

    public List<Square> getShipSquaresList() {
        return this.ShipSquaresList;
    }

    public void addToShipSquares(Square field) {
        this.ShipSquaresList.add(field);
    }

    public boolean placeOnTable(Ship newShip) {
        if (this.checkIfPlacementPossible(newShip)) {
            if (newShip.getOrientation().equals("H")) {
                for (int i = newShip.getPosX(); i < newShip.getPosX() + newShip.getLength(); i++) {
                    this.getOceanBoard()[newShip.getPosY()][i].changeStatus(SquareStatus.SHIP);
                    Square field = this.getSquareByPos(newShip.getPosY(), i);
                    newShip.addSquareToList(field);
                    this.addToShipSquares(field);
                }
            } else if (newShip.getOrientation().equals("V")) {
                for (int i = newShip.getPosY(); i < newShip.getPosY() + newShip.getLength(); i++) {
                    this.getOceanBoard()[i][newShip.getPosX()].changeStatus(SquareStatus.SHIP);
                    Square field = this.getSquareByPos(i, newShip.getPosX());
                    newShip.addSquareToList(field);
                    this.addToShipSquares(field);
                }
            }
            return true;
        }
        return false;
    }

    public String toString() {
        StringBuilder output = new StringBuilder(" ");
        for (int x = 1; x < 11; x++) {
            output.append(" ").append(x);
        }
        output.append("\n");
        for (int i = 0; i < this.oceanSize; i++) {
            output.append(LettersAJ.getLetter(i));
            for (int j = 0; j < this.oceanSize; j++) {
                output.append(oceanBoard[i][j].toString());
            }
            output.append("\n");
        }
        return output.toString();
    }

    public boolean checkIfPlacementPossible(Ship ship) {
        ArrayList<Square> potentialShipSquares = new ArrayList<>();
        if (!checkIfFitsOnMap(ship, this.getOceanBoard())) {
            return false;
        }
        for (int z = 0; z < ship.getLength(); z++) {
            if (ship.getOrientation().equals("H")) {
                for (int i = ship.getPosX(); i < ship.getPosX() + ship.getLength(); i++) {
                    potentialShipSquares.add(this.getOceanBoard()[ship.getPosY()][i]);
                }
            } else if (ship.getOrientation().equals("V")) {
                for (int i = ship.getPosY(); i < ship.getPosY() + ship.getLength(); i++) {
                    potentialShipSquares.add(this.getOceanBoard()[i][ship.getPosX()]);
                }
            }
        }
        for (Square element : potentialShipSquares) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    int x = element.getPosX() + j;
                    int y = element.getPosY() + i;
                    if (x >= 0 && x < 10 && y >= 0 && y < 10) {
                        if (this.getOceanBoard()[y][x].getStatus().equals(SquareStatus.SHIP)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean checkIfFitsOnMap(Ship theShip, Square[][] table) {
        int isHorizontal = theShip.getOrientation().equals("H") ? theShip.getPosX() : theShip.getPosY();
        return (theShip.getLength() + isHorizontal - 1 < table.length && isHorizontal >= 0);
    }
}
