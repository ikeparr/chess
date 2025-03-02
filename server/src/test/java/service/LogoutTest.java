package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LogoutTest {
    private UserDAO userDAO;
    private AuthDAO authDAO;
    private UserService logOutService;

    @BeforeEach
    void setUp() {
        authDAO = new MemoryAuth();
        logOutService = new UserService(userDAO, authDAO);
    }

    @Test
    void logOutSuccessful() throws DataAccessException {
        AuthData auth = new AuthData("testToken", "bob");
        boolean SuccessfulTest = true;
        authDAO.createAuth(auth);
        try {
            logOutService.logout(auth.authToken());
        }
        catch (DataAccessException error) {
            SuccessfulTest = false;
        }
        assert SuccessfulTest;
    }

    @Test
    void logOutUnsuccessful() throws DataAccessException {
        boolean SuccessfulTest = false;
        try {
            logOutService.logout("invalidToken");
        }
        catch (DataAccessException error) {
            SuccessfulTest = true;
        }
        assert SuccessfulTest;
    }
}
