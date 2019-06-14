package io.udpserver.news;

import java.util.List;

public class NewsService {

    private NewsScanThread newsScanThread;

    public NewsService(NewsScanThread newsScanThread){
        this.newsScanThread = newsScanThread;
    }

    public String getNewsString(){
        List<String> newsList = this.newsScanThread.getCachedNewsList();
        if(newsList == null ||newsList.isEmpty()){
            return "Error receiving the data from the service " + this.newsScanThread.getUrl();
        }
        StringBuilder stringBuilder = new StringBuilder();

        for (String news : newsList) {
            stringBuilder.append(news);
        }

        return stringBuilder.toString();
    }
}
