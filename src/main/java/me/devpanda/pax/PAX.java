package me.devpanda.pax;

import me.devpanda.pax.handler.PacketHandler;
import me.devpanda.pax.utils.Configuration;
import me.devpanda.pax.utils.Utils;
import org.bukkit.plugin.java.JavaPlugin;

public class PAX extends JavaPlugin {
    public static PAX plugin;

    @Override
    public void onEnable() {
        plugin = this;

        Configuration.load();
        PacketHandler.load();

        Utils.log("Successfully Enabled!");
    }

}
