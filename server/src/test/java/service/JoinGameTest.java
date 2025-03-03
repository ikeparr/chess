package service;

import dataaccess.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JoinGameTest {
    private GameDAO gameDAO;
    private GameService joinGameService;

    @BeforeEach
    void setUp() {
        gameDAO = new MemoryGame();
        joinGameService = new GameService(gameDAO);
    }


    @Test
    void joinGameSuccessful() throws DataAccessException {
        GameData game = new GameData(123, null, null, "GoodGame", null);
        gameDAO.createGame(game);

        boolean successfulTest = true;
        try {
            joinGameService.joinGame("me", "WHITE", 123);
        }
        catch (DataAccessException error) {
            successfulTest = false;
        }
        assert successfulTest;
    }


    @Test
    void joinGameColorTaken() throws DataAccessException {
        GameData game = new GameData(123, null, "takenSpot", "FailGame", null);
        gameDAO.createGame(game);

        boolean failTest = false;
        try {
            joinGameService.joinGame("me", "BLACK", 123);
        }
        catch (DataAccessException error) {
            failTest = true;
        }
        assert failTest;
    }
}

