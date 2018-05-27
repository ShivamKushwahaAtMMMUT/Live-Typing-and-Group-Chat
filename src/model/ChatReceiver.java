package model;

import javafx.application.Platform;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import view.ChatView;
import java.io.*;
import org.jdom2.*;
import org.jdom2.input.*;

public class ChatReceiver implements Runnable {
    private ChatView chatView = null;
    private MulticastSocket socket = null;
    private byte[] buff = null;

    public ChatReceiver(ChatView view){
        this.chatView = view;
        buff = new byte[51200];
        try {
            socket = new MulticastSocket(7619);
            InetAddress group = InetAddress.getByName("229.229.229.230");
            socket.joinGroup(group);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Chat Receiver thread started");
        while(true) {
            DatagramPacket packet = new DatagramPacket(buff, buff.length);
            try {
                socket.receive(packet);
                String data = new String(packet.getData(), 0, packet.getLength());
                String[] parsedData = parseData(data);
                Platform.runLater(() -> chatView.updateChat(parsedData[0], parsedData[1]));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private String[] parseData(String data) {
        String[] parsedData = new String[2];
        SAXBuilder builder = new SAXBuilder();
        try {
            Document document = builder.build(new ByteArrayInputStream(data.getBytes()));
            Element classElement = document.getRootElement();
            Element nameElement = classElement.getChild("name");
            Element contentElement = classElement.getChild("content");
            parsedData[0] = nameElement.getText();
            parsedData[1] = contentElement.getText();
        } catch (JDOMException | IOException ex) {
            ex.printStackTrace();
        }
        return parsedData;
    }
}
