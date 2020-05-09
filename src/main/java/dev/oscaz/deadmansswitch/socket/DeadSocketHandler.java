package dev.oscaz.deadmansswitch.socket;

import dev.oscaz.deadmansswitch.DeadMansSwitch;
import dev.oscaz.deadmansswitch.pojo.WebSocketMessage;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;
import java.util.stream.IntStream;

@WebSocket
public class DeadSocketHandler {

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {

    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {

    }

    @OnWebSocketMessage
    public void onMessage(Session user, String data) {
        WebSocketMessage message = DeadMansSwitch.GSON.fromJson(data, WebSocketMessage.class);
        message.setRawJson(data);
        MessageHandler.runMessageHandler(user, message);
    }

    // Util method to send session a message (json)
    private void sendMessage(String message, Session session) {
        try { session.getRemote().sendString(message); }
        catch (IOException e) { throw new RuntimeException(e); }
    }

}
