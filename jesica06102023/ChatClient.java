/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jesica06102023;

/**
 *
 * @author asus
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private static final int server_port = 12345;
    private static final String server_address = "localhost";

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(server_address, server_port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);
            System.out.println("Connect ke server, silahkan kirim pesan(exit untuk kelua");

            new PesanServer(socket, in).start();
            String message;
            while (true) {
                message = scanner.nextLine();
                out.println(message);
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
            }
            socket.close();
        } catch (IOException ex) {

        }
    }

    private static class PesanServer extends Thread {

        Socket socket;
        BufferedReader in;
        String message;

        public PesanServer(Socket socket, BufferedReader in) {
            this.socket = socket;
            this.in = in;
        }

        @Override
        public void run() {
            try {
                while ((message = in.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
