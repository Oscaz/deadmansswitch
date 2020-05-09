package dev.oscaz.deadmansswitch.pojo;

import dev.oscaz.deadmansswitch.gen.KeyGenerator;

import java.time.Duration;
import java.time.Instant;

public class LiveMansSwitch {

    private final String authKey;

    private final String name;
    private String data;
    private final Instant creationTime;
    private Instant lastCheckIn;
    private final Duration checkInDuration;

    public LiveMansSwitch(String name, String data, Instant creationTime, Instant lastCheckIn, Duration checkInDuration) {
        this.authKey = KeyGenerator.generateDeadKey();

        this.name = name;
        this.data = data;
        this.creationTime = creationTime;
        this.lastCheckIn = lastCheckIn;
        this.checkInDuration = checkInDuration;
    }

    public String getAuthKey() {
        return this.authKey;
    }

    public String getName() {
        return this.name;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Instant getCreationTime() {
        return this.creationTime;
    }

    public Instant getLastCheckIn() {
        return lastCheckIn;
    }

    public void setLastCheckIn(Instant lastCheckin) {
        this.lastCheckIn = lastCheckin;
    }

    public Duration getCheckInDuration() {
        return this.checkInDuration;
    }

    public boolean hasBecomeDead() {
        return this.lastCheckIn.plus(this.checkInDuration).isBefore(Instant.now());
    }

}
