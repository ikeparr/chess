package chess.PiecesMoveCalculator;

import chess.*;
import java.util.ArrayList;

public class BishopMoveCalculator implements PieceMoveCalculator {

    public ArrayList<ChessMove> possibleMoves(ChessBoard chessBoard, ChessPosition currentPosition, ChessGame.TeamColor teamColor) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        int startRow = currentPosition.getRow();
        int startColumn = currentPosition.getColumn();

        //Need for loop check UL UR DL DR, will use for Q, R, & B. Each is for loop.
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

        //UpLeft
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

        //DownLeft
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

        //DownRight
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

        return moves;
    }
}
