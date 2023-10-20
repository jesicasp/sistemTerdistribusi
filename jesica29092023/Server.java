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
import java.util.*;
/**
 *
 * @author hp
 */
public class Server {
    private static final List<String> questions = Arrays.asList(
        "Siapa pembuat Java",
        "Apa kepanjanngan dari HTML?"
    );

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server berjalan. Menunggu koneksi...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client terhubung: " + clientSocket.getInetAddress());
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            out.println("Selamat datang di Kuis Sederhana! Ketik 'permintaan' untuk mendapatkan pertanyaan.");
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if ("permintaan".equals(inputLine)) {
                    sendRandomQuestion(out);
                } else if (inputLine.startsWith("jawaban#")) {
                    checkAnswer(out, inputLine);
                } else {
                    out.println("Perintah tidak valid. Ketik 'permintaan' untuk pertanyaan atau 'jawaban#jawaban_anda' untuk menjawab.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendRandomQuestion(PrintWriter out) {
        int randomIndex = new Random().nextInt(questions.size());
        String question = questions.get(randomIndex);
        out.println(question);
        System.out.println("Mengirim pertanyaan: " + question);
    }

    private static void checkAnswer(PrintWriter out, String inputLine) {
        String[] parts = inputLine.split("#");
        if (parts.length == 2) {
            int questionNumber = Integer.parseInt(parts[0]);
            String answer = parts[1].trim();
            if (questionNumber >= 1 && questionNumber <= questions.size()) {
                String correctAnswer = questions.get(questionNumber - 1);
                if (answer.equalsIgnoreCase(correctAnswer)) {
                    out.println("Jawaban Anda benar! Terimakasih sudah berpartisipasi.");
                } else {
                    out.println("Jawaban Anda salah. Jawaban yang benar adalah: " + correctAnswer);
                }
            } else {
                out.println("Nomor pertanyaan tidak valid.");
            }
        } else {
            out.println("Format jawaban tidak valid. Gunakan format <nomor pertanyaan>#<jawaban_anda>.");
        }
    }
}
