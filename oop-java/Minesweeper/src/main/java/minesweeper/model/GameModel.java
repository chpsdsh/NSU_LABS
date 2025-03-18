package minesweeper.model;

import minesweeper.cells.Cell;
import minesweeper.controller.MainMenuController;
import minesweeper.view.GameView;
import minesweeper.view.MainMenuView;

public final class GameModel {
    private final MainMenuView mainMenuView;
    private MainMenuController mainMenuController;
    private Cell[][] cells;
    private int fieldSize;
    private int minesCount;

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
        System.out.println("new game created\n");
    }

    public void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }
}
