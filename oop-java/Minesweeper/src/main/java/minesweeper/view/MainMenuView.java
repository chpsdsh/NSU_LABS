package minesweeper.view;

import minesweeper.controller.GameController;
import minesweeper.model.GameModel;
import minesweeper.view.menupanels.AboutPanel;
import minesweeper.view.menudialogs.GameSettings;
import minesweeper.view.menupanels.HighScorePanel;
import minesweeper.view.menupanels.MainMenuPanel;

import java.awt.event.ActionListener;
import javax.swing.*;

public final class MainMenuView extends JFrame {
    private GameModel model;
    private MainMenuPanel menuPanel;
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

    public MainMenuView(GameModel model) {
        super("Minesweeper");
        this.model = model;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuPanel = new MainMenuPanel();
        aboutPanel = new AboutPanel();
        highScorePanel = new HighScorePanel();
        settingsDialog = new GameSettings(this);
        this.setSize(400, 800);
        this.setLocationRelativeTo(null);
        this.showMenu();
    }

    public void showMenu() {
        getContentPane().removeAll();
        getContentPane().add(menuPanel);
        this.setVisible(true);
        revalidate();
        repaint();
    }

    public void showAbout() {
        getContentPane().removeAll();
        this.add(aboutPanel);
        revalidate();
        repaint();
    }

    public void showHighScore() {
        getContentPane().removeAll();
        this.add(highScorePanel);
        revalidate();
        repaint();
    }

    public void startGame(){
        model.createNewGame(settingsDialog.getFieldSize().getText(),settingsDialog.getNumberOfMines().getText());
        this.dispose();
        GameView gameView = new GameView(model);
        GameController gameController = new GameController(gameView);
    }

    public void showGameSettings() {
        settingsDialog.setVisible(true);
    }

    public void exitGame() {
        model.exitGame();
    }
}
