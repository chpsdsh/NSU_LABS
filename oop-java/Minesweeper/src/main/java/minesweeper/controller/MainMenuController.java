package minesweeper.controller;

import minesweeper.view.MainMenuView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class MainMenuController {
    private final MainMenuView mainMenuView;


    public MainMenuController(MainMenuView mainMenuView) {
        this.mainMenuView = mainMenuView;
        mainMenuView.setMainMenuActionListener(new MainMenuListener());
        mainMenuView.setExitToMenuActionListener(new ExitToMenuListener());
        mainMenuView.setStartGameActionListener(new StartGameListener());
    }


    class MainMenuListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            switch (e.getActionCommand()){
                case "New game":
                    mainMenuView.showGameSettings();
                    break;
                case "High score":
                    mainMenuView.showHighScore();
                    break;
                case "About":
                    mainMenuView.showAbout();
                    break;
                case "Exit":
                    mainMenuView.exitGame();
            }
        }
    }

    class ExitToMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mainMenuView.showMenu();
        }
    }

    public class StartGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mainMenuView.startGame();
        }
    }
}
