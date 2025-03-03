package handlers;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

public class LogoutHandler implements Route {
    private final UserService logoutService;
    private final Gson gson = new Gson();

    public LogoutHandler(UserDAO userDAO, AuthDAO authDAO){
        logoutService = new UserService(userDAO, authDAO);
    }


    public Object handle(Request req, Response resp) {

        try {
            /// NOTE: grabs HEADER, not BODY. (deletes usually don't have body)
            String authToken = req.headers("authorization");
            logoutService.logout(authToken);
            resp.status(200);
            return gson.toJson(new SuccessResponse());
        }
        catch (DataAccessException error) {
            if (error.getMessage().contains("unauthorized")) {
                resp.status(401);
                return gson.toJson(new ErrorResponse("Error: " + error.getMessage()));
            }
            // GENERIC ERROR
            resp.status(500);
            return gson.toJson(new ErrorResponse("Error: " + error.getMessage()));
        }
    }

    private record SuccessResponse() {}
    private record ErrorResponse(String message) {}
}
