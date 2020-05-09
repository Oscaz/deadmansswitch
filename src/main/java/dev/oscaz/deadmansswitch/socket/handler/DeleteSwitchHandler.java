package dev.oscaz.deadmansswitch.socket.handler;

import dev.oscaz.deadmansswitch.SwitchGulag;
import dev.oscaz.deadmansswitch.gen.KeyGenerator;
import dev.oscaz.deadmansswitch.pojo.WebSocketMessage;
import dev.oscaz.deadmansswitch.socket.MessageHandlerAbstract;
import org.eclipse.jetty.websocket.api.Session;

public class DeleteSwitchHandler extends MessageHandlerAbstract {

    public DeleteSwitchHandler() {
        super("delete-switch");
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
        SwitchGulag.deleteSwitch(SwitchGulag.getSwitch(authKey));
    }
}
