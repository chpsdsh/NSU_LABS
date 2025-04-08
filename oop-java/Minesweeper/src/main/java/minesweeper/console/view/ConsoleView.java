package minesweeper.console.view;

import minesweeper.model.GameModel;

public class ConsoleView {
    private char[][] field;
    private GameModel model;
    private String flagNumber;

    public ConsoleView(GameModel model) {
        printInitializeParameters();
    }

    public void createField(int fieldSize) {
        field = new char[fieldSize][fieldSize];

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                field[i][j] = '@';
            }
        }
        drawField(fieldSize);
    }

    public void drawField(int fieldSize) {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printInputInfo(){
        System.out.println("Write coordinates splitted with space");
    }

    public void printInitializeParameters(){
        System.out.println("Write Field size and Number of mines separated with SPACE:");
    }

    public void printWin(){
        System.out.println("YOU WON!!!");
        System.out.println("Write your name");
    }

    public void printGameOver(){
        System.out.println("GAME OVER");
        System.out.println("WRITE RESTART/EXIT");
    }

    public void drawCell(int row, int col, int bombCount){
        switch (bombCount){
            case -1:
                field[row][col] = '*';
                break;
            case 0:
                field[row][col] = '0';
                break;
            case 1:
                field[row][col] = '1';
                break;
            case 2:
                field[row][col] = '2';
                break;
            case 3:
                field[row][col] = '3';
                break;
            case 4:
                field[row][col] = '4';
                break;
            case 5:
                field[row][col] = '5';
                break;
            case 6:
                field[row][col] = '6';
                break;
            case 7:
                field[row][col] = '7';
                break;
            case 8:
                field[row][col] = '8';
                break;
        }
    }
    public void drawFlag(int row, int col, boolean condition, Integer numberOfFlags){
        if(condition) {
            field[row][col] = 'F';
        }
        else{
            field[row][col] = '@';
        }
        System.out.println("Flags remaining: "+ numberOfFlags);
    }

//    public void showHighScores(ArrayList<HighScore> scores){
//        for (HighScore score : scores){
//            System.out.println(score.getName()+" "+ score.getTime()+" "+score.getFieldSize());
//        }
//    }
}

