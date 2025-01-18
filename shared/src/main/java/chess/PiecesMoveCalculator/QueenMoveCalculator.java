package chess.PiecesMoveCalculator;

import chess.*;
import java.util.ArrayList;

public class QueenMoveCalculator implements PieceMoveCalculator {

    public ArrayList<ChessMove> possibleMoves(ChessBoard chessBoard, ChessPosition currentPosition, ChessGame.TeamColor teamColor) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        int startRow = currentPosition.getRow();
        int startColumn = currentPosition.getColumn();

        //Up
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

        //Down
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

        //Left
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

        //Right
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

        return moves;
    }
}
