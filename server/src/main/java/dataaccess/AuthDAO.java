package dataaccess;
import model.AuthData;

public interface AuthDAO {

    void clear() throws DataAccessException;
    void createAuth(AuthData auth) throws DataAccessException;
    void deleteAuth(String authToken) throws DataAccessException;
    /// just like userdao, check if better way ///
    AuthData getAuth(String authToken) throws DataAccessException;

}
