package dataaccess;

import chess.ChessGame;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Collection;

public class SQLGameTests {
    private static SQLGame sqlGame;
    private static SQLUser sqlUser;

    @BeforeEach
    void setUp() throws DataAccessException {
        DatabaseManager.createDatabase();
        sqlUser = new SQLUser();
        sqlGame = new SQLGame();
        sqlGame.clear();
        sqlUser.clear();
    }


    /// GAME CREATION TESTS ///
    @Test
    void createGameSuccessful() throws DataAccessException {
        GameData game = new GameData(1234, null, null, "testGameName", new ChessGame());
        boolean successfulTest = true;
        try {
            sqlGame.createGame(game);
        }
        catch (DataAccessException error) {
            successfulTest = false;
        }
        assert successfulTest;
        // Verify game was added
        GameData result = sqlGame.getGame(1234);
        assert result != null;
    }
    @Test
    void createGameFail() throws DataAccessException {
        GameData game = new GameData(4567, null, null, "failGame", new ChessGame());
        sqlGame.createGame(game);
        boolean failTest = false;
        try {
            sqlGame.createGame(game);
        }
        catch (DataAccessException error) {
            failTest = true;
        }
        assert failTest;
    }


    /// GET GAME TESTS ///
    @Test
    void getGameSuccessful() throws DataAccessException {
        GameData game = new GameData(123, null, null, "getGameName", new ChessGame());
        sqlGame.createGame(game);
        boolean successfulTest = true;
        GameData result = null;
        try {
            result = sqlGame.getGame(123);
        }
        catch (DataAccessException error) {
            successfulTest = false;
        }
        assert successfulTest;
        assert result != null;
    }
    @Test
    void getGameFail() throws DataAccessException {
        boolean failTest = false;
        GameData results = null;
        try {
            results = sqlGame.getGame(456);
            if (results != null) {
                failTest = true;
            }
        }
        catch (DataAccessException error) {
            failTest = true;
        }
        assert !failTest;
    }
    @Test
    void clearSuccessful() throws DataAccessException {
        GameData game = new GameData(999, null, null, "clearGame", new ChessGame());
        sqlGame.createGame(game);
        boolean successfulTest = true;
        try {
            sqlGame.clear();
        }
        catch (DataAccessException error) {
            successfulTest = false;
        }
        assert successfulTest;
        Collection<GameData> results = sqlGame.listGames();
        assert results.isEmpty();
    }
}
