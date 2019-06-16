package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuController implements Initializable {

    @FXML
    private void handleCurrencyClick(MouseEvent event) {
        System.out.println("click");
        initStage(event, "currency");
    }

    @FXML
    private void handleWeatherClick(MouseEvent event) {
        System.out.println("click2");
        initStage(event, "weather");
    }

    @FXML
    private void handleNewsClick(MouseEvent event) {
        System.out.println("click3");
        initStage(event, "news");
    }

    @FXML
    private void handleFileTransferClick(MouseEvent event) {
        System.out.println("click69");
        initStage(event, "filetransfer");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void initStage(MouseEvent event, String stageLocation){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../view/"+stageLocation+".fxml"));
            /*
             * if "fx:controller" is not set in fxml
             * fxmlLoader.setController(NewWindowController);
             */
            Scene scene = new Scene(fxmlLoader.load(), 800, 500);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle(stageLocation);
            stage.setScene(scene);
            stage.setOnCloseRequest(closeEvent -> {
                Main.primaryStage.show();
            });
            stage.show();

            final Node source = (Node) event.getSource();
            final Stage staget = (Stage) source.getScene().getWindow();
            staget.close();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }
}
