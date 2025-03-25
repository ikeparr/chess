package ui;

import exceptions.ResponseException;
import ui.ChessBoard;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class GameplayClient {
    private final int serverUrl;
    public String authToken;
    public String color;

    public GameplayClient(int serverUrl, String authToken, String color) {
        this.serverUrl = serverUrl;
        this.authToken = authToken;
        this.color = color;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "leavegame" -> leaveGame();
                case "move" -> move();
                case "highlight" -> highlightMoves();
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
        return "User left the game\n";
    }

    public String move() throws ResponseException {
        return null;
    }

    public String highlightMoves() throws ResponseException {
        return null;
    }

    public String help() {
        return """
                - leaveGame
                - move
                - highlight
                - help
                """;
    }
}

