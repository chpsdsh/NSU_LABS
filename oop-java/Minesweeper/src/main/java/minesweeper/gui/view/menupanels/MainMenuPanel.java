package minesweeper.gui.view.menupanels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {
    private JButton newGameButton;
    private JButton highScoreButton;
    private JButton aboutButton;
    private JButton exitButton;

    public MainMenuPanel(){
        super();
        createMenu();
    }

    private void createMenu() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
       setBorder(BorderFactory.createEmptyBorder(300, 50, 50, 50));

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

        add(name);
        add(Box.createVerticalStrut(10));
        add(newGameButton);
        add(Box.createVerticalStrut(10));
        add(highScoreButton);
        add(Box.createVerticalStrut(10));
        add(aboutButton);
        add(Box.createVerticalStrut(10));
        add(exitButton);
    }

    public void setMainMenuActionListener(ActionListener listener) {
        newGameButton.addActionListener(listener);
        highScoreButton.addActionListener(listener);
        aboutButton.addActionListener(listener);
        exitButton.addActionListener(listener);
    }
}
