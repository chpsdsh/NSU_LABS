package minesweeper.model;

import minesweeper.cells.Cell;
import minesweeper.controller.MainMenuController;
import minesweeper.exceptions.BadGameSettingsParameter;
import minesweeper.view.GameView;
import minesweeper.view.MainMenuView;

public final class GameModel {
    private final MainMenuView mainMenuView;
    private GameView gameView;
    private MainMenuController mainMenuController;

    private Cell[][] cells;
    private int fieldSize;
    private int numberOfMines;


    public GameModel(MainMenuView mainMenuView) {
        this.mainMenuView = mainMenuView;
    }

    public

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
        }
        catch (NumberFormatException e){
            throw new BadGameSettingsParameter("Parameters must be integer ", e);
        }

        mainMenuView.dispose();
        gameView = new GameView(this);
        gameView.showGame();

        System.out.println("new game created\n");

    }


    public Cell getCell(int row, int column){
        return cells[row][column];
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
