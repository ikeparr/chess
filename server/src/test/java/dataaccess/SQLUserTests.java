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

}
