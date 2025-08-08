package Game.Pieces;

import Game.Board;

public class Bishop extends Piece{
    public Bishop(Coord position, boolean colour, Board board) {
        super(position, colour, board);
        symbol = "b";
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
