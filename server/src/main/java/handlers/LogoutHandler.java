package handlers;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

public class LogoutHandler implements Route {
    private static final Logger log = LoggerFactory.getLogger(LogoutHandler.class);
    private final UserService logoutService;
    private final Gson gson = new Gson();

    public LogoutHandler(UserDAO userDAO, AuthDAO authDAO){

        logoutService = new UserService(userDAO, authDAO);
    }

    public Object handle(Request req, Response resp) {
        try {
            /// NOTE BELOW: grabs header not body. Deletes usually don't have body
            String authToken = req.headers("authorization");

            logoutService.logout(authToken);
            resp.status(200);
            return gson.toJson(new SuccessResponse());
        }
        catch (DataAccessException error) {
            if (error.getMessage().contains("unauthorized")){
                resp.status(401);
                return gson.toJson(new ErrorResponse("Error: " + error.getMessage()));
            }
            resp.status(500);
            return gson.toJson(new ErrorResponse("Error: " + error.getMessage()));
        }

    }

    private record SuccessResponse() {}
    private record ErrorResponse(String message) {}
}
