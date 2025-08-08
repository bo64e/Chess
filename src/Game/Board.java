package Game;

import Game.Pieces.*;

import java.util.List;

public class Board {
    public Piece[][] board;
    public Board(){
        board = MakeBoard();
    }



    public Piece[][] MakeBoard(){
        Piece[][] _board = new Piece[8][8];
        _board[0][0] = new Rook(new Coord(0,0), false, this);
        _board[0][7] = new Rook(new Coord(7,0), false, this);
        _board[7][0] = new Rook(new Coord(0,7), true, this);
        _board[7][7] = new Rook(new Coord(7,7), true, this);


        _board[0][1] = new Knight(new Coord(1,0), false, this);
        _board[0][6] = new Knight(new Coord(6,0), false, this);
        _board[7][1] = new Knight(new Coord(1,7), true, this);
        _board[7][6] = new Knight(new Coord(6,7), true, this);


        _board[0][2] = new Bishop(new Coord(2,0), false, this);
        _board[0][5] = new Bishop(new Coord(5,0), false, this);
        _board[7][2] = new Bishop(new Coord(2,7), true, this);
        _board[7][5] = new Bishop(new Coord(5,7), true, this);


        _board[0][3] = new Queen(new Coord(3,0), false, this);
        _board[7][3] = new Queen(new Coord(3,7), true, this);


        _board[0][4] = new King(new Coord(4,0), false, this);
        _board[7][4] = new King(new Coord(4,7), true, this);

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (y == 1){
                    _board[y][x] = new Pawn(new Coord(x,y), false, this);
                }
                if (y == 6){
                    _board[y][x] = new Pawn(new Coord(x,y), true, this);
                }
                if (_board[y][x] == null){
                    _board[y][x] = new Empty(new Coord(x,y), true, this);
                }
            }
        }

        return _board;
    }

    public void print(){
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                System.out.print(board[y][x].GetSymbol());
            }
            System.out.println();
        }
        List<Move> moves = board[1][0].GetMoves();

        for (int i = 0; i < moves.size(); i++) {
            System.out.println(moves.get(i).ToFormat());
        }
    }
}
