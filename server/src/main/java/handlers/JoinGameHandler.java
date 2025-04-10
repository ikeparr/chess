package handlers;

import dataaccess.*;
import model.*;
import service.*;
import spark.Response;
import spark.Request;
import com.google.gson.Gson;
import spark.Route;

public class JoinGameHandler implements Route {
    private final GameService joinGameService;
    private final UserService userService;
    private final Gson gson = new Gson();

    public JoinGameHandler(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
        this.joinGameService = new GameService(gameDAO);
        this.userService = new UserService(userDAO, authDAO);
    }


    public Object handle(Request req, Response resp) {

        try {
            // PARSE & authToken VALIDATION
            JoinRequest request = gson.fromJson(req.body(), JoinRequest.class);
            String authToken = req.headers("authorization");
            AuthData user = userService.validateAuthToken(authToken);
            if (request.playerColor() == null || request.gameID() == null || request.gameID() <= 0) {
                resp.status(400);
                return gson.toJson(new ErrorResponse("Error: bad request, either color or gameID was null"));
            }

            joinGameService.joinGame(user.username(), request.playerColor, request.gameID());
            resp.status(200);
            return gson.toJson(new SuccessResponse());
        }
        catch (DataAccessException error) {
            if (error.getMessage().contains("bad")) {
                resp.status(400);
                return gson.toJson(new ErrorResponse("Error: bad request"));
            }
            if (error.getMessage().contains("unauthorized")) {
                resp.status(401);
                return gson.toJson(new ErrorResponse("Error: unauthorized"));
            }
            if (error.getMessage().contains("already")) {
                resp.status(403);
                return gson.toJson(new ErrorResponse("Error: already taken"));
            }
            resp.status(500);
            return gson.toJson(new ErrorResponse("Error: " + error.getMessage()));
        }
    }

    // DESERIALIZE JSON INPUT FOR JOINING GAME
    private record JoinRequest(String playerColor, Integer gameID) {}
    private record SuccessResponse() {}
    private record ErrorResponse(String message) {}
}
