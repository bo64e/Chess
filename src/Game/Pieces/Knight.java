package Game.Pieces;

import Game.Board;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{
    public Knight(Coord position, boolean colour, Board board) {
        super(position, colour, board);
        symbol = "n";
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
