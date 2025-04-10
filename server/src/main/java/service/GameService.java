package service;

import chess.ChessGame;
import dataaccess.*;
import model.GameData;
import java.util.Collection;

public class GameService {
    private final GameDAO gameDAO;

    public GameService(GameDAO gameDao) {
        this.gameDAO = gameDao;
    }


    public void clear() throws DataAccessException{
        gameDAO.clear();
    }


    public int createGame(String gameName) throws DataAccessException{
        // VERIFY gameName
        if (gameName == null || gameName.isEmpty()) {
                throw new DataAccessException("bad request");
        }

        int gameID = generateGameID();
        GameData game = new GameData(gameID, null, null, gameName, new ChessGame());
        gameDAO.createGame(game);

        return gameID;
    }


    public int generateGameID() throws DataAccessException{
        return listGames().size() + 1;
    }


    public Collection<GameData> listGames() throws DataAccessException{
        return gameDAO.listGames();
    }


    public void joinGame(String username, String color, int gameID) throws DataAccessException {
        // VALIDATE INPUTS
        if (username == null || color == null || gameID <= 0) {
            throw new DataAccessException("Error: bad request");
        }
        if (username.isEmpty() || color.isEmpty()) {
            throw new DataAccessException("Error: bad request, empty username or color");
        }
        if (!color.equals("WHITE") && !color.equals("BLACK")) {
            throw new DataAccessException("Error: bad request, invalid color");
        }

        // GET GAME
        GameData game = gameDAO.getGame(gameID);
        if (game == null) {
            throw new DataAccessException("Error: bad request, no game found");
        }

        // CHECK IF COLOR TAKEN
        if (color.equals("WHITE") && gameDAO.getGame(gameID).whiteUsername() != null) {
            throw new DataAccessException("Error: WHITE already taken");
        }
        if (color.equals("BLACK") && gameDAO.getGame(gameID).blackUsername() != null) {
            throw new DataAccessException("Error: BLACK already taken");
        }

        // UPDATE THE GAME
        GameData updateGame;
        if (color.equals("WHITE")) {
            updateGame = new GameData(game.gameID(), username, game.blackUsername(), game.gameName(), game.game());
        }
        else {
            updateGame = new GameData(game.gameID(), game.whiteUsername(), username, game.gameName(), game.game());
        }
        gameDAO.updateGame(updateGame);
    }
}
