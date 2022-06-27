package me.devpanda.pax.utils;

import me.devpanda.pax.PAX;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Configuration {
    private static final PAX plugin = PAX.plugin;

    public static String configPath = "plugins/PAX/config.yml";
    public static String dataPath = "plugins/PAX/data";
    public static YamlConfiguration config;

    public static boolean allowDebugMessages;
    public static boolean allowStaffAlerts;

    public static boolean enableCreativeGiveModule;

    public static void load() {
        File dataFolder = new File(dataPath);
        File configFile = new File(configPath);
        if (dataFolder.mkdirs() || !configFile.exists()) plugin.saveDefaultConfig();

        config = YamlConfiguration.loadConfiguration(configFile);
        allowDebugMessages = config.getBoolean("allow-debug-messages", false);
        allowStaffAlerts = config.getBoolean("send-staff-alerts", true);
        enableCreativeGiveModule = config.getBoolean("modules.creative-give", true);
    }
}
