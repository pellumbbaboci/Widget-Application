package io.udpserver.currency;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import java.io.IOException;


public class CurrencyScanThread implements Runnable {

    private String url = null;

    private String usdB = null;
    private String usdS = null;
    private String euroB = null;
    private String euroS = null;


    public CurrencyScanThread() {
        url = "https://www.tcmb.gov.tr/kurlar/today.xml";
        this.getCurrency();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(300000);
                getCurrency();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void getCurrency() {
        try {
            String response = httpRequest(url);
            org.jsoup.nodes.Document doc2 = Jsoup.parse(response, "", Parser.xmlParser());
            Elements elements = doc2.select("Currency");
            for (org.jsoup.nodes.Element element : elements)
            {
                if (element.select("Isim").text().equalsIgnoreCase("ABD DOLARI")) {

                    setUsdB(element.select("ForexBuying").text());
                    setUsdS(usdS = element.select("ForexSelling").text());

                } else if (element.select("Isim").text().equalsIgnoreCase("EURO")) {

                    setEuroB(element.select("ForexBuying").text());
                    setEuroS(element.select("ForexSelling").text());

                }
            }

        } catch (IOException e) {
            e.printStackTrace();

        }

        System.out.println(usdB);
        System.out.println(usdS);
        System.out.println(euroB);
        System.out.println(euroS);
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


    String getUrl() {
        return url;
    }

    void setUrl(String url) {
        this.url = url;
    }

    synchronized String getUsdB() {
        return usdB;
    }

    synchronized void setUsdB(String usdB) {
        this.usdB = usdB;
    }

    synchronized String getUsdS() {
        return usdS;
    }

    synchronized void setUsdS(String usdS) {
        this.usdS = usdS;
    }

    synchronized String getEuroB() {
        return euroB;
    }

    synchronized void setEuroB(String euroB) {
        this.euroB = euroB;
    }

    synchronized String getEuroS() {
        return euroS;
    }

    synchronized void setEuroS(String euroS) {
        this.euroS = euroS;
    }


}
