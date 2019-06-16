package sample.service;

import sample.socket.SocketClient;

import java.util.HashMap;
import java.util.Map;

public class CurrencyService {

    private SocketClient socketClient;

    public CurrencyService(SocketClient socketClient){
        this.socketClient = socketClient;
    }

    public Map<String, String> getCurrency(){
        String currencyString = socketClient.sendEcho("currency");
        String[] currencyList = currencyString.split(",");
        Map<String, String> stringMap = new HashMap<>();

        for(String i : currencyList){
                String[] value = i.split(":");
                stringMap.put(value[0], value[1]);
        }

        return stringMap;

    }
}
