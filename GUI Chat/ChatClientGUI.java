/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.zip.GZIPInputStream;

public class ChatClientGUI {
    private static final int SERVER_PORT = 12347;
    private JFrame frame;
    private JTextArea textArea;
    private JTextField textField;
    private PrintWriter out;

    public static void main(String[] args) {
        ChatClientGUI client = new ChatClientGUI();
        client.start();
    }

    public void start() {
        frame = new JFrame("Chat Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel topPanel = new JPanel();
        JTextField hostField = new JTextField("localhost", 10);

        JButton connectButton = new JButton("Connect");
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectToServer(hostField.getText());
            }
        });

        topPanel.add(new JLabel("Host: "));
        topPanel.add(hostField);
        topPanel.add(connectButton);
        frame.getContentPane().add(topPanel, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.setEditable(false);
        frame.getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);

        JLabel inputPesanLabel = new JLabel("Input Pesan");
        frame.getContentPane().add(inputPesanLabel, BorderLayout.SOUTH);

        textField = new JTextField();
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        frame.getContentPane().add(textField, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void connectToServer(String host) {
        try {
            Socket socket = new Socket(host, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(new Runnable() {
                @Override
                public void run() {
                    String message;
                    try {
                        while ((message = in.readLine()) != null) {
                            textArea.append("Diterima dari Server: " + message + "\n");
                            textArea.append("Decompress: " + decompress(message) + "\n");
                            textArea.append("Decrypt: " + decrypt(decompress(message)) + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            textField.setEnabled(true);

            JOptionPane.showMessageDialog(frame, "Connected to server!", "Connection Status", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String message = textField.getText();

        textArea.append("Sebelum Encryption: " + message + "\n");

        String encryptedMessage = encrypt(message);

        textArea.append("Setelah Encryption: " + encryptedMessage + "\n");

        out.println(encryptedMessage);

        textField.setText("");
    }

    public static String encrypt(String text){
        String temp = "";
        int key = 15;
        for (int i=0; i<text.length(); i++) {
            int h = (int) (text.charAt(i));
            char t = (char) (h^key);
            temp += t;
        }
        return temp;
    }
    
     public static String decrypt(String encryptedText) {
        String decryptedString = "";
        int key = 15;
        for (int i = 0; i < encryptedText.length(); i++) {
            int h = (int) (encryptedText.charAt(i));
            char t = (char) (h ^ key);
            decryptedString += t;
        }
        return decryptedString;
    }

    public static String decompress(String compressedText) {
        try {
            byte[] compressedData = Base64.getDecoder().decode(compressedText);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(compressedData);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try (GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = gzipInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            return new String(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
