package service;

import dataaccess.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateGameTest {
    private GameDAO gameDAO;
    private GameService createGameService;

    @BeforeEach
    void setUp() {
        gameDAO = new MemoryGame();
        createGameService = new GameService(gameDAO);
    }


    @Test
    void createGameSuccess() throws DataAccessException {
        boolean successfulTest = true;
        try {
            createGameService.createGame("TestGameName");
        }
        catch (DataAccessException error) {
            successfulTest = false;
        }
        assert successfulTest;
    }


    @Test
    void createGameMissingName() throws DataAccessException {
        boolean missingNameTest = false;
        try {
            createGameService.createGame(null);
        }
        catch (DataAccessException error) {
            missingNameTest = true;
        }
        assert missingNameTest;
    }

}
