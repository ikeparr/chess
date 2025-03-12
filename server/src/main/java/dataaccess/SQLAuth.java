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



    public AuthData getAuth(String authToken) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT authToken, username FROM auths WHERE authToken = ?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (var results = ps.executeQuery()) {
                    if (results.next()) {
                        String db_authToken = results.getString("authToken");
                        String db_username = results.getString("username");
                        return new AuthData(db_authToken, db_username);
                    }
                    return null;
                }
            }
        }
        catch (SQLException ex) {
            throw new DataAccessException("error getting auth: " + ex.getMessage());
        }
    }



    public void deleteAuth(String authToken) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "DELETE FROM auths WHERE authToken = ?";
            executeUpdate(statement, authToken);
        }
        catch (SQLException ex) {
            throw new DataAccessException("Error: couldn't delete authToken: " + ex.getMessage());
        }
    }



    private final String[] createAuthStatements = {
            """
            CREATE TABLE IF NOT EXISTS auths (
                authToken VARCHAR(256),
                username VARCHAR(256) NOT NULL,
                PRIMARY KEY (authToken)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };



    private void configureAuthDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createAuthStatements) {
                try (var ps = conn.prepareStatement(statement)) {
                    ps.executeUpdate();
                }
            }
        }
        catch (SQLException ex) {
            throw new DataAccessException("Error: unable to configure auth database: " + ex.getMessage());
        }
    }



    private int executeUpdate(String statement, Object... params) throws DataAccessException {
        try(var conn = DatabaseManager.getConnection(); var ps = conn.prepareStatement(statement)) {
            for (var i = 0; i < params.length; i++) {
                var param = params[i];
                if (param instanceof Integer) {
                    ps.setInt(i + 1, (Integer) param);
                }
                else if (param instanceof String) {
                    ps.setString(i + 1, (String) param);
                }
                else if (param == null) {
                    ps.setNull(i + 1, NULL);
                }
            }
            ps.executeUpdate();
            return 0;
        }
        catch (SQLException ex) {
            throw new DataAccessException("Error: could not execute update");
        }
    }
}
