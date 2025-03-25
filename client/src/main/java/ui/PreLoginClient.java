package ui;

import java.util.Arrays;

import com.google.gson.Gson;
import com.sun.nio.sctp.NotificationHandler;
import model.*;
import exceptions.ResponseException;
import ui.ServerFacade;

public class PreLoginClient {
    private final ServerFacade server;
    private final int serverURL;
    public String authToken;
    public String usernameLoggedIn;



    public PreLoginClient(int serverURL) {
        server = new ServerFacade(serverURL);
        this.serverURL = serverURL;
        authToken = null;
        usernameLoggedIn = null;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "register" -> registerUser(params);
                case "login" -> loginUser(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String registerUser(String... params) throws ResponseException {
        if (params.length == 3) {
            var username = params[0];
            var password = params[1];
            var email = params[2];
            var request = server.registerUser(username, password, email);
            authToken = request.authToken();
            usernameLoggedIn = request.username();
            return String.format("registered and logged in as %s", username);
//        }
//        else {
//            return "three parameters required for register: <username> <password> <email>";
        }
        throw new ResponseException(400, "Expected: <username> <password> <email>");
    }

    public String loginUser(String... params) throws ResponseException {
        if (params.length == 2) {
            var username = params[0];
            var password = params[1];
            var request = server.loginUser(username, password);
            authToken = request.authToken();
            usernameLoggedIn = request.username();
            return String.format("logged in as %s", username);
        }
        throw new ResponseException(400, "Expected: <username> <password>");
    }

    public String quit() throws ResponseException {
        return "quit";
    }

    public String help() {
            return """
                    - register <username> <password> <email>
                    - login <username> <password>
                    - help
                    - quit
                    """;
    }
}
