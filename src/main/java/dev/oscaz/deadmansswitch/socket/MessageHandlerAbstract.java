package dev.oscaz.deadmansswitch.socket;

import dev.oscaz.deadmansswitch.pojo.WebSocketMessage;
import org.eclipse.jetty.websocket.api.Session;

public abstract class MessageHandlerAbstract {

    private final String contentType;

    public MessageHandlerAbstract(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return this.contentType;
    }

    public abstract void run(Session user, WebSocketMessage message);
}
