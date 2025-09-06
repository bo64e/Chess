package Game.Pieces;

public enum Colour {
    WHITE,
    BLACK,
    NONE;

    public int direction(){
        return switch (this) {
            case WHITE -> -1;
            case BLACK -> 1;
            default -> 0;
        };
    }
    public Colour reverse(){
        return switch (this) {
            case WHITE -> BLACK;
            case BLACK -> WHITE;
            default -> NONE;
        };
    }
    public String toString(){
        return switch (this) {
            case WHITE -> "w";
            case BLACK -> "b";
            default -> "n";
        };
    }
}
