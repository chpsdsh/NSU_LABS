package minesweeper.gui.controller;

import minesweeper.gui.view.*;
import minesweeper.gui.view.frames.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameController {
    private final GameFrame gameFrame;
    private final GUIView guiView;

    public GameController(GameFrame gameFrame, GUIView guiView) {
        this.gameFrame = gameFrame;
        this.guiView = guiView;
        for (int i = 0; i < gameFrame.getModel().getFieldSize(); i++) {
            for (int j = 0; j < gameFrame.getModel().getFieldSize(); j++) {
                gameFrame.setNewGameMouseListener(gameMouseAdapter(i, j), i, j);
            }
        }
        gameFrame.setRestartDialogListener(new RestartDialogListener());
        gameFrame.setWinningDialogListener(new WinningDialogListener());
    }

    public MouseAdapter gameMouseAdapter(int row, int col) {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (MouseEvent.BUTTON1 == e.getButton()) {
                    gameFrame.openCells(row, col);

                } else if (MouseEvent.BUTTON3 == e.getButton()) {
                    gameFrame.putFlag(row, col);
                }
            }
        };
    }

    public class RestartDialogListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Exit":
                    gameFrame.exitGame();
                    break;
                case "Restart":
                    guiView.restartGame();
                    break;
            }
        }
    }

    public class WinningDialogListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gameFrame.confirmWinner();
            guiView.initializeGuiView();
        }
    }
}


