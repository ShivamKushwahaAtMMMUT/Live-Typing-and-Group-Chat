package model;

import java.net.*;
import java.io.*;

public class Publisher {
    private MulticastSocket socket = null;
    private InetAddress group = null;
    private int port;

    public Publisher() {
        port = 7393;
        int ttl = 5;
        try {
            socket = new MulticastSocket();
            group = InetAddress.getByName("229.229.229.229");
            socket.setTimeToLive(ttl);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void publish(String data) {
        byte[] buff = data.getBytes();
        DatagramPacket packet = new DatagramPacket(buff, buff.length, group, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}