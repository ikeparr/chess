package dataaccess;
import model.UserData;

public interface UserDAO {

    void clear() throws DataAccessException;
    void createUser(UserData user) throws DataAccessException;
    /// ASSESS WHETHER THERE IS A MORE EFFICIENT WAY
    UserData getUser(String username) throws DataAccessException;
}
