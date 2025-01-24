package chess.piecemovecalculator;

import chess.*;
import java.util.ArrayList;

public class RookMoveCalculator implements PieceMoveCalculator {

    public ArrayList<ChessMove> possibleMoves(ChessBoard chessBoard, ChessPosition currentPosition, ChessGame.TeamColor teamColor) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        //Utilize method from PieceMoveCalculator
        calculateRookMoves(chessBoard, currentPosition, teamColor, moves);

        return moves;
    }
}


