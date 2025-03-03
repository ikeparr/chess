package dataaccess;

import model.GameData;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemoryGame implements GameDAO {
    private final Map<Integer, GameData> games = new HashMap<>();

    public void clear() throws DataAccessException{
        games.clear();
    }

    public void createGame(GameData game) throws DataAccessException{
        games.put(game.gameID(), game);
    }

    public GameData getGame(int gameID) throws DataAccessException {
        return games.get(gameID);
    }

    public Collection<GameData> listGames() throws DataAccessException{
        return games.values();
    }
}
