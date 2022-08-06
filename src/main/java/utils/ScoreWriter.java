package utils;

import model.Score;

import java.io.FileWriter;
import java.io.IOException;

public class ScoreWriter {

    public void write(String fileName, Score score) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName, true);
        System.out.println("Writing " + score);
        fileWriter.write(score.name()+"|"+score.date()+"|"+score.guessing_time()+"|"+score.guessing_tries()+"\n");
        fileWriter.close();
    }
}
