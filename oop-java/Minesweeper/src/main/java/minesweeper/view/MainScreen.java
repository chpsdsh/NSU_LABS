package minesweeper.view;

import minesweeper.controller.Controller;

import java.awt.*;
import javax.swing.*;

public class MainScreen extends JFrame {

    private Controller controller;


    public MainScreen() {
        super("Minesweeper");
        createGUI();
    }

    private void createGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(300, 50, 50, 50));

        JLabel name = new JLabel("MINESWEEPER", JLabel.CENTER);
        name.setFont(new Font("Arial", Font.BOLD, 28));
        name.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton newGameButton = new JButton("New game");
        JButton highScoreButton = new JButton("High score");
        JButton aboutButton = new JButton("About");
        JButton exitButton = new JButton("Exit");

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

        this.add(menuPanel);
        this.setSize(400, 800);
        setLocationRelativeTo(null);
    }

}
