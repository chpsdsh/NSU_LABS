package minesweeper.controller;

import minesweeper.model.GameModel;
import minesweeper.view.MainMenuView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class MainMenuController {
    private final MainMenuView mainMenuView;
    private final GameModel model;

    public MainMenuController(MainMenuView mainMenuView, GameModel model) {
        this.mainMenuView = mainMenuView;
        this.model = model;

        mainMenuView.setNewGameActionListener(new NewGameListener());
        mainMenuView.setHighScoreActionListener(new HighScoreListener());
        mainMenuView.setAboutActionListener(new AboutListener());
        mainMenuView.setExitActionListener(new ExitListener());
        mainMenuView.setAboutExitActionListener(new AboutExitListener());
        model.setMainMenuController(this);
    }


    class NewGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.startGame();
        }
    }

    class HighScoreListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.showHighScore();
        }
    }

    class AboutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.showAbout();
        }
    }

    class ExitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.exitGame();
        }
    }

    class AboutExitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.showMenu();
        }
    }

    public class StartGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.createNewGame();
        }
    }
}
