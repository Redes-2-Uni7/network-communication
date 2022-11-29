package Entities;

import Application.Settings;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Switch {
    private Map<String, Integer> macTable = new HashMap<>(); //mac e port
    private List<Port> ports = new ArrayList<>();

    public Switch(String mac, Integer qtdPorts) {
        for(Integer i = 0; i < qtdPorts; i++) {
            ports.add(new Port(mac, this));
        }
    }

    public Map<String, Integer> getMacTable() {
        return macTable;
    }

    public List<Port> getPorts() {
        return ports;
    }

    public void setMacTable(Map<String, Integer> macTable) {
        this.macTable = macTable;
    }

    public void setPorts(List<Port> ports) {
        this.ports = ports;
    }

    public Boolean shouldSave(Packet pack) {
        return !macTable.containsKey(pack.getSourceMac());
    }
    
    public Boolean isPackFromSwitch(Packet pack, Port port) {
        List<String> teste = new ArrayList<>();
        teste.add(Settings.macSwitch1);
        teste.add(Settings.macSwitch2);
        return teste.contains(port.getCable().getPort2().getMac());
    }
    
    public Boolean isPackFromPort(Packet pack, Port port) {
        return port.getCable().getPort2().getMac().equals(pack.getSourceMac()) 
            || isPackFromSwitch(pack, port);
    }
    
    public Boolean portKnowed(Packet pack) {
        return macTable.containsKey(pack.getDestinationMac());
    }

    public void sendMessage(Packet pack) {
        System.out.println("Enviando pacote pelo switch");
        pack.print();
        
        Boolean isArp = pack.isBroadcast();

        if (shouldSave(pack)) {
            Port port = new Port();
            for(Port p : ports) { 
                if (isPackFromPort(pack, p)) 
                    port = p;
            }
            macTable.put(pack.getSourceMac(), port.getNumber());
        }

        if (isArp) {
            broadcast(pack);
        }
        else if (portKnowed(pack))
        {
            ports.get(macTable.get(pack.getDestinationMac()) - 1).sendMessageByCable(pack);
        }
        else {
            broadcast(pack);
        }
    }

    private void broadcast(Packet pack) {
        ports.forEach(p -> {
            if (!isPackFromPort(pack, p))
                p.sendMessageByCable(pack);
        });
    }
}
