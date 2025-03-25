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
    void clearDatabaseSuccess() throws ResponseException {
        boolean success = true;
        try {
            serverFacade.registerUser("testUser", "testPassword", "email@email.com");
            serverFacade.makeRequest("DELETE", "/db", null, null, null);
        }
        catch (ResponseException error) {
            success = false;
        }
        assert success;
    }

    @Test
    void clearDatabaseFail() throws ResponseException {
        serverFacade.registerUser("testUser", "testPassword", "email@email.com");
        serverFacade.makeRequest("DELETE", "/db", null, null, null);
        assert true;
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

    @Test
    void loginUserSuccess() throws ResponseException {
        AuthData authData = serverFacade.registerUser("testUser", "testPassword", "email@email.com");
        serverFacade.logoutUser(authData.authToken());
        boolean successfulTest = true;
        try {
            serverFacade.loginUser("testUser", "testPassword");
        }
        catch (ResponseException error) {
            successfulTest = false;
        }
        assert successfulTest;
    }

    @Test
    void loginUserFail() throws ResponseException {
        AuthData authData = serverFacade.registerUser("testUser", "testPassword", "email@email.com");
        serverFacade.logoutUser(authData.authToken());
        boolean failTest = false;
        try {
            serverFacade.loginUser("testUser", "testPassword2");
        }
        catch (ResponseException error) {
            failTest = true;
        }
        assert failTest;
    }

    @Test
    void logoutUserSuccess() throws ResponseException {
        AuthData authData = serverFacade.registerUser("testUser", "testPassword", "email@email.com");
        boolean successfulTest = true;
        try {
            serverFacade.logoutUser(authData.authToken());
        }
        catch (ResponseException error) {
            successfulTest = false;
        }
        assert successfulTest;
    }

    @Test
    void logoutUserFail() throws ResponseException {
        boolean failTest = false;
        try {
            serverFacade.logoutUser("nonexistentAuthToken");
        }
        catch (ResponseException error) {
            failTest = true;
        }
        assert failTest;
    }

    @Test
    void createGameSuccessful() throws ResponseException{
        AuthData authData = serverFacade.registerUser("testUser", "testPassword", "testEmail");
        int gameID = serverFacade.createGame("testGame", authData.authToken());
        assertEquals(1, gameID);
    }

    @Test
    void createGameFailure() throws ResponseException{
        AuthData authData = serverFacade.registerUser("testUser", "testPassword", "testEmail");
        boolean testFails = false;
        try {
            serverFacade.createGame("testGame", "fakeauthToken");
        }
        catch (ResponseException error) {
            testFails = true;
        }
        assert testFails;
    }

    @Test
    void joiningGameSuccess() throws ResponseException {
        AuthData authData = serverFacade.registerUser("testUser", "testPassword", "testEmail");
        serverFacade.createGame("testGame", authData.authToken());
        boolean testSuccess = true;
        try {
            serverFacade.joinGame(authData.username(), "WHITE", 1, authData.authToken());
        }
        catch (ResponseException error){
            testSuccess = false;
        }
        assert testSuccess;
    }

    @Test
    void joiningGameFail() throws ResponseException {
        AuthData authData = serverFacade.registerUser("testUser", "testPassword", "testEmail");
        boolean testFail = false;
        try {
            serverFacade.joinGame(authData.username(), "WHITE", 1, authData.authToken());
        }
        catch (ResponseException error){
            testFail = true;
        }
        assert testFail;
    }

    @Test
    void listGamesSuccess() throws ResponseException {
        AuthData authData = serverFacade.registerUser("testUser", "testPassword", "testEmail");
        serverFacade.createGame("testGame", authData.authToken());
        boolean testSuccess = true;
        try {
            serverFacade.listGames(authData.authToken());
        }
        catch (ResponseException error) {
            testSuccess = false;
        }
        assert testSuccess;
    }

    @Test
    void listGamesFail() throws ResponseException {
        AuthData authData = serverFacade.registerUser("testUser", "testPassword", "testEmail");
        serverFacade.logoutUser(authData.authToken());
        boolean testFail = true;
        try {
            serverFacade.listGames(authData.authToken());
        }
        catch (ResponseException error) {
            testFail = true;
        }
        assert testFail;
    }
}
