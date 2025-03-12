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

}
