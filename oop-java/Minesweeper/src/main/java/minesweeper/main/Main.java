package minesweeper.main;

import minesweeper.gui.view.*;
import minesweeper.model.GameModel;

public class Main {
    public static void main(String[] args) {
        if (args[0].equals("gui")) {
            GameModel model = new GameModel();
            new GUIView(model);
        } else if (args[0].equals("console")) {
//            GameModel model = new GameModel();
//            ConsoleView consoleView = new ConsoleView(model);
//            new ConsoleController(consoleView);
        }

    }
}