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

        mainMenuView.setMainMenuActionListener(new MainMenuListener());
        mainMenuView.setAboutExitActionListener(new AboutExitListener());
        model.setMainMenuController(this);
    }


    class MainMenuListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            switch (e.getActionCommand()){
                case "New game":
                    model.startGame();
                    break;
                case "High score":
                    model.showHighScore();
                    break;
                case "About":
                    model.showAbout();
                    break;
                case "Exit":
                    model.exitGame();
            }
        }
    }

//    class NewGameListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            model.startGame();
//        }
//    }
//
//    class HighScoreListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            model.showHighScore();
//        }
//    }
//
//    class AboutListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            model.showAbout();
//        }
//    }
//
//    class ExitListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            model.exitGame();
//        }
//    }

    class AboutExitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.showMenu();
        }
    }

    public class StartGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.createNewGameGUI();
        }
    }
}
