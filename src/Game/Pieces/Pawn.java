package Game.Pieces;

import Game.Board;

public class Pawn extends Piece{
    public Pawn(Coord position, boolean colour, Board board) {
        super(position, colour, board);
        symbol = "p";
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
