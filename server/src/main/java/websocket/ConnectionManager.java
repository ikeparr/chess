package websocket;

import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {

    public final ConcurrentHashMap<Integer, Set<Session>> connections = new ConcurrentHashMap<>();

    public void add(Integer gameID, Session session) {
        Set<Session> sessions = getSession(gameID);
        if (sessions == null) {
            Set<Session> ogSession = new HashSet<>();
            ogSession.add(session);
            connections.put(gameID, ogSession);
        }
        else {
            sessions.add(session);
        }
    }

    public void remove(Integer gameID, Session session) {
        Set<Session> sessions = getSession(gameID);
        sessions.remove(session);
    }

    public Set<Session> getSession(Integer gameID) {
        return connections.get(gameID);
    }
}
