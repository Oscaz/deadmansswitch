package dev.oscaz.deadmansswitch.io;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import dev.oscaz.deadmansswitch.SwitchGulag;
import dev.oscaz.deadmansswitch.pojo.LiveMansSwitch;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileHandler {

    private static final File LIVE_FILE;
    private static final File DEAD_FILE;

    static {
        LIVE_FILE = Paths.get(Paths.get(System.getProperty("user.dir")).toString() + "/live_switches.json").toFile();
        DEAD_FILE = Paths.get(Paths.get(System.getProperty("user.dir")).toString() + "/dead_switches.json").toFile();
        try {
            if (!LIVE_FILE.exists()) {
                LIVE_FILE.createNewFile();
            }
            if (!DEAD_FILE.exists()) {
                DEAD_FILE.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, LiveMansSwitch> extractLiveSwitches() {
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, LiveMansSwitch>>() {}.getType();
            FileReader fileReader = new FileReader(LIVE_FILE);
            Map<String, LiveMansSwitch> liveMansSwitchMap = gson.fromJson(fileReader, type);
            fileReader.close();
            return liveMansSwitchMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Maps.newConcurrentMap();
    }

    public static List<LiveMansSwitch> extractDeadSwitches() {
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<List<LiveMansSwitch>>() {}.getType();
            FileReader fileReader = new FileReader(DEAD_FILE);
            List<LiveMansSwitch> deadSwitches = gson.fromJson(fileReader, type);
            fileReader.close();
            return deadSwitches;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Lists.newArrayList();
    }

    public static void saveLiveSwitches(Map<String, LiveMansSwitch> switchMap) {
        try {
            new FileWriter(LIVE_FILE, false).close();
            FileWriter fileWriter = new FileWriter(LIVE_FILE);
            Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(gson.toJson(switchMap));

            fileWriter.write(gson.toJson(jsonElement));
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveDeadSwitches(List<LiveMansSwitch> deadSwitches) {
        try {
            new FileWriter(DEAD_FILE, false).close();
            FileWriter fileWriter = new FileWriter(DEAD_FILE);
            Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(gson.toJson(deadSwitches));

            fileWriter.write(gson.toJson(jsonElement));
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
