package Entities;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class Port {
    private String mac;
    private Cable cable;
    private static Queue<Packet> receiveQueue = new LinkedList<>();
    private static Queue<Packet> sendQueue = new LinkedList<>();
    private Switch switch1;
    private Host host;

    //auto increment
    private static final AtomicInteger count = new AtomicInteger(0); 
    private Integer number;
  
    public Port() { }

    public Port(String mac, Switch switch1) {
        this.mac = mac;
        this.switch1 = switch1;

        //auto increment
        this.number = count.incrementAndGet();
    }

    public Port(String mac, Host host) {
        this.mac = mac;
        this.host = host;

        //auto increment
        this.number = count.incrementAndGet();
    }

    public String getMac() {
        return mac;
    }

    public Integer getNumber() {
        return number;
    }

    public Cable getCable() {
        return cable;
    }

    public void setCable(Cable cable) {
        this.cable = cable;
    }

    public Boolean isConnected() {
        return cable != null;
    }

    public void sendMessageByCable(Packet pack) {
        sendQueue.add(pack);
        cable.send(sendQueue.poll(), this);
    }

    public void receiveMessageFromCable(Packet pack) {
        receiveQueue.add(pack);
        if (host != null) {
            host.receiveMessage(receiveQueue.poll());
        }
        else {
            switch1.sendMessage(receiveQueue.poll());
        }
    }
}
