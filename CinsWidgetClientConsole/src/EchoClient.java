import java.io.IOException;
import java.net.*;

public class EchoClient {
    private DatagramSocket socket;
    private InetAddress address;
 
    private byte[] buf;
 
    public EchoClient() throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("localhost");
    }
 
    public String sendEcho(String msg) {
        buf = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 9999);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }


        byte[] currencyReceived = new byte[1024];
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
 
    public void close() {
        socket.close();
    }
}