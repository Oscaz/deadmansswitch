package dev.oscaz.deadmansswitch.socket.handler;

import dev.oscaz.deadmansswitch.SparkPosts;
import dev.oscaz.deadmansswitch.SwitchGulag;
import dev.oscaz.deadmansswitch.gen.KeyGenerator;
import dev.oscaz.deadmansswitch.pojo.DeferSwitchMessage;
import dev.oscaz.deadmansswitch.pojo.WebSocketMessage;
import dev.oscaz.deadmansswitch.socket.MessageHandlerAbstract;
import org.eclipse.jetty.websocket.api.Session;

import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

public class DeferSwitchHandler extends MessageHandlerAbstract {

    public DeferSwitchHandler() {
        super("defer-switch");
    }

    @Override
    public void run(Session user, WebSocketMessage message) {
        DeferSwitchMessage deferSwitchMessage = (DeferSwitchMessage) message;
        String authKey = deferSwitchMessage.getAuthKey();
        if (!KeyGenerator.authenticateDeadKey(authKey)) {
            return; // dont need to checkin for incorrect keys.
        }
        if (SwitchGulag.getSwitch(authKey) == null) {
            return; // no switch exists with such key.
        }
        SwitchGulag.getSwitch(authKey).setLastCheckIn(Instant.now().plusSeconds(SparkPosts.parseTimeUnit(deferSwitchMessage.getTimeUnit()) * deferSwitchMessage.getPeriod()));
    }
}
