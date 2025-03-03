package service;

import dataaccess.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.*;

public class ListGamesTest {
    private GameDAO gameDAO;
    private GameService listGameService;

    @BeforeEach
    void setUp() {
        gameDAO = new MemoryGame();
        listGameService = new GameService(gameDAO);
    }


    @Test
    void listGamesSuccessful() throws DataAccessException{
        GameData testGame = new GameData(1, null, null, "testGame", null);
        gameDAO.createGame(testGame);
        boolean SuccessfulTest = true;

        try {
            listGameService.listGames();
        }
        catch (DataAccessException error){
            SuccessfulTest = false;
        }
        assert SuccessfulTest;
    }


    @Test
    void listGamesEmpty() throws DataAccessException{
        boolean SuccessfulListing = true;
        Collection<GameData> games = null;
        try {
            games = listGameService.listGames();
        }
        catch (DataAccessException error) {
            SuccessfulListing = false;
        }
        //asserts if the result is empty which isn't preferred. cannot be null due to hashmap
        assert SuccessfulListing;
        assertNotNull(games, "games should not be null");
        assertEquals(0, games.size(), "should return 0 games when empty");
    }
}

