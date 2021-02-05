package com.codecool.battleship_oop.board;

import java.util.ArrayList;
import java.util.List;

public enum LettersAJ {
    A('A', 0), B('B', 1), C('C', 2), D('D', 3), E('E', 4),
    F('F', 5), G('G', 6), H('H', 7), I('I', 8), J('J', 9);

    private final char letter;
    private final int no;

    LettersAJ(char letter, int no) {
        this.letter = letter;
        this.no = no;
    }

    public static char getLetter(int no) {
        for (LettersAJ letter : LettersAJ.values()) {
            if (letter.getNo() == no) {
                return letter.getLetter();
            }
        }
        throw new IllegalArgumentException();
    }

    public static int getNo(char aLetter) {
        for (LettersAJ letter : LettersAJ.values()) {
            if (letter.getLetter() == aLetter) {
                return letter.getNo();
            }
        }
        throw new IllegalArgumentException();
    }

    public static List<Character> getLettersList() {
        List<Character> letters = new ArrayList<>();
        for (LettersAJ letter : LettersAJ.values()) {
            letters.add(letter.getLetter());
        }
        return letters;
    }

    public int getNo() {
        return no;
    }

    public char getLetter() {
        return letter;
    }
}
