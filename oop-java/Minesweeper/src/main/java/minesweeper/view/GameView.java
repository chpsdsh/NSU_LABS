package minesweeper.view;


import minesweeper.model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class GameView extends JFrame {
    private GameModel model;
    private JPanel gamePanel;
    private JPanel mainPanel;
    private JPanel gameInfoPanel;
    private JLabel flagsNumberText;
    private JLabel gameTimerText;
    private JButton[][] buttons;
    private JButton restartButton;
    private JButton exitButton;
    private JDialog restartDialog;
    private JDialog winningDialog;
    private JTextField winnerName;
    private JButton winnerConfirmButton;
    private ImageIcon notOpened;
    private ImageIcon bomb;
    private ImageIcon flag;
    private ImageIcon ground;
    private ImageIcon one;
    private ImageIcon two;
    private ImageIcon three;
    private ImageIcon four;
    private ImageIcon five;
    private ImageIcon six;
    private ImageIcon seven;
    private ImageIcon eight;
    private ImageIcon clock;

    public JTextField getWinnerName() {
        return winnerName;
    }


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
//        showTimer();
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


    public void createRestartDialog() {
        restartDialog = new JDialog(this, "GAME OVER", true);
        restartDialog.setLocationRelativeTo(null);

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

        restartDialog.add(restartPanel);

        restartDialog.setSize(300, 300);
    }

    public void showRestartDialog() {
        restartDialog.setVisible(true);
    }

    public void createWinningDialog() {
        winningDialog = new JDialog(this, "YOU WON", true);
        winningDialog.setLocationRelativeTo(null);

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

        winningDialog.add(winningPanel);

        winningDialog.setSize(300, 300);
    }

    public GameModel getModel() {
        return model;
    }

    public void showWinningDialog() {
        winningDialog.setVisible(true);
    }

    public void setNewGameMouseListener(MouseAdapter listener, int row, int col) {
        buttons[row][col].addMouseListener(listener);
    }

    public void setRestartDialogListener(ActionListener listener) {
        restartButton.addActionListener(listener);
        exitButton.addActionListener(listener);
    }

    public void setWinningDialogListener(ActionListener listener) {
        winnerConfirmButton.addActionListener(listener);
    }
}
