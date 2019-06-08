package io.currency;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.StringReader;


public class CurrencyScanThread implements Runnable{

    private String usdB = null;
    private String usdS = null;
    private String euroB = null;
    private String euroS = null;


    public CurrencyScanThread() {
        getCurrency();
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(300000);
                getCurrency();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void getCurrency(){
        try {
            String response = getUrl("https://www.tcmb.gov.tr/kurlar/today.xml");
            //System.out.println(response);
            Document doc = convertStringToXMLDocument(response);
            //System.out.println(doc.getElementsByTagName("Isim").item(1).getNodeValue());
            //System.out.println(doc.getElementsByTagName("CurrencyName").item(5));

            org.jsoup.nodes.Document doc2 = Jsoup.parse(response, "", Parser.xmlParser());
            Elements elements = doc2.select("Currency");
            for (org.jsoup.nodes.Element element : elements) // iterate over each elements you've selected
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

    private String getUrl(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    private Document convertStringToXMLDocument(String xmlString) {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            //Parse the content to Document object
            return builder.parse(new InputSource(new StringReader(xmlString)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public synchronized String getUsdB() {
        return usdB;
    }

    public synchronized void setUsdB(String usdB) {
        this.usdB = usdB;
    }

    public synchronized String getUsdS() {
        return usdS;
    }

    public synchronized void setUsdS(String usdS) {
        this.usdS = usdS;
    }

    public synchronized String getEuroB() {
        return euroB;
    }

    public synchronized void setEuroB(String euroB) {
        this.euroB = euroB;
    }

    public synchronized String getEuroS() {
        return euroS;
    }

    public synchronized void setEuroS(String euroS) {
        this.euroS = euroS;
    }


}
