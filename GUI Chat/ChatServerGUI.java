/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package versi_bedul;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ChatServerGUI {
    private static final int PORT = 12347;
    private static final Set<PrintWriter> clientWriters = new HashSet<>();
    private JFrame frame;
    private JTextArea textArea;
    private JButton startButton;
    private JButton stopButton;
    private boolean isServerRunning = false;

    public static void main(String[] args) {
        ChatServerGUI server = new ChatServerGUI();
        server.start();
    }

    public void start() {
        frame = new JFrame("Chat Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startServer();
            }
        });

        stopButton = new JButton("Stop");
        stopButton.setEnabled(false);
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopServer();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.setEditable(false);
        frame.getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void startServer() {
        if (!isServerRunning) {
            isServerRunning = true;
            startButton.setEnabled(false);
            stopButton.setEnabled(true);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                        while (isServerRunning) {
                            new ClientHandler(serverSocket.accept()).start();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            textArea.append("Server started...\n");
        }
    }

    private void stopServer() {
        if (isServerRunning) {
            isServerRunning = false;
            startButton.setEnabled(true);
            stopButton.setEnabled(false);

            textArea.append("Server stopped...\n");
        }
    }

    private class ClientHandler extends Thread {
        private final Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                synchronized (clientWriters) {
                    clientWriters.add(out);
                }

                String message;
                while ((message = in.readLine()) != null) {
                    textArea.append("Diterima dari Client: " + message + "\n");

                    // Lakukan kompresi
                    String compressedMessage = compress(message);

                    textArea.append("Setelah di Compress: " + compressedMessage + "\n");

                    broadcastMessage(compressedMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    synchronized (clientWriters) {
                        clientWriters.remove(out);
                    }
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void broadcastMessage(String message) {
        synchronized (clientWriters) {
            for (PrintWriter writer : clientWriters) {
                writer.println(message);
                writer.flush();
            }
        }
    }

    public static String compress(String text) {
        try {
            byte[] data = text.getBytes();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream)) {
                gzipOutputStream.write(data);
            }
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
