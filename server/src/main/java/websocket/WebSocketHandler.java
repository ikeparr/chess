package websocket;

import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
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
        switch (command.getCommandType()) {
            case CONNECT -> connect(command, session);
            case MAKE_MOVE -> makeMove(command, session);
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

    private void sendMessage(String message, Session session) throws IOException {
        session.getRemote().sendString(message);
    }


}
