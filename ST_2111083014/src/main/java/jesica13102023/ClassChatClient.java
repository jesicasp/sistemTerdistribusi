/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jesica13102023;

/**
 *
 * @author asus
 */
import java.io.*;
import java.net.*;
import java.util.Scanner;
 
public class ClassChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12347;
        public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner scanner = new Scanner(System.in);
                String nama = "";
                
                
        System.out.print("Input Nama anda : ");
            nama = scanner.nextLine();
            System.out.println("Connect ke Server. Kirim Pesan ('exit' untuk keluar):");
            new PesanServer(socket, in).start();
                String message;
                while (true) {
                    message = scanner.nextLine();
                    String encryptedMessage = encrypt(message);
                    out.println(nama + ":" +encryptedMessage);
                        if (encryptedMessage.equalsIgnoreCase("exit")) {
                             break;
                            }
                        }
                        // Tutup koneksi
                        socket.close();
                    } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            public static class PesanServer extends Thread {
                Socket socket;
                BufferedReader in;
                String message;
                public PesanServer(Socket a, BufferedReader in) {
                    this.socket = a;
                    this.in = in;
                    }
                    @Override
                    public void run() {
                        try {
                    while ((message = in.readLine()) != null){
                System.out.println(message);
                }
            } catch (IOException e) {
        e.printStackTrace();
        }
    }
 }
public static String encrypt(String text){
        String temp = "";
        int key = 15;
        for (int i=0; i<text.length(); i++) {
            int h = (int) (text.charAt(i));
            char t = (char) (h^key);
            temp += t; 
        }
        System.out.println("Sebelum ="+text);
        System.out.println("Sesudah ="+temp);
        
        return text;
    }

}

