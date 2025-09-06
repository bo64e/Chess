package Game.Pieces;

import Game.BoardState;

import java.io.Console;

public record Move(Coord start, Coord end, Piece moving, Piece capture, Move extramove){
    public Move(Coord start, Coord end, Piece moving, Piece capture){
        this(start,end,moving,capture,null);
    }
    public Move(Coord start, Coord end, Piece moving){
        this(start,end,moving,null,null);
    }

    public Move(Coord start, Coord end, Piece moving, Piece capture, Move extramove){
        this.start = start;
        this.end = end;
        this.moving = moving;
        this.capture = capture;
        this.extramove = extramove;
        if (this.start() == this.end() || !this.moving().position.isEqual(this.start())){
            throw new RuntimeException("Invalid Move");
        }
    }

    public Move Clone(BoardState board){
        return new Move(start, end, board.At(moving().position), capture, extramove == null ? null : extramove.Clone(board));
    }

    public String ToFormat(Coord coord){
        String lets = "abcdefgh";
        return lets.charAt(coord.x()) +Integer.toString(8- coord.y());
    }

    public String ToFormat(){
        return ToFormat(end());
    }
}
