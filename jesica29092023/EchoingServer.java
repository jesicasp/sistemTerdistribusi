/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jesica29092023;

/**
 *
 * @author asus
 */
import java.net.*;
import java.io.*;

/**
 *
 * @author hp
 */
public class EchoingServer {

    public static void main(String[] args) {
        ServerSocket server = null;
        Socket client;
        try {
            server = new ServerSocket(1234);
            //1234 is an unused port number
        } catch (IOException ie) {
            System.out.println("Cannot open socket.");
            System.exit(1);
        }
        while (true) {
            try {
                System.out.println("Listening...");
                client = server.accept();
                OutputStream clientOut = client.getOutputStream();
                PrintWriter pw = new PrintWriter(clientOut, true);
                InputStream clientIn = client.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(clientIn));
                pw.println(br.readLine());
            } catch (IOException ie) {
            }
        }
    }
}