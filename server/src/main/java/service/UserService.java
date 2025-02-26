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
}
