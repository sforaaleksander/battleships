package com.codecool.battleship.input;

import com.codecool.battleship.board.LettersAJ;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Input {
    private final Scanner scan;

    public Input() {
        scan = new Scanner(System.in);
        scan.useDelimiter(System.lineSeparator());
    }

    public Scanner getScan() {
        return scan;
    }

    public String gatherInput(String title) {
        System.out.println(title);
        boolean validInput = true;
        String userInput;
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

    public void gatherEmptyInput(String title) {
        System.out.println(title);
        scan.next();
    }

    public int gatherIntInput(String title, int range) {
        System.out.println(title);
        String userInput;
        int userInt = 1;
        boolean validInput = false;
        while (!validInput) {
            userInput = scan.next();
            if (!userInput.equals("") && userInput.matches("^[0-9]*$") && userInt <= range) {
                userInt = Integer.parseInt(userInput);
                validInput = true;
            }
        }
        return userInt;
    }

    public String gatherVOrHInput(String title) {
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

    public String gatherPositionInput(String title) {
        System.out.println(title);
        boolean validInput = true;
        String userInput;
        do {
            if (!validInput) {
                System.out.println("Please provide the position in a correct format. (eg. F3)");
            }
            validInput = false;
            userInput = scan.next().toUpperCase();
            if (!userInput.equals("") && userInput.length() > 1) {
                if (userInput.substring(1).matches("^[0-9]*$")) {
                    if (LettersAJ.getLettersList().contains(userInput.charAt(0))
                            && Integer.parseInt(userInput.substring(1)) > 0
                            && Integer.parseInt(userInput.substring(1)) <= 10) {
                        validInput = true;
                    }
                }
            }
        } while (!validInput);
        return userInput;
    }

    public void saveHighScoreToFile(String playerName, int score) {
        LocalDate dateToday = LocalDate.now();
        String date = dateToday.toString();
        String highScoreEntry = playerName + "|" + date + "|" + score + "\n";
        try {
            FileWriter fileWriter = new FileWriter("highscores.txt", true);
            fileWriter.write(highScoreEntry);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            gatherEmptyInput("Could not write to file.");
        }
    }

    public String loadHighScores() {
        StringBuilder tenHighestScores = new StringBuilder();
        ArrayList<String[]> allHighScores = new ArrayList<>();
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
        Arrays.sort(sortedArr, Comparator.comparingInt(a -> Integer.parseInt(a[2])));
        int i = 1;
        int z = sortedArr.length - 1;
        while (z > 0 && i < 11) {
            String[] testArr = sortedArr[z];
            tenHighestScores.append(i).append(". ").append(String.join("|", testArr)).append("\n");
            z--;
            i++;
        }
        return tenHighestScores.toString();
    }
}
