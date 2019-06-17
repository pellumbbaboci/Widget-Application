package sample.controller;

import com.jfoenix.controls.JFXScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import sample.service.News;
import sample.service.NewsService;
import sample.socket.SocketClient;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NewsController implements Initializable {

    @FXML
    private JFXScrollPane scrollPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            SocketClient socketClient = new SocketClient();
            JFXScrollPane.smoothScrolling((ScrollPane) scrollPane.getChildren().get(0));

            Label title = new Label("News");
            title.setStyle("-fx-text-fill:WHITE; -fx-font-size: 40;");

            scrollPane.getTopBar().getChildren().add(title);

            NewsService newsService = new NewsService(socketClient);
            List<News> newsList = newsService.getNews();

            VBox sceneBox = new VBox();
            sceneBox.setPadding(new Insets(10));
            for (News i : newsList) {

                VBox vBox = new VBox();
                String url = i.getImg();
                url = url.replace("http", "https");
                Image image = new Image(url, true);
                if (image.isError()) {
                    System.out.println(image.getException().getMessage());
                }
                ImageView imageView = new ImageView();
                imageView.setImage(image);
                HBox hBox1 = new HBox();
                hBox1.setAlignment(Pos.CENTER);
                hBox1.getChildren().add(imageView);

                Label labelHeader = new Label(i.getHeader());
                labelHeader.setWrapText(true);
                labelHeader.setFont(new Font("cambria", 24));
                HBox hBox2 = new HBox();
                hBox2.getChildren().add(labelHeader);

                Label label = new Label(i.getBody());
                label.setWrapText(true);
                label.setFont(new Font("cambria", 18));

                HBox hBox3 = new HBox();
                hBox3.getChildren().add(label);


                vBox.getChildren().add(hBox1);
                vBox.getChildren().add(hBox2);
                vBox.getChildren().add(hBox3);
                vBox.setStyle("-fx-border-color: rgba(100,100,100,0.5); -fx-border-style: solid none none none");


                sceneBox.getChildren().add(vBox);
            }


            scrollPane.setContent(sceneBox);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
