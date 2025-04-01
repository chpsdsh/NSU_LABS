package minesweeper.controller;

import minesweeper.view.GameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameController {
    private final GameView gameView;

    public GameController(GameView gameView) {
        this.gameView = gameView;
        for (int i = 0; i < gameView.getModel().getFieldSize(); i++) {
            for (int j = 0; j < gameView.getModel().getFieldSize(); j++) {
                gameView.setNewGameMouseListener(gameMouseAdapter(i, j), i, j);
            }
        }
    }

    public MouseAdapter gameMouseAdapter(int row, int col) {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (MouseEvent.BUTTON1 == e.getButton()) {
                    gameView.openCells(row, col);

                } else if (MouseEvent.BUTTON3 == e.getButton()) {
                    System.out.println("RKM");
                    gameView.putFlag(row, col);

                }
            }
        };
    }

    public class RestartDialogListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Exit":
                    gameView.exitGame();
                    break;
                case "Restart":
//                    gameView.restartGame();
                    break;
            }
        }
    }

//    public class WinningDialogListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            gameView.confirmWinner(null);
//        }
//    }
}


