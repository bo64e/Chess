package Display;

import Game.Board;
import Game.Pieces.*;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Scanner;

public class Display extends Application {
    private static Board board;
    private static final int BOARD_SIZE = 8;
    private static final int SQUARE_SIZE = 90;
    private static final double DEFAULT_MOVE_DURATION = 500; // milliseconds

    private Pane gamePane;
    private Image whitePawn, whiteRook, whiteKnight, whiteBishop, whiteQueen, whiteKing;
    private Image blackPawn, blackRook, blackKnight, blackBishop, blackQueen, blackKing;
    private GridPane chessBoard;
    private StackPane[][] squares = new StackPane[BOARD_SIZE][BOARD_SIZE];
    private double mouseX, mouseY;
    private ImageView draggedPiece;
    private boolean isAnimating = false; // Prevent moves during animation
    private MouseEvent prevMouse;

    @Override
    public void start(Stage primaryStage) {
        // Use a Pane to allow free positioning during drag
        gamePane = new Pane();
        chessBoard = new GridPane();
        loadPieceImages();
        // Create the board squares
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                StackPane square = new StackPane();
                squares[row][col] = square;

                // Create the background rectangle
                Rectangle background = new Rectangle(SQUARE_SIZE, SQUARE_SIZE);
                if ((row + col) % 2 == 0) {
                    background.setFill(Color.web("#EEEED2"));
                } else {
                    background.setFill(Color.web("#769656"));
                }
                background.setStroke(null);
                square.getChildren().add(background);
                chessBoard.add(square, col, row);
            }
        }

        gamePane.getChildren().add(chessBoard);

        // Add draggable pieces
        addDraggablePieces();

        Scene scene = new Scene(gamePane, BOARD_SIZE * SQUARE_SIZE, BOARD_SIZE * SQUARE_SIZE);
        primaryStage.setTitle("Chess Board");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    private void addDraggablePieces() {
        // Add pieces to their starting positions
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                ImageView piece = getPieceFromBoard(row, col);
                if (piece != null) {
                    placePieceOnSquare(piece, row, col);
                    makePieceDraggable(piece);
                }
            }
        }
    }

    private void loadPieceImages() {
        try {
            // Load white pieces (w_ prefix)
            whitePawn = new Image(getClass().getResourceAsStream("/assets/w_pawn.png"));
            whiteRook = new Image(getClass().getResourceAsStream("/assets/w_rook.png"));
            whiteKnight = new Image(getClass().getResourceAsStream("/assets/w_knight.png"));
            whiteBishop = new Image(getClass().getResourceAsStream("/assets/w_bishop.png"));
            whiteQueen = new Image(getClass().getResourceAsStream("/assets/w_queen.png"));
            whiteKing = new Image(getClass().getResourceAsStream("/assets/w_king.png"));

            // Load black pieces (b_ prefix)
            blackPawn = new Image(getClass().getResourceAsStream("/assets/b_pawn.png"));
            blackRook = new Image(getClass().getResourceAsStream("/assets/b_rook.png"));
            blackKnight = new Image(getClass().getResourceAsStream("/assets/b_knight.png"));
            blackBishop = new Image(getClass().getResourceAsStream("/assets/b_bishop.png"));
            blackQueen = new Image(getClass().getResourceAsStream("/assets/b_queen.png"));
            blackKing = new Image(getClass().getResourceAsStream("/assets/b_king.png"));

        } catch (Exception e) {
            System.err.println("Error loading piece images: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private ImageView getPieceFromBoard(int row, int col) {
        Piece piece = board.board.At(col, row);
        ImageView imageView = null;
        Image pieceImage = null;
        if (piece instanceof Pawn)
            pieceImage = piece.GetColour() == Colour.WHITE ? whitePawn : blackPawn;
        else if (piece instanceof Rook)
            pieceImage = piece.GetColour() == Colour.WHITE ? whiteRook : blackRook;
        else if (piece instanceof Knight)
            pieceImage = piece.GetColour() == Colour.WHITE ? whiteKnight : blackKnight;
        else if (piece instanceof Bishop)
            pieceImage = piece.GetColour() == Colour.WHITE ? whiteBishop : blackBishop;
        else if (piece instanceof Queen)
            pieceImage = piece.GetColour() == Colour.WHITE ? whiteQueen : blackQueen;
        else if (piece instanceof King)
            pieceImage = piece.GetColour() == Colour.WHITE ? whiteKing : blackKing;
        else
            System.out.println(piece.getClass());


        if (pieceImage != null) {
            imageView = new ImageView(pieceImage);
            imageView.setFitWidth(SQUARE_SIZE);
            imageView.setFitHeight(SQUARE_SIZE);
            imageView.setPreserveRatio(true);
        }

        return imageView;
    }

    private void placePieceOnSquare(ImageView piece, int row, int col) {
        // Calculate the center position of the square
        double y = row * SQUARE_SIZE;
        double x = col * SQUARE_SIZE;

        piece.setLayoutX(x);
        piece.setLayoutY(y);

        // Store the grid position in the piece's properties
        piece.getProperties().put("gridRow", row);
        piece.getProperties().put("gridCol", col);

        // Only add to gamePane if it's not already there
        if (!gamePane.getChildren().contains(piece)) {
            gamePane.getChildren().add(piece);
        }
    }

    public boolean smoothMovePiece(int x1, int y1, int x2, int y2, double durationMillis) {

        ImageView piece = At(x1, y1);

        if (piece == null || isAnimating) return false;

        isAnimating = true;

        // Get current position
        int fromRow = (int) piece.getProperties().get("gridRow");
        int fromCol = (int) piece.getProperties().get("gridCol");

        // Calculate target position
        double targetX = x2 * SQUARE_SIZE;
        double targetY = y2 * SQUARE_SIZE;

        // Calculate the translation needed
        double deltaX = targetX - piece.getLayoutX();
        double deltaY = targetY - piece.getLayoutY();

        // Check if there's a piece at the destination to capture
        ImageView targetPiece = findPieceAtSquare(y2, x2);

        // Create the animation
        TranslateTransition transition = new TranslateTransition(Duration.millis(durationMillis), piece);
        transition.setByX(deltaX);
        transition.setByY(deltaY);

        // Bring piece to front during animation
        piece.toFront();

        transition.setOnFinished(event -> {
            // Reset the translation and update the layout position
            piece.setTranslateX(0);
            piece.setTranslateY(0);
            piece.setLayoutX(targetX);
            piece.setLayoutY(targetY);

            // Update grid properties
            piece.getProperties().put("gridRow", y2);
            piece.getProperties().put("gridCol", x2);

            // Remove captured piece if any
            if (targetPiece != null && targetPiece != piece) {
                gamePane.getChildren().remove(targetPiece);
            }

            isAnimating = false;
        });

        transition.play();
        return true;
    }

    private ImageView At(int x, int y) {
        for (Node node : gamePane.getChildren()) {
            if (node instanceof ImageView piece && node != chessBoard) {
                Integer pieceRow = (Integer) piece.getProperties().get("gridRow");
                Integer pieceCol = (Integer) piece.getProperties().get("gridCol");

                if (pieceRow != null && pieceCol != null &&
                        pieceRow == y && pieceCol == x) {
                    return piece;
                }
            }
        }
        return null;
    }

    public void smoothMovePiece(int x1, int y1, int x2, int y2) {
        smoothMovePiece(x1, y1, x2, y2, DEFAULT_MOVE_DURATION);
    }

    private void makePieceDraggable(ImageView piece) {
        piece.setOnMousePressed(this::handleMousePressed);
        piece.setOnMouseDragged(this::handleMouseDragged);
        piece.setOnMouseReleased(this::handleMouseReleased);
    }

    private void handleMousePressed(MouseEvent event) {
        if (isAnimating) {
            event.consume();
            return;
        }
        smoothMovePiece(0,0,7,7,1000);
        draggedPiece = (ImageView) event.getSource();


        // Store the offset from the mouse to the piece's center
        mouseX = event.getSceneX() - draggedPiece.getLayoutX();
        mouseY = event.getSceneY() - draggedPiece.getLayoutY();

        StackPane square = squares[(int) Math.floor(event.getSceneY() / SQUARE_SIZE)][(int) Math.floor(event.getSceneX() / SQUARE_SIZE)];
        for (Node node : square.getChildren()){
            if (node instanceof Rectangle rect){
                if (rect.getFill().equals(Color.web("#EEEED2"))){
                    rect.setFill(Color.web("#F7F769"));
                }
                else if (rect.getFill().equals(Color.web("#769656"))){
                    rect.setFill(Color.web("#BBCB2B"));
                }
            }
        }

        // Bring the piece to the front
        draggedPiece.toFront();
        prevMouse = event;

        event.consume();
    }

    private void handleMouseDragged(MouseEvent event) {
        if (draggedPiece != null && !isAnimating) {
            // Move the piece with the mouse, maintaining the offset
            draggedPiece.setLayoutX(event.getSceneX() - mouseX);
            draggedPiece.setLayoutY(event.getSceneY() - mouseY);
        }
        event.consume();
    }

    private void handleMouseReleased(MouseEvent event) {
        // Calculate which square the piece should snap to
        int newCol = (int) (event.getSceneX() / SQUARE_SIZE);
        int newRow = (int) (event.getSceneY() / SQUARE_SIZE);
        int originalRow = (int) draggedPiece.getProperties().get("gridRow");
        int originalCol = (int) draggedPiece.getProperties().get("gridCol");
        ImageView existingPiece = findPieceAtSquare(newRow, newCol);


        if (draggedPiece != null && !isAnimating) {

            StackPane square = squares[(int)draggedPiece.getProperties().get("gridRow")][(int)draggedPiece.getProperties().get("gridCol")];
            for (Node node : square.getChildren()){
                if (node instanceof Rectangle rect){
                    if (rect.getFill().equals(Color.web("#F7F769"))){
                        rect.setFill(Color.web("#EEEED2"));
                    }
                    else if (rect.getFill().equals(Color.web("#BBCB2B"))){
                        rect.setFill(Color.web("#769656"));
                    }
                }
            }
            System.out.println(new Coord(originalCol, originalRow).ToFormat());
            System.out.println(new Coord(newCol, newRow).ToFormat());
            // Check if the drop position is within the board
            if (newRow >= 0 && newRow < BOARD_SIZE && newCol >= 0 && newCol < BOARD_SIZE &&
                    board.Move(new Coord(originalCol, originalRow), new Coord(newCol, newRow)))
            {

                // Check if there's already a piece on the target square
                if (existingPiece != null && existingPiece != draggedPiece) {
                    // Remove the existing piece (capture)
                    gamePane.getChildren().remove(existingPiece);
                }

                // Snap the piece to the new square
                placePieceOnSquare(draggedPiece, newRow, newCol);

            } else {
                // If dropped outside the board, return to original position
                placePieceOnSquare(draggedPiece, originalRow, originalCol);
            }
        }
        draggedPiece = null;
        event.consume();
    }

    private ImageView findPieceAtSquare(int row, int col) {
        for (javafx.scene.Node node : gamePane.getChildren()) {
            if (node instanceof ImageView piece && node != chessBoard) {
                Integer pieceRow = (Integer) piece.getProperties().get("gridRow");
                Integer pieceCol = (Integer) piece.getProperties().get("gridCol");

                if (pieceRow != null && pieceCol != null &&
                        pieceRow == row && pieceCol == col) {
                    return piece;
                }
            }
        }
        return null;
    }
    public static void setBoard(Board board){
        Display.board = board;
    }
    public static void display(){
        Application.launch(Display.class);
    }
}