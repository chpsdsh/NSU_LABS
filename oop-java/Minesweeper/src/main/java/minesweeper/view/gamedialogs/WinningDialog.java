package minesweeper.view.gamedialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class WinningDialog extends JDialog {
    private JTextField winnerName;
    private JButton winnerConfirmButton;

    public JTextField getWinnerName() {
        return winnerName;
    }

    public WinningDialog(JFrame frame){
        super(frame, "YOU WON", true);
        createWinningDialog();
    }

    private void createWinningDialog() {
        setLocationRelativeTo(null);

        JPanel winningPanel = new JPanel();
        winningPanel.setLayout(new BoxLayout(winningPanel, BoxLayout.Y_AXIS));

        JLabel gameWinText = new JLabel("YOU WON!!!", JLabel.CENTER);
        gameWinText.setFont(new Font("Arial", Font.BOLD, 20));
        gameWinText.setAlignmentX(Component.CENTER_ALIGNMENT);

        winnerConfirmButton = new JButton("Confirm");
        winnerConfirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        winnerConfirmButton.setActionCommand("Confirm");

        JLabel nameText = new JLabel("Write your name", JLabel.CENTER);
        nameText.setFont(new Font("Arial", Font.BOLD, 16));
        nameText.setAlignmentX(Component.CENTER_ALIGNMENT);

        winnerName = new JTextField();
        winnerName.setFont(new Font("Arial", Font.PLAIN, 20));
        winnerName.setAlignmentX(Component.CENTER_ALIGNMENT);
        winnerName.setHorizontalAlignment(SwingConstants.CENTER);
        winnerName.setMaximumSize(new Dimension(150, 25));

        winningPanel.add(gameWinText);
        winningPanel.add(Box.createVerticalStrut(50));
        winningPanel.add(nameText);
        winningPanel.add(Box.createVerticalStrut(10));
        winningPanel.add(winnerName);
        winningPanel.add(Box.createVerticalStrut(10));
        winningPanel.add(winnerConfirmButton);

        winningPanel.setPreferredSize(new Dimension(25, 20));

        add(winningPanel);

        setSize(300, 300);
    }
    public void setWinningDialogListener(ActionListener listener) {
        winnerConfirmButton.addActionListener(listener);
    }
}
