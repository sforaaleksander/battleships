import java.util.HashMap;
import java.util.Map;
import java.util.Random;


class Engine {
    public static Character[] lettersAJ = new Character[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' };
    public static Map<String, Integer> shipsNameLength;
    public static Map<Character, Integer> lettersToNums;
    public static Map<Integer, String> computerDifficulty;

    public static void clearScreen() {
        try {
            new ProcessBuilder("clear").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void initializeHashMaps() {
        shipsNameLength = new HashMap<>();
        shipsNameLength.put("Carrier", 5);
        shipsNameLength.put("Battleship", 4);
        shipsNameLength.put("Cruiser", 3);
        shipsNameLength.put("Submarine", 3);
        shipsNameLength.put("Destroyer", 2);

        lettersToNums = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            lettersToNums.put(lettersAJ[i], i);
        }

        computerDifficulty = new HashMap<>();
        computerDifficulty.put(1, "EASY");
        computerDifficulty.put(2, "NORMAL");
        computerDifficulty.put(3, "HARD");
    }


    public static boolean checkIfAvailable(Square field) {
        return field.getIsAvailable();
    }

    public static int fromLetterToNum(Character letter) {
        return lettersToNums.get(letter);
    }

    public static boolean checkIfFitsOnMap(Ship theShip, Square[][] table) {
        int isHorizontal = theShip.getOrientation().equals("H") ? theShip.getPosX() : theShip.getPosY();
        return (theShip.getLength() + isHorizontal - 1 < table.length && isHorizontal >= 0);
    }

    public static boolean isFieldAShip(Square field) {
        return field.getStatus().equals("SHIP");
    }

    public static boolean isFieldAlreadyHit(Square field) {
        return field.getStatus().equals("HIT") || field.getStatus().equals("MISSED");
    }

    public static boolean arePlayersAlive(Player player1, Player player2) {
        int counterPlayer1 = countShipSquares(player1);
        int counterPlayer2 = countShipSquares(player2);
        return (counterPlayer1 > 0 && counterPlayer2 > 0);
    }

    private static int countShipSquares(Player player) {
        int counter = 0;
        for (Square element1 : player.getPlayerBoard().getShipSquaresList()) {
            if (isFieldAShip(element1)) {
                counter++;
            }
        }
        return counter;
    }

    public static void changeHotSeats() {
        clearScreen();
        IO.gatherEmptyInput("\n\n\nPress any key when ready.");
        clearScreen();
    }

    public static int getRandomNumber(int range) {
        Random r = new Random();
        int number = r.nextInt(range);
        return number;
    }
}
