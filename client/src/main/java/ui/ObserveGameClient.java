package ui;

import exceptions.ResponseException;
import ui.ChessBoard;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ObserveGameClient {
    private final int serverUrl;
    public String authToken;

    public ObserveGameClient(int serverUrl, String authToken) {
        this.serverUrl = serverUrl;
        this.authToken = authToken;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            return switch (cmd) {
                case "leavegame" -> leaveGame();
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public void printBoard() {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        ChessBoard.drawWhiteBoard(out);
    }

    public String leaveGame() throws ResponseException {
        return "Observer left the game\n";
    }

    public String help() {
        return """
                - leaveGame
                - help
                """;
    }
}
