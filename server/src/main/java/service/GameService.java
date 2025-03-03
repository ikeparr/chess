package service;

import dataaccess.*;
import model.GameData;

import java.util.Collection;

public class GameService {
    private final GameDAO gameDAO;
    private int nextGameID = 1;

    public GameService(GameDAO gameDao) {
        this.gameDAO = gameDao;
    }

    public void clear() throws DataAccessException{
        gameDAO.clear();
    }

    public int createGame(String gameName) throws DataAccessException{
        //validate auth
        if (gameName == null || gameName.isEmpty()) {
                throw new DataAccessException("bad request");
        }

        int gameID = (generateGameID());
        GameData game = new GameData(gameID, null, null, gameName, null);
        gameDAO.createGame(game);

        return gameID;
    }


    public int generateGameID() {
        return nextGameID++;
    }

    public Collection<GameData> listGames() throws DataAccessException{
        return gameDAO.listGames();
    }

}
