package Game.Pieces;

import Game.Board;

import java.util.List;

public abstract class Piece {
    Coord position;
    Board board;
    String symbol;
    boolean moved = false;
    boolean colour;
    public Piece(Coord position, boolean colour, Board board) {
        this.position = position;
        this.colour = colour;
        this.board = board;
    }

    public abstract List<Move> GetMoves();

    public abstract void Move(Move move);

    public String GetSymbol(){
        return symbol;
    }

    public boolean GetColour(){
        return colour;
    }
}
