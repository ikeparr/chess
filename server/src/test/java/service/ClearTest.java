package service;

import dataaccess.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClearTest {
    private AuthDAO authDAO;
    private GameDAO gameDAO;
    private UserDAO userDAO;
    private UserService userService;
    private GameService gameService;

    @BeforeEach
    void setUp() {
        authDAO = new MemoryAuth();
        gameDAO = new MemoryGame();
        userDAO = new MemoryUser();
        gameService = new GameService(gameDAO);
        userService = new UserService(userDAO, authDAO);
    }

    @Test
    void clearSuccess() throws DataAccessException {
        userDAO.createUser(new UserData("me", "myself", "andI@gmail.com"));
        boolean SuccessfulTest = true;
        try {
            userService.clear();
            gameService.clear();
        }
        catch (DataAccessException error) {
            if (error.getMessage().contains("Error")) {
                SuccessfulTest = false;
            }
        }
        assert SuccessfulTest;
    }
}