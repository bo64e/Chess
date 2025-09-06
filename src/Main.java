import Display.Display;
import Game.Board;

public class Main{
    public static void main(String[] args){
        Board board = new Board();
        Display.setBoard(board);
        Display.display();


    }
}
