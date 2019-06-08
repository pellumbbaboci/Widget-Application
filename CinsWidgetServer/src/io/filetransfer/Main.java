package io.filetransfer;

import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static List<ServerThread> serverThreadList = new ArrayList<>();

    public static void main(String[] args) {
        String folderName = System.getProperty("user.dir")+ "\\src\\io\\filetransfer\\resources\\";
        //Initialize Sockets
        ServerSocket ssock = null;
        try {
            ssock = new ServerSocket(5000);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileScanThread fileScanThread = new FileScanThread(new File(folderName));
        Thread thread2 = new Thread(fileScanThread);
        thread2.start();


        while(true){
            try{
                Socket socket = ssock.accept();
                System.out.println("Socket accepted.");
                ServerThread serverThread = new ServerThread(socket, folderName);
                serverThreadList.add(serverThread);
                Thread thread = new Thread(serverThread);
                thread.start();


            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
