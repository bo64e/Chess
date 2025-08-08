package Game.Pieces;

import Game.Board;

public class King extends Piece{
    public King(Coord position, boolean colour, Board board) {
        super(position, colour, board);
        symbol = "k";
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
