public class HumanPlayer extends Player{
    HumanPlayer(){
    super();
    this.playerBoard = placeManuallyOrRandomly();
    }

    private Ocean createPlayerBoard() {
        int oceanSize = 10;
        boolean isPlaceOK = true;
        ArrayList<Ship> list = new ArrayList<>();
        Ocean ocean = new Ocean(oceanSize);
        for (String key : Engine.shipsNameLength.keySet()) {
            Ship newShip;
            do {
                System.out.println(ocean.toString());
                System.out.println("Place the " + key + " ship on your board. The ship's length is "
                        + Engine.shipsNameLength.get(key) + ".");
                if (!isPlaceOK) {
                    System.out.println("The ships must fit on board and may not touch each other.");
                }
                String userOrientation = Engine
                        .gatherVOrHInput("Type [h] for horizontal or [v] for vertical for your ship placement.");
                String userPosition = Engine.gatherPositionInput("Type the position. (eg. F3)");
                char userLetter = userPosition.charAt(0);
                int posY = Engine.fromLetterToNum(userLetter);
                int posX = Integer.parseInt(userPosition.substring(1)) - 1;
                int length = Engine.shipsNameLength.get(key);
                newShip = new Ship(length, userOrientation, posX, posY, key);
                list.add(newShip);
                isPlaceOK = ocean.placeOnTable(newShip);
            } while (!isPlaceOK);
            this.getListOfShips().add(newShip);
            ocean.setFieldsUnavailable();
        }
        System.out.println(ocean.toString());
        Engine.changeHotSeats();
        return ocean;
    }

    public void displayScreen(String message) {
        Engine.clearScreen();
        System.out.println("CURRENT PLAYER: " + this.getPlayerName());
        System.out.println("CURRENT TURN: " + this.getTurn());
        System.out.println("");
        String playerBoard = this.getPlayerBoard().toString();
        String hitsBoard = this.getBoardOfShots().toString();
        System.out.println("YOUR SHIPS");
        System.out.println(playerBoard);
        System.out.println("_____________________\n");
        System.out.println("BOARD OF SHOTS");
        System.out.println(hitsBoard);
        System.out.println(message);
    }
}