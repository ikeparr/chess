package chess.piecemovecalculator;

import java.util.ArrayList;
import chess.*;

public interface PieceMoveCalculator {
    public ArrayList<ChessMove> possibleMoves(ChessBoard chessBoard, ChessPosition currentPosition, ChessGame.TeamColor teamColor);

    //CHECK IF IN BOUNDS
    public static boolean moveInBounds(int row, int column) {

        if (row < 1 || column < 1) {
            return false;
        }
        if (row > 8 || column > 8) {
            return false;
        }
        return true;
    }

    //IF SQUARE IS ELIGIBLE TO MOVE TO (not applicable for pawn)
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

    //PAWN-ONLY SQUARE LOGIC (cant move directly up or down to capture)
    public static boolean isFreeSquare(ChessBoard board, int row, int column) {
        ChessPiece piece = board.squares[row - 1][column - 1];
        if (piece == null) {
            return true;
        } else {
            return false;
        }
    }

    default void calculateRookMoves(ChessBoard chessBoard, ChessPosition currentPosition, ChessGame.TeamColor teamColor, ArrayList<ChessMove> moves) {
        int startRow = currentPosition.getRow();
        int startColumn = currentPosition.getColumn();

        //UP
        for (int i = 1; i < 8; i++) {
            if (PieceMoveCalculator.moveInBounds(startRow + i, startColumn)) {
                if (PieceMoveCalculator.isValidSquare(chessBoard, startRow + i, startColumn, teamColor)) {
                    ChessPosition newPosition = new ChessPosition(startRow + i, startColumn);
                    moves.add(new ChessMove(currentPosition, newPosition, null));

                    if (PieceMoveCalculator.isOpponentSquare(chessBoard, startRow + i, startColumn, teamColor)) {
                        break;
                    }
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        //DOWN
        for (int i = 1; i < 8; i++) {
            if (PieceMoveCalculator.moveInBounds(startRow - i, startColumn)) {
                if (PieceMoveCalculator.isValidSquare(chessBoard, startRow - i, startColumn, teamColor)) {
                    ChessPosition newPosition = new ChessPosition(startRow - i, startColumn);
                    moves.add(new ChessMove(currentPosition, newPosition, null));

                    if (PieceMoveCalculator.isOpponentSquare(chessBoard, startRow - i, startColumn, teamColor)) {
                        break;
                    }
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        //LEFT
        for (int i = 1; i < 8; i++) {
            if (PieceMoveCalculator.moveInBounds(startRow, startColumn - i)) {
                if (PieceMoveCalculator.isValidSquare(chessBoard, startRow, startColumn - i, teamColor)) {
                    ChessPosition newPosition = new ChessPosition(startRow, startColumn - i);
                    moves.add(new ChessMove(currentPosition, newPosition, null));

                    if (PieceMoveCalculator.isOpponentSquare(chessBoard, startRow, startColumn - i, teamColor)) {
                        break;
                    }
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        //RIGHT
        for (int i = 1; i < 8; i++) {
            if (PieceMoveCalculator.moveInBounds(startRow, startColumn + i)) {
                if (PieceMoveCalculator.isValidSquare(chessBoard, startRow, startColumn + i, teamColor)) {
                    ChessPosition newPosition = new ChessPosition(startRow, startColumn + i);
                    moves.add(new ChessMove(currentPosition, newPosition, null));

                    if (PieceMoveCalculator.isOpponentSquare(chessBoard, startRow, startColumn + i, teamColor)) {
                        break;
                    }
                } else {
                    break;
                }
            } else {
                break;
            }
        }
    }

    default void calculateBishopMoves(ChessBoard chessBoard, ChessPosition currentPosition, ChessGame.TeamColor teamColor, ArrayList<ChessMove> moves) {
        int startRow = currentPosition.getRow();
        int startColumn = currentPosition.getColumn();

        //UpRIGHT
        for (int i = 1; i < 8; i++) {
            if (PieceMoveCalculator.moveInBounds(startRow + i, startColumn + i)) {
                if (PieceMoveCalculator.isValidSquare(chessBoard, startRow + i, startColumn + i, teamColor)) {
                    ChessPosition newPosition = new ChessPosition(startRow + i, startColumn + i);
                    moves.add(new ChessMove(currentPosition, newPosition, null));

                    if (PieceMoveCalculator.isOpponentSquare(chessBoard, startRow + i, startColumn + i, teamColor)) {
                        break;
                    }
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        //UpLEFT
        for (int i = 1; i < 8; i++) {
            if (PieceMoveCalculator.moveInBounds(startRow + i, startColumn - i)) {
                if (PieceMoveCalculator.isValidSquare(chessBoard, startRow + i, startColumn - i, teamColor)) {
                    ChessPosition newPosition = new ChessPosition(startRow + i, startColumn - i);
                    moves.add(new ChessMove(currentPosition, newPosition, null));

                    if (PieceMoveCalculator.isOpponentSquare(chessBoard, startRow + i, startColumn - i, teamColor)) {
                        break;
                    }
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        //DownLEFT
        for (int i = 1; i < 8; i++) {
            if (PieceMoveCalculator.moveInBounds(startRow - i, startColumn - i)) {
                if (PieceMoveCalculator.isValidSquare(chessBoard, startRow - i, startColumn - i, teamColor)) {
                    ChessPosition newPosition = new ChessPosition(startRow - i, startColumn - i);
                    moves.add(new ChessMove(currentPosition, newPosition, null));

                    if (PieceMoveCalculator.isOpponentSquare(chessBoard, startRow - i, startColumn - i, teamColor)) {
                        break;
                    }
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        //DownRIGHT
        for (int i = 1; i < 8; i++) {
            if (PieceMoveCalculator.moveInBounds(startRow - i, startColumn + i)) {
                if (PieceMoveCalculator.isValidSquare(chessBoard, startRow - i, startColumn + i, teamColor)) {
                    ChessPosition newPosition = new ChessPosition(startRow - i, startColumn + i);
                    moves.add(new ChessMove(currentPosition, newPosition, null));

                    if (PieceMoveCalculator.isOpponentSquare(chessBoard, startRow - i, startColumn + i, teamColor)) {
                        break;
                    }
                } else {
                    break;
                }
            } else {
                break;
            }
        }
    }
}
