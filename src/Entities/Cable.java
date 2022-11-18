package Entities;

public class Cable {
    private Port port1;
    private Port port2;

    public Cable(Port port1, Port port2) {
        port1.setCable(this);
        port2.setCable(this);
        this.port1 = port1;
        this.port2 = port2;
    }

    public Port getPort1() {
        return port1;
    }

    public Port getPort2() {
        return port2;
    }

    public void send(Packet pack, Port port) {
        if (port == port1) {
            port2.receiveMessageFromCable(pack);
        }
        else {
            port1.receiveMessageFromCable(pack);
        }
    }
}
