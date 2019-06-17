package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.service.WeatherService;
import sample.socket.SocketClient;

import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class WeatherController implements Initializable {

    @FXML
    private ImageView imageView;

    @FXML
    private Label value;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SocketClient socketClient = null;
        try {
            socketClient = new SocketClient();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
        WeatherService weatherService = new WeatherService(socketClient);

        String[] values = weatherService.getWeather().split(",");
        value.setText(values[0] + " °C");
        imageView.setPreserveRatio(false);
        imageView.setFitHeight(360);
        imageView.setFitWidth(800);

        if (values[1].equalsIgnoreCase("CB") ||
                values[1].equalsIgnoreCase("AB") ||
                values[1].equalsIgnoreCase("PB")) {
            String url = System.getProperty("user.dir") + "\\src\\sample\\pic\\cloudy.png";
            Image image = new Image("file:\\" + url);
            imageView.setImage(image);
        } else if (values[1].equalsIgnoreCase("GSY") ||
                values[1].equalsIgnoreCase("SY") ||
                values[1].equalsIgnoreCase("Y")) {
            String url3 = System.getProperty("user.dir") + "\\src\\sample\\pic\\rainy.png";
            Image image3 = new Image("file:\\" + url3);
            imageView.setImage(image3);
        } else {
            String url2 = System.getProperty("user.dir") + "\\src\\sample\\pic\\sunny.png";
            Image image2 = new Image("file:\\" + url2);
            imageView.setImage(image2);
        }

    }
}
