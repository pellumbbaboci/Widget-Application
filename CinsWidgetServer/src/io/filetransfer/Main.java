package io.filetransfer;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        String folderName = System.getProperty("user.dir") + "\\src\\io\\filetransfer\\resources\\";
        //Initialize TCP socket
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(5000);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileScanThread fileScanThread = new FileScanThread(new File(folderName));
        Thread thread2 = new Thread(fileScanThread);
        thread2.start();

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Socket accepted.");
                ServerThread serverThread = new ServerThread(socket, folderName);
                Thread thread = new Thread(serverThread);
                thread.start();
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
