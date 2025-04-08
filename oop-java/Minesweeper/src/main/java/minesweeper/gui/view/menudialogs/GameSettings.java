package minesweeper.gui.view.menudialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameSettings extends JDialog {
    private JTextField fieldSize;
    private JTextField numberOfMines;
    private JButton startGameButton;

    public JTextField getFieldSize() {
        return fieldSize;
    }

    public JTextField getNumberOfMines() {
        return numberOfMines;
    }

    public GameSettings(JFrame frame){
        super(frame, "Game settings", true);
        createGameSettings();
    }

    private void createGameSettings() {
        setLocationRelativeTo(null);

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

        add(dialogPanel);

        setSize(300, 300);
    }

    public void clear(){
        numberOfMines.setText("");
        fieldSize.setText("");
    }

    public void setStartGameActionListener(ActionListener listener) {
        startGameButton.addActionListener(listener);
    }
}
