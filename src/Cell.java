public class Cell {
    Cell() {
        pieceOnCell = 0;
        side = "empty";
    }
    public String side; //true - white, false - black
    public int pieceOnCell;
    /*  0 = none
        1 = King
        2 = Queen
        3 = Rook 0
        4 = Rook 1
        5 = Bishop 0
        6 = Bishop 1
        7 = Knight 0
        8 = Knight 1
        9 = Pawn 0
        10 = Pawn 1
        11 = Pawn 2
        12 = Pawn 3
        13 = Pawn 4
        14 = Pawn 5
        15 = Pawn 6
        16 = Pawn 7
     */
}
