package Game.Pieces;

import Game.*;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    public boolean passant = false;

    public Pawn(Coord position, Colour colour, BoardState board) {
        super(position, colour, board);
        symbol = "p";
        if (colour == Colour.WHITE) {
            symbol = symbol.toUpperCase();
        }
    }

    @Override
    public Pawn Copy(BoardState board){
        Pawn pawn = new Pawn(position, colour, board);
        pawn.symbol = symbol;
        pawn.moved = moved;
        pawn.passant = passant;
        return pawn;
    }

    @Override
    public List<Move> GetMoves() {
        List<Move> moves = new ArrayList<>();
        int direction = colour.direction();

        ForwardMoves(moves, direction);
        Captures(moves, direction);
        EnPassant(moves, direction);

        return moves;
    }

    public void ForwardMoves(List<Move> moves, int direction){
        for (int i = 1; (i <= 2 && !moved) || (i <= 1); i++) {
            if (BoardState.InBounds(position.x(), position.y() + (i * direction)) && board.At(position.x(), position.y() + (i * direction)) instanceof Empty) {
                if (i == 1 || board.At(position.x(), position.y()+direction) instanceof Empty)
                    if (position.y() + (i * direction) == 0 || position.y() + (i * direction) == 7){
                        moves.add(new Move(position,
                                new Coord(position.x(), position.y() + (i * direction)),
                                this,
                                null,
                                new Move(
                                        position,
                                        new Coord(position.x(), position.y() + (i * direction)),
                                        new Queen(position, colour, board),
                                        this
                                )
                                ));
                    }
                    else{
                        moves.add(new Move(position,
                                new Coord(position.x(), position.y() + (i * direction)),
                                this));
                    }
            }
        }
    }

    public void Captures(List<Move> moves, int direction) {
        if (BoardState.InBounds(position.x() - 1, position.y() + direction) && !(board.At(position.x() - 1, position.y() + direction) instanceof Empty)) {
            if (board.At(position.x() - 1, position.y() + direction).GetColour() != colour) {
                moves.add(new Move(
                        position,
                        new Coord(position.x() - 1, position.y() + direction),
                        this,
                        board.At(position.x() - 1, position.y() + direction)
                ));
            }
        }

        if (BoardState.InBounds(position.x() + 1, position.y() + direction) && !(board.At(position.x() + 1, position.y() + direction) instanceof Empty)) {
            if (board.At(position.x() + 1, position.y() + direction).GetColour() != colour) {
                moves.add(new Move(
                        position,
                        new Coord(position.x() + 1, position.y() + direction),
                        this,
                        board.At(position.x() + 1, position.y() + direction)
                ));
            }
        }
    }

    public void EnPassant(List<Move> moves, int direction) {
        // this uses a stupid flag specific to pawns that tells if a pawn has just moved two spaces
        // as a result, risky casting is used

        for (int x = -1; x <= 1; x+=2) {
            try {
                if (BoardState.InBounds(position.x()+ x, position.y()) && board.At(position.x() + x, position.y()) instanceof Pawn) {
                    if (((Pawn) board.At(position.x() + x, position.y())).passant &&
                            board.At(position.x() + x, position.y()).GetColour() != colour) {
                        moves.add(new Move(
                                position,
                                new Coord(position.x() + x, position.y()+direction),
                                this,
                                board.At(position.x() + x, position.y())
                        ));
                    }
                }
            } catch (ClassCastException e) {
                System.out.println(String.format("Tried to cast a piece to a pawn at (%d, %d)", position.x(), position.y()));
            }

        }
    }

    @Override
    public boolean Move(Move move) {
        if (!super.Move(move)){return false;}
        moved = true;
        passant = Math.abs(move.start().y() - move.end().y()) > 1;
        if (passant){
            return true;
        }
        return true;
    }
}
