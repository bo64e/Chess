package Game.Pieces;

import Game.Board;

public class Rook extends Piece {
    public Rook(Coord position, boolean colour, Board board) {
        super(position, colour, board);
        symbol = "r";
        if (colour){
            symbol = symbol.toUpperCase();
        }
    }

    @Override
    public Move[] GetMoves() {
        return new Move[0];
    }

    @Override
    public void Move() {

    }
}
