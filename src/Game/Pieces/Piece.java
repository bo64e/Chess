package Game.Pieces;

import Game.*;

import java.util.List;

public abstract class Piece {
    public Coord position;
    BoardState board;
    String symbol;
    public boolean moved = false;
    Colour colour;
    public Piece(Coord position, Colour colour, BoardState board) {
        this.position = position;
        this.colour = colour;
        this.board = board;
    }

    public abstract Piece Copy(BoardState board);

    public abstract List<Move> GetMoves();

    public boolean Move(Move move){
        return this.Move(move, false);
    }

    public boolean Move(Move move, boolean skipchecks){
        if (skipchecks){
            position = move.end();
            return true;
        }
        for (Move imove : this.GetMoves()){
            if (imove.end().isEqual(move.end())){
                position = move.end();
                return true;
            }
        }
        return false;
    }

    public String GetSymbol(){
        return symbol;
    }

    public Colour GetColour(){
        return colour;
    }
}
