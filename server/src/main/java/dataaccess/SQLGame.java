package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.*;
import java.util.ArrayList;
import java.util.Collection;
import java.sql.*;
import static java.sql.Types.NULL;
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
            var statement = "TRUNCATE games";
            executeUpdate(statement);
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

    private int executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String) {
                        ps.setString(i + 1, (String) param);
                    }
                    else if (param instanceof Integer) {
                        ps.setInt(i + 1, (Integer) param);
                    }
                    else if (param == null) {
                        ps.setNull(i + 1, NULL);
                    }
                }
                ps.executeUpdate();

                return 0;
            }
        }
        catch (SQLException ex) {
            throw new DataAccessException("Error, could not execute update");
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
                PRIMARY KEY (gameID),
                FOREIGN KEY (whiteUsername) REFERENCES users(username),
                FOREIGN KEY (blackUsername) REFERENCES users(username),
                INDEX(gameName)
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
