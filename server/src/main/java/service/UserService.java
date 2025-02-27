package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;

import java.util.UUID;

public class UserService {

    /// NOTE: AuthService merged into UserService, they're both used together a lot ///
    private final UserDAO userDAO;
    private final AuthDAO authDAO;

    public UserService(UserDAO userDAO, AuthDAO authDAO){
        this.userDAO = userDAO;
        this.authDAO = authDAO;

    }

    public void clear() throws DataAccessException{
        userDAO.clear();
        authDAO.clear();
    }

    ///
    /// test stuff for register *REVIEW AND ADJUST WITH SAMUEL*
    ///
    public AuthData register(UserData user) throws DataAccessException{
        /// Error cases
        if (user.username() == null || user.password() == null || user.email() == null) {
            throw new DataAccessException("bad request: null parameters");
        }
        if (user.username().isEmpty() || user.email().isEmpty() || user.password().isEmpty()) {
            throw new DataAccessException("bad request: empty parameters");
        }
        if (userDAO.getUser(user.username()) != null) {
            throw new DataAccessException("Username already taken");
        }

        userDAO.createUser(user);
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, user.username());
        authDAO.createAuth(authData);

        return authData;
    }

}
