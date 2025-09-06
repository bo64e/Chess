package Game.Pieces;

import Game.*;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{
    public Knight(Coord position, Colour colour, BoardState board) {
        super(position, colour, board);
        symbol = "n";
        if (colour == Colour.WHITE){
            symbol = symbol.toUpperCase();
        }
    }

    @Override
    public Knight Copy(BoardState board){
        Knight knight = new Knight(position, colour, board);
        knight.symbol = symbol;
        knight.moved = moved;
        return knight;
    }

    @Override
    public List<Move> GetMoves() {
        ArrayList<Move> moves = new ArrayList<>();

        for (int y = -2; y <= 2; y += 4) {
                for (int x = -1; x <= 1; x += 2) {
                    if (BoardState.InBounds(position.x()+x, position.y()+y) && board.At(position.x()+x, position.y()+y) instanceof Empty){
                        moves.add(new Move(position, new Coord(position.x()+x, position.y()+y), this));
                    }
                    else if (BoardState.InBounds(position.x()+x, position.y()+y) && board.At(position.x()+x, position.y()+y).GetColour() != colour){
                        moves.add(new Move(position, new Coord(position.x()+x, position.y()+y), this, board.At(position.x()+x, position.y()+y)));
                    }
            }
        }

        for (int x = -2; x <= 2; x += 4) {
            for (int y = -1; y <= 1; y += 2) {
                if (BoardState.InBounds(position.x()+x, position.y()+y) && board.At(position.x()+x, position.y()+y) instanceof Empty){
                    moves.add(new Move(position, new Coord(position.x()+x, position.y()+y), this));
                }
                else if (BoardState.InBounds(position.x()+x, position.y()+y) && board.At(position.x()+x, position.y()+y).GetColour() != colour){
                    moves.add(new Move(position, new Coord(position.x()+x, position.y()+y), this, board.At(position.x()+x, position.y()+y)));
                }
            }
        }
        return moves;
    }

//    @Override
//    public void Move(Move move) {
//
//    }
}
