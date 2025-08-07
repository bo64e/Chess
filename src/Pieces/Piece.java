package Pieces;

public abstract class Piece {
    Coord position;
    boolean moved = false;
    public Piece(Coord position) {
        this.position = position;
    }

    public abstract Move[] GetMoves();
}
