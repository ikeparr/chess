package client;

import model.*;
import ui.ServerFacade;
import exceptions.*;
import org.junit.jupiter.api.*;
import server.Server;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        serverFacade = new ServerFacade(port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    void clearDatabase() throws ResponseException {
        serverFacade.makeRequest("DELETE", "/db", null, null, null);
    }

    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    public void registerUserPositive() throws ResponseException {
        AuthData authData = serverFacade.registerUser("testUser", "testPassword", "email@email.com");
        assertEquals("testUser", authData.username());
    }

    @Test
    public void registerUserNegative() throws ResponseException {
        AuthData authData = serverFacade.registerUser("testUser", "testPassword", "email@email.com");
        assertNotEquals("testUser23", authData.username());
    }

}
