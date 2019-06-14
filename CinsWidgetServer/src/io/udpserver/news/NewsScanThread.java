package io.udpserver.news;

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
import java.util.ArrayList;
import java.util.List;

public class NewsScanThread implements Runnable {

    private List<String> cachedNewsList;
    private String marker;
    String url = null;

    public NewsScanThread(){
        this.cachedNewsList = new ArrayList<>();
        //request only 5 news so the network bandwidth is not wasted.
        this.url = "https://www.trthaber.com/xml_mobile.php?tur=xml_genel&adet=5";
        this.marker = "*#*";
        this.getNews();
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(300000);
                getNews();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void getNews(){
        try {
            String response = httpRequest(url);
            //System.out.println(response);
            Document doc = convertStringToXMLDocument(response);
            //System.out.println(doc.getElementsByTagName("Isim").item(1).getNodeValue());
            //System.out.println(doc.getElementsByTagName("CurrencyName").item(5));

            this.cachedNewsList = new ArrayList<>();

            org.jsoup.nodes.Document doc2 = Jsoup.parse(response, "", Parser.xmlParser());
            Elements elementParent = doc2.select("haberler");
            Elements elements = elementParent.select("haber");
            int i = 1;
            for (org.jsoup.nodes.Element element : elements) // iterate over each elements you've selected
            {
                System.out.println("FORRRRR" + i);
                String header = element.select("haber_manset").text();
                System.out.println(header);

                String img = element.select("haber_resim").text();
                System.out.println(img);

                String link = element.select("haber_link").text();
                System.out.println(link);

                String body = element.select("haber_metni").text();
                System.out.println(body);

                String date = element.select("haber_tarihi").text();
                System.out.println(date);

                this.cachedNewsList.add("header:" + header +
                        this.marker + "img:"+ img +
                        this.marker + "link:"+ link +
                        this.marker + "body:" + body +
                        this.marker + "date:"+ date +
                        this.marker);


                i++;
            }

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


    String getUrl() {
        return url;
    }

    void setUrl(String url) {
        this.url = url;
    }

    public List<String> getCachedNewsList() {
        return cachedNewsList;
    }

    public void setCachedNewsList(List<String> cachedNewsList) {
        this.cachedNewsList = cachedNewsList;
    }
}
