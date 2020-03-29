import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;
import java.time.LocalDate;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

class Engine {
    public static Scanner scan;
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

    public static void initializeScanner() {
        scan = new Scanner(System.in);
        scan.useDelimiter(System.lineSeparator());

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


    public static String gatherInput(String title) {
        System.out.println(title);
        boolean validInput = true;
        String userInput = "";
        do {
            if (!validInput) {
                System.out.println("Your input must contain at least one character! Enter again: ");
            }
            validInput = false;
            userInput = scan.next().toUpperCase();
            if (!userInput.equals("")) {
                validInput = true;
            }
        } while (!validInput);
        return userInput;
    }

    public static String gatherEmptyInput(String title) {
        System.out.println(title);
        String userInput = scan.next().toUpperCase();
        return userInput;
    }

    public static int gatherIntInput(String title, int range) {
        System.out.println(title);
        String userInput;
        int userInt = 1;
        boolean validInput = false;
        while (!validInput) {
            userInput = scan.next();
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
            userInput = scan.next().toUpperCase();
            if (userInput.equals("H") || userInput.equals("V")) {
                validInput = true;
            }
        }
        return userInput;
    }

    public static String gatherPositionInput(String title) {
        System.out.println(title);
        boolean validInput = true;
        String userInput = "";
        do {
            if (!validInput) {
                System.out.println("Please provide the position in a correct format. (eg. F3)");
            }
            validInput = false;
            userInput = scan.next().toUpperCase();
            if (!userInput.equals("") && userInput.length() > 1) {
                if (userInput.substring(1).matches("^[0-9]*$")) {
                    if (Arrays.asList(lettersAJ).contains(userInput.charAt(0))
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
        gatherEmptyInput("\n\n\nPress any key when ready.");
        clearScreen();
    }

    public static int getRandomNumber(int range) {
        Random r = new Random();
        int number = r.nextInt(range);
        return number;
    }

    public static void saveHighScoreToFile(String playerName, int score) {
        LocalDate dateToday = LocalDate.now();
        String date = dateToday.toString();
        String highScoreEntry = playerName + "|" + date + "|" + Integer.toString(score) + "\n";
        try {
            FileWriter fileWriter = new FileWriter("highscores.txt", true);
            fileWriter.write(highScoreEntry);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e);
            Engine.gatherEmptyInput("Could not write to file.");
        }
    }

    public static String loadHighScores() {
        String tenHighestScores = "";
        List<String[]> allHighScores = new ArrayList<String[]>();
        try {
            File file = new File("highscores.txt");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String[] entry = reader.nextLine().split("\\|");
                allHighScores.add(entry);
            }
            reader.close();
        } catch (IOException e) {
            return "No highscores yet.";
        }
        String[][] sortedArr = allHighScores.toArray(new String[allHighScores.size()][3]);
        Arrays.sort(sortedArr, (a, b) -> Integer.compare(Integer.parseInt(a[2]), Integer.parseInt(b[2])));
        int i = 0;
        int z = sortedArr.length - 1;
        while (z > 0 && i < 10) {
            String[] testArr = sortedArr[z];
            tenHighestScores += String.join("|", testArr) + "\n";
            z--;
            i++;
        }
        return tenHighestScores;
    }
}
