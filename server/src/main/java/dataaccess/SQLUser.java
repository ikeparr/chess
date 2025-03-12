package dataaccess;

import model.*;
import java.sql.*;
import static java.sql.Types.NULL;
import org.mindrot.jbcrypt.BCrypt;


public class SQLUser implements UserDAO {

    public SQLUser() {
        try {
            configureUserDatabase();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void clear() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "DELETE FROM users";
            executeUpdate(statement);
        }
        catch (SQLException ex) {
            throw new DataAccessException("Error: couldn't clear user table: " +ex.getMessage());
        }
    }

    public void createUser(UserData user) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "INSERT INTO users (username, hashed_password, email) VALUES (?,?,?)";
            try (var ps = conn.prepareStatement(statement)) {
                String hashed_password = BCrypt.hashpw(user.password(), BCrypt.gensalt());
                ps.setString(1, user.username());
                ps.setString(2, hashed_password);
                ps.setString(3, user.email());
                ps.executeUpdate();
            }
        }
        catch (SQLException ex) {
            if (ex.getMessage().contains("Duplicate entry")) {
                throw new DataAccessException("User already exists: " + user.username());
            }
        }
    }

    public UserData getUser(String username) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, hashed_password, email FROM users WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                try (var results = ps.executeQuery()) {
                    if (results.next()) {
                        String db_username = results.getString("username");
                        String hashed_password = results.getString("hashed_password");
                        String email = results.getString("email");
                        return new UserData(db_username, hashed_password, email);
                    }
                    return null; //This means user not found
                }
            }
        }
        catch (SQLException ex) {
            throw new DataAccessException("User not found: " + username);
        }
    }


    // executes given changes to DB //
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
            throw new DataAccessException("Error, could not execute update " + ex.getMessage());
        }
    }


    // creates the tables //
    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS users (
                username VARCHAR(256) UNIQUE NOT NULL,
                hashed_password VARCHAR(256) NOT NULL,
                email VARCHAR(256) UNIQUE NOT NULL,
                PRIMARY KEY (username),
                INDEX(hashed_password),
                INDEX(email)
            )
            """
    };


    //  configures database by creating DB and inserting above tables //
    private void configureUserDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        }
        catch (SQLException ex) {
            throw new DataAccessException("Error: unable to configure database: " + ex.getMessage());
        }
    }
}
