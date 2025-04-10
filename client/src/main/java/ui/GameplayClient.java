package ui;

import exceptions.ResponseException;
import ui.ChessBoard;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import websocket.NotificationHandler;
import websocket.WebSocketFacade;

public class GameplayClient {
    private final int serverUrl;
    public String authToken;
    public String color;
    public String username;
    public int gameID;

    private WebSocketFacade ws;
//    private final NotificationHandler notificationHandler;


    public GameplayClient(int serverUrl, String authToken, String color, int gameID) {
        this.serverUrl = serverUrl;
        this.authToken = authToken;
        this.color = color;
        this.username = username;
//        this.notificationHandler = notificationHandler;
//        this.ws = new WebSocketFacade(serverUrl, this);
//        ws.connectToGame(authToken, gameID);

    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "leavegame" -> leaveGame();
                case "makeMove" -> move();
                case "highlightMoves" -> highlightMoves();
//                case "redrawBoard" -> redrawBoard();
                case "resign" -> resign();
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public void printBoard() {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        if (color.equals("WHITE")) {
            ChessBoard.drawWhiteBoard(out);
        }
        if (color.equals("BLACK")) {
            ChessBoard.drawBlackBoard(out);
        }
    }

    public String leaveGame() throws ResponseException {
        /// IMPLEMENT WEBSOCKET HERE
        ws.leaveGame(authToken, gameID);
        return "User left the game\n";
    }

    public String move() throws ResponseException {
        /// IMPLEMENT WEBSOCKET HERE
        return null;
    }

    public String highlightMoves() throws ResponseException {
        /// IMPLEMENT WEBSOCKET HERE
        return null;
    }

    public String resign() throws ResponseException {
        /// IMPLEMENT WEBSOCKET HERE
//        ws.resignGame(authToken, gameID);
        return "User resigned\n";
    }

    public String help() {
        return """
                - leaveGame
                - move
                - highlight
                - resign
                - help
                """;
    }
}

