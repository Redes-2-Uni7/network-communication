package Application;

import Entities.NetworkAddress;

public class Settings {
    public static final String macSwitch1 = "11:11:11:11:11:11";
    public static final String macSwitch2 = "22:22:22:22:22:22";
    public static final String macRouter1 = "AA:AA:AA:AA:AA:AA";
    public static final String macRouter2 = "BB:BB:BB:BB:BB:BB";
    public static final String ipRouter1 = "10.20.30.0";
    public static final String ipRouter2 = "10.20.31.0";

    public static final NetworkAddress networkAddress1 = new NetworkAddress("10.20.30.0", 24);
    public static final NetworkAddress networkAddress2 = new NetworkAddress("10.20.31.0", 24);

    public static final String macBroadcast = "FF:FF:FF:FF:FF:FF";
    public static final String ipUnknown = "255.255.255.255";

    public static final String ipSource1 = "10.20.30.1";
    public static final String macSource1 = "AA:BB:CC:DD:EE:FF";

    public static final String ipSource2 = "10.20.30.2";
    public static final String macSource2 = "BB:CC:DD:EE:FF:GG";

    public static final String ipSource3 = "10.20.31.1";
    public static final String macSource3 = "CC:DD:EE:FF:GG:HH";
}
