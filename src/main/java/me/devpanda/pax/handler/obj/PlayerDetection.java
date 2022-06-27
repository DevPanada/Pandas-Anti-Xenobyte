package me.devpanda.pax.handler.obj;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PlayerDetection {

    private final String playerUuid;
    private final XenobytePacket xenobytePacket;
    private final String dateAndTime;

    public PlayerDetection(final String playerUuid, final XenobytePacket xenobytePacket) {
        this.playerUuid = playerUuid;
        this.xenobytePacket = xenobytePacket;
        this.dateAndTime = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
    }

    public String getPlayerUuid() {
        return playerUuid;
    }

    public XenobytePacket getXenobytePacket() {
        return xenobytePacket;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }
}
