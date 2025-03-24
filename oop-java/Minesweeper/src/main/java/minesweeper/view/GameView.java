package minesweeper.view;

import minesweeper.model.GameModel;

import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {
    private JPanel gamePanel;
    private GameModel model;
    private JButton[][] buttons;

    public GameView(GameModel model) {
        super("MINESWEEPER");
        this.model = model;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(800, 800);
        this.setLocationRelativeTo(null);
        createGame();
        showGame();
        this.setVisible(true);
    }

    private void createGame() {
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(model.getFieldSize(), model.getFieldSize()));
        buttons = new JButton[model.getFieldSize()][model.getFieldSize()];
        gamePanel.setBorder(BorderFactory.createEmptyBorder(300, 50, 50, 50));

        JLabel name = new JLabel("MINESWEEPER", JLabel.CENTER);
        name.setFont(new Font("Arial", Font.BOLD, 28));
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        gamePanel.add(name);
    }

    private void initializeButtons(){
        for(int i = 0; i <)
    }

    public void showGame() {
        getContentPane().removeAll();
        getContentPane().add(gamePanel);
        revalidate();
        repaint();
    }



}
