package sample.service;

import sample.socket.SocketClient;

public class WeatherService {

    private SocketClient socketClient;

    public WeatherService(SocketClient socketClient){
        this.socketClient = socketClient;
    }

    public String getWeather(){
        String weatherString = socketClient.sendEcho("weather");
        System.out.println(weatherString);
        System.out.println(weatherString);
        System.out.println(weatherString);
        System.out.println(weatherString);
        System.out.println(weatherString);
        return weatherString;
    }
}
