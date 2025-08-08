package Game.Pieces;

import Game.Board;

public class Empty extends Piece {
    public Empty(Coord position, boolean colour, Board board) {
        super(position, colour, board);
        symbol = " ";
    }

    @Override
    public Move[] GetMoves() {
        return new Move[0];
    }

    @Override
    public void Move() {

    }
}
