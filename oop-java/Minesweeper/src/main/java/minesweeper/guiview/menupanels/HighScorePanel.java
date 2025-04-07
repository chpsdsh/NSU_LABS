package minesweeper.guiview.menupanels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HighScorePanel extends JPanel {
    private JButton highScoreExitButton;
    public HighScorePanel(){
        super();
        createHighScore();
    }

    private void createHighScore() {
        highScoreExitButton = new JButton("Exit");
        highScoreExitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(200, 50, 50, 50));

        add(Box.createVerticalStrut(10));
        add(highScoreExitButton);
    }

    public void setExitActionListener(ActionListener listener) {
        highScoreExitButton.addActionListener(listener);
    }
}
