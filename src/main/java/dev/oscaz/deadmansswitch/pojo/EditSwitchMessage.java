package dev.oscaz.deadmansswitch.pojo;

public class EditSwitchMessage extends WebSocketMessage {

    private final String authKey;
    private final String newData;

    public EditSwitchMessage(String type, String contents, String authKey, String newData) {
        super(type, contents);
        this.authKey = authKey;
        this.newData = newData;
    }

    public String getAuthKey() {
        return this.authKey;
    }

    public String getNewData() {
        return this.newData;
    }
}
