package Game.Pieces;

import Game.Board;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    public Rook(Coord position, boolean colour, Board board) {
        super(position, colour, board);
        symbol = "r";
        if (colour){
            symbol = symbol.toUpperCase();
        }
    }

    @Override
    public List<Move> GetMoves() {
        return new ArrayList<Move>();
    }

    @Override
    public void Move(Move move) {

    }
}
