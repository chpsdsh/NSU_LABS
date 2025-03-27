package minesweeper.view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class GameView extends JFrame {
    private JPanel gamePanel;
    private JButton[][] buttons;
    private ImageIcon notOpened;
    private ImageIcon bomb;
    private ImageIcon flag;

    public GameView( int fieldSize) {
        super("MINESWEEPER");
        notOpened = new ImageIcon("src/main/resources/grass.png");
        bomb = new ImageIcon("src/main/resources/bomb.png");
        flag = new ImageIcon("src/main/resources/flag.png");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(50 * fieldSize + 100, 50 * fieldSize + 100);
        this.setLocationRelativeTo(null);
        createGame(fieldSize);
        showGame();
        this.setVisible(true);
    }

    private void createGame(int fieldSize) {
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(fieldSize, fieldSize));
        buttons = new JButton[fieldSize][fieldSize];
        gamePanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        initializeButtons(fieldSize);
    }

    public void initializeButtons(int fieldSize) {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                buttons[i][j] = new JButton(notOpened);
                gamePanel.add(buttons[i][j]);
            }
        }
    }


    public void showGame() {
        getContentPane().removeAll();
        getContentPane().add(gamePanel);
        revalidate();
        repaint();
    }

    public void drawFlag(int row, int col, boolean condition){
        if(condition) {
            buttons[row][col].setIcon(flag);
        }
        else{
            buttons[row][col].setIcon(notOpened);
        }
        buttons[row][col].revalidate();
        buttons[row][col].repaint();
    }

    public void setNewGameMouseListener(MouseAdapter listener, int row, int col) {
        buttons[row][col].addMouseListener(listener);
    }
//     public JButton getButton(int row, int col){
//        return buttons[row][col];
//     }



}
