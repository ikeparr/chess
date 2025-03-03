package dataaccess;
import model.AuthData;
import java.util.HashMap;
import java.util.Map;

public class MemoryAuth implements AuthDAO {
    private final Map<String, AuthData> authTokens = new HashMap<>();

    public void clear() throws DataAccessException {
        authTokens.clear();
    }

    public void createAuth(AuthData auth) throws DataAccessException {
        authTokens.put(auth.authToken(), auth);
    }

    public AuthData getAuth(String authToken) throws DataAccessException{
        return authTokens.get(authToken);
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        authTokens.remove(authToken);
    }
}
