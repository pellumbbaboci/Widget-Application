package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class FileTransferController implements Initializable {

    @FXML
    private JFXListView<Label> jfxListView;

    @FXML
    private JFXButton downloadButton;

    @FXML
    private JFXButton openButton;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private VBox mainBox;

    @FXML
    private StackPane stackPane;




    private String fileFolder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileFolder = System.getProperty("user.dir")+ "\\src\\sample\\download\\";
        String[] fileNameList = getFileNames();
        if(fileNameList.length == 0){
            System.out.println("error");
            return;
        }
        for(String i : fileNameList){
            jfxListView.getItems().add(new Label(i));
        }
    }

    @FXML
    private void handleDownloadClick(MouseEvent event) {
        progressBar.setProgress(0);
        progressBar.setVisible(false);
        Label label = jfxListView.getSelectionModel().getSelectedItem();
        if(label == null){
            return;
        }
        downloadFile(label.getText());
    }

    @FXML
    private void handleOpenClick(MouseEvent event) {
        try {

            File file = new File (System.getProperty("user.dir")+ "\\src\\sample\\download\\");
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getFileNames(){
        try {

            Socket socket = new Socket(InetAddress.getByName("localhost"), 5000);
            byte[] contents = new byte[10000];

            InputStream is = socket.getInputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            dataOutputStream.writeUTF("filenames");
            dataOutputStream.flush();
            System.out.println("sent");

            String fileNamesConcat = dataInputStream.readUTF();
            System.out.println(fileNamesConcat);

            return fileNamesConcat.split(",");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String[0];
    }

    public void downloadFile(String fileName){

        progressBar.setVisible(true);

        try {
            Socket socket = new Socket(InetAddress.getByName("localhost"), 5000);
            InputStream is = socket.getInputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            dataOutputStream.writeUTF("download,"+ fileName);
            dataOutputStream.flush();

            FileOutputStream fos = new FileOutputStream(fileFolder+fileName);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            long fileSize = dataInputStream.readLong();
            System.out.println(fileSize);
            dataOutputStream.writeUTF("OK");
            dataOutputStream.flush();

            double fileChunkRatio = fileSize / 10000;

            //No of bytes read in one read() call
            int bytesRead = 0;
            int progressTurn = 0;
            byte[] contents = new byte[10000];
            while((bytesRead=dataInputStream.read(contents))!=-1){
                progressTurn++;
                double progress = ( progressTurn / fileChunkRatio ) * 100;
                progressBar.setProgress(progress);
                bos.write(contents, 0, bytesRead);
            }

            bos.flush();
            bos.close();
            fos.close();

            final Stage dialog = new Stage();
            dialog.setResizable(false);
            dialog.initModality(Modality.APPLICATION_MODAL);
            VBox dialogVbox = new VBox(20);
            dialogVbox.setPadding(new Insets(10));
            Text text = new Text("Download completed.");
            text.setFont(new Font("cambria",18));
            dialogVbox.getChildren().add(text);
            Scene dialogScene = new Scene(dialogVbox, 200, 100);
            dialog.setScene(dialogScene);
            dialog.show();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
