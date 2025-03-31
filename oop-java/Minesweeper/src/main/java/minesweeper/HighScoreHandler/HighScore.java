package minesweeper.HighScoreHandler;

public class HighScore {
    private String name;
    private int time;
    private int fieldSize;

    public HighScore(){
    }

    public HighScore(String name, int time, int fieldSize){
        this.name = name;
        this.time = time;
        this.fieldSize = fieldSize;
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public int getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public void setFieldSize(int fieldSize) {
        this.fieldSize = fieldSize;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(int time) {
        this.time = time;
    }
}

