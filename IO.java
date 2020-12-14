import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;


public class IO {
    public static Scanner scan;

    public static void initializeScanner() {
        scan = new Scanner(System.in);
        scan.useDelimiter(System.lineSeparator());
    }

    public static String gatherInput(String title) {
        System.out.println(title);
        boolean validInput = true;
        String userInput = "";
        do {
            if (!validInput) {
                System.out.println("Your input must contain at least one character. Enter again: ");
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
                    if (Arrays.asList(Engine.lettersAJ).contains(userInput.charAt(0))
                            && Integer.parseInt(userInput.substring(1)) > 0
                            && Integer.parseInt(userInput.substring(1)) <= 10) {
                        validInput = true;
                    }
                }
            }
        } while (!validInput);
        return userInput;
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
            IO.gatherEmptyInput("Could not write to file.");
        }
    }

    public static String loadHighScores() {
        String tenHighestScores = "";
        ArrayList<String[]> allHighScores = new ArrayList<String[]>();
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
            tenHighestScores += i+1 + ". " + String.join("|", testArr) + "\n";
            z--;
            i++;
        }
        return tenHighestScores;
    }
}