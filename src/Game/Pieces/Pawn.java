package Game.Pieces;

import Game.Board;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece{
    public Pawn(Coord position, boolean colour, Board board) {
        super(position, colour, board);
        symbol = "p";
        if (colour){
            symbol = symbol.toUpperCase();
        }
    }



    @Override
    public List<Move> GetMoves() {
        List<Move> moves = new ArrayList<>();
        int direction = colour ? 1 : -1;
        for (int i = 1; (i <= 2 && !moved) || (i <= 1); i++) {
            try{
                if (board.board[position.y()+(i*direction)][position.x()] instanceof Empty){
                    moves.add(new Move(position, new Coord(position.y()+(i*direction),position.x())));
                }
            } catch (IndexOutOfBoundsException _) {

            }
        }
        AddCaptures(moves, direction);
        return new ArrayList<>();
    }

    public void AddCaptures(List<Move> moves, int direction){

    }

    public void EnPassant(List<Move> moves, int direction){

    }

    @Override
    public void Move(Move move) {

    }
}
