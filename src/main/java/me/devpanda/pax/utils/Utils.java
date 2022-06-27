package me.devpanda.pax.utils;

import me.devpanda.pax.PAX;
import me.devpanda.pax.handler.obj.XenobytePacket;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class Utils {

    public static final String prefix = "&7[&bPAX-AC&7]";

    /**
     * Logs an INFO message into console.
     */
    public static void log(final Object o) {
        PAX.plugin.getLogger().info(colorize(prefix+"&7 "+o.toString()));
    }

    /**
     * Logs a WARN message into console.
     * You can ignore these at own risk.
     */
    public static void warn(final Object o) {
        PAX.plugin.getLogger().warning(colorize(prefix+"&e "+o.toString()));
    }

    /**
     * Logs an ERROR message into console.
     * DO NOT IGNORE THESE ERRORS. THEY ARE IMPORTANT.
     */
    public static void error(final Object o) {
        PAX.plugin.getLogger().severe(colorize(prefix+"&c "+o.toString()));
    }

    /**
     * Logs a DEBUG message into console.
     * These are safe to ignore, if present in normal release, it means I forgot to remove them.
     * I'm big dumdum sometimes ngl.
     */
    public static void debug(final Object o) {
        if (!Configuration.allowDebugMessages) return;
        PAX.plugin.getLogger().info(colorize(prefix+"&7[&6DEBUG&7] &6"+o.toString()));
    }

    /**
     * Sends a staff alert to all online players with permission "pax.alert".
     * Requires configuration option "send-staff-alerts" to be true.
     */
    public static void alertStaff(final OfflinePlayer offender, final XenobytePacket packet) {
        if (!Configuration.allowStaffAlerts) return;
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (p.hasPermission("pax.alert")) p.sendMessage(colorize(prefix+" &e"+offender.getName()+" &7has been flagged for &e"+packet.cheatName+"&7."));
        }
    }

    /**
     * Sends a staff alert to all online players with permission "pax.alert".
     */
    public static void alertStaffCustom(final Object o) {
        if (!Configuration.allowStaffAlerts) return;
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (p.hasPermission("pax.alert")) p.sendMessage(colorize(prefix+" "+o.toString()));
        }
    }

    public static String colorize(Object o) {
        return ChatColor.translateAlternateColorCodes('&', o.toString());
    }

}
