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
