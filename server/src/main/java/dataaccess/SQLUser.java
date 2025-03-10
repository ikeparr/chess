package dataaccess;

import model.*;
import java.sql.*;
import static java.sql.Types.NULL;
import org.mindrot.jbcrypt.BCrypt;


public class SQLUser implements UserDAO {

    public SQLUser() throws DataAccessException {
        configureDatabase();
    }

    public void clear() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "TRUNCATE users";
            executeUpdate(statement);
        }
        catch (SQLException ex) {
            throw new DataAccessException("Error: couldn't clear DB");
        }
    }

    public void createUser(UserData user) throws DataAccessException {
//        String hashedPassword = BCrypt.hasw(user.password(), BCrypt.gensalt());
        //TRY TO GET STUPID BCRYPT TO WORK AGAIN

        var statement = "INSERT INTO users (username, hashed_password, email) VALUES (?,?,?)";
        executeUpdate(statement, user.username(), user.password(), user.email());
    }

    public UserData getUser(String username) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, hashed_password, email FROM users WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                try (var results = ps.executeQuery()) {
                    results.next();
                    results.getString("username");
                    var password = results.getString("hashed_password");
                    var email = results.getString("email");
                    return new UserData(username, password, email);
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
            throw new DataAccessException("Error, could not execute update");
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
    private void configureDatabase() throws DataAccessException {
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
