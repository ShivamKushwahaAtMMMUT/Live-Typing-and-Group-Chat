package model;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.jdom2.*;
import org.jdom2.output.*;

public class ChatPublisher {
    private MulticastSocket socket = null;
    private InetAddress group = null;
    private int port;

    public ChatPublisher() {
        port = 7619;
        int ttl = 5;
        try {
            socket = new MulticastSocket();
            group = InetAddress.getByName("229.229.229.230");
            socket.setTimeToLive(ttl);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void publish(String name, String content) {
        String data = createData(name, content);
        byte[] buff = data.getBytes();
        DatagramPacket packet = new DatagramPacket(buff, buff.length, group, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String createData(String name, String content) {
        String data = null;
        try {
            Element classElement = new Element("chat-data");
            Document document = new Document(classElement);
            Element nameElement = new Element("name");
            nameElement.setText(name);
            Element contentElement = new Element("content");
            contentElement.setText(content);
            document.getRootElement().addContent(nameElement);
            document.getRootElement().addContent(contentElement);
            XMLOutputter out = new XMLOutputter();
            out.setFormat(Format.getPrettyFormat());
            data = out.outputString(document);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return data;
    }
}
