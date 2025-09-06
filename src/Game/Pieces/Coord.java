package Game.Pieces;

public record Coord(int x, int y){
    public String ToFormat(){
        String lets = "abcdefgh";
        return lets.charAt(x()) +Integer.toString(8- y());
    }

    public static Coord FromFormat(String s){
        String lets = "abcdefgh";
        if (s.length() != 2 || lets.indexOf(s.charAt(0)) == -1 || !Character.isDigit(s.charAt(1))){
            throw new RuntimeException("Attemted to parse an invalid string to a coordinate");
        }

        return new Coord(lets.indexOf(s.charAt(0)),'8'-s.charAt(1));
    }

    public boolean isEqual(Coord coord){
        return (x() == coord.x() && y() == coord.y());
    }

    public static boolean isEqual(Coord coord1, Coord coord2){
        return (coord1.x() == coord2.x() && coord1.y() == coord2.y());
    }
}
