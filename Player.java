package mazegame;

public class Player {

    private int row;
    private int col;

    // Constructor
    public Player(int startRow, int startCol) {
        row = startRow;
        col = startCol;
    }

    // Get current row
    public int getRow() {
        return row;
    }

    // Get current column
    public int getCol() {
        return col;
    }

    // Move Up
    public void moveUp() {
        row--;
    }

    // Move Down
    public void moveDown() {
        row++;
    }

    // Move Left
    public void moveLeft() {
        col--;
    }

    // Move Right
    public void moveRight() {
        col++;
    }
}