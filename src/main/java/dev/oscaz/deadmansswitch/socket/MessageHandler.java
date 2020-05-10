package dev.oscaz.deadmansswitch.socket;

import dev.oscaz.deadmansswitch.DeadMansSwitch;
import dev.oscaz.deadmansswitch.exception.InvalidContentTypeException;
import dev.oscaz.deadmansswitch.pojo.DeferSwitchMessage;
import dev.oscaz.deadmansswitch.pojo.EditSwitchMessage;
import dev.oscaz.deadmansswitch.pojo.NewSwitchMessage;
import dev.oscaz.deadmansswitch.pojo.WebSocketMessage;
import dev.oscaz.deadmansswitch.socket.handler.*;
import org.eclipse.jetty.websocket.api.Session;

public enum MessageHandler {

    CHECKIN_HANDLER(CheckinHandler.class),
    EDIT_HANDLER(EditSwitchHandler.class, EditSwitchMessage.class),
    DELETE_HANDLER(DeleteSwitchHandler.class),
    DEFER_HANDLER(DeferSwitchHandler.class, DeferSwitchMessage.class);

    private final Class<? extends MessageHandlerAbstract> clazz;
    private final MessageHandlerAbstract messageHandler;
    private final Class<? extends WebSocketMessage> messageClass;

    MessageHandler(Class<? extends MessageHandlerAbstract> clazz) {
        try {
            this.clazz = clazz;
            this.messageHandler = clazz.newInstance();
            this.messageClass = null;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    MessageHandler(Class<? extends MessageHandlerAbstract> clazz, Class<? extends WebSocketMessage> messageClass) {
        try {
            this.clazz = clazz;
            this.messageHandler = clazz.newInstance();
            this.messageClass = messageClass;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Class<? extends WebSocketMessage> getMessageClass() {
        return this.messageClass;
    }

    public Class<? extends MessageHandlerAbstract> getClazz() {
        return this.clazz;
    }

    public MessageHandlerAbstract getMessageHandler() {
        return this.messageHandler;
    }

    private static MessageHandler getEnumHandlerByString(String contentType) {
        for (MessageHandler messageHandler : MessageHandler.values()) {
            if (messageHandler.getClazz() == null) continue;
            if (messageHandler.getMessageHandler().getContentType().equalsIgnoreCase(contentType)) {
                return messageHandler;
            }
        }
        throw new InvalidContentTypeException("No content type found for " + contentType + "!");
    }

    public static MessageHandlerAbstract getHandlerByString(String contentType) {
        for (MessageHandler messageHandler : MessageHandler.values()) {
            if (messageHandler.getClazz() == null) continue;
            if (messageHandler.getMessageHandler().getContentType().equalsIgnoreCase(contentType)) {
                return messageHandler.getMessageHandler();
            }
        }
        throw new InvalidContentTypeException("No content type found for " + contentType + "!");
    }

    public static void runMessageHandler(Session user, WebSocketMessage message) {
        MessageHandler messageHandler = getEnumHandlerByString(message.getContentType());
        if (messageHandler.messageClass == null) {
            messageHandler.getMessageHandler().run(user, message);
        } else {
            messageHandler.getMessageHandler().run(
                    user,
                    DeadMansSwitch.GSON.fromJson(
                            message.getRawJson(), messageHandler.messageClass)
            );
        }
    }
}
