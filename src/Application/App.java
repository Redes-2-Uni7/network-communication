package Application;

import Entities.Switch;
import Entities.Host;
import Entities.Cable;
import Entities.Packet;
import Entities.Router;

public class App {
    public static void main(String[] args) {

        Router router1 = new Router(Settings.macRouter1, Settings.networkAddress1);
        Router router2 = new Router(Settings.macRouter2, Settings.networkAddress2);

        Switch switch1 = new Switch(Settings.macSwitch1, 3);

        Host h1 = new Host(Settings.ipSource1, Settings.macSource1);
        Host h2 = new Host(Settings.ipSource2, Settings.macSource2);
        Host h3 = new Host(Settings.ipSource3, Settings.macSource3);

        new Cable(switch1.getPorts().get(0), h1.getPort());
        new Cable(switch1.getPorts().get(1), h2.getPort());
        new Cable(switch1.getPorts().get(2), h3.getPort());

        Packet pack = new Packet(h1.getMac(), Settings.macBroadcast, h1.getIp(), h2.getIp(), "Arp Request");

        System.out.println("");
        System.out.println("Host1 Mac: " + h1.getMac() + "; Ip: " + h1.getIp());
        System.out.println("Host2 Mac: " + h2.getMac() + "; Ip: " + h2.getIp());
        System.out.println("Host3 Mac: " + h3.getMac() + "; Ip: " + h3.getIp());
        System.out.println("");
        
        h1.sendMessage(pack);
 
        System.out.println("");
        System.out.println("Mac Table do switch");
        System.out.println("Switch: " + switch1.getMacTable().toString().replaceAll(",", ";").replaceAll("=", ", "));

        System.out.println("");
        System.out.println("Mac Table do Host1");
        System.out.println("Host1: " + h1.getMacTable().toString().replaceAll(",", ";").replaceAll("=", ", "));
        
        System.out.println("");
        System.out.println("Mac Table do Host2");
        System.out.println("Host2: " + h2.getMacTable().toString().replaceAll(",", ";").replaceAll("=", ", "));
        
        System.out.println("");
        System.out.println("Mac Table do Host3");
        System.out.println("Host3: " + h3.getMacTable().toString().replaceAll(",", ";").replaceAll("=", ", "));
    }
}
