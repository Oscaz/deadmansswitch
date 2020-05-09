package dev.oscaz.deadmansswitch.socket.handler;

import dev.oscaz.deadmansswitch.SwitchGulag;
import dev.oscaz.deadmansswitch.gen.KeyGenerator;
import dev.oscaz.deadmansswitch.pojo.WebSocketMessage;
import dev.oscaz.deadmansswitch.socket.MessageHandlerAbstract;
import org.eclipse.jetty.websocket.api.Session;

import java.time.Instant;

public class CheckinHandler extends MessageHandlerAbstract {

    public CheckinHandler() {
        super("check-in");
    }

    @Override
    public void run(Session user, WebSocketMessage message) {
        String authKey = message.getContents();
        if (!KeyGenerator.authenticateDeadKey(authKey)) {
            return; // dont need to checkin for incorrect keys.
        }
        if (SwitchGulag.getSwitch(authKey) == null) {
            return; // no switch exists with such key.
        }
        SwitchGulag.getSwitch(authKey).setLastCheckIn(Instant.now());
    }
}
