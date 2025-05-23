package websocket;

import chess.*;
import com.google.gson.Gson;
import exceptions.ErrorResponse;
import exceptions.ResponseException;
import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.*;


import java.io.IOException;

@WebSocket
public class WebSocketHandler {

    private final ConnectionManager connections = new ConnectionManager();
    private UserDAO userDAO = new SQLUser();
    private GameDAO gameDAO = new SQLGame();
    private AuthDAO authDAO = new SQLAuth();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        MakeMoveCommand moveCommand = new Gson().fromJson(message, MakeMoveCommand.class);
        switch (command.getCommandType()) {
            case CONNECT -> connect(command, session);
            case MAKE_MOVE -> makeMove(moveCommand, session);
            case LEAVE -> leave(command, session);
            case RESIGN -> resign(command, session);
        }
    }

    private void broadcastMessage(int gameID, NotificationMessage message, Session session) throws IOException {
        for (Session gameSessions : connections.getSession(gameID)) {
            if (gameSessions.isOpen() && !gameSessions.equals(session)) {
                String notification = new Gson().toJson(message);
                gameSessions.getRemote().sendString(notification);
            }
        }
    }

    private void broadcastLoadGameMessage(int gameID, LoadGameMessage message, Session session) throws IOException {
        for (Session gameSessions : connections.getSession(gameID)) {
            if (gameSessions.isOpen() && !gameSessions.equals(session)) {
                String notification = new Gson().toJson(message);
                gameSessions.getRemote().sendString(notification);
            }
        }
    }

    private void sendMessage(String message, Session session) throws IOException {
        session.getRemote().sendString(message);
    }

    private void onError(Throwable throwable, Session session) throws IOException {
        ErrorMessage errorMessage = new ErrorMessage(throwable.getMessage());
        String errorString = new Gson().toJson(errorMessage);
        sendMessage(errorString, session);
    }

    private void connect(UserGameCommand command, Session session) throws IOException {
        try {
            connections.add(command.getGameID(), session);
            String username = authDAO.getAuth(command.getAuthToken()).username();
            GameData game = gameDAO.getGame(command.getGameID());

            if (game.blackUsername() != null && username.equals(game.blackUsername())) {
                broadcastMessage(command.getGameID(),
                        new NotificationMessage(username + " has joined the game as BLACK"),
                        session);
            }
            else if (game.whiteUsername() != null && username.equals(game.whiteUsername())) {
                broadcastMessage(command.getGameID(),
                        new NotificationMessage(username + " has joined the game as WHITE"),
                        session);
            }
            else {
                broadcastMessage(command.getGameID(),
                        new NotificationMessage(username + " has joined the game as an observer"),
                        session);
            }
            LoadGameMessage loadGameMessage = new LoadGameMessage(game.game());
            String loadGameString = new Gson().toJson(loadGameMessage);
            sendMessage(loadGameString, session);

        }
        catch (Exception ex) {
            onError(ex, session);
        }
    }

    private void leave(UserGameCommand command, Session session) throws IOException {
        try {
            connections.remove(command.getGameID(), session);
            String username = authDAO.getAuth(command.getAuthToken()).username();
            GameData game = gameDAO.getGame(command.getGameID());

            if (game.blackUsername() != null && username.equals(game.blackUsername())) {
                broadcastMessage(command.getGameID(),
                        new NotificationMessage(username + " has left the game"),
                        null);
                gameDAO.updateGame(new GameData(game.gameID(),
                        game.whiteUsername(),
                        null,
                        game.gameName(),
                        game.game()));
            }
            else if (game.whiteUsername() != null && username.equals(game.whiteUsername())) {
                broadcastMessage(command.getGameID(),
                        new NotificationMessage(username + " has left the game"),
                        null);
                gameDAO.updateGame(new GameData(game.gameID(),
                        null,
                        game.blackUsername(),
                        game.gameName(),
                        game.game()));
            }
            else {
                broadcastMessage(command.getGameID(),
                        new NotificationMessage(username + " is no longer observing the game"),
                        null);
            }
        }
        catch (Exception ex) {
            onError(ex, session);
        }
    }

    public void makeMove(MakeMoveCommand command, Session session) throws IOException {
        try {
            String username = authDAO.getAuth(command.getAuthToken()).username();
            GameData game = gameDAO.getGame(command.getGameID());
            ChessMove move = command.getMove();
            ChessPiece piece = game.game().getBoard().getPiece(move.getStartPosition());

            if (game.blackUsername() != null && username.equals(game.blackUsername()) && !game.game().gameOver) {
                if (piece.getTeamColor() != ChessGame.TeamColor.BLACK) {
                    throw new InvalidMoveException("Cannot move other teams piece");
                }

                game.game().makeMove(move);
                gameDAO.updateGame(game);
                broadcastLoadGameMessage(command.getGameID(), new LoadGameMessage(game.game()), null);
                broadcastMessage(command.getGameID(), new NotificationMessage(username + "made a move"), session);


            }
            else if (game.whiteUsername() != null && username.equals(game.whiteUsername()) && !game.game().gameOver) {
                if (piece.getTeamColor() != ChessGame.TeamColor.WHITE) {
                    throw new InvalidMoveException("Cannot move other teams piece");
                }

                game.game().makeMove(move);
                gameDAO.updateGame(game);
                broadcastLoadGameMessage(command.getGameID(), new LoadGameMessage(game.game()), null);
                broadcastMessage(command.getGameID(), new NotificationMessage(username + "made a move"), session);

            }
            /// CHANGE TO BE SPECIFIC LATER
            else if (game.game().isInCheck(game.game().getTeamTurn()) ||
                    game.game().isInStalemate(game.game().getTeamTurn()) ||
                    game.game().isInCheckmate(game.game().getTeamTurn())) {
                broadcastMessage(command.getGameID(), new NotificationMessage(username + "is now in stalemate, checkmate, OR check :)"), null);
            }
            else {
                ErrorMessage errorMessage = new ErrorMessage("Observer cannot make moves");
                String eMessage = new Gson().toJson(errorMessage);
                sendMessage(eMessage, session);
            }
        }
        catch (Exception ex) {
            onError(ex, session);

        }
    }

    public void resign(UserGameCommand command, Session session) throws IOException {
        try {
            String username = authDAO.getAuth(command.getAuthToken()).username();
            GameData game = gameDAO.getGame(command.getGameID());

            if (game.blackUsername() != null && username.equals(game.blackUsername()) && !game.game().gameOver||
                    game.whiteUsername() != null && username.equals(game.whiteUsername()) && !game.game().gameOver) {

                game.game().setGameOver(true);
                broadcastMessage(command.getGameID(),
                        new NotificationMessage(username + " has resigned. GAME OVER"),
                        null);
                gameDAO.updateGame(game);
            }
            else {
                ErrorMessage errorMessage = new ErrorMessage("Observer cannot resign the game");
                String eMessage = new Gson().toJson(errorMessage);
                sendMessage(eMessage, session);
            }
        }
        catch (Exception ex) {
            onError(ex, session);
        }
    }

}
