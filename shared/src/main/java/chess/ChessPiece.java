package chess;

import chess.PiecesMoveCalculator.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor color;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.color = pieceColor;
        this.type = type;
    }


    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }


    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return color;
    }


    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }


    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        PieceMoveCalculator calculator = null;

        if (type == PieceType.BISHOP) {
            calculator = new BishopMoveCalculator();
        }
        if (type == PieceType.ROOK) {
            calculator = new RookMoveCalculator();
        }
        if (type == PieceType.QUEEN) {
            calculator = new QueenMoveCalculator();
        }
        if (type == PieceType.KING) {
            calculator = new KingMoveCalculator();
        }
        if (type == PieceType.KNIGHT) {
            calculator = new KnightMoveCalculator();
        }
        //Temp Place Holder
        if (calculator == null) {
            return new ArrayList<>();
        }
        ChessGame.TeamColor teamColor = this.getTeamColor();
        return calculator.possibleMoves(board, myPosition, teamColor);
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return color == that.color && type == that.type;
    }


    @Override
    public int hashCode() {
        return Objects.hash(color, type);
    }


    @Override
    public String toString() {
        return "ChessPiece{" +
                "color=" + color +
                ", type=" + type +
                '}';
    }
}
