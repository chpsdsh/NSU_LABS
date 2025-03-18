package minesweeper.view;

import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {
    private JPanel gamePanel;

    public GameView() {
        super("MINESWEEPER");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(800, 800);
        this.setLocationRelativeTo(null);
        createGame();
        //showGame();
        //this.setVisible(true);
    }

    private void createGame() {
        gamePanel = new JPanel();
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
        gamePanel.setBorder(BorderFactory.createEmptyBorder(300, 50, 50, 50));

        JLabel name = new JLabel("MINESWEEPER", JLabel.CENTER);
        name.setFont(new Font("Arial", Font.BOLD, 28));
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        gamePanel.add(name);
    }

    public void showGame() {
        getContentPane().removeAll();
        getContentPane().add(gamePanel);
        revalidate();
        repaint();
    }


}
