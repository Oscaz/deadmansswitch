package dev.oscaz.deadmansswitch.socket.handler;

import dev.oscaz.deadmansswitch.SwitchGulag;
import dev.oscaz.deadmansswitch.gen.KeyGenerator;
import dev.oscaz.deadmansswitch.pojo.EditSwitchMessage;
import dev.oscaz.deadmansswitch.pojo.LiveMansSwitch;
import dev.oscaz.deadmansswitch.pojo.WebSocketMessage;
import dev.oscaz.deadmansswitch.socket.MessageHandlerAbstract;
import org.eclipse.jetty.websocket.api.Session;

public class EditSwitchHandler extends MessageHandlerAbstract {

    public EditSwitchHandler() {
        super("edit-switch");
    }

    @Override
    public void run(Session user, WebSocketMessage message) {
        EditSwitchMessage editSwitchMessage = (EditSwitchMessage) message;
        String authKey = editSwitchMessage.getAuthKey();

        if (!KeyGenerator.authenticateDeadKey(authKey)) {
            return; // dont need to checkin for incorrect keys.
        }
        if (SwitchGulag.getSwitch(authKey) == null) {
            return; // no switch exists with such key.
        }

        LiveMansSwitch liveMansSwitch = SwitchGulag.getSwitch(authKey);
        liveMansSwitch.setData(editSwitchMessage.getNewData()); // done
    }
}
