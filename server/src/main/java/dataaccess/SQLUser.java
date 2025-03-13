package dataaccess;

import model.*;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;


public class SQLUser implements UserDAO {

    public SQLUser() {
        try {
            configureUserDatabase();
        } catch (DataAccessException error) {
            throw new RuntimeException(error);
        }
    }

    public void clear() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "DELETE FROM users";
            DatabaseManager.executeUpdate(statement);
        }
        catch (SQLException ex) {
            throw new DataAccessException("Error: couldn't clear user table: " +ex.getMessage());
        }
    }

    public void createUser(UserData user) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "INSERT INTO users (username, hashed_password, email) VALUES (?,?,?)";
            try (var ps = conn.prepareStatement(statement)) {
                String hashedPassword = BCrypt.hashpw(user.password(), BCrypt.gensalt());
                ps.setString(1, user.username());
                ps.setString(2, hashedPassword);
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
                        String dbUsername = results.getString("username");
                        String hashedPassword = results.getString("hashed_password");
                        String email = results.getString("email");
                        return new UserData(dbUsername, hashedPassword, email);
                    }
                    return null; //This means user not found
                }
            }
        }
        catch (SQLException ex) {
            throw new DataAccessException("User not found: " + username);
        }
    }


    // creates the tables //
    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS users (
                username VARCHAR(256) UNIQUE NOT NULL,
                hashed_password VARCHAR(256) NOT NULL,
                email VARCHAR(256) UNIQUE NOT NULL,
                PRIMARY KEY (username)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
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
