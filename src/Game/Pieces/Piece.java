package Game.Pieces;

import Game.Board;

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

    public abstract Move[] GetMoves();

    public abstract void Move();

    public String GetSymbol(){
        return symbol;
    }
}
