package dataaccess;

import model.UserData;
import model.AuthData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class SQLAuthTests {
    private SQLAuth sqlAuth;
    private SQLUser sqlUser;

    @BeforeEach
    void setUp() throws DataAccessException {
        DatabaseManager.createDatabase();
        sqlUser = new SQLUser();
        sqlAuth = new SQLAuth();
        sqlAuth.clear();
        sqlUser.clear();
    }

    @Test
    void createAuthSuccessful() throws DataAccessException {
        sqlUser.createUser(new UserData("testAuthUserBOBBY", "password", "auth@gmail.com"));
        String authToken = UUID.randomUUID().toString();
        AuthData input = new AuthData(authToken, "testAuthUserBOBBY");
        boolean successfulTest = true;
        try {
            sqlAuth.createAuth(input);
        }
        catch (DataAccessException error) {
            successfulTest = false;
        }
        assert successfulTest;
    }
    @Test
    void createAuthFail() throws DataAccessException {
        String authToken = UUID.randomUUID().toString();
        AuthData input = new AuthData(authToken, "fakeUsername");
        boolean failTest = false;
        try {
            sqlAuth.createAuth(input);
            sqlAuth.createAuth(input);
        }
        catch (DataAccessException error) {
            failTest = true;
        }
        assert failTest;
    }


    @Test
    void getAuthSuccessful() throws DataAccessException {
        sqlUser.createUser(new UserData("testAuthUser", "password", "auth@gmail.com"));
        String authToken = UUID.randomUUID().toString();
        AuthData input = new AuthData(authToken, "testAuthUser");
        sqlAuth.createAuth(input);
        AuthData results = null;
        boolean successfulTest = true;
        try {
            results = sqlAuth.getAuth(authToken);
        }
        catch (DataAccessException error) {
            successfulTest = false;
        }
        assert successfulTest;
        assert results != null;
        assert authToken.equals(results.authToken());
    }
    @Test
    void getAuthFail() throws DataAccessException {
        boolean failTest = false;
        AuthData results = null;
        try {
            results = sqlAuth.getAuth("fakeAuthToken");
            if (results != null) {
                failTest = true;
            }
        }
        catch (DataAccessException error) {
            failTest = true;
        }
        assert !failTest;
    }


    @Test
    void deleteAuthSuccessful() throws DataAccessException {
        sqlUser.createUser(new UserData("deleteUser", "password", "delete@email.com"));
        String authToken = UUID.randomUUID().toString();
        AuthData auth = new AuthData(authToken, "deleteUser");
        sqlAuth.createAuth(auth);
        boolean successfulTest = true;
        try {
            sqlAuth.deleteAuth(authToken);
        } catch (DataAccessException error) {
            successfulTest = false;
        }
        assert successfulTest;
        assert sqlAuth.getAuth(authToken) == null;
    }


    @Test
    void clearSuccessful() throws DataAccessException {
        sqlUser.createUser(new UserData("deleteUser", "password", "delete@email.com"));
        String authToken = UUID.randomUUID().toString();
        AuthData auth = new AuthData(authToken, "deleteUser");
        sqlAuth.createAuth(auth);
        boolean successfulTest = true;
        try {
            sqlAuth.clear();
        }
        catch (DataAccessException error) {
            successfulTest = false;
        }
        assert successfulTest;
        AuthData retrieved = sqlAuth.getAuth(authToken);
        assert retrieved == null;
    }

}
