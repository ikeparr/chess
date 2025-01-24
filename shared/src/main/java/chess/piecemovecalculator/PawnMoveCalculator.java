package chess.piecemovecalculator;
import chess.*;
import java.util.ArrayList;

public class PawnMoveCalculator implements PieceMoveCalculator {
    public ArrayList<ChessMove> possibleMoves(ChessBoard chessBoard,
                                              ChessPosition currentPosition, ChessGame.TeamColor teamColor) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int startRow = currentPosition.getRow();
        int startColumn = currentPosition.getColumn();
        if (teamColor == ChessGame.TeamColor.WHITE) {
            //CHECK IF FIRST MOVE
            if (startRow == 2) {
                if (PieceMoveCalculator.moveInBounds(startRow + 1, startColumn)
                        && PieceMoveCalculator.isFreeSquare(chessBoard, startRow + 1, startColumn)) {
                    ChessPosition newPosition = new ChessPosition(startRow + 1, startColumn);
                    moves.add(new ChessMove(currentPosition, newPosition, null));
                    if (PieceMoveCalculator.moveInBounds(startRow + 2, startColumn)
                            && PieceMoveCalculator.isFreeSquare(chessBoard, startRow + 2, startColumn)) {
                        ChessPosition doubleNewPosition = new ChessPosition(startRow + 2, startColumn);
                        moves.add(new ChessMove(currentPosition, doubleNewPosition, null));
                    }
                }
            }
            //ALL OTHER STARTING ROWS
            else {
                if (PieceMoveCalculator.moveInBounds(startRow + 1, startColumn)
                        && PieceMoveCalculator.isFreeSquare(chessBoard, startRow + 1, startColumn)) {
                    if (startRow + 1 == 8) {
                        ChessPosition newPosition = new ChessPosition(startRow + 1, startColumn);
                        addPromotionPiece(moves, currentPosition, newPosition);
                    } else { ChessPosition newPosition = new ChessPosition(startRow + 1, startColumn);
                        moves.add(new ChessMove(currentPosition, newPosition, null));
                    }
                }
            }
            //LEFT-UP DIAGONAL ATTACK
            if (PieceMoveCalculator.moveInBounds(startRow + 1, startColumn - 1)
                    && PieceMoveCalculator.isOpponentSquare(chessBoard, startRow + 1, startColumn - 1, teamColor)) {
                if (startRow + 1 == 8) {
                    ChessPosition newPosition = new ChessPosition(startRow + 1, startColumn - 1);
                    addPromotionPiece(moves, currentPosition, newPosition);
                } else { ChessPosition newPosition = new ChessPosition(startRow + 1, startColumn - 1);
                    moves.add(new ChessMove(currentPosition, newPosition, null));
                }
            }
            //RIGHT-UP DIAGONAL ATTACK
            if (PieceMoveCalculator.moveInBounds(startRow + 1, startColumn + 1)
                    && PieceMoveCalculator.isOpponentSquare(chessBoard, startRow + 1, startColumn + 1, teamColor)) {
                if (startRow + 1 == 8) {
                    ChessPosition newPosition = new ChessPosition(startRow + 1, startColumn + 1);
                    addPromotionPiece(moves, currentPosition, newPosition);
                } else { ChessPosition newPosition = new ChessPosition(startRow + 1, startColumn + 1);
                    moves.add(new ChessMove(currentPosition, newPosition, null));
                }
            }
        }
        if (teamColor == ChessGame.TeamColor.BLACK) {
            //CHECK IF FIRST MOVE
            if (startRow == 7) {
                if (PieceMoveCalculator.moveInBounds(startRow - 1, startColumn)
                        && PieceMoveCalculator.isFreeSquare(chessBoard, startRow - 1, startColumn)) {
                    ChessPosition newPosition = new ChessPosition(startRow - 1, startColumn);
                    moves.add(new ChessMove(currentPosition, newPosition, null));
                    if (PieceMoveCalculator.moveInBounds(startRow - 2, startColumn)
                            && PieceMoveCalculator.isFreeSquare(chessBoard, startRow - 2, startColumn)) {
                        ChessPosition doubleNewPosition = new ChessPosition(startRow - 2, startColumn);
                        moves.add(new ChessMove(currentPosition, doubleNewPosition, null));
                    }
                }
            }
            //ALL OTHER STARTING ROWS
            else {
                if (PieceMoveCalculator.moveInBounds(startRow - 1, startColumn)
                        && PieceMoveCalculator.isFreeSquare(chessBoard, startRow - 1, startColumn)) {
                    if (startRow - 1 == 1) {
                        ChessPosition newPosition = new ChessPosition(startRow - 1, startColumn);
                        addPromotionPiece(moves, currentPosition, newPosition);
                    } else { ChessPosition newPosition = new ChessPosition(startRow - 1, startColumn);
                        moves.add(new ChessMove(currentPosition, newPosition, null));
                    }
                }
            }
            //LEFT-DOWN DIAGONAL ATTACK
            if (PieceMoveCalculator.moveInBounds(startRow - 1, startColumn - 1)
                    && PieceMoveCalculator.isOpponentSquare(chessBoard, startRow - 1, startColumn - 1, teamColor)) {
                if (startRow - 1 == 1) {
                    ChessPosition newPosition = new ChessPosition(startRow - 1, startColumn - 1);
                    addPromotionPiece(moves, currentPosition, newPosition);
                } else { ChessPosition newPosition = new ChessPosition(startRow - 1, startColumn - 1);
                    moves.add(new ChessMove(currentPosition, newPosition, null));
                }
            }
            //RIGHT DOWN DIAGONAL ATTACK
            if (PieceMoveCalculator.moveInBounds(startRow - 1, startColumn + 1)
                    && PieceMoveCalculator.isOpponentSquare(chessBoard, startRow - 1, startColumn + 1, teamColor)) {
                if (startRow - 1 == 1) {
                    ChessPosition newPosition = new ChessPosition(startRow - 1, startColumn + 1);
                    addPromotionPiece(moves, currentPosition, newPosition);
                } else { ChessPosition newPosition = new ChessPosition(startRow - 1, startColumn + 1);
                    moves.add(new ChessMove(currentPosition, newPosition, null));
                }
            }
        }
        return moves;
    }
}