package dataaccess;

import model.UserData;
import java.util.HashMap;
import java.util.Map;

public class MemoryUser implements UserDAO {
    private final Map<String, UserData> users = new HashMap<>();

    public void clear() throws DataAccessException {
        users.clear();
    }

    public void createUser(UserData user) throws DataAccessException {
        if (users.containsKey(user.username())) {
            throw new DataAccessException("Username already taken :(");
        }
        users.put(user.username(), user);
    }

    public UserData getUser(String username) throws DataAccessException{
        return users.get(username);
    }
}
