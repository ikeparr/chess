package ui;

import com.google.gson.*;
import com.google.gson.Gson;
import exceptions.ResponseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.net.*;

public class ServerFacade {

    private final String serverURL;

    public ServerFacade(String url) {
        serverURL = url;
    }


    /// MOSTLY PETSHOP CODE BELOW ///
    // CREATE REQUEST
    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws ResponseException {
        try {
            URL url = (new URI(serverURL + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);
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
        if (http.getResponseCode() != 200) {
            throw new ResponseException("Error: " + http.getResponseCode());
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
