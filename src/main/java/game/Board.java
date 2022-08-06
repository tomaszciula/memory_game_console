package game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    public void drawAllHidden(int size) {
        System.out.print("  ");
        for (int i = 0; i < size; i++) {
            System.out.print(" " +i+" ");
        }
        System.out.println();
        System.out.print("A ");
        for (int i = 0; i < size; i++) {
            System.out.print(" X ");
        }
        System.out.println();
        System.out.print("B ");
        for (int i = 0; i < size; i++) {
            System.out.print(" X ");
        }
    }

    public void drawUncover(List<String> wordsA, List<String> wordsB, int index, char row, HashMap<Integer, Character> guessedA, HashMap<Integer, Character> guessedB) {
        System.out.print("  ");
        for (int i = 0; i < wordsA.size(); i++) {
            System.out.print(" " +i+" ");
        }
        System.out.println();
        System.out.print("A ");
        for (int i = 0; i < wordsA.size(); i++) {
            if (i == index && row == 'A') {
                System.out.print(wordsA.get(index));
            } else if (guessedA.containsKey(i)) {
                if (guessedA.get(i) == 'A') {
                    System.out.print(wordsA.get(i) + " ");
                } else {
                    System.out.print(" X ");
                }
            } else {
                System.out.print(" X ");
            }
        }
        System.out.println();
        System.out.print("B ");
        for (int i = 0; i < wordsB.size(); i++) {
            if (i == index && row == 'B') {
                System.out.print(wordsB.get(index));
            } else if (guessedB.containsKey(i)) {
                if (guessedB.get(i) == 'B') {
                    System.out.print(wordsB.get(i) + " ");
                } else {
                    System.out.print(" X ");
                }
            } else {
                System.out.print(" X ");
            }
        }
    }
    public boolean isGuessed(List<String> wordsA, List<String> wordsB, int indexA, int indexB) {
        return wordsA.get(indexA).equals(wordsB.get(indexB));
    }
}
