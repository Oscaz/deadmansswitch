package dev.oscaz.deadmansswitch;

import dev.oscaz.deadmansswitch.pojo.LiveMansSwitch;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class SwitchReaperService {

    public static void initialize() {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
        executorService.scheduleAtFixedRate(() -> {
            List<LiveMansSwitch> toKill = SwitchGulag.getLiveSwitches().stream()
                    .filter(LiveMansSwitch::hasBecomeDead)
                    .collect(Collectors.toList());
            toKill.forEach(SwitchGulag::killSwitch);
        }, 1, 1, TimeUnit.SECONDS);
    }

}
