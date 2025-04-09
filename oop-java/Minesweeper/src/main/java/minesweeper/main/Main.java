package minesweeper.main;

import minesweeper.console.view.ConsoleView;
import minesweeper.gui.view.*;
import minesweeper.model.GameModel;

public class Main {
    public static void main(String[] args) {
        if (args[0].equals("gui")) {
            GameModel model = new GameModel();
            new GUIView(model);
        } else if (args[0].equals("console")) {
            GameModel model = new GameModel();
            new ConsoleView(model);
        }
    }
}