package minesweeper.gui.view.menupanels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AboutPanel extends JPanel {
    private JButton aboutExitButton;

    public void setExitActionListener(ActionListener listener) {
        aboutExitButton.addActionListener(listener);
    }

    public AboutPanel() {
        super();
        createAbout();
    }

    private void createAbout() {
        aboutExitButton = new JButton("Exit");
        aboutExitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(200, 50, 50, 50));

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

        add(name);
        add(text);
        add(Box.createVerticalStrut(10));
        add(aboutExitButton);
    }
}
