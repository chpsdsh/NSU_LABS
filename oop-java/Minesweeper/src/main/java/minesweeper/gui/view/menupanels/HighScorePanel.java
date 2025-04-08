package minesweeper.gui.view.menupanels;

import minesweeper.highscore.HighScore;
import minesweeper.highscore.HighScoreHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HighScorePanel extends JPanel {
    private JButton highScoreExitButton;
    DefaultTableModel tableModel;

    public void setExitActionListener(ActionListener listener) {
        highScoreExitButton.addActionListener(listener);
    }

    public HighScorePanel() {
        super();
        createHighScore();
    }

    private void createHighScore() {
        highScoreExitButton = new JButton("Exit");
        highScoreExitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(70, 50, 50, 50));
        JLabel name = new JLabel("HIGH SCORES");
        name.setFont(new Font("Arial", Font.BOLD, 24));
        name.setAlignmentX(Component.CENTER_ALIGNMENT);

        HighScoreHandler highScoreHandler = new HighScoreHandler("src/main/resources/Results.json");
        ArrayList<HighScore> scores = highScoreHandler.getScores();

        String[] columnNames = {"№", "Name", "Time", "Field size", "Mines"};
        tableModel = new DefaultTableModel(columnNames, 0);
        createTableModel(scores);

        JTable highScores = new JTable(tableModel);
        highScores.setFont(new Font("Arial", Font.PLAIN, 14));
        highScores.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(highScores);

        scrollPane.setPreferredSize(new Dimension(600, 400));
        add(name);
        add(Box.createVerticalStrut(10));
        add(scrollPane);
        add(Box.createVerticalStrut(10));
        add(highScoreExitButton);
    }

    private void createTableModel(ArrayList<HighScore> scores) {
        int index = 1;
        for (HighScore score : scores) {
            Object[] row = {index++, score.getName(), score.getTime(), score.getFieldSize(), score.getNumberOfMines()};
            tableModel.addRow(row);
        }
    }
}
