package game;


import model.Score;
import utils.FileReader;
import utils.RandomGenerator;
import utils.ScoreService;
import utils.ScoreWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Game {

    private static final int EASY_LEVEL_WORDS = 4;
    private static final int EASY_LEVEL_CHANCES = 10;
    private static final int HARD_LEVEL_WORDS = 8;
    private static final int HARD_LEVEL_CHANCES = 15;

    private static final int TOP_SCORES_RANGE = 10;
    private static final String DATA_FILE_NAME = "Words.txt";

    private static final String HISTORY_FILE_NAME = "history.txt";
    private static final FileReader reader = new FileReader();
    private static final RandomGenerator randomGenerator = new RandomGenerator();
    private static final Board board = new Board();
    private static final BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

    private static final ScoreWriter scoreWriter = new ScoreWriter();

    private static final ScoreService scoreService = new ScoreService();

    public void play() throws IOException {
        String continueGame = "YES";
        printBestScores();
        do {
            System.out.println("Choose level, type EASY or HARD");
            String level = consoleReader.readLine();
            System.out.println("Please provide your name: ");
            String name = consoleReader.readLine();
            if (level.equals("EASY") || level.equals("HARD")) {
                if (level.equals("EASY")) {
                    long startTime = System.nanoTime();
                    int chances = generateBoard(EASY_LEVEL_WORDS, EASY_LEVEL_CHANCES, "EASY");
                    long endTime = System.nanoTime();
                    long duration = (endTime - startTime) / 1000000;
                    if (chances == 0) {
                        System.out.println("You loose :(");
                    } else {
                        long durationInSec = duration / 1000;
                        scoreWriter.write(HISTORY_FILE_NAME,
                                new Score(name, LocalDate.now(), durationInSec, chances));
                        System.out.printf("End game. Used chances: %s %n Played time in seconds %d",
                                chances, durationInSec);
                    }
                } else {
                    long startTime = System.nanoTime();
                    int chances = generateBoard(HARD_LEVEL_WORDS, HARD_LEVEL_CHANCES, "HARD");
                    long endTime = System.nanoTime();
                    long duration = (endTime - startTime) / 1000000;
                    if (chances == 0) {
                        System.out.println("You loose :(");
                    } else {
                        System.out.printf("%nEnd game. Used chances: %s %n Played time in seconds %d",
                                chances, duration / 1000);
                    }
                }
            } else {
                System.out.println("Invalid choice");
            }
            System.out.println();
            System.out.println("Restart game? Please provide YES or NO");
            continueGame = consoleReader.readLine();
            System.out.println();
        } while (continueGame.equals("YES"));

    }

    private int generateBoard(int numberOfWords, int numberOfChances, String level) throws IOException {
        List<String> words = reader.getWords(DATA_FILE_NAME);
        HashMap<Integer, Character> guessedA = new HashMap<>();
        HashMap<Integer, Character> guessedB = new HashMap<>();
        List<String> wordsA = randomGenerator.getRandomWords(words, numberOfWords);
        List<String> wordsB = new ArrayList<>(wordsA);
        Collections.shuffle(wordsB);
        int chances = 0;
        board.drawAllHidden(numberOfWords);
        System.out.println();
        int indexA = -1;
        int indexB = -1;
        do {
            System.out.println();
            System.out.println("Please provide coordinates to uncover ");
            String firstCoord = consoleReader.readLine();
            while (true) {
                if (getIndex(firstCoord) >= wordsA.size()) {
                    System.out.println("Invalid coordinates. Please provide coordinates to uncover ");
                    firstCoord = consoleReader.readLine();
                } else {
                    break;
                }
            }
            if (getRow(firstCoord) == 'A') {
                indexA = getIndex(firstCoord);
            } else {
                indexB = getIndex(firstCoord);
            }
            System.out.println("-".repeat(numberOfWords * 2));
            System.out.printf("Level: %s%n", level);
            System.out.printf("Guessed chances: %d%n", chances);
            System.out.printf("Max chances: %d%n", numberOfChances);
            board.drawUncover(wordsA, wordsB, getIndex(firstCoord), getRow(firstCoord), guessedA, guessedB);
            System.out.println();
            System.out.println("Please provide coordinates to uncover ");
            String secondCoord = consoleReader.readLine();
            while (true) {
                if (getIndex(secondCoord) >= wordsB.size()) {
                    System.out.println("Invalid coordinates. Please provide coordinates to uncover ");
                    secondCoord = consoleReader.readLine();
                } else {
                    break;
                }
            }
            if (getRow(secondCoord) == 'A') {
                indexA = getIndex(secondCoord);
            } else {
                indexB = getIndex(secondCoord);
            }
            System.out.println("-".repeat(numberOfWords * 2));
            System.out.printf("Level: %s%n", level);
            System.out.printf("Guessed chances: %d%n", chances);
            System.out.printf("Max chances: %d%n", numberOfChances);
            board.drawUncover(wordsA, wordsB, getIndex(secondCoord), getRow(secondCoord), guessedA, guessedB);
            if (indexA != -1 && indexB != -1) {
                if (board.isGuessed(wordsA, wordsB, indexA, indexB)) {
                    guessedA.put(indexA, 'A');
                    guessedB.put(indexB, 'B');
                    System.out.println();
                    System.out.print("You guessed!");
                } else {
                    chances++;
                }
            }
            if (guessedA.size() == wordsA.size()) {
                return chances;
            }
        } while (chances < numberOfChances);
        return 0;
    }

    private static char getRow(String coord) {
        return coord.charAt(0);
    }

    private static int getIndex(String coord) {
        return Integer.parseInt(coord.substring(coord.length() - 1));
    }

    private void printBestScores() {
        List<Score> scores = scoreService.getTopScores(TOP_SCORES_RANGE);
        System.out.println("Best scores :");
        scores.forEach(System.out::println);
    }
}
