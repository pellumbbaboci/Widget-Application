package io.currency;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.Charset;

public class CurrencyServer implements Runnable {
 
    private DatagramSocket socket;
    private boolean running;
    private byte[] buffer = new byte[256];

    private CurrencyScanThread currencyScanThread;


 
    public CurrencyServer(CurrencyScanThread currencyScanThread) {
        this.currencyScanThread = currencyScanThread;
        try {
            socket = new DatagramSocket(9999);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
 
    public void run() {
        running = true;
 
        while (running) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }


            String received = new String(packet.getData(), packet.getOffset(), packet.getLength());
            System.out.println(received);

            InetAddress address = packet.getAddress();
            int port = packet.getPort();


            if(received.equalsIgnoreCase("currency")){
                StringBuilder sendData = new StringBuilder();
                sendData.append("euroB:"+this.currencyScanThread.getEuroB()+",");
                sendData.append("euroS:"+this.currencyScanThread.getEuroS()+",");
                sendData.append("usdB:"+this.currencyScanThread.getUsdB()+",");
                sendData.append("usdS:"+this.currencyScanThread.getUsdS());

                String sendDataString = sendData.toString();
                byte[] b = sendDataString.getBytes(Charset.forName("UTF-8"));



                packet = new DatagramPacket(b, b.length, address, port);

                try {
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("sent");

            }

        }
        socket.close();
    }


}