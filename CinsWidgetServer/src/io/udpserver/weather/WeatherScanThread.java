package io.udpserver.weather;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WeatherScanThread implements Runnable {

    public String demo = null;

    private String url = null;

    public WeatherScanThread() {
        url = "https://servis.mgm.gov.tr/api/sondurumlar?il=manisa";
        this.getWeather();
    }


    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(300000);
                getWeather();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void getWeather(){
        try {
            String response = httpRequest(url);
            System.out.println(response);
            String response2 = response.substring(1,response.length() -1);
            System.out.println(response2);


            JsonElement jelement = new JsonParser().parse(response2);
            JsonObject jobject = jelement.getAsJsonObject();
            jobject = jobject.getAsJsonObject();
            String temperature = jobject.get("sicaklik").getAsString();


            System.out.println(temperature);
            System.out.println(temperature);
            System.out.println(temperature);
            System.out.println(temperature);

            demo = temperature;

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private String httpRequest(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

}
