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
        boolean SuccessfulTest = true;
        try {
            createGameService.createGame("TestGameName");
        }
        catch (DataAccessException error) {
            SuccessfulTest = false;
        }
        assert SuccessfulTest;
    }


    @Test
    void createGameMissingName() throws DataAccessException {
        boolean MissingNameTest = false;
        try {
            createGameService.createGame(null);
        }
        catch (DataAccessException error) {
            MissingNameTest = true;
        }
        assert MissingNameTest;
    }

}
