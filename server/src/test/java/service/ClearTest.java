package service;

import dataaccess.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
        //assert is making sure it should be null, otherwise give message
        userDAO.createUser(new UserData("testUser", "password", "email@email.com"));
        gameService.clear();
        userService.clear();
        assertNull(userDAO.getUser("testUser"), "User should be cleared");
    }
}