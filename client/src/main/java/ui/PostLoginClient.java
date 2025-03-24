package ui;

import java.util.Arrays;
import com.google.gson.Gson;
import com.sun.nio.sctp.NotificationHandler;
import model.*;
import exceptions.ResponseException;
import ui.ServerFacade;

public class PostLoginClient {
    private final ServerFacade serverFacade;
    private final int serverURL;
    public String authToken;
    /// ADJUST PETSHOP CODE BELOW:


    public PostLoginClient(int serverUrl, String authToken) {
        serverFacade = new ServerFacade(serverUrl);
        this.serverURL = serverUrl;
        this.authToken = authToken;

    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "logout" -> logout();
                case "creategame" -> createGame(params);
                case "listgames" -> listGames();
                case "joingame" -> joinGame(params);
                case "observegame" -> observeGame();
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String createGame(String... params) throws ResponseException {
        if (params.length == 1) {
            var gameName = params[0];
            var request = serverFacade.createGame(gameName, authToken);
            return String.format("Game created. GameID: " + request);
        }
        throw new ResponseException(400, "Expected: <gameName>");
    }

    public String listGames() throws ResponseException {
        var games = serverFacade.listGames(authToken);
        var result = new StringBuilder();
        var gson = new Gson();
        for (var game : games) {
            // SPLIT UP USING FORMATED STRING TO AVOID JSON OUTPUT
            result.append(String.format("GameID: %d, White: %s, Black: %s, GameName: %s%n",
                    game.gameID(),
                    game.whiteUsername() != null ? game.whiteUsername() : "empty",
                    game.blackUsername() != null ? game.blackUsername() : "empty",
                    game.gameName())).append("\n");
        }
        return result.toString();
    }

    public String logout() throws ResponseException {
        serverFacade.logoutUser(authToken);
        authToken = null;
        return "User logged out\n";
    }

    public String help() {
        return """
                - listGames
                - createGame <gameName>
                - joinGame <game id>
                - observeGame <game id>
                - logout
                """;
    }
}
