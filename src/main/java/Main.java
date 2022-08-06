import game.Game;
import model.Score;
import utils.FileReader;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {

    private static final Game game = new Game();

    public static void main(String[] args) throws IOException {
        game.play();
    }
}
