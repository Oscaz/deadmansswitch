package dev.oscaz.deadmansswitch.socket;

import org.eclipse.jetty.websocket.api.Session;

import java.util.UUID;

public class LiveSocketConnection {

    private final String socketId = UUID.randomUUID().toString();
    private Session session;

    public LiveSocketConnection() {

    }

    public Session getSession() {
        return this.session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getSocketId() {
        return this.socketId;
    }
}
