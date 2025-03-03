package handlers;

import dataaccess.*;
import model.*;
import service.UserService;
import spark.Request;
import spark.Response;
import com.google.gson.Gson;
import spark.Route;

public class RegisterHandler implements Route {
    private final UserService registerService;
    private final Gson gson = new Gson();

    public RegisterHandler(UserDAO userDAO, AuthDAO authDAO) {
        this.registerService = new UserService(userDAO, authDAO);
    }


    public Object handle(Request req, Response resp) throws DataAccessException {

        try {
            // DESERIALIZE REQ BODY
            UserData request = gson.fromJson(req.body(), UserData.class);
            if (request == null){
                resp.status(400);
                return gson.toJson(new ErrorResponse("Error, bad request"));
            }

            // REGISTER AND GET authToken
            AuthData authData = registerService.register(request);
            resp.status(200);
            return gson.toJson(new SuccessResponse(authData.username(), authData.authToken()));
        }
        catch (DataAccessException error) {
            if (error.getMessage().contains("bad request")){
                resp.status(400);
                return gson.toJson(new ErrorResponse("Error, bad request"));
            }
            if (error.getMessage().contains("already taken")) {
                resp.status(403);
                return gson.toJson(new ErrorResponse("Error, username already taken"));
            }
            // GENERIC ERROR
            resp.status(500);
            return gson.toJson(new ErrorResponse("Unexpected Error, " + error.getMessage()));
        }
    }

    private record ErrorResponse(String message) {}
    private record SuccessResponse(String username, String authToken) {}
}
