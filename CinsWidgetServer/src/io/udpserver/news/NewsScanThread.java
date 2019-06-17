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

    String url = null;
    private List<String> cachedNewsList;
    private String outerMarker;
    private String innerMarker;

    public NewsScanThread() {
        this.cachedNewsList = new ArrayList<>();
        //request only 5 news so the network bandwidth is not wasted.
        this.url = "https://www.trthaber.com/xml_mobile.php?tur=xml_genel&adet=5";
        this.outerMarker = "outerMarker";
        this.innerMarker = "innerMarker";
        this.getNews();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(300000);
                getNews();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void getNews() {
        try {
            String response = httpRequest(url);

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

                this.cachedNewsList.add(header +
                        this.innerMarker + img +
                        this.innerMarker + link +
                        this.innerMarker + body +
                        this.innerMarker + date +
                        this.outerMarker);


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


    String getUrl() {
        return url;
    }

    void setUrl(String url) {
        this.url = url;
    }

    List<String> getCachedNewsList() {
        return cachedNewsList;
    }

}
