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

    public AuthData login(String username, String password) throws DataAccessException {
        /// error cases
        if (username == null || password == null) {
            throw new DataAccessException("unauthorized, null parameters");
        }
        if (username.isEmpty() || password.isEmpty()) {
            throw new DataAccessException("unauthorized, empty parameters");
        }
        //check if user exists and if password is right
        UserData user = userDAO.getUser(username);
        if (user == null || !user.password().equals(password)) {
            throw new DataAccessException("unauthorized");
        }

        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, user.username());
        authDAO.createAuth(authData);

        return authData;
    }

    public AuthData logout(String authToken) throws DataAccessException {
        /// error cases
        if (authToken == null || authToken.isEmpty()) {
            throw new DataAccessException("unauthorized, authToken is null or empty");
        }
        //checdk if authToken exists
        AuthData auth = authDAO.getAuth(authToken);
        if (auth == null ) {
            throw new DataAccessException("unauthorized");
        }

        authDAO.deleteAuth(authToken);
        return auth;
    }

}
