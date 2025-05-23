package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;

public class UserService {
    /// NOTE: AuthService merged into UserService, they're both used together a lot ///
    private final UserDAO userDAO;
    private final AuthDAO authDAO;

    public UserService(UserDAO userDAO, AuthDAO authDAO){
        this.userDAO = userDAO;
        this.authDAO = authDAO;

    }

    ///  NOTE: used in GameService so I dont have to have authDAO in there
    public AuthData validateAuthToken(String authToken) throws DataAccessException{
        if (authToken == null || authToken.isEmpty()){
            throw new DataAccessException("ErrorL unauthorized, missing authToken");
        }
        AuthData auth = authDAO.getAuth(authToken);
        if (auth == null){
            throw new DataAccessException("Error: unauthorized, invalid authToken");
        }
        return auth;
    }


    public void clear() throws DataAccessException{
        userDAO.clear();
        authDAO.clear();
    }


    public AuthData register(UserData user) throws DataAccessException{
        // VALIDATE INPUTS
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
        // STORES authToken
        authDAO.createAuth(authData);

        return authData;
    }


    public AuthData login(String username, String password) throws DataAccessException {
        // VALIDATE INPUTS
        if (username == null || password == null) {
            throw new DataAccessException("unauthorized, null parameters");
        }
        if (username.isEmpty() || password.isEmpty()) {
            throw new DataAccessException("unauthorized, empty parameters");
        }
        // VERIFY CREDENTIALS
        UserData user = userDAO.getUser(username);
        if (user == null || !BCrypt.checkpw(password, user.password())) {
            throw new DataAccessException("unauthorized");
        }
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, user.username());
        authDAO.createAuth(authData);

        return authData;
    }


    public void logout(String authToken) throws DataAccessException {
        // VALIDATE INPUTS
        if (authToken == null || authToken.isEmpty()) {
            throw new DataAccessException("unauthorized, authToken is null or empty");
        }
        // CHECK IF authToken EXISTS
        AuthData auth = authDAO.getAuth(authToken);
        if (auth == null ) {
            throw new DataAccessException("unauthorized");
        }

        authDAO.deleteAuth(authToken);
    }
}
