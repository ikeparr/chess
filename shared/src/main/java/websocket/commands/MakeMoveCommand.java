package websocket.commands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand {
    ChessMove move;

    public MakeMoveCommand(ChessMove move, String authToken, int gameID) {
        super(CommandType.MAKE_MOVE, authToken, gameID);
        this.move = move;
    }

    public ChessMove getMove() {
        return move;
    }
}
