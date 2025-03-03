package service;

import dataaccess.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoginTest {
    private UserDAO userDAO;
    private AuthDAO authDAO;
    private UserService loginService;

    @BeforeEach
    void setUp() {
        userDAO = new MemoryUser();
        authDAO = new MemoryAuth();
        loginService = new UserService(userDAO, authDAO);
    }


    @Test
    void loginSuccessful() throws DataAccessException {
        UserData user = new UserData("me", "myself", "andI@gmail.com" );
        boolean successfulTest = true;
        userDAO.createUser(user);
        try {
            AuthData auth = loginService.login(user.username(), user.password());
        }
        catch (DataAccessException error) {
            successfulTest = false;
        }
        assert successfulTest;
    }


    @Test
    void loginFailPassword() throws DataAccessException {
        UserData user = new UserData("me", "myself", "andI@gmail.com" );
        boolean successfulTest = false;
        userDAO.createUser(user);
        try {
            AuthData auth = loginService.login(user.username(), "notUserPassword");
        }
        catch (DataAccessException error) {
            successfulTest = true;
        }
        assert successfulTest;
    }


    @Test
    void loginFailUsername() throws DataAccessException {
        UserData user = new UserData("me", "myself", "andI@gmail.com" );
        boolean successfulTest = false;
        userDAO.createUser(user);
        try {
            AuthData auth = loginService.login("notUsername", user.password());
        }
        catch (DataAccessException error) {
            successfulTest = true;
        }
        assert successfulTest;
    }
}
