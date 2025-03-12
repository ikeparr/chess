package dataaccess;

import model.*;
import java.sql.*;
import static java.sql.Types.NULL;

public class SQLAuth implements AuthDAO {

    public SQLAuth() {
        try {
            configureAuthDatabase();
        }
        catch (DataAccessException error) {
            throw new RuntimeException(error);
        }
    }



    public void clear() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "DELETE FROM auths";
            executeUpdate(statement);
        }
        catch (SQLException ex) {
            throw new DataAccessException("Error: couldnt clear auth table: " + ex.getMessage());
        }
    }
}
