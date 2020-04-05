
public class AIhelpers {
    public static String findDirection(Square baseShotSquare, int posY, int posX) {
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

    public static String switchDirection(String direction) {
        switch (direction) {
            case "LEFT":
                return "RIGHT";
            case "RIGHT":
                return "LEFT";
            case "UP":
                return "DOWN";
            case "DOWN":
                return "UP";
        }
        return "";
    }

    public static void nextPosByDirection(ComputerPlayer player) {
        switch (player.getDirection()) {
            case "LEFT":
                player.setCurrentY(player.getBaseShotSquare().getPosY());
                player.setCurrentX(player.getBaseShotSquare().getPosX() - 1);
                break;
            case "RIGHT":
                player.setCurrentY(player.getBaseShotSquare().getPosY());
                player.setCurrentX(player.getBaseShotSquare().getPosX() + 1);
                break;
            case "UP":
                player.setCurrentY(player.getBaseShotSquare().getPosY() - 1);
                player.setCurrentX(player.getBaseShotSquare().getPosX());
                break;
            case "DOWN":
                player.setCurrentY(player.getBaseShotSquare().getPosY() + 1);
                player.setCurrentX(player.getBaseShotSquare().getPosX());
                break;
        }

    }

    public static boolean checkColour(int posY, int posX, String colour) {
        if (colour.equals("WHITE")) {
            return (posX % 2 == 0 && posY % 2 == 0) || (posX % 2 != 0 && posY % 2 != 0);
        } else
            return (posX % 2 != 0 && posY % 2 == 0) || (posX % 2 == 0 && posY % 2 != 0);
    }

    public static void addFieldsAroundSunkShip(ComputerPlayer shootingPlayer, Player playerBeingShot) {
        for (Square field : playerBeingShot.getPlayerBoard().getAllSquaresList()) {
            if (field.getStatus().equals("HIT")) {
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        int x = field.getPosX() + j;
                        int y = field.getPosY() + i;
                        if (x >= 0 && x < 10 && y >= 0 && y < 10 && !shootingPlayer.getListOfFieldsNotToShootAt()
                                .contains(playerBeingShot.getPlayerBoard().getOceanBoard()[y][x])) {
                            shootingPlayer.addToListOfFieldsNotToShootAt(playerBeingShot.getPlayerBoard().getOceanBoard()[y][x]);
                        }
                    }
                }
            }
        }
    }
}