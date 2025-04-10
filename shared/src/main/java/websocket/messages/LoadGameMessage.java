package websocket.messages;

import chess.ChessGame;

public class LoadGameMessage extends ServerMessage {
    // public object game
    ChessGame game;

    //try object game
    public LoadGameMessage(ChessGame game) {
        super(ServerMessageType.LOAD_GAME);
        this.game = game;
    }

    public ChessGame getGame() {
        return game;
    }
}

