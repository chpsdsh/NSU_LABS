package minesweeper.main;

import minesweeper.controller.ConsoleController;
import minesweeper.controller.MainMenuController;
import minesweeper.model.GameModel;
import minesweeper.view.ConsoleView;
import minesweeper.view.MainMenuView;


public class Main {
    public static void main(String[] args) {
        if (args[0].equals("gui")) {
            GameModel model = new GameModel();
            MainMenuView mainMenuView = new MainMenuView(model);
            MainMenuController controller = new MainMenuController(mainMenuView);
        } else if (args[0].equals("console")) {
            GameModel model = new GameModel();
            ConsoleView consoleView = new ConsoleView(model);
            ConsoleController controller = new ConsoleController(consoleView);
        }

    }
}