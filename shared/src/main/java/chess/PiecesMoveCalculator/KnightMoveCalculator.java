package chess.PiecesMoveCalculator;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public class KnightMoveCalculator implements PieceMoveCalculator {
    public ArrayList<ChessMove> possibleMoves(ChessBoard chessBoard, ChessPosition currentPosition, ChessGame.TeamColor teamColor) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        int startRow = currentPosition.getRow();
        int startColumn = currentPosition.getColumn();

        //NorthEast
        if (PieceMoveCalculator.moveInBounds(startRow + 2, startColumn + 1)) {
            if (PieceMoveCalculator.isValidSquare(chessBoard, startRow + 2, startColumn + 1, teamColor)) {
                ChessPosition newPosition = new ChessPosition(startRow + 2, startColumn + 1);
                moves.add(new ChessMove(currentPosition, newPosition, null));
            }
        }


        //NorthWest
        if (PieceMoveCalculator.moveInBounds(startRow + 2, startColumn - 1)) {
            if (PieceMoveCalculator.isValidSquare(chessBoard, startRow + 2, startColumn - 1, teamColor)) {
                ChessPosition newPosition = new ChessPosition(startRow + 2, startColumn - 1);
                moves.add(new ChessMove(currentPosition, newPosition, null));
            }
        }


        //EastNorth
        if (PieceMoveCalculator.moveInBounds(startRow + 1, startColumn + 2)) {
            if (PieceMoveCalculator.isValidSquare(chessBoard, startRow + 1, startColumn + 2, teamColor)) {
                ChessPosition newPosition = new ChessPosition(startRow + 1, startColumn + 2);
                moves.add(new ChessMove(currentPosition, newPosition, null));
            }
        }


        //EastSouth
        if (PieceMoveCalculator.moveInBounds(startRow - 1, startColumn + 2)) {
            if (PieceMoveCalculator.isValidSquare(chessBoard, startRow - 1, startColumn + 2, teamColor)) {
                ChessPosition newPosition = new ChessPosition(startRow - 1, startColumn + 2);
                moves.add(new ChessMove(currentPosition, newPosition, null));
            }
        }


        //WestNorth
        if (PieceMoveCalculator.moveInBounds(startRow + 1, startColumn - 2)) {
            if (PieceMoveCalculator.isValidSquare(chessBoard, startRow + 1, startColumn - 2, teamColor)) {
                ChessPosition newPosition = new ChessPosition(startRow + 1, startColumn - 2);
                moves.add(new ChessMove(currentPosition, newPosition, null));
            }
        }


        //WestSouth
        if (PieceMoveCalculator.moveInBounds(startRow - 1, startColumn - 2)) {
            if (PieceMoveCalculator.isValidSquare(chessBoard, startRow - 1, startColumn - 2, teamColor)) {
                ChessPosition newPosition = new ChessPosition(startRow - 1, startColumn - 2);
                moves.add(new ChessMove(currentPosition, newPosition, null));
            }
        }


        //SouthWest
        if (PieceMoveCalculator.moveInBounds(startRow - 2, startColumn - 1)) {
            if (PieceMoveCalculator.isValidSquare(chessBoard, startRow - 2, startColumn - 1, teamColor)) {
                ChessPosition newPosition = new ChessPosition(startRow - 2, startColumn - 1);
                moves.add(new ChessMove(currentPosition, newPosition, null));
            }
        }


        //SouthEast
        if (PieceMoveCalculator.moveInBounds(startRow - 2, startColumn + 1)) {
            if (PieceMoveCalculator.isValidSquare(chessBoard, startRow - 2, startColumn + 1, teamColor)) {
                ChessPosition newPosition = new ChessPosition(startRow - 2, startColumn + 1);
                moves.add(new ChessMove(currentPosition, newPosition, null));
            }
        }
        return moves;
    }
}

