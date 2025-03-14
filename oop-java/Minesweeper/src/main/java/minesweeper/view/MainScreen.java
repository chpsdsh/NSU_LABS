package minesweeper.view;

import java.awt.*;
import javax.swing.*;

public class MainScreen extends JFrame {

    public MainScreen() {
        super("Minesweeper");
        createGUI();
    }

    private void createGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel name = new JLabel("MINESWEEPER", JLabel.CENTER);
        name.setSize(300, 100);
        name.setLocation(50, 200);
        name.setFont(new Font("Arial", Font.BOLD, 28));
        add(name);

        JButton newGameButton = new JButton("New game");
        newGameButton.setSize(100, 50);
        newGameButton.setLocation(150, 300);
        add(newGameButton);

        JButton highScoreButton = new JButton("High score");
        highScoreButton.setSize(100, 50);
        highScoreButton.setLocation(150, 360);
        add(highScoreButton);

        JButton aboutButton = new JButton("About");
        aboutButton.setSize(100, 50);
        aboutButton.setLocation(150, 420);
        add(aboutButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setSize(100, 50);
        exitButton.setLocation(150, 480);
        add(exitButton);

        setSize(400, 800);
        setLocationRelativeTo(null);
    }

}
