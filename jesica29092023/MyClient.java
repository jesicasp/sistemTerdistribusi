/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jesica29092023;

/**
 *
 * @author asus
 */
import java.io.*;
import java.net.*;

public class MyClient {

     public static void main(String args[]) {
        try {
            while(true){
            Socket client = new Socket(InetAddress.getLocalHost(),1234);
            InputStream clientIn = client.getInputStream();
            OutputStream clientOut = client.getOutputStream();
            PrintWriter pw = new PrintWriter(clientOut, true);
            BufferedReader br = new BufferedReader(new InputStreamReader(clientIn));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Type a message for the server: ");
            pw.println(stdIn.readLine());
            System.out.println("Server message: ");
            System.out.println(br.readLine());
            pw.close();
            br.close();
            client.close();
            }
        } catch (ConnectException ce) {
            System.out.println("Cannot connect to the server.");
        } catch (IOException ie) {
            System.out.println("I/O Error.");
        }
    }
}
