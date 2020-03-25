import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class Engine {
    public static void clearScreen() {
        try {
            new ProcessBuilder("clear").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String gatherInput(String title) {
        System.out.println(title);
        String userInput = Main.scan.next().toUpperCase();
        return userInput;
    }

    public static int gatherIntInput(String title) {
        System.out.println(title);
        int userInput = Main.scan.nextInt();
        return userInput;
    }

    public static boolean checkIfAvailable(Square field) {
        if (field.getIsAvailable()) {
            return true;
        }
        return false;
    }

    public static int fromLetterToNum(Character letter) {
        Map<Character, Integer> letterNums = new HashMap<>();
        letterNums.put('A', 0);
        letterNums.put('B', 1);
        letterNums.put('C', 2);
        letterNums.put('D', 3);
        letterNums.put('E', 4);
        letterNums.put('F', 5);
        letterNums.put('G', 6);
        letterNums.put('H', 7);
        letterNums.put('I', 8);
        letterNums.put('J', 9);
        return letterNums.get(letter);
    }

    public static boolean checkIfFitsOnMap(Ship theShip, Square[][] table) {
        if (theShip.getOrientation().equals("H")) {
            if (theShip.getLength() + theShip.getPosX() - 1 < table.length && theShip.getPosX() >= 0) {
                return true;
            }
            return false;
        } else {
            if (theShip.getLength() + theShip.getPosY() - 1 < table.length && theShip.getPosY() >= 0) {
                return true;
            }
            return false;
        }
    }

    public static boolean isFieldAShip(int posX, int posY, Square[][] board) {
        if (board[posY][posX].getStatus().equals("SHIP")) {
            return true;
        }
        return false;
    }

    public static boolean isFieldAlreadyHit(int posX, int posY, Square[][] board) {
        if (board[posY][posX].getStatus().equals("HIT") || board[posY][posX].getStatus().equals("MISSED")) {
            return true;
        }
        return false;
    }

    public static boolean areBothPlayersAlive(Player player1, Player player2) {
        for (Square element1 : player1.getPlayerBoard().getAllShipSquares()) {
            if (element1.getStatus().equals("SHIP")) {
                for (Square element2 : player2.getPlayerBoard().getAllShipSquares()) {
                    if (element2.getStatus().equals("SHIP")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean arePlayersAlive(Player player1, Player player2) {
        int counterPlayer1 = 0;
        int counterPlayer2 = 0;

        for (Square element1 : player1.getPlayerBoard().getAllShipSquares()) {
            if (element1.getStatus().equals("SHIP")) {
                counterPlayer1 = counterPlayer1 + 1;
            }
        }
        for (Square element2 : player2.getPlayerBoard().getAllShipSquares()) {
            if (element2.getStatus().equals("SHIP")) {
                counterPlayer2 = counterPlayer2 + 1;
            }
        }
        if (counterPlayer1 > 0 && counterPlayer2 > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static void changeHotSeats() {
        clearScreen();
        gatherInput("\n\n\nPress any key when ready.");
        clearScreen();
    }

    public static int getRandomNumber() {
        Random r = new Random();
        int number = r.nextInt(10);
        return number;
    }
}

// public static boolean checkPlacement(Ship theShip, Square[][] oceanBoard) {
// if (theShip.getOrientation().equals("HORIZONTAL")) {
// if (theShip.getPosX() == 0){
// for (int i = theShip.getPosX(); i < theShip.getPosX() + theShip.getLength() +
// 1; i++) {
// if (oceanBoard[theShip.getPosY()][i].getStatus().equals("SHIP")){
// return false;
// }
// if (oceanBoard[theShip.getPosY()][i].getStatus().equals("SHIP")){
// return false;
// }
// }
// }
// } else {
// for (int i = theShip.getPosY(); i < theShip.getPosY() + theShip.getLength();
// i++) {
// if (oceanBoard[i][theShip.getPosX()].getStatus().equals("SHIP")){
// return false;
// };
// }

// }
// return true;}