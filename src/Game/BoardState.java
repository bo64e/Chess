package Game;

import Game.Pieces.*;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BoardState {
    public int[] MoveCount;
    private final Piece[][] board;
    public Board Board;
    public Colour colour = Colour.WHITE;

    public BoardState(Piece[][] _board, Board _Board, int[] _MoveCount){
        if (_board == null){
            board = new Piece[8][8];
            MakeBoard();
        }
        else{
            board = _board;
        }
        MoveCount = _MoveCount;
        Board = _Board;
    }
    public BoardState(Board _Board){
        this(null, _Board, new int[]{0,1});
    }

    public BoardState(Piece[][] _board, Board _Board){
        this(_board, _Board, new int[]{0,1});
    }

    public BoardState(Board _Board, int[] MoveCount){
        this(null, _Board, MoveCount);
    }

    public void LoadFEN(String FEN){
        String[] FENS = FEN.split(" ");
        MakeBoard(FENS[0]);

        if (Objects.equals(FENS[1], "w"))
            colour = Colour.WHITE;
        else
            colour = Colour.BLACK;

        if (At(4,7) instanceof King){
            At(4,7).moved = false;
            if (At(0,7) instanceof Rook && FENS[2].contains("Q")){
                At(0,7).moved = false;
            }if (At(7,7) instanceof Rook && FENS[2].contains("K")){
                At(7,7).moved = false;
            }
        }


        if (At(4,0) instanceof King){
            At(4,0).moved = false;
            if (At(0,0) instanceof Rook && FENS[2].contains("k")){
                At(0,0).moved = false;
            }if (At(7,0) instanceof Rook && FENS[2].contains("k")){
                At(7,0).moved = false;
            }
        }

        if (!Objects.equals(FENS[3], "-")){
            Coord c = Coord.FromFormat(FENS[3]);
            if (At(c.x(), c.y() + colour.reverse().direction()) instanceof Pawn){
                ((Pawn) At(c.x(), c.y() + colour.reverse().direction())).passant = true;
            }
        }

        MoveCount[0] = Integer.parseInt(FENS[4]);
        MoveCount[1] = Integer.parseInt(FENS[5]);
    }

    public void MakeBoard(){
        this.Add(Rook.class, 0, 0, Colour.BLACK);
        this.Add(Rook.class, 7,0, Colour.BLACK);
        this.Add(Rook.class, 0,7, Colour.WHITE);
        this.Add(Rook.class, 7,7, Colour.WHITE);


        this.Add(Knight.class, 1,0, Colour.BLACK);
        this.Add(Knight.class, 6,0, Colour.BLACK);
        this.Add(Knight.class, 1,7, Colour.WHITE);
        this.Add(Knight.class, 6,7, Colour.WHITE);


        this.Add(Bishop.class, 2,0, Colour.BLACK);
        this.Add(Bishop.class, 5,0, Colour.BLACK);
        this.Add(Bishop.class, 2,7, Colour.WHITE);
        this.Add(Bishop.class, 5,7, Colour.WHITE);


        this.Add(Queen.class, 3,0, Colour.BLACK);
        this.Add(Queen.class, 3,7, Colour.WHITE);


        this.Add(King.class, 4,0, Colour.BLACK);
        this.Add(King.class, 4,7, Colour.WHITE);

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (y == 1){
                    this.Add(Pawn.class, x,y, Colour.BLACK);
                }
                if (y == 6){
                    this.Add(Pawn.class, x,y, Colour.WHITE);
                }
                if (this.At(x,y) == null){
                    this.Add(Empty.class, x,y, Colour.WHITE);
                }
            }
        }

    }

    public void MakeBoard(String fen){
        int x = 0;
        int y = 0;

        for (String s : fen.split("/")){
            System.out.println(s);
            x=0;
            for (char c : s.toCharArray()){
                Colour piececolour = c == Character.toLowerCase(c) ? Colour.BLACK : Colour.WHITE;
                System.out.println(x);
                System.out.println(y);
                System.out.println(Character.toString(c));
                System.out.println();
                switch (Character.toLowerCase(c)){
                    case 'r':
                        Add(Rook.class, x, y, piececolour);
                        At(x,y).moved = true;
                        x++;
                        break;
                    case 'n':
                        Add(Knight.class, x, y, piececolour);
                        At(x,y).moved = true;
                        x++;
                        break;
                    case 'b':
                        Add(Bishop.class, x, y, piececolour);
                        At(x,y).moved = true;
                        x++;
                        break;
                    case 'k':
                        Add(King.class, x, y, piececolour);
                        At(x,y).moved = true;
                        x++;
                        break;
                    case 'q':
                        Add(Queen.class, x, y, piececolour);
                        At(x,y).moved = true;
                        x++;
                        break;
                    case 'p':
                        Add(Pawn.class, x, y, piececolour);
                        if ((piececolour == Colour.BLACK && y == 1) || (piececolour == Colour.WHITE && y == 6))
                            At(x,y).moved = false;
                        else
                            At(x,y).moved = true;
                        x++;
                        break;
                    default:
                        int n = c - '0';
                        if (!(n >= 1 && n <= 8))
                            throw new RuntimeException("invalid FEN");
                        for (int i = 0; i < n; i++) {
                            Add(Empty.class, x, y, Colour.WHITE);
                            x++;
                        }
                }
            }
            y++;
        }
    }

    public <T extends Piece> void Add(Class<T> pieceClass, int x, int y, Colour colour) {
        try {
            Constructor<T> constructor = pieceClass.getConstructor(Coord.class, Colour.class, BoardState.class);
            Coord pos = new Coord(x, y);
            this.board[y][x] = constructor.newInstance(pos, colour, this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Piece At(int x, int y){
        return board[y][x];
    }

    public Piece At(Coord coord){
        return At(coord.x(), coord.y());
    }

    public String print(){
        StringBuilder s = new StringBuilder();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                s.append(board[y][x].GetSymbol());
            }
            if (y != 7)
                s.append("\n");
        }
        return s.toString();
//        List<Move> moves = this.At(4,4).GetMoves();
//        System.out.println(this.At(4,4).GetSymbol());
//
//        for (Move move : moves) {
//            System.out.println(move.ToFormat());
//        }
//        GetMoves();
    }

    public static boolean InBounds(int x, int y) {
        return (x >= 0 && x < 8 && y >= 0 && y < 8);
    }
    
    public List<Move> GetMoves(boolean all){
        ArrayList<Move> moves = new ArrayList<>();
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (!(this.At(x,y) instanceof Empty) && (this.At(x,y).GetColour() == colour || all)){
//                    if (this.At(x, y).GetMoves().isEmpty()){
//                        System.out.println("No moves on " + this.At(x,y).position.ToFormat());
//                    }
                    moves.addAll(this.At(x, y).GetMoves());
                }
            }
        }
        return moves;
    }

    public List<Move> GetMoves(){
        return GetMoves(false);
    }

    public boolean Move(Move move, boolean clone){
        if (Board.winner != null)
                return false;
        if (MoveCount[0] >= 100){
            Board.winner = Colour.NONE;
            return true;
        }
        if (!clone){
            BoardState cloned = Clone();
            if (!cloned.Move(move.Clone(cloned), true)){
                return false;
            }
            if (cloned.CheckCheck(cloned.colour.reverse())){
                throw new RuntimeException("Check error");
            }
        }
        Move originalMove = move.Clone(this);

        if (!move.moving(). Move(move)){return false;}

        UpdateMove(move);
        while (move.extramove() != null && move.extramove() != move){
            move = move.extramove();
            if (!move.moving(). Move(move, true)){return false;}

            UpdateMove(move);
        }
        MoveCount[0]++;
        if (originalMove.capture() != null || originalMove.moving() instanceof Pawn){
            MoveCount[0] = 0;
        }
        if (move.moving().GetColour() == Colour.BLACK){
            MoveCount[1]++;
        }



        if (colour == Colour.WHITE){
            colour = Colour.BLACK;
        }
        else{
            colour = Colour.WHITE;
        }
        if (!clone && CheckCheck()){
            System.out.println("check");
            if (CheckCheckMate()){
                Board.winner = colour.reverse();
            }
        }
        return true;
    }

    public boolean Move(Move move){
        return Move(move, false);
    }

    public void UpdateMove(Move move){
        this.Add(Empty.class, move.start().x(), move.start().y(), Colour.WHITE);
        if (move.capture() != null){
            this.Add(Empty.class, move.capture().position.x(), move.capture().position.y(), Colour.WHITE);
        }
        board[move.end().y()][move.end().x()] = move.moving();
        if (move.extramove() != null && move.extramove() != move){
            UpdateMove(move.extramove());
        }
    }

    public BoardState Clone(){
        BoardState newboard = new BoardState(new Piece[8][8], Board, MoveCount);
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                newboard.board[y][x] = board[y][x].Copy(newboard);
            }
        }
        return newboard;
    }

    public boolean CheckCheck(Colour coloure){
        if (coloure == null){
            coloure = colour;
        }
        for (Move move : GetMoves(true)){
            if (move.capture() != null && move.capture().GetColour() == coloure && move.capture() instanceof King){
                return true;
            }
        }
        return false;
    }

    public boolean CheckCheck(){
        return CheckCheck(null);
    }

    public boolean CheckCheckMate(){
        BoardState cloned;
        for (Move move : GetMoves()){
            cloned = Clone();
            if (!cloned.Move(move.Clone(cloned), true)){
                throw new RuntimeException("Move failed in checkmate check");
            }
            if (!cloned.CheckCheck()){
                return false;
            }
        }
        return true;
    }

}
