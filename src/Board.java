import Pieces.*;

public class Board {
    Piece[][] board;
    public Board(){
        board = MakeBoard();
    }

    public Piece[][] MakeBoard(){
        Piece[][] _board = new Piece[8][8];
        return _board;
    }
}
