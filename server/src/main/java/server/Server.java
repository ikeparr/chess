package server;

import handlers.*;
import spark.*;
import dataaccess.*;

public class Server {
    private final AuthDAO authDAO = new MemoryAuth();
    private final GameDAO gameDAO = new MemoryGame();
    private final UserDAO userDAO = new MemoryUser();

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();
        Spark.delete("/db", new ClearHandler(authDAO, gameDAO, userDAO));
        /// YET TO BE IMPLEMENTED BELOW ///
        //Spark.delete("/session", new LogoutHandler(authDAO)::handle);
        //Spark.get("/game", new ListGamesHandler(authDAO)::handle);
        //Spark.post("/game", new CreateGameHandler( ??? )::handle);
        //Spark.put("/game", new JoinGameHandler( ??? )::handle

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
