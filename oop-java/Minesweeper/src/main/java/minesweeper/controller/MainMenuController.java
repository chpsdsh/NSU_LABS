package minesweeper.controller;

import minesweeper.guiview.GUIView;
import minesweeper.guiview.frames.MainMenuFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class MainMenuController {
    private final MainMenuFrame mainMenuFrame;
    private final GUIView guiView;


    public MainMenuController(MainMenuFrame mainMenuFrame, GUIView guiView) {
        this.mainMenuFrame = mainMenuFrame;
        this.guiView = guiView;
        mainMenuFrame.setMainMenuActionListener(new MainMenuListener());
        mainMenuFrame.setExitToMenuActionListener(new ExitToMenuListener());
        mainMenuFrame.setStartGameActionListener(new StartGameListener());
    }


    class MainMenuListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            switch (e.getActionCommand()){
                case "New game":
                    mainMenuFrame.showGameSettings();
                    break;
                case "High score":
                    mainMenuFrame.showHighScore();
                    break;
                case "About":
                    mainMenuFrame.showAbout();
                    break;
                case "Exit":
                    mainMenuFrame.exitGame();
            }
        }
    }

    class ExitToMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mainMenuFrame.showMenu();
        }
    }

    public class StartGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
           guiView.startGame();
        }
    }
}
