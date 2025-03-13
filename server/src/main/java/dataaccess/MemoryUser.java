package dataaccess;

import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

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
        String hashedPassword = BCrypt.hashpw(user.password(), BCrypt.gensalt());
        UserData hashedUser = new UserData(user.username(), hashedPassword, user.email());
        users.put(user.username(), hashedUser);
    }

    public UserData getUser(String username) throws DataAccessException{
        return users.get(username);
    }
}
