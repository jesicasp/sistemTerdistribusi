/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jesica06102023;

/**
 *
 * @author asus
 */
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class MulticastChatServer {

    private static final String address = "230.0.0.1";
    private static final int port = 12346;
    private static final int max_user = 10;

    private static Map<String, InetAddress> users = new HashMap<>();

    public static void main(String[] args) throws UnknownHostException, IOException {
        try {
            InetAddress group = InetAddress.getByName(address);
            MulticastSocket multicastSocket = new MulticastSocket(port);
            multicastSocket.joinGroup(group);
            System.out.println("Multicast chat server running...");
            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                multicastSocket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength());
                String[] parts = message.split(":", 2);
                if (parts.length == 2) {
                    String username = parts[0].trim();
                    String content = parts[1].trim();
                    if (!users.containsKey(username)) {
                        if (users.size() >= max_user) {
                            System.out.println("user penuh. " + username + " tidak bisa join");
                            continue;
                        }
                        users.put(username, packet.getAddress());
                        System.out.println(username + "Join");
                    }
                    for (Map.Entry<String, InetAddress> entry : users.entrySet()) {
                        if (!entry.getKey().equals(username)) {
                            InetAddress userAddress = entry.getValue();
                            DatagramPacket sendPacket = new DatagramPacket(
                                    message.getBytes(),
                                    message.length(),
                                    userAddress,
                                    port
                            );
                            multicastSocket.send(sendPacket);
                        }
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
       
    }
}
