package minesweeper.model;
import minesweeper.cells.Cell;
import minesweeper.exceptions.*;
import minesweeper.highscore.*;
import java.util.Random;

public final class GameModel {
    private int openedCells;
    private Cell[][] cells;
    private int fieldSize;
    private int numberOfMines;
    private int numberOfFlags;
    private boolean gameOver;
    private boolean gameWin;
    private Timer gameTimer;

    public class Timer extends Thread {
        private Integer seconds = 0;
        @Override
        public void run() {
            synchronized (this) {
                try {
                    while (!gameOver) {
                        this.wait(1000);
                        seconds++;
                    }
                } catch (InterruptedException e) {
                    throw new TimerException("Timer Exception", e);
                }
            }
        }

        public  Integer getSeconds() {
            return seconds;
        }
    }


    public void exitGame() {
        System.exit(0);
    }

    public void createNewGame(String fieldSize, String numberOfMines) {
        try {
            this.fieldSize = Integer.parseInt(fieldSize);
            this.numberOfMines = Integer.parseInt(numberOfMines);
        } catch (NumberFormatException e) {
            throw new BadGameSettingsParameter("Parameters must be integer ", e);
        }
        if (this.numberOfMines >= this.fieldSize * this.fieldSize) {
            throw new MinesOverflowException("To many mines provided: " + numberOfMines);
        } else if (this.numberOfMines < 0) {
            throw new NegativeMinesNumberException("Number of mines must be positive value");
        } else if (this.fieldSize < 0) {
            throw new NegativeFieldSizeException("Number of fields must be positive");
        }
        gameOver = false;
        gameWin = false;
        openedCells = 0;
        numberOfFlags = this.numberOfMines;
        cells = new Cell[this.fieldSize][this.fieldSize];
        gameTimer = new Timer();
        gameTimer.start();
    }



    public void openCells(int row, int col) {
        if (openedCells == 0) {
            createField(row, col);
            revealCells(row, col);
            System.out.println("OP cells 0");
        } else if (cells[row][col].isBomb() && !gameOver) {
            revealAllBombs();
            gameOver = true;
        } else if (!gameOver) {
            revealCells(row, col);
        }
        if (openedCells == fieldSize * fieldSize) {
           gameWin = true;
           gameOver = true;
        }
    }

    public void revealCells(int row, int col) {
        if (row < 0 || col < 0 || row >= fieldSize || col >= fieldSize) {
            return;
        }
        if (countBombsNear(row, col) == 0 && !cells[row][col].isOpened()) {
            cells[row][col].setOpened(true);
            openedCells++;
            revealCells(row - 1, col - 1);
            revealCells(row - 1, col);
            revealCells(row - 1, col + 1);
            revealCells(row, col - 1);
            revealCells(row, col + 1);
            revealCells(row + 1, col - 1);
            revealCells(row + 1, col);
            revealCells(row + 1, col + 1);
        } else if (!cells[row][col].isOpened()) {
            cells[row][col].setOpened(true);
            openedCells++;
        }
    }

    public void revealAllBombs() {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (cells[i][j].isBomb()) {
                   cells[i][j].setOpened(true);
                }
            }
        }
    }

    public int countBombsNear(int row, int col) {
            int bombCount = 0;

            for (int i = row - 1; i <= row + 1; i++) {
                for (int j = col - 1; j <= col + 1; j++) {
                    if(i ==row && j == col){
                        continue;
                    }
                    if (i >= 0 && i < fieldSize && j >= 0 && j < fieldSize) {
                        if (cells[i][j].isBomb()) {
                            bombCount++;
                        }
                    }
                }
            }
            return bombCount;
    }


    public void putFlag(int row, int col) {
        if (openedCells != 0 && !gameOver) {
            if (cells[row][col].isFlag()) {
                cells[row][col].setFlag(false);
                cells[row][col].setOpened(false);
                openedCells--;
                numberOfFlags++;
            } else if (numberOfFlags > 0 && !cells[row][col].isOpened()) {
                cells[row][col].setFlag(true);
                cells[row][col].setOpened(true);
                openedCells++;
                numberOfFlags--;

            }
        }
        if (openedCells == fieldSize * fieldSize) {
            gameWin = true;
            gameOver = true;
        }
    }

    public void createField(int row, int col) {
        Random random = new Random();
        int minesPlanted = 0, x, y;
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                cells[i][j] = new Cell(false, false, false);
            }
        }
        while (minesPlanted < numberOfMines) {
            x = random.nextInt(fieldSize);
            y = random.nextInt(fieldSize);
            if (x == row && y == col || cells[x][y].isBomb()) {
                continue;
            } else {
                cells[x][y].setBomb(true);
                minesPlanted++;
            }
        }
    }

    public void confirmWinner(String winnerName) {
        HighScoreHandler highScoreHandler = new HighScoreHandler("src/main/resources/Results.json");
        highScoreHandler.addScores(new HighScore(winnerName, gameTimer.seconds, fieldSize));
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    public int getNumberOfFlags() {
        return numberOfFlags;
    }

    public boolean isFlag(int row, int col){
        return cells[row][col].isFlag();
    }

    public boolean isOpened(int row, int col){
        return cells[row][col].isOpened();
    }

    public boolean isBomb(int row, int col){
        return cells[row][col].isBomb();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isGameWin(){return gameWin;}

    public Timer getGameTimer() {
        return gameTimer;
    }

    //    public void setMainMenuController(MainMenuController mainMenuController) {
//        this.mainMenuController = mainMenuController;
//    }

//    public void setConsoleController(ConsoleController consoleController) {
//        this.consoleController = consoleController;
//    }

    private String[] splitLine(String line) {
        line = line.trim();
        if (line.isEmpty() || line.startsWith("#")) {
            return null;
        }
        return line.split(" ");
    }


}