package server;

import handlers.*;
import spark.*;
import dataaccess.*;

public class Server {
    private final AuthDAO authDAO = new MemoryAuth();
    private final GameDAO gameDAO = new MemoryGame();
    private final UserDAO userDAO = new MemoryUser();
//    private final GameDAO gameDAO = new MemoryGame();
//    private final UserDAO userDAO = new MemoryUser();
    private final UserDAO userDAO = new SQLUser();
    private final GameDAO gameDAO = new SQLGame();


    public int run(int desiredPort) {
        Spark.port(desiredPort);
        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", new ClearHandler(authDAO, gameDAO, userDAO));
        Spark.post("/user", new RegisterHandler(userDAO, authDAO));
        Spark.post("/session", new LoginHandler(userDAO, authDAO));
        Spark.delete("/session", new LogoutHandler(userDAO, authDAO));
        Spark.post("/game", new CreateGameHandler(userDAO, authDAO, gameDAO));
        Spark.get("/game", new ListGamesHandler(userDAO, authDAO, gameDAO));
        Spark.put("/game", new JoinGameHandler(userDAO, authDAO, gameDAO));

        //This line initializes the server and can be removed once you have a functioning endpoint
        // Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
