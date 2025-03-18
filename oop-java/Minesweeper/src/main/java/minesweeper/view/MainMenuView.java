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
    private JPanel menuPanel;
    private JPanel aboutPanel;

    public MainMenuView() {
        super("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createMenu();
        createAbout();
        this.setSize(400, 800);
        setLocationRelativeTo(null);
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

}
