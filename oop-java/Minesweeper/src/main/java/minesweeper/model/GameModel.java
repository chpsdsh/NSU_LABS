package minesweeper.model;

import minesweeper.cells.Cell;
import minesweeper.controller.GameController;
import minesweeper.controller.MainMenuController;
import minesweeper.exceptions.BadGameSettingsParameter;
import minesweeper.exceptions.MinesOverflowException;
import minesweeper.exceptions.NegativeFieldSizeException;
import minesweeper.exceptions.NegativeMinesNumberException;
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
    private boolean gameOver;
    private String winnerName;


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
        if (numberOfMines >= fieldSize * fieldSize) {
            throw new MinesOverflowException("To many mines provided: " + numberOfMines);
        }
        if (numberOfMines < 0) {
            throw new NegativeMinesNumberException("Number of mines must be positive value");
        }
        if (fieldSize < 0) {
            throw new NegativeFieldSizeException("Number of fields must be positive");
        }
        gameOver = false;
        openedCells = 0;
        numberOfFlags = 0;
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
            revealCells(row, col);

        } else if (cells[row][col].isBomb() && !gameOver) {
            gameView.drawCell(row, col, -1);
            revealAllBombs();
            gameOver = true;
            gameOver();
        } else if (!gameOver) {
            revealCells(row, col);
        }

        if (openedCells == fieldSize * fieldSize) {
            gameWin();
        }
    }

    public void revealCells(int row, int col) {
        if (row < 0 || col < 0 || row >= fieldSize || col >= fieldSize) {
            return;
        }
        if (countBombsNear(row, col) == 0 && !cells[row][col].isOpened()) {
            gameView.drawCell(row, col, 0);
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
        } else if(!cells[row][col].isOpened()){
            gameView.drawCell(row, col, countBombsNear(row, col));
            cells[row][col].setOpened(true);
            openedCells++;
        }
    }

    public void revealAllBombs() {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (cells[i][j].isBomb()) {
                    gameView.drawCell(i, j, -1);
                }
            }
        }
    }

    public void gameOver() {
        gameView.createRestartDialog();
        gameView.setRestartDialogListener(gameController.new RestartDialogListener());
        gameView.showRestartDialog();
    }

    public void gameWin(){
        gameView.createWinningDialog();
        gameView.setWinningDialogListener(gameController.new WinningDialogListener());
        gameView.showWinningDialog();
    }

    public void restartGame(){
        gameView.dispose();
        startGame();
    }

    public int countBombsNear(int row, int col) {
        int bombCount = 0;

        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
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
                gameView.drawFlag(row, col, false);
                openedCells--;
                numberOfFlags--;
            } else if (numberOfFlags < numberOfMines && !cells[row][col].isOpened()) {
                cells[row][col].setFlag(true);
                cells[row][col].setOpened(true);
                gameView.drawFlag(row, col, true);
                openedCells++;
                numberOfFlags++;
            }
        }
        if (openedCells == fieldSize * fieldSize) {
            gameWin();
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
            if (x == row && y == col && cells[x][y].isBomb()) {
                continue;
            } else {
                cells[x][y].setBomb(true);
                minesPlanted++;
            }
        }
    }

    public void confirmWinner(){
        winnerName = gameView.getWinnerName().getText();
        System.out.println(winnerName);
    }

    public int getFieldSize() {
        return fieldSize;
    }


    public void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }
}
