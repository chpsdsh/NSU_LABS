package minesweeper.console.view;

import minesweeper.console.controller.ConsoleController;
import minesweeper.highscore.HighScore;
import minesweeper.highscore.HighScoreHandler;
import minesweeper.model.GameModel;
import minesweeper.timerlistener.TimerListener;

import java.util.ArrayList;

public class ConsoleView {
    private char[][] field;
    private final GameModel model;
    private int seconds;
    private ConsoleController consoleController;

    public ConsoleView(GameModel model) {
        this.model = model;
        TimerListener timerListener = seconds -> this.seconds = seconds;
        model.setTimerListener(timerListener);
        consoleController = new ConsoleController(this);
    }

    public void createField(String[] parameters) {
        model.createNewGame(parameters[0], parameters[1]);
        field = new char[model.getFieldSize()][model.getFieldSize()];
        for (int i = 0; i < model.getFieldSize(); i++) {
            for (int j = 0; j < model.getFieldSize(); j++) {
                field[i][j] = '@';
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private void updateField() {
        for (int i = 0; i < model.getFieldSize(); i++) {
            for (int j = 0; j < model.getFieldSize(); j++) {
                if (model.isOpened(i, j) && !model.isFlag(i, j)) {
                    updateCell(i, j, model.countBombsNear(i, j));
                }
            }
        }
    }

    private void drawField() {
        System.out.println("Time: " + seconds + " Flags: " + model.getNumberOfFlags());
        for (int i = 0; i < model.getFieldSize(); i++) {
            for (int j = 0; j < model.getFieldSize(); j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public void openCells(String[] coordinates) {
        model.openCells(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
        if (model.isGameWin()) {
            updateField();
            drawField();
        } else if (model.isGameOver()) {
            updateBombs();
            drawField();
        } else {
            updateField();
            drawField();
        }
    }

    private void updateBombs() {
        for (int i = 0; i < model.getFieldSize(); i++) {
            for (int j = 0; j < model.getFieldSize(); j++) {
                if (model.isBomb(i, j)) {
                    field[i][j] = '*';
                }
            }
        }
    }

    private void updateCell(int row, int col, int bombCount) {
        switch (bombCount) {
            case 0:
                field[row][col] = '0';
                break;
            case 1:
                field[row][col] = '1';
                break;
            case 2:
                field[row][col] = '2';
                break;
            case 3:
                field[row][col] = '3';
                break;
            case 4:
                field[row][col] = '4';
                break;
            case 5:
                field[row][col] = '5';
                break;
            case 6:
                field[row][col] = '6';
                break;
            case 7:
                field[row][col] = '7';
                break;
            case 8:
                field[row][col] = '8';
                break;
        }
    }

    public void drawFlag(String[] coordinates) {
        int row = Integer.parseInt(coordinates[0]), col = Integer.parseInt(coordinates[1]);
        model.putFlag(row, col);
        if (model.isFlag(row, col)) {
            field[row][col] = 'F';
        } else {
            field[row][col] = '@';
        }
        if (!model.isGameWin()) {
            System.out.println("Flags remaining: " + model.getNumberOfFlags());
            updateField();
            drawField();
        }
    }

    public void gameWin() {
        drawField();
        System.out.println("YOU WON!!!");
        System.out.println("Write your name");
    }

    public void gameOver() {
        updateBombs();
        System.out.println("GAME OVER");
        printRestartInfo();
    }

    public void restartOrExit(String parameter) {
        switch (parameter) {
            case "EXIT" -> model.exitGame();
            case "RESTART" -> restartGame();
            case "HIGHSCORE" -> showHighScores();
            case null, default -> System.out.println("WRITE RESTART/EXIT/HIGHSCORE");
        }
    }

    private void restartGame() {
        consoleController = null;
        field = null;
        consoleController = new ConsoleController(this);
    }

    private void showHighScores() {
        HighScoreHandler highScoreHandler = new HighScoreHandler("src/main/resources/Results.json");
        ArrayList<HighScore> scores = highScoreHandler.getScores();
        for (HighScore score : scores) {
            System.out.println(score.getName() + " " + score.getTime() + " " + score.getFieldSize() + " " + score.getNumberOfMines());
        }
        printRestartInfo();
    }

    public void printInputInfo() {
        System.out.println("Write coordinates splitted with space");
    }

    public void printInitializeParameters() {
        System.out.println("Write Field size and Number of mines separated with SPACE:");
    }

    public void printRestartInfo() {
        System.out.println("WRITE RESTART/EXIT/HIGHSCORE");
    }

    public void confirmWinner(String winnerName) {
        model.confirmWinner(winnerName);
    }

    public boolean isGameOver() {
        return model.isGameOver();
    }

    public boolean isGameWin() {
        return model.isGameWin();
    }
}