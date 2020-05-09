package dev.oscaz.deadmansswitch.pojo;

import java.util.concurrent.TimeUnit;

public class NewSwitchMessage extends WebSocketMessage {

    private final String name;
    private final String data;
    private final int period;
    private final String timeUnit;

    public NewSwitchMessage(String contentType, String contents, String name, String data, int period, String timeUnit) {
        super(contentType, contents);
        this.name = name;
        this.data = data;
        this.period = period;
        this.timeUnit = timeUnit;
    }

    public String getName() {
        return this.name;
    }

    public String getData() {
        return this.data;
    }

    public int getPeriod() {
        return this.period;
    }

    public String getTimeUnit() {
        return this.timeUnit;
    }
}
