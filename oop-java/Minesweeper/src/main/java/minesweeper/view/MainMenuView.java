package minesweeper.view;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public final class MainMenuView extends JFrame {

    private JButton newGameButton;
    private JButton highScoreButton;
    private JButton aboutButton;
    private JButton exitButton;
    private JButton aboutExitButton;
    private JButton startGameButton;
    private JPanel menuPanel;
    private JPanel aboutPanel;
    private JDialog settingsDialog;
    private JTextField fieldSize;
    private JTextField numberOfMines;

    public JTextField getFieldSize(){
        return fieldSize;
    }
    public JTextField getNumberOfMines(){
        return numberOfMines;
    }
    public MainMenuView() {
        super("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createMenu();
        createAbout();
        this.setSize(400, 800);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
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
        highScoreButton = new JButton("High score");
        aboutButton = new JButton("About");
        exitButton = new JButton("Exit");

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

    public void showAbout() {
        getContentPane().removeAll();
        this.add(aboutPanel);
        revalidate();
        repaint();
    }

    public void showMenu() {
        getContentPane().removeAll();
        getContentPane().add(menuPanel);
        revalidate();
        repaint();
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
        numberOfMines.setMaximumSize(new Dimension(40, 25));
        fieldSize = new JTextField();
        fieldSize.setFont(new Font("Arial", Font.PLAIN, 20));
        fieldSize.setAlignmentX(Component.CENTER_ALIGNMENT);
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

    public void setNewGameActionListener(ActionListener listener) {
        newGameButton.addActionListener(listener);
    }

    public void setHighScoreActionListener(ActionListener listener) {
        highScoreButton.addActionListener(listener);
    }

    public void setAboutActionListener(ActionListener listener) {
        aboutButton.addActionListener(listener);
    }

    public void setExitActionListener(ActionListener listener) {
        exitButton.addActionListener(listener);
    }

    public void setAboutExitActionListener(ActionListener listener) {
        aboutExitButton.addActionListener(listener);
    }

    public void setStartGameActionListener(ActionListener listener) {
        startGameButton.addActionListener(listener);
    }
}
