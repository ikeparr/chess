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



    public PreLoginClient(int serverURL) {
        server = new ServerFacade(serverURL);
        this.serverURL = serverURL;
        authToken = null;
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
