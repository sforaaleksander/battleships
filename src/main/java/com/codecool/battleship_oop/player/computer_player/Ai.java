package com.codecool.battleship_oop.player.computer_player;

import com.codecool.battleship_oop.board.Square;
import com.codecool.battleship_oop.board.SquareStatus;
import com.codecool.battleship_oop.player.Player;

public interface Ai {
    default String findDirection(Square baseShotSquare, int posY, int posX) {
        if (baseShotSquare.getPosY() > posY) {
            return "UP";
        } else if (baseShotSquare.getPosY() < posY) {
            return "DOWN";
        } else if (baseShotSquare.getPosX() > posX) {
            return "LEFT";
        } else if (baseShotSquare.getPosX() < posX) {
            return "RIGHT";
        }
        return "";
    }

    default String switchDirection(String direction) {
        return switch (direction) {
            case "LEFT" -> "RIGHT";
            case "RIGHT" -> "LEFT";
            case "UP" -> "DOWN";
            case "DOWN" -> "UP";
            default -> "";
        };
    }

    default void nextPosByDirection(ComputerPlayerNormal player) {
        switch (player.getDirection()) {
            case "LEFT" -> {
                player.setCurrentY(player.getBaseShotSquare().getPosY());
                player.setCurrentX(player.getBaseShotSquare().getPosX() - 1);
            }
            case "RIGHT" -> {
                player.setCurrentY(player.getBaseShotSquare().getPosY());
                player.setCurrentX(player.getBaseShotSquare().getPosX() + 1);
            }
            case "UP" -> {
                player.setCurrentY(player.getBaseShotSquare().getPosY() - 1);
                player.setCurrentX(player.getBaseShotSquare().getPosX());
            }
            case "DOWN" -> {
                player.setCurrentY(player.getBaseShotSquare().getPosY() + 1);
                player.setCurrentX(player.getBaseShotSquare().getPosX());
            }
        }
    }

    default void addFieldsAroundSunkShip(ComputerPlayerNormal shootingPlayer, Player playerBeingShot) {
        for (Square field : playerBeingShot.getPlayerBoard().getAllSquaresList()) {
            if (field.getStatus().equals(SquareStatus.HIT)) {
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        int x = field.getPosX() + j;
                        int y = field.getPosY() + i;
                        if (x >= 0 && x < 10 && y >= 0 && y < 10) {
                            shootingPlayer.addToFieldsNotToShootAt(playerBeingShot.getBoardSquares()[y][x]);
                        }
                    }
                }
            }
        }
    }
}
