package dev.oscaz.deadmansswitch;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.oscaz.deadmansswitch.io.FileHandler;
import dev.oscaz.deadmansswitch.web.WebServer;
import org.slf4j.LoggerFactory;
import spark.Spark;

public class DeadMansSwitch {

    public static final Gson GSON = new GsonBuilder().create();

    public static void main(String[] args) {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.INFO);

        WebServer.initialize();

        SwitchGulag.addSwitches(FileHandler.extractLiveSwitches());
        SwitchGulag.addDeadSwitches(FileHandler.extractDeadSwitches());

        Spark.get("/deadmansswitch", WebServer.resolve("/index.vtl"));
        Spark.get("/createswitch", WebServer.resolve("/createswitch.vtl"));
        Spark.get("/checkswitch", WebServer.resolve("/checkswitch.vtl"));
        Spark.get("/switchview", WebServer.resolve("/switchview.vtl"));
        Spark.get("/deadswitches", WebServer.resolve("/deadswitches.vtl"));
        Spark.get("/deadswitchview", WebServer.resolve("/deadswitchview.vtl"));

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            FileHandler.saveLiveSwitches(SwitchGulag.getLiveSwitchMap());
            FileHandler.saveDeadSwitches(SwitchGulag.getDeadSwitches());
        }));

        SparkPosts.initialize();
        SwitchReaperService.initialize();
    }

}
