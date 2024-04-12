package ChessPieces;

public class ChessPiece {
    public boolean side; //true - white, false - black
    public int x;
    public int y;

    public ChessPiece(int x, int y, boolean side) {
        this.side = side;
        this.x = x;
        this.y = y;
    }

    public boolean isLegalMove(int oldX, int oldY, int x, int y, boolean side) {
        return false;
    }

}
