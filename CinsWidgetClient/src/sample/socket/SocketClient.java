package sample.socket;

import java.io.IOException;
import java.net.*;

public class SocketClient {
    private DatagramSocket socket;
    private InetAddress address;

    public SocketClient() throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("localhost");
    }

    public String sendEcho(String msg) {
        byte[] buffer = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 9999);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }


        byte[] currencyReceived = new byte[1024 * 64];
        packet = new DatagramPacket(currencyReceived, currencyReceived.length);
        try {
            socket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String received = new String(packet.getData(), 0, packet.getLength());

        System.out.println(received);

        return received;
    }

}