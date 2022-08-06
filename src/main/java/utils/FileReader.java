package utils;

import model.Score;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileReader {

    public List<String> getWords(String fileName) {
        List<String> words = new ArrayList<>();
        try {
            Scanner reader = new Scanner(new File(fileName));
            while (reader.hasNextLine()) {
                words.add(reader.nextLine());
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return words;
    }

    public List<Score> getScores(String fileName) {
        List<Score> scores = new ArrayList<>();
        try {
            Scanner reader = new Scanner(new File(fileName));
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] splitLine = line.split("\\|");
                if(splitLine.length == 4) {
                    scores.add(new Score(
                            splitLine[0],
                            LocalDate.parse(splitLine[1]),
                            Long.parseLong(splitLine[2]),
                            Integer.parseInt(splitLine[3])));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return scores;
    }
}
