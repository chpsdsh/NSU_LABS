package minesweeper.main;

import minesweeper.controller.ConsoleController;
import minesweeper.model.GameModel;
import minesweeper.guiview.ConsoleView;
import minesweeper.guiview.GUIView;


public class Main {
    public static void main(String[] args) {
        if (args[0].equals("gui")) {
            GameModel model = new GameModel();
            new GUIView(model);
        } else if (args[0].equals("console")) {
            GameModel model = new GameModel();
            ConsoleView consoleView = new ConsoleView(model);
            new ConsoleController(consoleView);
        }

    }
}