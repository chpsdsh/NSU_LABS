package minesweeper.highscore;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import minesweeper.exceptions.HighScoreHandlingException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HighScoreHandler {
    private final File file;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private ArrayList<HighScore> scores;


    public HighScoreHandler(String path) {
        file = new File(path);
        this.scores = loadScores();
    }

    private ArrayList<HighScore> loadScores() {
        if (!file.exists()) return new ArrayList<>();
        try {
            return objectMapper.readValue(file, new TypeReference<ArrayList<HighScore>>() {
            });
        } catch (IOException e) {
            throw new HighScoreHandlingException("Error loading scores ", e);
        }
    }

    public void addScores(HighScore score) {
        scores.add(score);
        scores.sort((b, a) -> Integer.compare(b.getTime(), a.getTime()));
        saveScores();
    }

    public void saveScores() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, scores);
        } catch (IOException e) {
            throw new HighScoreHandlingException("Error saving scores ", e);
        }
    }

    public ArrayList<HighScore> getScores() {
        return scores;
    }
}
