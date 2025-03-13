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
}
