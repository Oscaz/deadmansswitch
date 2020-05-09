package dev.oscaz.deadmansswitch.socket.handler;

import dev.oscaz.deadmansswitch.DeadMansSwitch;
import dev.oscaz.deadmansswitch.pojo.WebSocketMessage;
import dev.oscaz.deadmansswitch.socket.DeadSocketHandler;
import dev.oscaz.deadmansswitch.socket.LiveSocketConnection;
import dev.oscaz.deadmansswitch.socket.MessageHandlerAbstract;
import org.eclipse.jetty.websocket.api.Session;
import spark.Spark;

public class AuthenticateHandler extends MessageHandlerAbstract {


    public AuthenticateHandler() {
        super("authenticate");
        Spark.post("/authenticate/", ((request, response) -> {
            if (request.session(true).attribute("socket-id") != null) {
                return request.session().attribute("socket-id");
            } else {
                LiveSocketConnection connection = new LiveSocketConnection();
                DeadMansSwitch.sockets.put(connection.getSocketId(), connection);
                request.session(true).attribute("socket-id", connection.getSocketId());
                return connection.getSocketId();
            }
        }));
    }

    @Override
    public void run(Session user, WebSocketMessage message) {
        // update socket to fit authentication uuid
        DeadMansSwitch.sockets.get(message.getContents()).setSession(user);
    }
}
