package Game.Pieces;

public record Move(Coord start, Coord end, Piece capture, Move extramove){
    public Move(Coord start, Coord end, Piece capture){
        this(start,end,capture,null);
    }
    public Move(Coord start, Coord end){
        this(start,end,null,null);
    }
};
