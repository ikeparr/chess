package dataaccess;

import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SQLUserTests {
    private SQLUser sqlUser;

    @BeforeEach
    void setUp() throws DataAccessException {
        DatabaseManager.createDatabase();
        sqlUser = new SQLUser();
        sqlUser.clear();
    }

    /// USER CREATION TESTS ///
    @Test
    void createUserSuccessful() throws DataAccessException {
        UserData user = new UserData("testUser", "testPassword", "test@email.com");
        boolean successfulTest = true;
        try {
            sqlUser.createUser(user);
        } catch (DataAccessException error) {
            successfulTest = false;
        }
        assert successfulTest;
        // Verify the user was added
        UserData result = sqlUser.getUser("testUser");
        assert result != null;
        assert "testUser".equals(result.username());
        assert "test@email.com".equals(result.email());
    }
    @Test
    void createUserFail() throws DataAccessException {
        UserData user = new UserData("failUser", "failPassword", "fail@email.com");
        sqlUser.createUser(user);
        boolean failTest = false;
        try {
            sqlUser.createUser(user);
        }
        catch (DataAccessException error) {
            failTest = true;
        }
        assert failTest;
    }


    /// GET USER TESTS ///
    @Test
    void getUserSuccessful() throws DataAccessException {
        UserData user = new UserData("getUser", "getPassword", "getUser@email.com");
        sqlUser.createUser(user);
        boolean successfulTest = true;
        UserData result = null;
        try {
            result = sqlUser.getUser("getUser");
        }
        catch (DataAccessException error) {
            successfulTest = false;
        }
        assert successfulTest;
        assert result != null;
        assert "getUser".equals(result.username());
    }
    @Test
    void getUserFail() throws DataAccessException {
        boolean failTest = false;
        UserData result = null;
        try {
            result = sqlUser.getUser("getUser2");
            if (result != null) {
                failTest = true;
            }
        }
        catch (DataAccessException error) {
            failTest = true;
        }
        assert !failTest;
    }


    /// CLEAR TEST ///
    @Test
    void clearSuccessful() throws DataAccessException {
        UserData user = new UserData("testUser", "testPassword", "test@email.com");
        sqlUser.createUser(user);

        boolean successfulTest = true;
        try {
            sqlUser.clear();
        } catch (DataAccessException error) {
            successfulTest = false;
        }
        assert successfulTest;

        // Verify the table is empty
        UserData retrieved = sqlUser.getUser("testUser");
        assert retrieved == null;
    }
}
