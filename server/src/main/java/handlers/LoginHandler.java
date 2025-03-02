package handlers;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import service.*;
import spark.Response;
import spark.Request;
import com.google.gson.Gson;
import spark.Route;


public class LoginHandler implements Route {
    private final UserService loginService;
    private final Gson gson = new Gson();

    public LoginHandler(UserDAO userDAO, AuthDAO authDAO) {
        this.loginService = new UserService(userDAO, authDAO);
    }

    public Object handle(Request req, Response resp) {
        try {
            UserData request = gson.fromJson(req.body(), UserData.class);
            if (request == null || request.username() == null || request.password() == null){
                resp.status(401);
                return gson.toJson(new ErrorResponse("Error: unauthorized"));
            }

            AuthData authData = loginService.login(request.username(), request.password());
            resp.status(200);
            return gson.toJson(new SuccessResponse(authData.username(), authData.authToken()));
        }
        catch (DataAccessException error) {
            if (error.getMessage().contains("unauthorized")) {
                resp.status(401);
                return gson.toJson(new ErrorResponse("Error: unauthorized"));
            }
            resp.status(500);
            return gson.toJson(new ErrorResponse("Error: " + error.getMessage()));
        }
    }

    private record SuccessResponse(String username, String authToken) {}
    private record ErrorResponse(String message) {}
}
