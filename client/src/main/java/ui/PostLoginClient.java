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
    public String usernameLoggedIn;
    public String playerColor;
    public int gameIDJoined;


    public PostLoginClient(int serverUrl, String authToken, String usernameLoggedIn) {
        serverFacade = new ServerFacade(serverUrl);
        this.serverURL = serverUrl;
        this.authToken = authToken;
        this.usernameLoggedIn = usernameLoggedIn;

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
                case "observegame" -> observeGame(params);
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

    public String joinGame(String... params) throws ResponseException {
        if (params.length == 2) {
            try {
                int gameID = Integer.parseInt(params[0]);
                var color = params[1].toUpperCase();
                if (gameID >= 1) {
                    if (color.equals("BLACK") || color.equals("WHITE")) {
                        serverFacade.joinGame(usernameLoggedIn, color, gameID, authToken);
                        playerColor = color;
                        gameIDJoined = gameID;
                        return "User joined game.\n";
                    }
                }
            }
            catch (NumberFormatException error) {
                throw new ResponseException(400, "Invalid gameID: " + params[0]);
            }
        }
        throw new ResponseException(400, "Expected: <gameID> <teamColor>");
    }

    public String observeGame(String ... params) throws ResponseException {
        if (params.length == 1) {
            int gameID = Integer.parseInt(params[0]);
            if (gameID >= 1) {
                gameIDJoined = gameID;
                return "User observing game.\n";
            }
        }
        throw new ResponseException(400, "Expected: <gameID>");
    }

    public String logout() throws ResponseException {
        serverFacade.logoutUser(authToken);
        authToken = null;
        return "User logged out.\n";
    }

    public String help() {
        return """
                - listGames
                - createGame <gameName>
                - joinGame <game id> <team color>
                - observeGame <game id>
                - logout
                """;
    }
}
