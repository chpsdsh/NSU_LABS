package minesweeper.guiview.gamepanels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class GamePanel extends JPanel {
    private final ImageIcon notOpened;
    private final ImageIcon bomb;
    private final ImageIcon flag;
    private final ImageIcon ground;
    private final ImageIcon one;
    private final ImageIcon two;
    private final ImageIcon three;
    private final ImageIcon four;
    private final ImageIcon five;
    private final ImageIcon six;
    private final ImageIcon seven;
    private final ImageIcon eight;
    private final ImageIcon clock;
    private JPanel gamePanel;
    private JPanel gameInfoPanel;
    private JLabel flagsNumberText;
    private JLabel gameTimerText;
    private JButton[][] buttons;

    public GamePanel(int fieldSize, int numberOfFlags){
        super();
        notOpened = new ImageIcon("src/main/resources/grass.png");
        bomb = new ImageIcon("src/main/resources/bomb.png");
        flag = new ImageIcon("src/main/resources/flag.png");
        ground = new ImageIcon("src/main/resources/ground.png");
        one = new ImageIcon("src/main/resources/1.png");
        two = new ImageIcon("src/main/resources/2.png");
        three = new ImageIcon("src/main/resources/3.png");
        four = new ImageIcon("src/main/resources/4.png");
        five = new ImageIcon("src/main/resources/5.png");
        six = new ImageIcon("src/main/resources/6.png");
        seven = new ImageIcon("src/main/resources/7.png");
        eight = new ImageIcon("src/main/resources/8.png");
        clock = new ImageIcon("src/main/resources/clock.png");
        createGame(fieldSize,numberOfFlags);
    }

    private void createGame(Integer fieldSize, Integer numberOfFlags) {

        setLayout(new BorderLayout());
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(fieldSize, fieldSize));
        buttons = new JButton[fieldSize][fieldSize];
        gamePanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 50, 50));
        initializeButtons(fieldSize);

        gameInfoPanel = new JPanel();
        gameInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel clockLabel = new JLabel(clock);
        gameTimerText = new JLabel("0");
        gameTimerText.setFont(new Font("Arial", Font.BOLD, 20));
        gameTimerText.setPreferredSize(new Dimension(50, 30));
        JLabel flagLabel = new JLabel(flag);
        flagsNumberText = new JLabel(numberOfFlags.toString());
        flagsNumberText.setFont(new Font("Arial", Font.BOLD, 20));
        gameInfoPanel.add(clockLabel);
        gameInfoPanel.add(Box.createHorizontalStrut(10));
        gameInfoPanel.add(gameTimerText);
        gameInfoPanel.add(flagLabel);
        gameInfoPanel.add(flagsNumberText);
        add(gameInfoPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);
    }

    public void initializeButtons(int fieldSize) {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                buttons[i][j] = new JButton(notOpened);
                gamePanel.add(buttons[i][j]);
            }
        }
    }

    public void drawCell(int row, int col, int bombCount) {
        switch (bombCount) {
            case 0:
                buttons[row][col].setIcon(ground);
                break;
            case 1:
                buttons[row][col].setIcon(one);
                break;
            case 2:
                buttons[row][col].setIcon(two);
                break;
            case 3:
                buttons[row][col].setIcon(three);
                break;
            case 4:
                buttons[row][col].setIcon(four);
                break;
            case 5:
                buttons[row][col].setIcon(five);
                break;
            case 6:
                buttons[row][col].setIcon(six);
                break;
            case 7:
                buttons[row][col].setIcon(seven);
                break;
            case 8:
                buttons[row][col].setIcon(eight);
                break;
        }
        revalidate();
        repaint();
    }

    public void drawBomb(int row, int col){
        buttons[row][col].setIcon(bomb);
    }

    public void drawFlag(int row, int col, boolean isFlag, boolean isOpened, Integer numberOfFlags) {
        if (isFlag) {
            buttons[row][col].setIcon(flag);
            flagsNumberText.setText(numberOfFlags.toString());
        } else if(!isOpened) {
            buttons[row][col].setIcon(notOpened);
            flagsNumberText.setText(numberOfFlags.toString());
        }
        revalidate();
        repaint();
    }

    public void setNewGameMouseListener(MouseAdapter listener, int row, int col) {
        buttons[row][col].addMouseListener(listener);
    }

//        public void showTimer() {
//        gameTimerText.setText(model.getGameTimer().getSeconds().toString());
//        revalidate();
//        repaint();
//    }
}
