package ChessPieces;

public class Pawn extends ChessPiece {

    boolean isFirstMove;

    public Pawn(int x, int y, boolean side) {
        super(x, y, side);
        isFirstMove = true;
    }

    @Override
    public boolean isLegalMove(int oldX, int oldY, int x, int y, boolean side, String newCellSide) {
        if (side) {
            if (oldX == x && y - oldY <= 2 && y - oldY > 0) {
                return true;
            }
        } else {
            if (oldX == x && oldY - y <= 2 && oldY - y > 0) {
                return true;
            }
        }
//        if (isFirstMove) {
//            return true;
//        }
        return false;
    }
}