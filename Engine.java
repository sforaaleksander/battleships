import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;

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
        boolean validInput = true;
        String userInput = "";
        do {
            if (!validInput) {
                System.out.println("Text MUST have some length! Enter again: ");
            }
            validInput = false;
            userInput = Main.scan.next().toUpperCase();
            if (!userInput.equals("")) {
                validInput = true;
            }
        } while (!validInput);
        return userInput;
    }

    public static String gatherEmptyInput(String title) {
        System.out.println(title);
        String userInput = Main.scan.next().toUpperCase();
        return userInput;
    }

    public static int gatherIntInput(String title, int range) {
        System.out.println(title);
        String userInput;
        int userInt = 1;
        boolean validInput = false;
        while (!validInput) {
            userInput = Main.scan.next();
            if (!userInput.equals("")) {
                if (userInput.matches("^[0-9]*$")) {
                    userInt = Integer.parseInt(userInput);
                    if (userInt > 0 && userInt <= range) {
                        validInput = true;
                    }
                }
            }
        }
        return userInt;
    }

    public static String gatherVOrHInput(String title) {
        System.out.println(title);
        String userInput = "";
        boolean validInput = false;
        while (!validInput) {
            userInput = Main.scan.next().toUpperCase();
            if (userInput.equals("H") || userInput.equals("V")) {
                validInput = true;
            }
        }
        return userInput;
    }

    public static String gatherPositionInput(String title) {
        System.out.println(title);
        Character[] letters = new Character[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' };
        boolean validInput = true;
        String userInput = "";
        do {
            if (!validInput) {
                System.out.println("Please provide the position in a correct format. (eg. F3)");
            }
            validInput = false;
            userInput = Main.scan.next().toUpperCase();
            if (!userInput.equals("") && userInput.length()>1) {
                if (userInput.substring(1).matches("^[0-9]*$")) {
                    if (Arrays.asList(letters).contains(userInput.charAt(0))
                            && Integer.parseInt(userInput.substring(1)) > 0
                            && Integer.parseInt(userInput.substring(1)) <= 10) {
                        validInput = true;
                    }
                }
            }
        } while (!validInput);
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

    public static boolean isFieldAShip(Square field) {
        if (field.getStatus().equals("SHIP")) {
            return true;
        }
        return false;
    }

    public static boolean isFieldAlreadyHit(Square field) {
        if (field.getStatus().equals("HIT") || field.getStatus().equals("MISSED")) {
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
        gatherEmptyInput("\n\n\nPress any key when ready.");
        clearScreen();
    }

    public static int getRandomNumber(int range) {
        Random r = new Random();
        int number = r.nextInt(range);
        return number;
    }

    public static void addFieldsAsNotToShootAt(ArrayList<Square> list) {
    }
}
