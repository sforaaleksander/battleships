package com.codecool.battleship_oop.board;

import com.codecool.battleship_oop.input.Input;
import com.codecool.battleship_oop.player.Player;
import com.codecool.battleship_oop.ship.Ship;
import com.codecool.battleship_oop.ship.ShipType;

import java.util.concurrent.ThreadLocalRandom;

public class BoardFactory {
    private final Input input;

    public BoardFactory(Input input) {
        this.input = input;
    }

    public Ocean placeManuallyOrRandomly(Player player) {
        String userChoice = input.gatherInput("Do you want to have your ships placed randomly? [Y/N]");
        if (userChoice.equals("Y")) {
            return createRandomBoard(player);
        }
        return createPlayerBoard(player);
    }

    public Ocean createRandomBoard(Player player) {
        int oceanSize = 10;
        boolean isPlaceOK;
        Ocean ocean = new Ocean(oceanSize);
        for (ShipType shipType : ShipType.values()) {
            Ship newShip;
            do {
                String computerOrientation = ThreadLocalRandom.current().nextInt(10) < 5 ? "H" : "V";
                int posY = ThreadLocalRandom.current().nextInt(10);
                int posX = ThreadLocalRandom.current().nextInt(10);
                newShip = new Ship(shipType, computerOrientation, posX, posY);
                isPlaceOK = ocean.placeOnTable(newShip);
            } while (!isPlaceOK);
            player.getListOfShips().add(newShip);
        }
        System.out.println(ocean.toString());
        return ocean;
    }

    private Ocean createPlayerBoard(Player player) {
        int oceanSize = 10;
        boolean isPlaceOK = true;
        Ocean ocean = new Ocean(oceanSize);
        for (ShipType shipType : ShipType.values()) {
            Ship newShip;
            do {
                System.out.println(ocean.toString());
                System.out.println("Place the " + shipType.getName() + " ship on your board. The ship's length is "
                        + shipType.getLength() + ".");
                if (!isPlaceOK) {
                    System.out.println("The ships must fit on board and may not touch each other.");
                }
                String userOrientation = input
                        .gatherVOrHInput("Type [h] for horizontal or [v] for vertical for your ship placement.");
                String userPosition = input.gatherPositionInput("Type the position. (eg. F3)");
                char userLetter = userPosition.charAt(0);
                int posY = LettersAJ.getNo(userLetter);
                int posX = Integer.parseInt(userPosition.substring(1)) - 1;
                newShip = new Ship(shipType, userOrientation, posX, posY);
                isPlaceOK = ocean.placeOnTable(newShip);
            } while (!isPlaceOK);
            player.getListOfShips().add(newShip);
        }
        System.out.println(ocean.toString());
        return ocean;
    }
}
