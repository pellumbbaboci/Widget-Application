package sample.service;

public class News {
    private String header;
    private String body;
    private String link;
    private String img;
    private String date;

    public News() {
    }

    public News(String header, String img, String link, String body, String date) {
        this.header = header;
        this.body = body;
        this.link = link;
        this.img = img;
        this.date = date;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
