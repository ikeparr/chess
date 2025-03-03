package service;

import dataaccess.*;
import model.AuthData;
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
        boolean successfulTest = true;
        authDAO.createAuth(auth);
        try {
            logOutService.logout(auth.authToken());
        }
        catch (DataAccessException error) {
            successfulTest = false;
        }
        assert successfulTest;
    }


    @Test
    void logOutUnsuccessful() throws DataAccessException {
        boolean successfulTest = false;
        try {
            logOutService.logout("invalidToken");
        }
        catch (DataAccessException error) {
            successfulTest = true;
        }
        assert successfulTest;
    }
}
