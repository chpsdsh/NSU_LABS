package minesweeper.highscore;

public class HighScore {
    private String name;
    private int time;
    private int fieldSize;
    private int numberOfMines;

    public int getFieldSize() {
        return fieldSize;
    }

    public int getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    public HighScore() {
    }

    public HighScore(String name, int time, int fieldSize, int numberOfMines) {
        this.name = name;
        this.time = time;
        this.fieldSize = fieldSize;
        this.numberOfMines = numberOfMines;
    }
}

