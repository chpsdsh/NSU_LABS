package minesweeper.gui.view.gamedialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RestartDialog extends JDialog {
    private JButton restartButton;
    private JButton exitButton;

    public void setRestartDialogListener(ActionListener listener) {
        restartButton.addActionListener(listener);
        exitButton.addActionListener(listener);
    }

    public RestartDialog(JFrame frame){
        super(frame, "GAME OVER", true);
        createRestartDialog();
    }

    private void createRestartDialog() {
        setLocationRelativeTo(null);

        JPanel restartPanel = new JPanel();
        restartPanel.setLayout(new BoxLayout(restartPanel, BoxLayout.Y_AXIS));

        JLabel gameOverText = new JLabel("GAME OVER", JLabel.CENTER);
        gameOverText.setFont(new Font("Arial", Font.BOLD, 20));
        gameOverText.setAlignmentX(Component.CENTER_ALIGNMENT);

        restartButton = new JButton("Restart");
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        exitButton = new JButton("Exit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        restartButton.setActionCommand("Restart");
        exitButton.setActionCommand("Exit");

        restartPanel.add(gameOverText);
        restartPanel.add(Box.createVerticalStrut(50));
        restartPanel.add(restartButton);
        restartPanel.add(Box.createVerticalStrut(10));
        restartPanel.add(exitButton);

        restartPanel.setPreferredSize(new Dimension(25, 20));

        add(restartPanel);

        setSize(300, 300);
    }
}
