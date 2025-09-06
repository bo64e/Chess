package Game.Pieces;

import Game.*;

import java.util.ArrayList;
import java.util.List;

public class Empty extends Piece {

    public Empty(Coord position, Colour colour, BoardState board) {
        super(position, colour, board);
        symbol = "-";
    }
    
    @Override
    public Empty Copy(BoardState board){
        Empty empty = new Empty(position, colour, board);
        empty.symbol = symbol;
        empty.moved = moved;
        return empty;
    }
    
    @Override
    public List<Move> GetMoves() {
        return new ArrayList<Move>();
    }

    @Override
    public boolean Move(Move move) {
        System.out.println("Attempted to move an empty square");
        return false;
    }
}
