package Game.Pieces;

import Game.*;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece{
    public King(Coord position, Colour colour, BoardState board) {
        super(position, colour, board);
        symbol = "k";
        if (colour == Colour.WHITE){
            symbol = symbol.toUpperCase();
        }
    }

    @Override
    public King Copy(BoardState board){
        King king = new King(position, colour, board);
        king.symbol = symbol;
        king.moved = moved;
        return king;
    }

    @Override
    public List<Move> GetMoves() {
        ArrayList<Move> moves = new ArrayList<>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x != 0 || y != 0){
                    if (BoardState.InBounds(position.x()+x, position.y()+y) && board.At(position.x()+x, position.y()+y) instanceof Empty){
                        moves.add(new Move(position, new Coord(position.x()+x, position.y()+y), this));
                    }
                    else if (BoardState.InBounds(position.x()+x, position.y()+y) && board.At(position.x()+x, position.y()+y).GetColour() != colour){
                        moves.add(new Move(position, new Coord(position.x()+x, position.y()+y), this, board.At(position.x()+x, position.y()+y)));
                    }
                }
            }
        }
        AddCastle(moves);
        return moves;
    }

    public void AddCastle(List<Move> moves){
        if (moved)
            return;
        boolean valid = true;
        for (int x = position.x()+1; x < 7; x++) {
            if (!(board.At(x, position.y()) instanceof Empty)){
                valid = false;
            }
        }
        if (valid && board.At(7, position.y()) instanceof Rook && !board.At(7, position.y()).moved){
            moves.add(new Move(
                    position,
                    new Coord(6, position.y()),
                    this,
                    null,
                    new Move(
                            board.At(7, position.y()).position,
                            new Coord(5, position.y()),
                            board.At(7, position.y())
                    )
            ));
        }

        valid = true;
        for (int x = position.x()-1; x > 0; x--) {
            if (!(board.At(x, position.y()) instanceof Empty)){
                valid = false;
            }
        }
        if (valid && board.At(0, position.y()) instanceof Rook && !board.At(0, position.y()).moved){
            moves.add(new Move(
                    position,
                    new Coord(2, position.y()),
                    this,
                    null,
                    new Move(
                            board.At(0, position.y()).position,
                            new Coord(3, position.y()),
                            board.At(0, position.y())
                    )
            ));
        }
    }

    @Override
    public boolean Move(Move move) {
        if (!super.Move(move)){return false;}
        moved = true;
        return true;
    }
}
