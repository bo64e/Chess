package Game.Pieces;

import Game.*;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece{
    public Bishop(Coord position, Colour colour, BoardState board) {
        super(position, colour, board);
        symbol = "b";
        if (colour == Colour.WHITE){
            symbol = symbol.toUpperCase();
        }
    }

    @Override
    public Bishop Copy(BoardState board){
        Bishop bishop = new Bishop(position, colour, board);
        bishop.symbol = symbol;
        bishop.moved = moved;
        return bishop;
    }

    @Override
    public List<Move> GetMoves() {
        List<Move> moves = new ArrayList<>();
        CheckDirection(moves, -1, -1);
        CheckDirection(moves, 1, -1);
        CheckDirection(moves, -1, 1);
        CheckDirection(moves, 1, 1);
        return moves;
    }

//    @Override
//    public void Move(Move move) {
//
//    }

    public void CheckDirection(List<Move> moves, int i_x, int i_y) {
        int i = 1;
        while (position.x() + (i_x * i) >= 0 && position.x() + (i_x * i) < 8 &&
                position.y() + (i_y * i) >= 0 && position.y() + (i_y * i) < 8) {

            if (board.At(position.x() + (i * i_x), position.y() + (i * i_y)) instanceof Empty) {
                moves.add(new Move(
                        position,
                        new Coord(position.x() + (i * i_x), position.y() + (i * i_y)),
                        this
                ));
            } else if (board.At(position.x() + (i * i_x), position.y() + (i * i_y)).GetColour() != colour) {
                moves.add(new Move(
                        position,
                        new Coord(position.x() + (i * i_x), position.y() + (i * i_y)),
                        this,
                        board.At(position.x() + (i * i_x), position.y() + (i * i_y))
                ));
                return;
            } else {
                return;
            }
            i++;
        }
    }
}
