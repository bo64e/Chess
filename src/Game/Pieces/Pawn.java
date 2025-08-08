package Game.Pieces;

import Game.Board;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece{
    public boolean passant = false;
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
                    moves.add(new Move(position, new Coord(position.x(),position.y()+(i*direction)),this));
                }
            } catch (IndexOutOfBoundsException _) {}
        }
        AddCaptures(moves, direction);
        return new ArrayList<>();
    }

    public void AddCaptures(List<Move> moves, int direction){
        try{
            if (!(board.board[position.y()+direction][position.x()-1] instanceof Empty)){
                if (board.board[position.y()+direction][position.x()-1].GetColour() != colour){
                    moves.add(new Move(
                            position, 
                            new Coord(position.x()-1,position.y()+direction),
                            this,
                            board.board[position.y()+direction][position.x()-1]
                    ));
                }
            }
        } catch (IndexOutOfBoundsException _) {}

        try{
            if (!(board.board[position.y()+direction][position.x()+1] instanceof Empty)){
                if (board.board[position.y()+direction][position.x()+1].GetColour() != colour) {
                    moves.add(new Move(
                            position,
                            new Coord(position.x() + 1, position.y() + direction),
                            this,
                            board.board[position.y()+direction][position.x()+1]
                    ));
                }
            }
        } catch (IndexOutOfBoundsException _) {}
    }

    public void EnPassant(List<Move> moves, int direction){
        //this uses a stupid flag specific to pawns that tells if a pawn has just moved two spaces
        //as a result, risky casting is used
        //TMI
        try{
            if (board.board[position.y()][position.x()-1] instanceof Pawn){
                if (((Pawn) board.board[position.y()][position.x()-1]).passant &&
                        board.board[position.y()][position.x()-1].GetColour() != colour){
                    moves.add(new Move(
                            position,
                            new Coord(position.x() - 1, position.y()),
                            this,
                            board.board[position.y()][position.x()-1]
                    ));
                }
            }
        } catch (ClassCastException e) {
            System.out.println(String.format("Tried to cast a piece to a pawn at (%d, %d)",position.x(), position.y()));
        } catch (IndexOutOfBoundsException _){}

        try{
            if (board.board[position.y()][position.x()+1] instanceof Pawn){
                if (((Pawn) board.board[position.y()][position.x()+1]).passant &&
                        board.board[position.y()][position.x()+1].GetColour() != colour){
                    moves.add(new Move(
                            position,
                            new Coord(position.x() + 1, position.y()),
                            this,
                            board.board[position.y()][position.x()+1]
                    ));
                }
            }
        } catch (ClassCastException e) {
            System.out.println(String.format("Tried to cast a piece to a pawn at (%d, %d)",position.x(), position.y()));
        } catch (IndexOutOfBoundsException _){}
    }

    @Override
    public void Move(Move move) {
        moved = true;
        passant = false;
        if (Math.abs(move.start().y() - move.end().y()) > 1){
            passant = true;
        }
        position = move.end();
    }
}
