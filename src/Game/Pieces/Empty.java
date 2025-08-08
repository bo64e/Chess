package Game.Pieces;

import Game.Board;

import java.util.ArrayList;
import java.util.List;

public class Empty extends Piece {

    public Empty(Coord position, boolean colour, Board board) {
        super(position, colour, board);
    }

    @Override
    public List<Move> GetMoves() {
        return new ArrayList<Move>();
    }

    @Override
    public void Move(Move move) {
        System.out.println("Attempted to move an empty square");
        return;
    }
}
