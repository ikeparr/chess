package chess.PiecesMoveCalculator;

import chess.*;

import java.util.ArrayList;

public class KingMoveCalculator implements PieceMoveCalculator {
    public ArrayList<ChessMove> possibleMoves(ChessBoard chessBoard, ChessPosition currentPosition, ChessGame.TeamColor teamColor) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        int startRow = currentPosition.getRow();
        int startColumn = currentPosition.getColumn();

        //Up
            if (PieceMoveCalculator.moveInBounds(startRow + 1, startColumn)) {
                if (PieceMoveCalculator.isValidSquare(chessBoard, startRow + 1, startColumn, teamColor)) {
                    ChessPosition newPosition = new ChessPosition(startRow + 1, startColumn);
                    moves.add(new ChessMove(currentPosition, newPosition, null));
                }
            }


        //Down

            if (PieceMoveCalculator.moveInBounds(startRow - 1, startColumn)) {
                if (PieceMoveCalculator.isValidSquare(chessBoard, startRow - 1, startColumn, teamColor)) {
                    ChessPosition newPosition = new ChessPosition(startRow - 1, startColumn);
                    moves.add(new ChessMove(currentPosition, newPosition, null));
                }
            }


        //Left

            if (PieceMoveCalculator.moveInBounds(startRow, startColumn - 1)) {
                if (PieceMoveCalculator.isValidSquare(chessBoard, startRow, startColumn - 1, teamColor)) {
                    ChessPosition newPosition = new ChessPosition(startRow, startColumn - 1);
                    moves.add(new ChessMove(currentPosition, newPosition, null));
                }
            }


        //Right

            if (PieceMoveCalculator.moveInBounds(startRow, startColumn + 1)) {
                if (PieceMoveCalculator.isValidSquare(chessBoard, startRow, startColumn + 1, teamColor)) {
                    ChessPosition newPosition = new ChessPosition(startRow, startColumn + 1);
                    moves.add(new ChessMove(currentPosition, newPosition, null));
                }
            }


        //UpRIGHT

            if (PieceMoveCalculator.moveInBounds(startRow + 1, startColumn + 1)) {
                if (PieceMoveCalculator.isValidSquare(chessBoard, startRow + 1, startColumn + 1, teamColor)) {
                    ChessPosition newPosition = new ChessPosition(startRow + 1, startColumn + 1);
                    moves.add(new ChessMove(currentPosition, newPosition, null));
                }
            }


        //UpLEFT

            if (PieceMoveCalculator.moveInBounds(startRow + 1, startColumn - 1)) {
                if (PieceMoveCalculator.isValidSquare(chessBoard, startRow + 1, startColumn - 1, teamColor)) {
                    ChessPosition newPosition = new ChessPosition(startRow + 1, startColumn - 1);
                    moves.add(new ChessMove(currentPosition, newPosition, null));

                }
            }


        //DownLEFT

            if (PieceMoveCalculator.moveInBounds(startRow - 1, startColumn - 1)) {
                if (PieceMoveCalculator.isValidSquare(chessBoard, startRow - 1, startColumn - 1, teamColor)) {
                    ChessPosition newPosition = new ChessPosition(startRow - 1, startColumn - 1);
                    moves.add(new ChessMove(currentPosition, newPosition, null));

                }
            }


        //DownRIGHT

            if (PieceMoveCalculator.moveInBounds(startRow - 1, startColumn + 1)) {
                if (PieceMoveCalculator.isValidSquare(chessBoard, startRow - 1, startColumn + 1, teamColor)) {
                    ChessPosition newPosition = new ChessPosition(startRow - 1, startColumn + 1);
                    moves.add(new ChessMove(currentPosition, newPosition, null));

                }
            }


        return moves;
    }
}
