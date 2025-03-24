package ui;

import com.google.gson.Gson;
import exceptions.ResponseException;
import model.GameData;
import model.UserData;
import model.AuthData;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.*;

public class ServerFacade {

    private final String serverURL;

    public ServerFacade(int port) {
        serverURL = "http://localhost:" + port;
    }

    public AuthData registerUser(String username, String password, String email) throws ResponseException {
        var path = "/user";
        UserData user = new UserData(username, password, email);
        return this.makeRequest("POST", path, user, AuthData.class, null);
    }

    public AuthData loginUser(String username, String password) throws ResponseException {
        var path = "/session";
        record loginReq(String username, String password) {}
        return this.makeRequest("POST", path, new loginReq(username, password), AuthData.class, null);
    }

    public void logoutUser(String authToken) throws  ResponseException {
        var path = "/session";
        this.makeRequest("DELETE", path, null, null, authToken);
//        authToken = null;
    }

    public int createGame(String gameName, String authToken) throws ResponseException {
        var path = "/game";
        record GameID(int gameID) {};
        record GameReq(String gameName) {}
        GameID response = this.makeRequest("POST", path, new GameReq(gameName), GameID.class, authToken);
        return response.gameID();
    }

    public void joinGame(String username, String color, int gameID, String authToken) throws ResponseException {
        var path = "/game";
        record joinGame(String username, String color, int gameID) {};
        this.makeRequest("PUT", path, new joinGame(username, color, gameID), null, authToken);
    }

    public GameData[] listGames(String authToken) throws ResponseException {
        var path = "/game";
        record listGames(GameData[] games) {}
        var response = this.makeRequest("GET", path, null, listGames.class, authToken);
        return response.games();
    }



    /// MOSTLY PETSHOP CODE BELOW ///
    // CREATE REQUEST
    /// /// Check if can pass in authtoken in make request /// ///
    public <T> T makeRequest(String method, String path, Object request, Class<T> responseClass, String authToken) throws ResponseException {
        try {
            URL url = (new URI(serverURL + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);
            //MUST HAVE AUTHORIZATION FOR LISTGAME, JOINGAME, CREATEGAME, LOGOUT
            if (authToken != null) {
                http.addRequestProperty("Authorization", authToken);
            }
            //CREATE BODY
            writeBody(request, http);
            //SEND REQ TO SERVER
            http.connect();
            //MAKE SURE IS VALID
            throwIfNotSuccessful(http);
            //GET RESPONSE
            return readBody(http, responseClass);
        }
        catch (ResponseException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }

    }

    //CREATE REQ BODY
    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    //MAKE SURE IS VALID
    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            try (InputStream respErr = http.getErrorStream()) {
                if (respErr != null) {
                    throw ResponseException.fromJson(respErr, status);
                }
            }

            throw new ResponseException(status, "other failure: " + status);
        }
    }

    //READ BODY, RETURN RESPONSE
    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }


    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}
