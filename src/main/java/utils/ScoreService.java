package utils;

import model.Score;

import java.util.Comparator;
import java.util.List;

public class ScoreService {

    private static final FileReader fileReader = new FileReader();

    public List<Score> getTopScores(int range) {
        List<Score> scores = fileReader.getScores("history.txt");
        scores.sort(Comparator.comparing(score -> score.guessing_tries()));
        return scores.stream().limit(10).toList();
    }
}
