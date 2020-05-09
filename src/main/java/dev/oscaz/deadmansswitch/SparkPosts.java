package dev.oscaz.deadmansswitch;

import com.google.gson.Gson;
import dev.oscaz.deadmansswitch.pojo.LiveMansSwitch;
import dev.oscaz.deadmansswitch.pojo.NewSwitchMessage;
import spark.Spark;

import java.time.Duration;
import java.time.Instant;

public class SparkPosts {

    public static void initialize() {
        Spark.post("/switch-info/", (request, response) -> {
            try {
                return DeadMansSwitch.GSON.toJson(SwitchGulag.getSwitch(request.body()));
            } catch (IllegalArgumentException e) {
                return 0;
            }
        });

        Spark.post("/new-switch/", (request, response) -> {
            NewSwitchMessage newSwitchMessage = DeadMansSwitch.GSON.fromJson(request.body(), NewSwitchMessage.class);
            LiveMansSwitch liveMansSwitch = new LiveMansSwitch(
                    newSwitchMessage.getName().equalsIgnoreCase("") ? "Anonymous" : newSwitchMessage.getName(),
                    newSwitchMessage.getData(),
                    Instant.now(), Instant.now(),
                    Duration.ofSeconds(newSwitchMessage.getPeriod() *
                            parseTimeUnit(newSwitchMessage.getTimeUnit()))
            );
            SwitchGulag.addSwitch(liveMansSwitch);

            return liveMansSwitch.getAuthKey();
        });

        Spark.post("/dead-switches/", (request, response) -> {
            return DeadMansSwitch.GSON.toJson(SwitchGulag.getDeadSwitches());
        });

        Spark.post("/dead-switch-info/", (request, response) -> {
            try {
                return DeadMansSwitch.GSON.toJson(SwitchGulag.getDeadSwitch(request.body()));
            } catch (IllegalArgumentException e) {
                return 0;
            }
        });
    }

    public static int parseTimeUnit(String unitName) {
        switch (unitName) {
            case "Minutes": return 60;
            case "Hours": return parseTimeUnit("Minutes") * 60;
            case "Days": return parseTimeUnit("Hours") * 24;
            case "Weeks": return parseTimeUnit("Days") * 7;
            case "Months": return parseTimeUnit("Weeks") * 4 + parseTimeUnit("Days") * 2;
            case "Years": return parseTimeUnit("Days") * 365;
        }
        return 1;
    }
}
