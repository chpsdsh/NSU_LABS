package minesweeper.controller;

import minesweeper.model.GameModel;
import minesweeper.view.GameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameController {

    private final GameView gameView;
    private final GameModel model;

    public GameController(GameModel model, GameView gameView) {
        this.model = model;
        this.gameView = gameView;
        for (int i = 0; i < model.getFieldSize(); i++) {
            for (int j = 0; j < model.getFieldSize(); j++) {
                gameView.setNewGameMouseListener(gameMouseAdapter(i, j), i, j);
            }
        }
    }

    public MouseAdapter gameMouseAdapter(int row, int col) {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (MouseEvent.BUTTON1 == e.getButton()) {
                    System.out.println("LKM");
                    model.openCells(row, col);

                } else if (MouseEvent.BUTTON3 == e.getButton()) {
                    System.out.println("RKM");
                    model.putFlag(row, col);

                }
            }
        };
    }

    public class RestartDialogListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Exit":
                    System.out.println("IDI NAXUY");
                    System.exit(0);
                    break;
                case "Restart":
                    model.restartGame();
                    break;

            }
        }
    }

    public class WinningDialogListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.confirmWinner();
        }
    }
}


