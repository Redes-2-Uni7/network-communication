package Entities;

import Application.Settings;

import java.util.Map;
import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Router {
    private String mac;
    private Port interface1;
    private Port interface2;

    private Map<String, Integer> macTable = new HashMap<>(); //mac e interface
    private Map<NetworkAddress, Integer> routeTable = new HashMap<>(); //networkAddress e interface
    private Map<String, String> ipMacTable = new HashMap<>(); //ip e mac

    public Router(String mac, NetworkAddress selfNetworkAddress) {
        routeTable.put(selfNetworkAddress, 2);
        this.mac = mac;
    }

    public Map<String, Integer> getMacTable() {
        return macTable;
    }

    public void setMacTable(Map<String, Integer> macTable) {
        this.macTable = macTable;
    }

    public Map<NetworkAddress, Integer> getRouteTable() {
        return routeTable;
    }

    public void setRouteTable(Map<NetworkAddress, Integer> routeTable) {
        this.routeTable = routeTable;
    }

    public Map<String, String> getIpMacTable() {
        return ipMacTable;
    }

    public void setIpMacTable(Map<String, String> ipMacTable) {
        this.ipMacTable = ipMacTable;
    }

    public Boolean shouldSaveMacTable(Packet pack) {
        return !macTable.containsKey(pack.getSourceMac());
    }
    
    public Boolean shouldSaveIpMacTable(Packet pack) {
        return !ipMacTable.containsKey(pack.getSourceIp());
    }
    
    public Boolean isPackFromRouter(Packet pack, Port port) {
        List<String> aux = new ArrayList<>();
        aux.add(Settings.macRouter1);
        aux.add(Settings.macRouter2);
        return aux.contains(port.getCable().getPort2().getMac());
    }
    
    public Boolean isPackFromPort(Packet pack, Port port) {
        return port.getCable().getPort2().getMac().equals(pack.getSourceMac()) 
            || isPackFromRouter(pack, port);
    }
    
    public Boolean portKnowed(Packet pack) {
        return macTable.containsKey(pack.getDestinationMac());
    }

    public Boolean isFromInside(Packet pack) {
        NetworkAddress key = null;
        for (NetworkAddress k : routeTable.keySet()) {
            Integer value = routeTable.get(k);
            if (value == 2)
                key = k;
        }
        return key.networkAddressContainsIp(pack.getSourceIp());
    }

    public Boolean isToOutside(Packet pack) {
        NetworkAddress key = null;
        for (NetworkAddress k : routeTable.keySet()) {
            Integer value = routeTable.get(k);
            if (value == 2)
                key = k;
        }
        return !key.networkAddressContainsIp(pack.getDestinationIp());
    }

    public void sendMessage(Packet pack) {
        System.out.println("Enviando pacote pelo router");
        pack.print();
        
        Boolean isArp = pack.isBroadcast();

        if (isPackFromPort(pack, interface2) && shouldSaveIpMacTable(pack)) 
            ipMacTable.put(pack.getSourceIp(), pack.getSourceMac());

        if (shouldSaveMacTable(pack)) {
            if (isPackFromPort(pack, interface1)) 
                macTable.put(pack.getSourceMac(), interface1.getNumber());
            else
                macTable.put(pack.getSourceMac(), interface2.getNumber());
        }

        Boolean isFromInside = isFromInside(pack);
        Boolean isToOutside = isToOutside(pack);

        if (isArp) {
            if (isToOutside) {
                Packet arpPack = new Packet(mac, Settings.macBroadcast, pack.getDestinationIp(), pack.getSourceIp(), "Arp Reply");
                interface2.sendMessageByCable(arpPack);
            }
            if (isFromInside && pack.getSourceMac() == mac) {
                interface2.sendMessageByCable(pack);
            }
        }
        else if (!isFromInside) {
            pack.setSourceMac(mac);
            String destinationMac = ipMacTable.get(pack.getDestinationIp());

            if (destinationMac != null)
                pack.setDestinationMac(destinationMac);
            else {
                Packet arpPack = new Packet(pack.getSourceMac(), Settings.macBroadcast, pack.getSourceIp(), pack.getDestinationIp(), "Arp Request");
                sendMessage(arpPack);
                destinationMac = ipMacTable.get(pack.getDestinationIp());
                if (destinationMac == null)
                    System.out.println("Não conhece o mac de destino, mesmo depois de ter enviado o arp request e ter recebido o reply.");
                pack.setDestinationMac(destinationMac);
            }

            interface2.sendMessageByCable(pack);
        }
        else {
            interface1.sendMessageByCable(pack);
        }
    }
}
