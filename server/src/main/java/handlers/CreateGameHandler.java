package handlers;

import dataaccess.*;
import model.*;
import service.GameService;
import service.UserService;
import spark.Response;
import spark.Request;
import spark.Route;
import com.google.gson.Gson;

public class CreateGameHandler implements Route {
    private final GameService createGameService;
    private final UserService userService;
    private final Gson gson = new Gson();

    public CreateGameHandler(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO){
        this.createGameService = new GameService(gameDAO);
        this.userService = new UserService(userDAO, authDAO);
    }

    public Object handle(Request req, Response resp) throws DataAccessException {
        try {
            GameData request = gson.fromJson(req.body(), GameData.class);
            //get and validate authToken before continuing
            String authToken = req.headers("authorization");
            userService.validateAuthToken(authToken);

            if (request == null) {
                resp.status(400);
                return gson.toJson(new ErrorResponse("Error: bad request"));
            }

            int gameID = createGameService.createGame(request.gameName());
            resp.status(200);
            return gson.toJson(new SuccessResponse(gameID));
        }
        catch (DataAccessException error) {
            if (error.getMessage().contains("bad request")){
                resp.status(400);
                return gson.toJson(new ErrorResponse("Error: bad request"));
            }
            if (error.getMessage().contains("unauthorized")){
                resp.status(401);
                return gson.toJson(new ErrorResponse("Error: unauthorized"));
            }
            resp.status(500);
            return gson.toJson(new ErrorResponse("Error: " + error.getMessage()));
        }
    }

    private record SuccessResponse(int gameID) {}
    private record ErrorResponse(String message) {}
}
