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



    public void createAuth(AuthData auth) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "INSERT INTO auths (authToken, username) VALUES (?,?)";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, auth.authToken());
                ps.setString(2, auth.username());
                ps.executeUpdate();
            }
        }
        catch (SQLException ex) {
            throw new DataAccessException("Error creating authToken: " + ex.getMessage());
        }
    }

}
