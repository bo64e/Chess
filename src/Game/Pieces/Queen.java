package Game.Pieces;

import Game.Board;

public class Queen extends Piece{
    public Queen(Coord position, boolean colour, Board board) {
        super(position, colour, board);
        symbol = "q";
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
