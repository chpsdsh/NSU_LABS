package minesweeper.view;

import minesweeper.controller.GameController;
import minesweeper.model.GameModel;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public final class MainMenuView extends JFrame {
    private GameModel model;
    private JButton newGameButton;
    private JButton highScoreButton;
    private JButton aboutButton;
    private JButton exitButton;
    private JButton aboutExitButton;
    private JButton highScoreExitButton;
    private JButton startGameButton;
    private JPanel menuPanel;
    private JPanel aboutPanel;
    private JPanel highScorePanel;
    private JDialog settingsDialog;
    private JTextField fieldSize;
    private JTextField numberOfMines;

    public JTextField getFieldSize() {
        return fieldSize;
    }

    public JTextField getNumberOfMines() {
        return numberOfMines;
    }

    public MainMenuView(GameModel model) {
        super("Minesweeper");
        this.model = model;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createMenu();
        createAbout();
        createHighScore();
        this.setSize(400, 800);
        this.setLocationRelativeTo(null);
        this.showMenu();
    }

    private void createMenu() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(300, 50, 50, 50));

        JLabel name = new JLabel("MINESWEEPER", JLabel.CENTER);
        name.setFont(new Font("Arial", Font.BOLD, 28));
        name.setAlignmentX(Component.CENTER_ALIGNMENT);

        newGameButton = new JButton("New game");
        newGameButton.setActionCommand("New game");
        highScoreButton = new JButton("High score");
        highScoreButton.setActionCommand("High score");
        aboutButton = new JButton("About");
        aboutButton.setActionCommand("About");
        exitButton = new JButton("Exit");
        exitButton.setActionCommand("Exit");

        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        highScoreButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        aboutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        menuPanel.add(name);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(newGameButton);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(highScoreButton);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(aboutButton);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(exitButton);

    }

    private void createHighScore() {
        highScorePanel = new JPanel();
        highScoreExitButton = new JButton("Exit");

        highScoreExitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        highScorePanel.setLayout(new BoxLayout(highScorePanel, BoxLayout.Y_AXIS));
        highScorePanel.setBorder(BorderFactory.createEmptyBorder(200, 50, 50, 50));

        highScorePanel.add(Box.createVerticalStrut(10));
        highScorePanel.add(highScoreExitButton);

    }

    private void createAbout() {
        aboutPanel = new JPanel();
        aboutExitButton = new JButton("Exit");
        aboutExitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        aboutPanel.setLayout(new BoxLayout(aboutPanel, BoxLayout.Y_AXIS));
        aboutPanel.setBorder(BorderFactory.createEmptyBorder(200, 50, 50, 50));

        JLabel name = new JLabel("MINESWEEPER RULES", JLabel.CENTER);
        name.setFont(new Font("Arial", Font.BOLD, 22));
        name.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel text = new JLabel("<html>The grid contains hidden mines. Your task is to avoid them.<br>" +
                "Left-click on a tile to reveal it. If it's a mine, the game is over.<br>" +
                "If the tile is safe, it will show a number indicating how many mines are adjacent to it.<br>" +
                "Use these numbers to deduce where the mines are hidden.<br>" +
                "Right-click on a tile to place a flag if you suspect it contains a mine.<br>" +
                "The game is won when all safe tiles are revealed and all mines are correctly flagged.</html>");
        text.setFont(new Font("Arial", Font.BOLD, 14));
        text.setAlignmentX(Component.CENTER_ALIGNMENT);


        aboutPanel.add(name);
        aboutPanel.add(text);
        aboutPanel.add(Box.createVerticalStrut(10));
        aboutPanel.add(aboutExitButton);
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
    public void startGame(ActionListener listener){
        createGameSettings();
        startGameButton.addActionListener(listener);
        showGameSettings();
    }

    public void createNewGame(){
        model.createNewGame(fieldSize.getText(),numberOfMines.getText());
        this.dispose();
        GameView gameView = new GameView(model);
        GameController gameController = new GameController(gameView);
    }

    public void createGameSettings() {
        settingsDialog = new JDialog(this, "Game Settings", true);
        settingsDialog.setLocationRelativeTo(null);

        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));

        JLabel fieldSizeText = new JLabel("Size", JLabel.CENTER);
        fieldSizeText.setFont(new Font("Arial", Font.BOLD, 16));
        fieldSizeText.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel numberOfMinesText = new JLabel("Number of mines", JLabel.CENTER);
        numberOfMinesText.setFont(new Font("Arial", Font.BOLD, 16));
        numberOfMinesText.setAlignmentX(Component.CENTER_ALIGNMENT);

        numberOfMines = new JTextField();
        numberOfMines.setFont(new Font("Arial", Font.PLAIN, 20));
        numberOfMines.setAlignmentX(Component.CENTER_ALIGNMENT);
        numberOfMines.setHorizontalAlignment(SwingConstants.CENTER);
        numberOfMines.setMaximumSize(new Dimension(40, 25));
        fieldSize = new JTextField();
        fieldSize.setFont(new Font("Arial", Font.PLAIN, 20));
        fieldSize.setAlignmentX(Component.CENTER_ALIGNMENT);
        fieldSize.setHorizontalAlignment(SwingConstants.CENTER);
        fieldSize.setMaximumSize(new Dimension(40, 25));

        startGameButton = new JButton("Start game");
        startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        dialogPanel.add(fieldSizeText);
        dialogPanel.add(Box.createVerticalStrut(10));
        dialogPanel.add(fieldSize);
        dialogPanel.add(Box.createVerticalStrut(10));
        dialogPanel.add(numberOfMinesText);
        dialogPanel.add(Box.createVerticalStrut(10));
        dialogPanel.add(numberOfMines);
        dialogPanel.add(Box.createVerticalStrut(10));
        dialogPanel.add(startGameButton);

        dialogPanel.setPreferredSize(new Dimension(25, 20));

        settingsDialog.add(dialogPanel);

        settingsDialog.setSize(300, 300);
    }

    public void showGameSettings() {
        settingsDialog.setVisible(true);
    }

    public void exitGame() {
        model.exitGame();
    }

    public void setMainMenuActionListener(ActionListener listener) {
        newGameButton.addActionListener(listener);
        highScoreButton.addActionListener(listener);
        aboutButton.addActionListener(listener);
        exitButton.addActionListener(listener);
    }

    public void setExitToMenuActionListener(ActionListener listener) {
        aboutExitButton.addActionListener(listener);
        highScoreExitButton.addActionListener(listener);
    }

    public void setStartGameActionListener(ActionListener listener) {
        startGameButton.addActionListener(listener);
    }
}
