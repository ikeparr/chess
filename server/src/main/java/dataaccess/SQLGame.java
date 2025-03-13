package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.*;
import java.util.ArrayList;
import java.util.Collection;
import java.sql.*;
import java.sql.Types;

public class SQLGame implements GameDAO {

    private final Gson gson = new Gson();

    public SQLGame(){
        try {
            configureDatabase();
        }
        catch (DataAccessException error) {
            throw new RuntimeException("Failed to initialize SQLGame: " + error.getMessage());
        }
    }


    public void clear() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "DELETE FROM games";
            DatabaseManager.executeUpdate(statement);
        }
        catch (SQLException ex) {
            throw new DataAccessException("Error: couldn't clear game table");
        }
    }


    public void createGame(GameData game) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "INSERT INTO games (gameID, whiteUsername, blackUsername, gameName, gameStatus) VALUES (?,?,?,?,?)";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, game.gameID());
                ps.setNull(2, Types.VARCHAR);
                ps.setNull(3, Types.VARCHAR);
                ps.setString(4, game.gameName());
                ps.setString(5, gson.toJson(game.game()));
                ps.executeUpdate();
            }
        }
        catch (SQLException ex) {
            if (ex.getMessage().contains("Duplicate entry")) {
                throw new DataAccessException("Error game already exists: " + ex.getMessage());
            }
            throw new DataAccessException("Error creating game: " + ex.getMessage());
        }
    }


    public GameData getGame(int gameID) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()){
            var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, gameStatus FROM games WHERE gameID = ?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (var results = ps.executeQuery()) {
                    if (results.next()) {
                        int dbGameID = results.getInt("gameID");
                        String whiteUsername = results.getString("whiteUsername");
                        String blackUsername = results.getString("blackUsername");
                        String gameName = results.getString("gameName");
                        String gameStatus = results.getString("gameStatus");
                        ChessGame gameObj = gson.fromJson(gameStatus, ChessGame.class);
                        return new GameData(dbGameID, whiteUsername, blackUsername, gameName, gameObj);
                    }
                    return null;
                }
            }
        }
        catch (SQLException ex) {
            throw new DataAccessException("Error getting game: " + ex.getMessage());

        }
    }


    public void updateGame(GameData game) throws DataAccessException {
        String statement = "REPLACE INTO games (gameID, whiteUsername, blackUsername, gameName, gameStatus) VALUES (?,?,?,?,?)";
        DatabaseManager.executeUpdate(statement,game.gameID(),game.whiteUsername(),game.blackUsername(),game.gameName(),gson.toJson(game.game()));
    }

    public Collection<GameData> listGames() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            Collection<GameData> games = new ArrayList<>();
            var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, gameStatus FROM games";
            try (var ps = conn.prepareStatement(statement); var results = ps.executeQuery()) {
                while (results.next()) {
                    int gameID = results.getInt("gameID");
                    String whiteUsername = results.getString("whiteUsername");
                    String blackUsername = results.getString("blackUsername");
                    String gameName = results.getString("gameName");
                    String gameStatus = results.getString("gameStatus");
                    ChessGame gameObj = gson.fromJson(gameStatus, ChessGame.class);
                    games.add(new GameData(gameID, whiteUsername, blackUsername, gameName, gameObj));
                }
                return games;
            }
        }
        catch (SQLException ex) {
            throw new DataAccessException("Error listing games: " + ex.getMessage());
        }
    }


    // Creates the tables //
    private final String[] createGameStatements = {
            """
            CREATE TABLE IF NOT EXISTS games (
                gameID INT AUTO_INCREMENT NOT NULL,
                whiteUsername VARCHAR(256) NULL,
                blackUsername VARCHAR(256) NULL,
                gameName VARCHAR(256) UNIQUE NOT NULL,
                gameStatus TEXT NOT NULL,
                PRIMARY KEY (gameID)
            )
            """
    };

    // configures database by creating DB and inserting above tables //
    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createGameStatements) {
                try (var ps = conn.prepareStatement(statement)) {
                    ps.executeUpdate();
                }
            }
        }
        catch (SQLException ex) {
            throw new DataAccessException("Error: unable to configure database: " + ex.getMessage());
        }
    }
}
