package me.devpanda.pax.handler;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.reflect.StructureModifier;
import me.devpanda.pax.PAX;
import me.devpanda.pax.handler.obj.XenobytePacket;
import me.devpanda.pax.utils.Configuration;
import me.devpanda.pax.utils.Utils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PacketHandler {

    public static HashMap<String, XenobytePacket> packets = new HashMap<>();
    public static HashMap<String, Integer> radiusMalsisDoorDetect = new HashMap<>();

    public static void load() {
        packets.put("CreativeGive", new XenobytePacket("CreativeGive", XenobytePacket.Severity.HIGH));
        packets.put("CacheGive", new XenobytePacket("CacheGive", XenobytePacket.Severity.HIGH));
        packets.put("MalsisDoor", new XenobytePacket("MalsisDoor", XenobytePacket.Severity.MEDIUM));

        startListener();
    }

    private static void startListener() {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(new PacketAdapter(PAX.plugin, ListenerPriority.HIGHEST, PacketType.Play.Client.getInstance().values()) {

            @Override
            public void onPacketReceiving(PacketEvent e) {
                PacketContainer packet = e.getPacket();
                Player player = e.getPlayer();
                if (player == null) return;
                String packetName = packet.getType().name();

                if (packetName.equalsIgnoreCase("SET_CREATIVE_SLOT")) {
                    if (!Configuration.enableCreativeGiveModule || player.getGameMode().equals(GameMode.CREATIVE)) return;
                    e.setCancelled(true);
                    Utils.alertStaff(player, packets.get("CreativeGive"));
                    return;
                }

                if (packetName.equalsIgnoreCase("CUSTOM_PAYLOAD")) {
                    StructureModifier<Object> a = packet.getModifier();
                    if (a.getValues().size() < 2) return;

                    if (Configuration.enableCacheGiveModule && a.getValues().get(1).equals(60) && a.getValues().get(2).getClass().getName().equalsIgnoreCase("[b")) {
                        Location loc = player.getEyeLocation();
                        Vector v = loc.getDirection().normalize();
                        for (int i = 1; i <= 1000; i++) {
                            loc.add(v);
                            if (loc.getBlock().getType() != Material.AIR)
                                break;
                        }
                        Block targetedBlock = loc.getBlock();
                        if (targetedBlock.getType().toString().equals("THERMALEXPANSION_CACHE")) {
                            e.setCancelled(true);
                            Utils.alertStaff(player, packets.get("CacheGive"));
                        }
                        return;
                    }

                    if (Configuration.enableMalsisDoorModule && a.getValues().get(1).equals(64)) {
                        e.setCancelled(true);
                        String playerUuid = player.getUniqueId().toString();
                        if (radiusMalsisDoorDetect.containsKey(playerUuid)) {
                            radiusMalsisDoorDetect.put(playerUuid, radiusMalsisDoorDetect.get(playerUuid)+1);
                            if (radiusMalsisDoorDetect.get(playerUuid) >= 3) {
                                if (!player.isOnline()) return;
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        if (!player.isOnline()) return;
                                        player.kickPlayer(Utils.colorize(Utils.prefix+" &cYou have been automatically kicked for &cHacked Client Detected&7."));
                                        Utils.alertStaffCustom("&c"+player.getName()+" &7has been automatically kicked for: &cRadius Malsis Door Hack (&ex"+radiusMalsisDoorDetect.get(playerUuid)+"&7)!");
                                    }
                                }.runTask(PAX.plugin);
                                return;
                            }
                        } else radiusMalsisDoorDetect.put(playerUuid, 1);
                        Utils.alertStaff(player, packets.get("MalsisDoor"));
                        if (!radiusMalsisDoorDetect.containsKey(playerUuid)) {
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    radiusMalsisDoorDetect.remove(playerUuid);
                                }
                            }.runTaskLater(PAX.plugin, 20 * 10);
                        }
                        return;
                    }

                    // TODO: Carpenter's use module
                }
            }
        });
    }

}
