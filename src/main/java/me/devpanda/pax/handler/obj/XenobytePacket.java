package me.devpanda.pax.handler.obj;

public class XenobytePacket {

    public String cheatName;
    public Severity severity;

    public XenobytePacket(final String cheatName, final Severity severity) {
        this.cheatName = cheatName;
        this.severity = severity;
    }

    public enum Severity {
        LOW,
        MEDIUM,
        HIGH
    }
}
