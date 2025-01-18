package chess.PiecesMoveCalculator;

import java.util.ArrayList;
import chess.*;

public interface PieceMoveCalculator {
    public ArrayList<ChessMove> possibleMoves(ChessBoard chessBoard, ChessPosition currentPosition, ChessGame.TeamColor teamColor);

        //Check if move in bounds (CAN IMPROVE)
    public static boolean moveInBounds(int row, int column) {

        if (row < 1 || column < 1) {
            return false;
        }
        if (row > 8 || column > 8) {
            return false;
        }
        return true;
    }
    //If the square is able to move to
    public static boolean isValidSquare(ChessBoard board, int row, int column, ChessGame.TeamColor teamColor) {
        // MINUS 1 FOR 0 INDEX FORM
        ChessPiece piece = board.squares[row - 1][column - 1];
        //empty square
        if (piece == null) {
            return true;
        }
        ChessGame.TeamColor pieceColor = piece.getTeamColor();
        //is friend or foe (check team color)
        if (pieceColor != teamColor){
            return true;
        }
        else {
            return false;
        }
    }

    //HAD TO IMPLEMENT SO I COULD BREAK ONCE HIT PIECE, WHETHER CAPTURED OR NOT
    public static boolean isOpponentSquare(ChessBoard board, int row, int column, ChessGame.TeamColor teamColor) {
        ChessPiece piece = board.squares[row - 1][column - 1];
        if (piece == null) {
            return false;
        }
        if (piece.getTeamColor() != teamColor){
            return true;
        }
        else {
            return false;
        }
    }
}
