package minesweeper.main;

import minesweeper.controller.ConsoleController;
import minesweeper.controller.MainMenuController;
import minesweeper.model.GameModel;
import minesweeper.view.ConsoleView;
import minesweeper.view.MainMenuView;


public class Main {
    public static void main(String[] args) {
        if (args[0].equals("gui")) {
            MainMenuView mainMenuView = new MainMenuView();
            GameModel model = new GameModel(mainMenuView);
            MainMenuController controller = new MainMenuController(mainMenuView, model);
        } else if (args[0].equals("console")) {
            ConsoleView consoleView = new ConsoleView();
            GameModel model = new GameModel(consoleView);
            ConsoleController controller = new ConsoleController(model, consoleView);
        }

    }
}