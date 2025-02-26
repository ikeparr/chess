package service;

import dataaccess.*;
import model.GameData;

public class GameService {
    private final GameDAO gameDAO;

    public GameService(GameDAO gameDao) {
        this.gameDAO = gameDao;
    }

    public void clear() throws DataAccessException{
        gameDAO.clear();
    }

}
