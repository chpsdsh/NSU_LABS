package minesweeper.guiview.frames;


import minesweeper.model.GameModel;
import minesweeper.guiview.gamedialogs.RestartDialog;
import minesweeper.guiview.gamedialogs.WinningDialog;
import minesweeper.guiview.gamepanels.GamePanel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class GameFrame extends JFrame {
    private final GameModel model;
    private final GamePanel gamePanel;
    private final RestartDialog restartDialog;
    private final WinningDialog winningDialog;

    public GameFrame(GameModel model) {
        super("MINESWEEPER");
        this.model = model;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(50 * model.getFieldSize() + 100, 50 * model.getFieldSize() + 100);
        this.setLocationRelativeTo(null);
        gamePanel = new GamePanel(model.getFieldSize(),model.getNumberOfFlags());
        restartDialog = new RestartDialog(this);
        winningDialog = new WinningDialog(this);
        gamePanel.setVisible(true);
        showGame();
        this.setVisible(true);
    }


    public void openCells(int row, int col) {
        model.openCells(row, col);
        if(model.isGameWin()){
            drawField();
            gameWin();
        }
        else if(model.isGameOver()){
            drawBombs();
            gameOver();
        }
        else {
            drawField();
        }
    }


    public void drawField() {
        for (int i = 0; i < model.getFieldSize(); i++) {
            for (int j = 0; j < model.getFieldSize(); j++) {
                if(model.isOpened(i,j) && !model.isFlag(i,j)) {
                    gamePanel.drawCell(i, j, model.countBombsNear(i, j));
                }
            }
        }
    }

    public void putFlag(int row, int col){
        model.putFlag(row,col);
        gamePanel.drawFlag(row,col,model.isFlag(row,col), model.isOpened(row, col),model.getNumberOfFlags());
        if(model.isGameWin()){
            gameWin();
        }
    }

    public void drawBombs() {
        for (int i = 0; i < model.getFieldSize(); i++) {
            for (int j = 0; j < model.getFieldSize(); j++) {
                if(model.isBomb(i,j)) {
                    gamePanel.drawBomb(i,j);
                }
            }
        }
        revalidate();
        repaint();
    }

    public void showGame() {
        getContentPane().removeAll();
        getContentPane().add(gamePanel);
        revalidate();
        repaint();
    }

    public void gameOver() {
        restartDialog.setVisible(true);
        revalidate();
        repaint();
    }

    public void exitGame() {
        model.exitGame();
    }

    public void gameWin() {
        winningDialog.setVisible(true);
        revalidate();
        repaint();
    }

    public void confirmWinner(){
        String winnerName = winningDialog.getWinnerName().getText();
        model.confirmWinner(winnerName);
    }

    public GameModel getModel() {
        return model;
    }

    public void setNewGameMouseListener(MouseAdapter listener, int row, int col) {
        gamePanel.setNewGameMouseListener(listener,row,col);
    }

    public void setRestartDialogListener(ActionListener listener){
        restartDialog.setRestartDialogListener(listener);
    }
    public void setWinningDialogListener(ActionListener listener){
        winningDialog.setWinningDialogListener(listener);
    }
}
