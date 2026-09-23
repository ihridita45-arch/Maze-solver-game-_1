package mazegame;

public class Player {

    private int row;
    private int col;

    public Player(int startRow, int startCol) {
        row = startRow;
        col = startCol;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void moveUp() {
        row--;
    }

    public void moveDown() {
        row++;
    }

    public void moveLeft() {
        col--;
    }

    public void moveRight() {
        col++;
    }

    // Update player position
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }
}