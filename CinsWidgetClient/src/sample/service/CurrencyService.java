package sample.service;

import sample.socket.EchoClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyService {

    private EchoClient echoClient;

    public CurrencyService(EchoClient echoClient){
        this.echoClient = echoClient;
    }

    public Map<String, String> getCurrency(){
        String currencyString = echoClient.sendEcho("currency");
        String[] currencyList = currencyString.split(",");
        Map<String, String> stringMap = new HashMap<>();

        for(String i : currencyList){
                String[] value = i.split(":");
                stringMap.put(value[0], value[1]);
        }

        return stringMap;

    }
}
