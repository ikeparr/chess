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

        //NorthEAST
        if (PieceMoveCalculator.moveInBounds(startRow + 2, startColumn + 1)) {
            if (PieceMoveCalculator.isValidSquare(chessBoard, startRow + 2, startColumn + 1, teamColor)) {
                ChessPosition newPosition = new ChessPosition(startRow + 2, startColumn + 1);
                moves.add(new ChessMove(currentPosition, newPosition, null));
            }
        }

        //NorthWEST
        if (PieceMoveCalculator.moveInBounds(startRow + 2, startColumn - 1)) {
            if (PieceMoveCalculator.isValidSquare(chessBoard, startRow + 2, startColumn - 1, teamColor)) {
                ChessPosition newPosition = new ChessPosition(startRow + 2, startColumn - 1);
                moves.add(new ChessMove(currentPosition, newPosition, null));
            }
        }

        //EastNORTH
        if (PieceMoveCalculator.moveInBounds(startRow + 1, startColumn + 2)) {
            if (PieceMoveCalculator.isValidSquare(chessBoard, startRow + 1, startColumn + 2, teamColor)) {
                ChessPosition newPosition = new ChessPosition(startRow + 1, startColumn + 2);
                moves.add(new ChessMove(currentPosition, newPosition, null));
            }
        }

        //EastSOUTH
        if (PieceMoveCalculator.moveInBounds(startRow - 1, startColumn + 2)) {
            if (PieceMoveCalculator.isValidSquare(chessBoard, startRow - 1, startColumn + 2, teamColor)) {
                ChessPosition newPosition = new ChessPosition(startRow - 1, startColumn + 2);
                moves.add(new ChessMove(currentPosition, newPosition, null));
            }
        }

        //WestNORTH
        if (PieceMoveCalculator.moveInBounds(startRow + 1, startColumn - 2)) {
            if (PieceMoveCalculator.isValidSquare(chessBoard, startRow + 1, startColumn - 2, teamColor)) {
                ChessPosition newPosition = new ChessPosition(startRow + 1, startColumn - 2);
                moves.add(new ChessMove(currentPosition, newPosition, null));
            }
        }

        //WestSOUTH
        if (PieceMoveCalculator.moveInBounds(startRow - 1, startColumn - 2)) {
            if (PieceMoveCalculator.isValidSquare(chessBoard, startRow - 1, startColumn - 2, teamColor)) {
                ChessPosition newPosition = new ChessPosition(startRow - 1, startColumn - 2);
                moves.add(new ChessMove(currentPosition, newPosition, null));
            }
        }

        //SouthWEST
        if (PieceMoveCalculator.moveInBounds(startRow - 2, startColumn - 1)) {
            if (PieceMoveCalculator.isValidSquare(chessBoard, startRow - 2, startColumn - 1, teamColor)) {
                ChessPosition newPosition = new ChessPosition(startRow - 2, startColumn - 1);
                moves.add(new ChessMove(currentPosition, newPosition, null));
            }
        }

        //SouthEAST
        if (PieceMoveCalculator.moveInBounds(startRow - 2, startColumn + 1)) {
            if (PieceMoveCalculator.isValidSquare(chessBoard, startRow - 2, startColumn + 1, teamColor)) {
                ChessPosition newPosition = new ChessPosition(startRow - 2, startColumn + 1);
                moves.add(new ChessMove(currentPosition, newPosition, null));
            }
        }
        return moves;
    }
}

