package minesweeper.model;

import minesweeper.cells.Cell;
import minesweeper.controller.GameController;
import minesweeper.controller.MainMenuController;
import minesweeper.exceptions.BadGameSettingsParameter;
import minesweeper.view.GameView;
import minesweeper.view.MainMenuView;

import java.util.Random;

public final class GameModel {
    private final MainMenuView mainMenuView;
    private GameView gameView;
    private MainMenuController mainMenuController;
    private GameController gameController;
    private int openedCells;
    private Cell[][] cells;
    private int fieldSize;
    private int numberOfMines;
    private int numberOfFlags;
    private int puttedFlags;


    public GameModel(MainMenuView mainMenuView) {
        this.mainMenuView = mainMenuView;

    }


    public void startGame() {
        mainMenuView.createGameSettings();
        mainMenuView.setStartGameActionListener(mainMenuController.new StartGameListener());
        mainMenuView.showGameSettings();

    }

    public void showHighScore() {
        System.out.println("HIGH SCORE\n");
    }

    public void showAbout() {
        mainMenuView.showAbout();
    }

    public void showMenu() {
        mainMenuView.showMenu();
    }

    public void exitGame() {
        System.exit(0);
    }

    public void createNewGame() {
        try {
            fieldSize = Integer.parseInt(mainMenuView.getFieldSize().getText());
            numberOfMines = Integer.parseInt(mainMenuView.getNumberOfMines().getText());
        } catch (NumberFormatException e) {
            throw new BadGameSettingsParameter("Parameters must be integer ", e);
        }
        openedCells = 0;
        puttedFlags = 0;
        numberOfFlags = numberOfMines;
        cells = new Cell[fieldSize][fieldSize];
        mainMenuView.dispose();
        gameView = new GameView(fieldSize);
        gameView.showGame();
        gameController = new GameController(this, gameView);
        System.out.println("new game created\n");

    }


    public void openCells(int row, int col) {
        if (openedCells == 0) {
            createField(row, col);
        }

    }

    public void putFlag(int row, int col) {
        if (openedCells == 0) {
            createField(row, col);
        }
        if (cells[row][col].isFlag()) {
            cells[row][col].setFlag(false);
            gameView.drawFlag(row, col, false);
            openedCells--;
        } else if(numberOfFlags < numberOfMines){
            cells[row][col].setFlag(true);
            gameView.drawFlag(row, col, true);
            gameView.drawFlag(row, col, false);
            openedCells++;
            numberOfFlags++;
        }
    }

    public void createField(int row, int col) {
        Random random = new Random();
        int minesToPlant = numberOfMines;
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (i == row && j == col || minesToPlant == 0) {
                    cells[i][j] = new Cell(false, false, false);
                } else {
                    boolean isBomb = random.nextBoolean();
                    cells[i][j] = new Cell(isBomb, false, false);
                    if (isBomb) {
                        minesToPlant--;
                    }
                }
            }
        }
        if (minesToPlant > 0) {
            createField(row, col);
        }
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    public void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }
}
