import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try{
            //Initialize socket
            Socket socket = new Socket(InetAddress.getByName("10.103.163.4"), 5000);
            byte[] contents = new byte[10000];
            String fileFolder = System.getProperty("user.dir")+ "\\src\\downloads\\";

            //Initialize the FileOutputStream to the output file's full path.


            InputStream is = socket.getInputStream();


            DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));


            outputStream.writeUTF("filenames");
            outputStream.flush();
            System.out.println("sent");

            String fileNamesConcat = inputStream.readUTF();
            System.out.println(fileNamesConcat);

            String[] fileNameList = fileNamesConcat.split(",");


            outputStream.writeUTF("download,"+ fileNameList[0]);
            outputStream.flush();
            System.out.println("download request sent");

            FileOutputStream fos = new FileOutputStream(fileFolder+fileNameList[0]);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            //No of bytes read in one read() call
            int bytesRead = 0;

            while((bytesRead=is.read(contents))!=-1){
                System.out.println(bytesRead);
                bos.write(contents, 0, bytesRead);
            }


            System.out.println("bisey23");



            bos.flush();

//
            System.out.println("File saved successfully!");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("File saved successfully2!");

    }
}
