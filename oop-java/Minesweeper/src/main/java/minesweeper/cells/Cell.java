package minesweeper.cells;


public class Cell  {
    private boolean isBomb;
    private boolean isOpened;
    private boolean isFlag;
//    private int neighboursNumber;

    public Cell(boolean isBomb, boolean isOpened,boolean isFlag){
        this.isBomb = isBomb;
        this.isOpened = isOpened;
        this.isFlag = isFlag;
//        this.neighboursNumber = neighboursNumber;
    }

    public boolean isBomb() {
        return isBomb;
    }
    public boolean isFlag() {
        return isFlag;
    }
    //    public void setNeighboursNumber(int neighboursNumber) {
//        this.neighboursNumber = neighboursNumber;
//    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    public void setBomb(boolean bomb){
        isBomb = bomb;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }
}
