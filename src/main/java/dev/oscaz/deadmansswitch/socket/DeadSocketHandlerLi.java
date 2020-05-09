package dev.oscaz.deadmansswitch.socket;

import dev.oscaz.deadmansswitch.DeadMansSwitch;
import dev.oscaz.deadmansswitch.pojo.WebSocketMessage;

public class DeadSocketHandlerLi {

    public String handleMessage(String data) {
        System.out.println("DATA RECEIVED FOR HANDLER: " + data);
        WebSocketMessage message = DeadMansSwitch.GSON.fromJson(data, WebSocketMessage.class);
        message.setRawJson(data);
        MessageHandler.runMessageHandler(null, message);
        return null;
    }
}
