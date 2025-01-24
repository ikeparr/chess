package chess.piecemovecalculator;

import chess.*;
import java.util.ArrayList;

public class QueenMoveCalculator implements PieceMoveCalculator {

    public ArrayList<ChessMove> possibleMoves(ChessBoard chessBoard, ChessPosition currentPosition, ChessGame.TeamColor teamColor) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        //Utilize rook and bishop method from PieceMoveCalculator
        calculateBishopMoves(chessBoard, currentPosition, teamColor, moves);
        calculateRookMoves(chessBoard, currentPosition, teamColor, moves);

        return moves;
    }
}
