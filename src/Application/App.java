package Application;

import Entities.Switch;
import Entities.Host;
import Entities.Cable;
import Entities.Packet;
import Entities.Router;

public class App {
    public static void main(String[] args) {

        Router router1 = new Router(Settings.macRouter1, Settings.ipRouter1, Settings.networkAddress1);
        Router router2 = new Router(Settings.macRouter2, Settings.ipRouter2, Settings.networkAddress2);

        Switch switch1 = new Switch(Settings.macSwitch1, 3);
        Switch switch2 = new Switch(Settings.macSwitch2, 2);

        Host h1 = new Host(Settings.ipSource1, Settings.macSource1);
        Host h2 = new Host(Settings.ipSource2, Settings.macSource2);
        Host h3 = new Host(Settings.ipSource3, Settings.macSource3);
        
        new Cable(switch1.getPorts().get(0), h1.getPort());
        new Cable(switch1.getPorts().get(1), h2.getPort());
        new Cable(switch1.getPorts().get(2), router1.getInterface2());
        new Cable(switch2.getPorts().get(0), h3.getPort());
        new Cable(switch2.getPorts().get(1), router2.getInterface2());
        new Cable(router1.getInterface1(), router2.getInterface1());

        router1.getRouteTable().put(Settings.networkAddress2, 1);
        router2.getRouteTable().put(Settings.networkAddress1, 1);

        // Packet pack = new Packet(h1.getMac(), Settings.macBroadcast, h1.getIp(), h3.getIp(), "Arp Request");

        Packet pack = new Packet(h1.getMac(), Settings.macBroadcast, h1.getIp(), h3.getIp(), "Mensagem de uma rede para outra.");

        System.out.println("");
        System.out.println("Host1 Mac: " + h1.getMac() + "; Ip: " + h1.getIp());
        System.out.println("Host2 Mac: " + h2.getMac() + "; Ip: " + h2.getIp());
        System.out.println("Host3 Mac: " + h3.getMac() + "; Ip: " + h3.getIp());
        System.out.println("");

        h1.sendMessage(pack);

        System.out.println("");
        System.out.println("Mac Table do Router1");
        System.out.println("Router1: " + router1.getMacTable().toString().replaceAll(",", ";").replaceAll("=", ", "));

        System.out.println("");
        System.out.println("IP Mac Table do Router1");
        System.out.println("Router1: " + router1.getIpMacTable().toString().replaceAll(",", ";").replaceAll("=", ", "));

        System.out.println("");
        System.out.println("Route Table do Router1");
        System.out.println("Router1: " + router1.getRouteTable().toString().replaceAll(",", ";").replaceAll("=", ", "));

        System.out.println("");
        System.out.println("Mac Table do Switch1");
        System.out.println("Switch: " + switch1.getMacTable().toString().replaceAll(",", ";").replaceAll("=", ", "));

        System.out.println("");
        System.out.println("Mac Table do Host1");
        System.out.println("Host1: " + h1.getMacTable().toString().replaceAll(",", ";").replaceAll("=", ", "));

        System.out.println("");
        System.out.println("Mac Table do Host2");
        System.out.println("Host2: " + h2.getMacTable().toString().replaceAll(",", ";").replaceAll("=", ", "));

        System.out.println("");
        System.out.println("Mac Table do Router2");
        System.out.println("Router2: " + router2.getMacTable().toString().replaceAll(",", ";").replaceAll("=", ", "));

        System.out.println("");
        System.out.println("IP Mac Table do Router2");
        System.out.println("Router2: " + router2.getIpMacTable().toString().replaceAll(",", ";").replaceAll("=", ", "));

        System.out.println("");
        System.out.println("Route Table do Router2");
        System.out.println("Router2: " + router2.getRouteTable().toString().replaceAll(",", ";").replaceAll("=", ", "));

        System.out.println("");
        System.out.println("Mac Table do Switch2");
        System.out.println("Switch2: " + switch2.getMacTable().toString().replaceAll(",", ";").replaceAll("=", ", "));

        System.out.println("");
        System.out.println("Mac Table do Host3");
        System.out.println("Host3: " + h3.getMacTable().toString().replaceAll(",", ";").replaceAll("=", ", "));
    }
}
