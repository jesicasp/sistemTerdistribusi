/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jesica15092023;

/**
 *
 * @author asus
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author hp
 */
public class WriteFile {
     public static void main(String[] args) throws IOException {
        System.out.println("What is the name of the file to be written to?");
        String filename;
        BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
        filename = br.readLine();
        System.out.println("Enter data to write to " + filename + "...");
        System.out.println("Type q$ to end.");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filename);
        } catch (FileNotFoundException ex) {
            System.out.println("File cannot be openned for writing. ");
        }
        try {
            boolean done = false;
            int data;
        do {
            data = br.read();
            if ((char)data == 'q') {
                data = br.read();
                if ((char)data == '$') {
                    done = true;
                } else {
                    fos.write('q');
                    fos.write(data);
                }
            } else {
                    fos.write(data);
                }
            } while (!done);
        } catch (IOException ex) {
            System.out.println("Problem in reading from the file.");
        }
    }
}
