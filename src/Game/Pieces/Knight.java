package Game.Pieces;

import Game.Board;

public class Knight extends Piece{
    public Knight(Coord position, boolean colour, Board board) {
        super(position, colour, board);
        symbol = "n";
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
