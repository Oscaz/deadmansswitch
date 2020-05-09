package dev.oscaz.deadmansswitch.pojo;

public class DeferSwitchMessage extends WebSocketMessage {

    private final String authKey;
    private final int period;
    private final String timeUnit;

    public DeferSwitchMessage(String type, String contents, String authKey, int period, String timeUnit) {
        super(type, contents);
        this.authKey = authKey;
        this.period = period;
        this.timeUnit = timeUnit;
    }

    public String getAuthKey() {
        return this.authKey;
    }

    public int getPeriod() {
        return this.period;
    }

    public String getTimeUnit() {
        return this.timeUnit;
    }
}
