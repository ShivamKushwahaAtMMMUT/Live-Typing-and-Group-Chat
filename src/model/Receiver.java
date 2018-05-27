package model;

import java.net.*;
import java.io.*;

import javafx.application.Platform;
import view.View;

public class Receiver implements Runnable {
    private View view = null;
    private MulticastSocket socket = null;
    private byte[] buff = null;

    public Receiver(View view){
        this.view = view;
        buff = new byte[51200];
        try {
            socket = new MulticastSocket(7393);
            InetAddress group = InetAddress.getByName("229.229.229.229");
            socket.joinGroup(group);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Receiver thread started");
        while(true) {
            DatagramPacket packet = new DatagramPacket(buff, buff.length);
            try {
                socket.receive(packet);
                String data = new String(packet.getData(), 0, packet.getLength());
                Platform.runLater(() -> view.updateContent(data));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
