package handlers;

import dataaccess.*;
import service.*;
import spark.Request;
import spark.Response;
import com.google.gson.Gson;
import spark.Route;

public class ClearHandler implements Route {
    private final GameService gameService;
    private final UserService userService;
    private final Gson gson = new Gson();

    public ClearHandler(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO){
        this.gameService = new GameService(gameDAO);
        this.userService = new UserService(userDAO, authDAO);
    }


    public Object handle(Request req, Response resp) throws DataAccessException {

        try {
            gameService.clear();
            userService.clear();
            resp.status(200);
            return gson.toJson(new SuccessResponse());
        }
        catch (DataAccessException error){
            resp.status(500);
            return gson.toJson(new ErrorResponse("Error: " + error.getMessage()));
        }
    }

    // serializes to empty JSON object
    private record SuccessResponse() {}
    private record ErrorResponse(String message) {}
}
