package io.udpserver;


import io.udpserver.currency.CurrencyScanThread;
import io.udpserver.currency.CurrencyService;
import io.udpserver.news.NewsScanThread;
import io.udpserver.news.NewsService;
import io.udpserver.weather.WeatherScanThread;
import io.udpserver.weather.WeatherService;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.Charset;

public class Main {

    private static DatagramSocket socket;
    private static byte[] buffer = new byte[256];

    public static void main(String[] args) {

        try {
            socket = new DatagramSocket(9999);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        CurrencyScanThread currencyScanThread = new CurrencyScanThread();
        CurrencyService currencyService = new CurrencyService(currencyScanThread);

        NewsScanThread newsScanThread = new NewsScanThread();
        NewsService newsService = new NewsService(newsScanThread);

        WeatherScanThread weatherScanThread = new WeatherScanThread();
        WeatherService weatherService = new WeatherService(weatherScanThread);


        Thread threadCurrencyScan = new Thread(currencyScanThread);
        threadCurrencyScan.start();
        Thread threadNewsScan = new Thread(newsScanThread);
        threadNewsScan.start();
        Thread threadWeatherScan = new Thread(weatherScanThread);
        threadWeatherScan.start();


        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }


            String received = new String(packet.getData(), packet.getOffset(), packet.getLength());
            System.out.println(received);

            InetAddress address = packet.getAddress();
            int port = packet.getPort();


            if(received.equalsIgnoreCase("currency")){
                String sendDataString = currencyService.getCurrencyString();
                byte[] b = sendDataString.getBytes(Charset.forName("UTF-8"));
                packet = new DatagramPacket(b, b.length, address, port);
                try {
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
                System.out.println("sent");
            }else if(received.equalsIgnoreCase("weather")){
                buffer = new byte[1024 * 64 ];
                String sendDataString = weatherService.getWeatherString();
                byte[] b = sendDataString.getBytes(Charset.forName("UTF-8"));
                packet = new DatagramPacket(b, b.length, address, port);
                try {
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }


            }else if(received.equalsIgnoreCase("news")){
                buffer = new byte[1024 * 64];
                String sendDataString = newsService.getNewsString();
                byte[] b = sendDataString.getBytes(Charset.forName("UTF-8"));
                packet = new DatagramPacket(b, b.length, address, port);
                try {
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }else{
                continue;
                //TODO ignore
                //TODO ignore
                //TODO ignore
                //TODO ignore
                //TODO ignore
            }

        }
        socket.close();

    }


}
