package dev.oscaz.deadmansswitch;

import dev.oscaz.deadmansswitch.gen.KeyGenerator;
import dev.oscaz.deadmansswitch.pojo.LiveMansSwitch;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SwitchGulag {

    private static final Map<String, LiveMansSwitch> liveSwitches = new ConcurrentHashMap<>();

    private static final List<LiveMansSwitch> deadSwitches = new ArrayList<>();

    public static void addSwitch(LiveMansSwitch liveMansSwitch) {
        liveSwitches.put(liveMansSwitch.getAuthKey(), liveMansSwitch);
    }

    public static LiveMansSwitch getSwitch(String authKey) {
        if (!KeyGenerator.authenticateDeadKey(authKey)) {
            throw new IllegalArgumentException("Authentication key is not valid!");
        }
        return liveSwitches.get(authKey);
    }

    public static void deleteSwitch(LiveMansSwitch liveMansSwitch) {
        liveSwitches.remove(liveMansSwitch.getAuthKey());
    }

    public static void killSwitch(LiveMansSwitch liveMansSwitch) {
        liveSwitches.remove(liveMansSwitch.getAuthKey());
        deadSwitches.add(0, liveMansSwitch);
    }

    public static Collection<LiveMansSwitch> getLiveSwitches() {
        return Collections.unmodifiableCollection(liveSwitches.values());
    }

    public static void addSwitches(Map<String, LiveMansSwitch> switchMap) {
        liveSwitches.putAll(switchMap);
    }

    public static void addDeadSwitches(Collection<LiveMansSwitch> switches) {
        deadSwitches.addAll(switches);
    }

    public static Map<String, LiveMansSwitch> getLiveSwitchMap() {
        return Collections.unmodifiableMap(liveSwitches);
    }

    public static List<LiveMansSwitch> getDeadSwitches() {
        return Collections.unmodifiableList(deadSwitches);
    }

    public static LiveMansSwitch getDeadSwitch(String authKey) {
        return deadSwitches.parallelStream()
                .filter(deadSwitch -> deadSwitch.getAuthKey().equalsIgnoreCase(authKey))
                .findFirst().orElse(null);
    }

}
