package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor teamTurn;
    private ChessBoard board;

    public ChessGame() {
        this.board = new ChessBoard();
        this.board.resetBoard();
        this.teamTurn = TeamColor.WHITE;

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return this.teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ArrayList<ChessMove> possibleMoves = this.board.getPiece(startPosition).pieceMoves(this.board, startPosition);
        ArrayList<ChessMove> validMoves = new ArrayList<>();
        ChessPiece piece = board.getPiece(startPosition);
        for (ChessMove move : possibleMoves){
            if (isMoveOkay(piece, startPosition, move)){
                validMoves.add(move);
            }
        }
        return validMoves;
    }

    private boolean isMoveOkay(ChessPiece piece, ChessPosition startPosition, ChessMove move){
        ChessPiece testPiece = board.getPiece(move.getEndPosition());   //test piece placeholder to check if move okay
        board.addPiece(startPosition, null); //remove OG piece
        board.addPiece(move.getEndPosition(), piece);   //place OG piece in spot being checked
        boolean isOkay = !isInCheck(piece.getTeamColor());  //returns true if not in check
        board.addPiece(startPosition, piece);   //places OG piece to starting spot
        board.addPiece(move.getEndPosition(), testPiece); //restores piece (or null) at endPosition to ensure OG board back

        return isOkay;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        //get king piece position
        ChessPosition kingPosition = null;
        for (int row = 1; row <= 8 && kingPosition == null; row++){
            for (int col = 1; col <= 8 && kingPosition == null; col++){
                ChessPiece currentPositionCheck = board.getPiece(new ChessPosition(row, col));
                if (currentPositionCheck == null) {
                    continue;
                }
                if (currentPositionCheck.getPieceType() == ChessPiece.PieceType.KING && currentPositionCheck.getTeamColor() == teamColor){
                    kingPosition = new ChessPosition(row, col);
                }
            }
        }
        //contrast possible moves of enemy with king location
        for (int row = 1; row <= 8; row++){
            for (int col = 1; col <= 8; col++){
                ChessPiece pieceCauseCheckChecker = board.getPiece(new ChessPosition(row, col));
                if (pieceCauseCheckChecker == null){
                    continue;
                }
                if (pieceCauseCheckChecker.getTeamColor() != teamColor){
                    ArrayList<ChessMove> possibleMoves = pieceCauseCheckChecker.pieceMoves(board, new ChessPosition(row, col));
                    for (ChessMove possibleMove : possibleMoves){
                        ChessPosition checkingPosition = possibleMove.getEndPosition();
                        if (checkingPosition.getRow() == kingPosition.getRow() && checkingPosition.getColumn() == kingPosition.getColumn()){
                            return true; //King in check
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }
}
