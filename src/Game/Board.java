package Game;

import Game.Pieces.*;

import java.util.List;

public class Board {
    public BoardState board;
    public Colour winner = null;
    public Board(){
        board = new BoardState(this);
    }

    public void Move(String move){
        String[] moves = move.strip().split(" ");
        if (moves.length != 2){
            if (move.length() == 4){
                moves = new String[]{move.substring(0,2), move.substring(2,4)};
            }
            else if (move.length() == 2){
                Coord end = Coord.FromFormat(move);
                Move selected = null;
                for (Move imove : board.GetMoves()){
                    if (imove.end().isEqual(end)){
                        if (selected != null){
                            throw new RuntimeException("Nonspecific move");
                        }
                        else{
                            selected = imove;
                        }
                    }
                }
                if (selected == null){
                    if (board.GetMoves().isEmpty()){
                        winner = Colour.NONE;
                        return;
                    }
                    throw new RuntimeException("Move not found");
                }
                else{
                    if (!board.Move(selected)){
                        throw new RuntimeException("Move failed");
                    }
                    return;
                }
            }
            else{throw new RuntimeException("Attemted to parse an invalid string to a coordinate");}
        }
        Coord start = Coord.FromFormat(moves[0]);
        Coord end = Coord.FromFormat(moves[1]);


        for (Move imove : board.GetMoves()){
            if (imove.start().isEqual(start) && imove.end().isEqual(end)){
                if (!board.Move(imove)){
                    throw new RuntimeException("Move failed");
                }
                return;
            }
        }
        if (board.GetMoves().isEmpty()){
            winner = Colour.NONE;
            return;
        }
        throw new RuntimeException("Move not found");
    }

    public boolean Move(Coord start, Coord end){
        for (Move move : board.GetMoves()){
            if (move.end().isEqual(end) && move.start().isEqual(start)){
                return board.Move(move);
            }
        }
        return false;
    }

    public String print(){
        return board.print();
    }
//    public Piece[][] MakeBoard(){
//        Piece[][] _board = new Piece[8][8];
//        _board[0][0] = new Rook(new Coord(0,0), false, this);
//        _board[0][7] = new Rook(new Coord(7,0), false, this);
//        _board[7][0] = new Rook(new Coord(0,7), true, this);
//        _board[7][7] = new Rook(new Coord(7,7), true, this);
//
//
//        _board[0][1] = new Knight(new Coord(1,0), false, this);
//        _board[0][6] = new Knight(new Coord(6,0), false, this);
//        _board[7][1] = new Knight(new Coord(1,7), true, this);
//        _board[7][6] = new Knight(new Coord(6,7), true, this);
//
//
//        _board[0][2] = new Bishop(new Coord(2,0), false, this);
//        _board[0][5] = new Bishop(new Coord(5,0), false, this);
//        _board[7][2] = new Bishop(new Coord(2,7), true, this);
//        _board[7][5] = new Bishop(new Coord(5,7), true, this);
//
//
//        _board[0][3] = new Queen(new Coord(3,0), false, this);
//        _board[7][3] = new Queen(new Coord(3,7), true, this);
//
//
//        _board[0][4] = new King(new Coord(4,0), false, this);
//        _board[7][4] = new King(new Coord(4,7), true, this);
//
//        for (int y = 0; y < 8; y++) {
//            for (int x = 0; x < 8; x++) {
//                if (y == 1){
//                    _board[y][x] = new Pawn(new Coord(x,y), false, this);
//                }
//                if (y == 6){
//                    _board[y][x] = new Pawn(new Coord(x,y), true, this);
//                }
//                if (_board[y][x] == null){
//                    _board[y][x] = new Empty(new Coord(x,y), true, this);
//                }
//            }
//        }
//        _board[4][4] = new Rook(new Coord(4,4), false, this);
//
//        return _board;
//    }

}
