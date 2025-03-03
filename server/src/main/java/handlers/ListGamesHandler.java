package handlers;

import dataaccess.*;
import model.*;
import service.GameService;
import service.UserService;
import spark.Response;
import spark.Request;
import spark.Route;
import com.google.gson.Gson;

import java.util.Collection;

public class ListGamesHandler implements Route {
    private final UserService userService;
    private final GameService listGamesService;
    private final Gson gson = new Gson();

    public ListGamesHandler(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO){
        this.userService = new UserService(userDAO, authDAO);
        this.listGamesService = new GameService(gameDAO);
    }

    public Object handle(Request req, Response resp) {
        try {
            String authToken = req.headers("authorization");
            userService.validateAuthToken(authToken);
            Collection<GameData> games = listGamesService.listGames();

            resp.status(200);
            return gson.toJson(new SuccessResponse(games));
        }
        catch (DataAccessException error) {
            if (error.getMessage().contains("unauthorized")){
                resp.status(401);
                return gson.toJson(new ErrorResponse("Error: unauthorized"));
            }
            resp.status(500);
            return gson.toJson(new ErrorResponse("Error: " + error.getMessage()));
        }

    }

    private record SuccessResponse(Collection<GameData> games) {}
    private record ErrorResponse(String message) {}
}
