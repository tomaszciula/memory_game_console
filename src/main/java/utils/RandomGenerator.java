package utils;

import exception.RandomGeneratorException;

import java.util.*;

public class RandomGenerator {

    private static final Random random = new Random();

    public List<String> getRandomWords(List<String> words, int size) {
        if(words.size() < size) {
            throw new RandomGeneratorException("Words list too short");
        }
        List<String> randomWords = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            int randomIndex = random.nextInt(words.size());
            randomWords.add(words.get(randomIndex));
        }
        return randomWords;
    }

}
