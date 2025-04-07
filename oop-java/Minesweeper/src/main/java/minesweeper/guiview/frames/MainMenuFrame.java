package minesweeper.guiview.frames;

import minesweeper.model.GameModel;
import minesweeper.guiview.menupanels.AboutPanel;
import minesweeper.guiview.menudialogs.GameSettings;
import minesweeper.guiview.menupanels.HighScorePanel;
import minesweeper.guiview.menupanels.MainMenuPanel;

import java.awt.event.ActionListener;
import javax.swing.*;

public final class MainMenuFrame extends JFrame {
    private final GameModel model;
    private final MainMenuPanel menuPanel;
    private final AboutPanel aboutPanel;
    private final HighScorePanel highScorePanel;
    private final GameSettings settingsDialog;

    public void setMainMenuActionListener(ActionListener listener) {
        menuPanel.setMainMenuActionListener(listener);
    }

    public void setExitToMenuActionListener(ActionListener listener) {
        aboutPanel.setExitActionListener(listener);
        highScorePanel.setExitActionListener(listener);
    }

    public void setStartGameActionListener(ActionListener listener) {
        settingsDialog.setStartGameActionListener(listener);
    }

    public MainMenuFrame(GameModel model) {
        super("Minesweeper");
        this.model = model;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuPanel = new MainMenuPanel();
        aboutPanel = new AboutPanel();
        highScorePanel = new HighScorePanel();
        settingsDialog = new GameSettings(this);
        setSize(400, 800);
        setLocationRelativeTo(null);

    }

    public void showMenu() {
        getContentPane().removeAll();
        getContentPane().add(menuPanel);
        setVisible(true);
        revalidate();
        repaint();
    }

    public void showAbout() {
        getContentPane().removeAll();
        add(aboutPanel);
        revalidate();
        repaint();
    }

    public void showHighScore() {
        getContentPane().removeAll();
        add(highScorePanel);
        revalidate();
        repaint();
    }

    public void startGame(){
        model.createNewGame(settingsDialog.getFieldSize().getText(),settingsDialog.getNumberOfMines().getText());
        dispose();
    }

    public void showGameSettings() {
        settingsDialog.clear();
        settingsDialog.setVisible(true);
    }

    public void exitGame() {
        model.exitGame();
    }
}
