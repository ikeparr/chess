package service;

import dataaccess.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegisterTest {
    private UserDAO userDAO;
    private AuthDAO authDAO;
    private UserService registerService;

    @BeforeEach
    void setUp() {
        userDAO = new MemoryUser();
        authDAO = new MemoryAuth();
        registerService = new UserService(userDAO, authDAO);
    }


    @Test
    void registerSuccessful() throws DataAccessException {
        UserData user = new UserData("me", "myself", "andI@gmail.com");
        boolean successfulTest = true;
        try {
            AuthData auth = registerService.register(user);
        }
        catch (DataAccessException error) {
            if (error.getMessage().contains("bad request") || error.getMessage().contains("Username already taken")) {
                successfulTest = false;
            }
        }
        assert successfulTest;
    }


    @Test
    void registerUnsuccessfulNulls() throws DataAccessException {
        UserData user = new UserData(null, "myself", "andI@gmail.com");
        boolean nullErrorTest = false;
        try {
            AuthData auth = registerService.register(user);
        }
        catch (DataAccessException error) {
            if (error.getMessage().contains("bad request")) {
                nullErrorTest = true;
            }
        }
        assert nullErrorTest;
    }
}

