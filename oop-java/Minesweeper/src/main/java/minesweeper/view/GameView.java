package minesweeper.view;


import minesweeper.model.GameModel;
import minesweeper.view.gamedialogs.RestartDialog;
import minesweeper.view.gamedialogs.WinningDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class GameView extends JFrame {
    private final GameModel model;
    private JPanel gamePanel;
    private JPanel mainPanel;
    private JPanel gameInfoPanel;
    private JLabel flagsNumberText;
    private JLabel gameTimerText;
    private JButton[][] buttons;
    private RestartDialog restartDialog;
    private WinningDialog winningDialog;

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


    public GameView(GameModel model) {
        super("MINESWEEPER");
        this.model = model;
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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(50 * model.getFieldSize() + 100, 50 * model.getFieldSize() + 100);
        this.setLocationRelativeTo(null);
        createGame(model.getFieldSize(), model.getNumberOfMines());
        showGame();
        this.setVisible(true);
    }

    private void createGame(Integer fieldSize, Integer numberOfMines) {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
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
        flagsNumberText = new JLabel(numberOfMines.toString());
        flagsNumberText.setFont(new Font("Arial", Font.BOLD, 20));
        gameInfoPanel.add(clockLabel);
        gameInfoPanel.add(Box.createHorizontalStrut(10));
        gameInfoPanel.add(gameTimerText);
        gameInfoPanel.add(flagLabel);
        gameInfoPanel.add(flagsNumberText);
        mainPanel.add(gameInfoPanel, BorderLayout.NORTH);
        mainPanel.add(gamePanel, BorderLayout.CENTER);
    }

    public void initializeButtons(int fieldSize) {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                buttons[i][j] = new JButton(notOpened);
                gamePanel.add(buttons[i][j]);
            }
        }
    }


    public void openCells(int row, int col) {
        model.openCells(row, col);
        if(!model.isGameOver()){
            drawField();
        }
        else{
            drawBombs();
        }
    }

    public void drawField() {
        for (int i = 0; i < model.getFieldSize(); i++) {
            for (int j = 0; j < model.getFieldSize(); j++) {
                if(model.isOpened(i,j) && !model.isFlag(i,j)) {
                    drawCell(i, j, model.countBombsNear(i, j));
                }
            }
        }
    }
    public void drawBombs() {
        for (int i = 0; i < model.getFieldSize(); i++) {
            for (int j = 0; j < model.getFieldSize(); j++) {
                if(model.isBomb(i,j)) {
                    buttons[i][j].setIcon(bomb);
                }
            }
        }
        revalidate();
        repaint();
    }

    public void showGame() {
        getContentPane().removeAll();
        getContentPane().add(mainPanel);
        revalidate();
        repaint();
    }

    public void drawFlag(int row, int col, boolean condition, Integer numberOfFlags) {
        if (condition) {
            buttons[row][col].setIcon(flag);
            flagsNumberText.setText(numberOfFlags.toString());
        } else if(!model.isOpened(row,col)){
            buttons[row][col].setIcon(notOpened);
            flagsNumberText.setText(numberOfFlags.toString());
        }
        revalidate();
        repaint();
    }

    public void exitGame() {
        model.exitGame();
    }


    public void showTimer() {
        gameTimerText.setText(model.getGameTimer().getSeconds().toString());
        revalidate();
        repaint();
    }

    public void putFlag(int row, int col){
        model.putFlag(row,col);
        drawFlag(row,col,model.isFlag(row,col),model.getNumberOfFlags());
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


    public GameModel getModel() {
        return model;
    }

    public void showRestartDialog() {
        restartDialog.setVisible(true);
    }

    public void showWinningDialog() {
        winningDialog.setVisible(true);
    }

    public void setNewGameMouseListener(MouseAdapter listener, int row, int col) {
        buttons[row][col].addMouseListener(listener);
    }

    public void setRestartDialogListener(ActionListener listener) {
        restartDialog.setRestartDialogListener(listener);
    }

    public void setWinningDialogListener(ActionListener listener) {
        winningDialog.setWinningDialogListener(listener);
    }
}
