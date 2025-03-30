package minesweeper.cells;

public class Cell  {
    private boolean isBomb;
    private boolean isOpened;
    private boolean isFlag;

    public Cell(boolean isBomb, boolean isOpened,boolean isFlag){
        this.isBomb = isBomb;
        this.isOpened = isOpened;
        this.isFlag = isFlag;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public boolean isOpened() {
        return isOpened;
    }

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
