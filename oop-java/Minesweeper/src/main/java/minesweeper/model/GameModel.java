package minesweeper.model;

import minesweeper.cells.Cell;
import minesweeper.controller.ConsoleController;
import minesweeper.controller.GameController;
import minesweeper.controller.MainMenuController;
import minesweeper.exceptions.*;
import minesweeper.view.ConsoleView;
import minesweeper.view.GameView;
import minesweeper.view.MainMenuView;

import java.util.Random;

public final class GameModel {
    private final MainMenuView mainMenuView;
    private final ConsoleView consoleView;
    private GameView gameView;
    private MainMenuController mainMenuController;
    private GameController gameController;
    private ConsoleController consoleController;
    private int openedCells;
    private Cell[][] cells;
    private int fieldSize;
    private int numberOfMines;
    private int numberOfFlags;
    private boolean gameOver;
    private String winnerName;
    private Timer gameTimer;
    private final String gameMode;


    class Timer extends Thread {
        private Integer seconds = 0;

        @Override
        public void run() {
            synchronized (this) {
                try {
                    while (!gameOver) {
                        this.wait(1000);
                        seconds++;
                        if (gameMode.equals("GUI")) {
                            gameView.showTimer(seconds);
                        } else if (gameMode.equals("CONSOLE")) {
//                            consoleView.
                        }
                    }
                } catch (InterruptedException e) {
                    throw new TimerException("Timer Exception", e);
                }
            }
        }
    }


    public GameModel(MainMenuView mainMenuView) {
        this.mainMenuView = mainMenuView;
        gameMode = "GUI";
        this.consoleView = null;
    }

    public GameModel(ConsoleView consoleView) {
        this.consoleView = consoleView;
        gameMode = "CONSOLE";
        this.mainMenuView = null;
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

    public void createNewGameGUI() {
        try {
            fieldSize = Integer.parseInt(mainMenuView.getFieldSize().getText());
            numberOfMines = Integer.parseInt(mainMenuView.getNumberOfMines().getText());
        } catch (NumberFormatException e) {
            throw new BadGameSettingsParameter("Parameters must be integer ", e);
        }
        if (numberOfMines >= fieldSize * fieldSize) {
            throw new MinesOverflowException("To many mines provided: " + numberOfMines);
        } else if (numberOfMines < 0) {
            throw new NegativeMinesNumberException("Number of mines must be positive value");
        } else if (fieldSize < 0) {
            throw new NegativeFieldSizeException("Number of fields must be positive");
        }
        gameOver = false;
        openedCells = 0;
        numberOfFlags = numberOfMines;
        cells = new Cell[fieldSize][fieldSize];
        mainMenuView.dispose();
        gameView = new GameView(fieldSize, numberOfFlags);
        gameView.showGame();
        gameController = new GameController(this, gameView);
        gameTimer = new Timer();
        gameTimer.start();
    }

    public void createNewGameConsole(String parametersLine) {
        try {
            String[] parameters = splitLine(parametersLine);
            fieldSize = Integer.parseInt(parameters[0]);
            numberOfMines = Integer.parseInt(parameters[1]);
        } catch (NumberFormatException e) {
            throw new ReadingGameParametersException("Parameters must be integer ", e);
        } catch (NullPointerException e) {
            throw new ReadingGameParametersException("Must be two parameters Field size and Number off mines \n SPLITTED WITH SPACE");
        }
        if (numberOfMines >= fieldSize * fieldSize) {
            throw new MinesOverflowException("To many mines provided: " + numberOfMines);
        } else if (numberOfMines < 0) {
            throw new NegativeMinesNumberException("Number of mines must be positive value");
        } else if (fieldSize < 0) {
            throw new NegativeFieldSizeException("Number of fields must be positive");
        }
        gameOver = false;
        openedCells = 0;
        numberOfFlags = numberOfMines;
        cells = new Cell[fieldSize][fieldSize];
        consoleView.createField(fieldSize);
        consoleController.inputCells();
    }

    public void executeConsoleInput(String coordinateLine) {
        System.out.println(coordinateLine);
        try {

            String[] coordinates = splitLine(coordinateLine);
            int row = Integer.parseInt(coordinates[0]) - 1;
            int col = Integer.parseInt(coordinates[1]) - 1;
            if (coordinates.length == 2) {
                openCells(row, col);
            } else if (coordinates.length == 3 && coordinates[2].equals("F")) {
                putFlag(row, col);
            }
            consoleView.drawField(fieldSize);
        } catch (NumberFormatException e) {
            throw new CoordinateException("Coordinates must be integer", e);
        } catch (NullPointerException e) {
            throw new CoordinateException("Must be two coordinates row and column \n SPLITTED WITH SPACE");
        }
    }

    public void openCells(int row, int col) {
        if (openedCells == 0) {
            createField(row, col);
            revealCells(row, col);
            if (gameMode.equals("CONSOLE")) {
                consoleView.drawField(fieldSize);
            }
        } else if (cells[row][col].isBomb() && !gameOver) {
            if (gameMode.equals("GUI")) {
                gameView.drawCell(row, col, -1);
            } else if (gameMode.equals("CONSOLE")) {
                consoleView.drawCell(row, col, -1);
                consoleView.drawField(fieldSize);
            }
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
            if (gameMode.equals("GUI")) {
                gameView.drawCell(row, col, 0);
            } else if (gameMode.equals("CONSOLE")) {
                consoleView.drawCell(row, col, 0);
            }
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
            if (gameMode.equals("GUI")) {
                gameView.drawCell(row, col, countBombsNear(row, col));
            } else if (gameMode.equals("CONSOLE")) {
                consoleView.drawCell(row, col, countBombsNear(row, col));
            }
            cells[row][col].setOpened(true);
            openedCells++;
        }
    }

    public void revealAllBombs() {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (cells[i][j].isBomb()) {
                    if (gameMode.equals("GUI")) {
                        gameView.drawCell(i, j, -1);
                    } else if (gameMode.equals("CONSOLE")) {
                        consoleView.drawCell(i, j, -1);
                    }
                }
            }
        }
        if (gameMode.equals("CONSOLE")) {
            consoleView.drawField(fieldSize);
        }
    }

    public void gameOver() {
        if (gameMode.equals("GUI")) {
            gameView.createRestartDialog();
            gameView.setRestartDialogListener(gameController.new RestartDialogListener());
            gameView.showRestartDialog();
        } else if (gameMode.equals("CONSOLE")) {
            consoleView.printGameOver();
            consoleController.restartOrExit();
        }
    }

    public void gameWin() {
        if (gameMode.equals("GUI")) {
            gameOver = true;
            gameView.createWinningDialog();
            gameView.setWinningDialogListener(gameController.new WinningDialogListener());
            gameView.showWinningDialog();
        } else if (gameMode.equals("CONSOLE")) {
            consoleView.printWin();
            consoleController.writeWinnerName();
        }
    }

    public void restartOrExit(String parametersLine) {
        if (parametersLine.equals("EXIT")) {
            exitGame();
        } else if (parametersLine.equals("RESTART")) {
            restartGame();
        }
    }

    public void restartGame() {
        if (gameMode.equals("GUI")) {
            gameView.dispose();
            startGame();
        } else if (gameMode.equals("CONSOLE")) {
            consoleView.printInitializeParameters();
            consoleController.initializeGameParameters();
        }

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
                openedCells--;
                numberOfFlags++;
                if (gameMode.equals("GUI")) {
                    gameView.drawFlag(row, col, false, numberOfFlags);
                } else if (gameMode.equals("CONSOLE")) {
                    consoleView.drawFlag(row, col, false, numberOfFlags);
                }
            } else if (numberOfFlags > 0 && !cells[row][col].isOpened()) {
                cells[row][col].setFlag(true);
                cells[row][col].setOpened(true);
                openedCells++;
                numberOfFlags--;
                if (gameMode.equals("GUI")) {
                    gameView.drawFlag(row, col, true, numberOfFlags);
                } else if (gameMode.equals("CONSOLE")) {
                    consoleView.drawFlag(row, col, true, numberOfFlags);
                }
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

    public void confirmWinner() {
        winnerName = gameView.getWinnerName().getText();
        System.out.println(winnerName);
    }

    public int getFieldSize() {
        return fieldSize;
    }


    public void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }

    public void setConsoleController(ConsoleController consoleController) {
        this.consoleController = consoleController;
    }

    private String[] splitLine(String line) {
        line = line.trim();
        if (line.isEmpty() || line.startsWith("#")) {
            return null;
        }
        return line.split(" ");
    }
}
