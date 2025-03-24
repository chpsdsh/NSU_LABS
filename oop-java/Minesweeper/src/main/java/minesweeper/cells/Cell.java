package minesweeper.cells;


public class Cell  {
    private boolean isBomb;
    private boolean isOpened;
    private boolean isFlag;
    private int neighboursNumber;

    public Cell(boolean isBomb, boolean isOpened,boolean isFlag, int neighboursNumber){
        this.isBomb = isBomb;
        this.isOpened = isOpened;
        this.isFlag = isFlag;
        this.neighboursNumber = neighboursNumber;
    }

    public void setNeighboursNumber(int neighboursNumber) {
        this.neighboursNumber = neighboursNumber;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    public void setBomb(boolean bomb){
        isBomb = bomb;
    }
 }
