package io.udpserver.weather;

public class WeatherService {

    private final WeatherScanThread weatherScanThread;

    public WeatherService(WeatherScanThread weatherScanThread) {
        this.weatherScanThread = weatherScanThread;
    }

    public String getWeatherString() {
        return this.weatherScanThread.getValue() + "," + this.weatherScanThread.getCode();
    }
}
