package io.filetransfer;

import java.io.*;
import java.net.Socket;

public class ServerThread implements Runnable {

    private Socket socket;

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private String folderName;


    public ServerThread(Socket socket, String folderName) throws IOException {
        //SOCKET INPUT OUTPUT STREAMS
        System.out.println("Socket activated "+ socket.getPort());
        this.socket = socket;
        dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        this.folderName = folderName;

    }


    @Override
    public void run() {
        System.out.println("Thread started.");

            try {
                System.out.println("flag");
                String input = dataInputStream.readUTF();
                System.out.println(input);
                if(input.equalsIgnoreCase("filenames")){
                    StringBuilder fileListString = new StringBuilder();
                    for(int i = 0 ; i < FileScanThread.folderList.size(); i++){
                        String fileName = FileScanThread.folderList.get(i);
                        fileListString.append(fileName+",");
                    }

                    String sendFileListString = fileListString.toString();


                    dataOutputStream.writeUTF(sendFileListString);
                    dataOutputStream.flush();

                    socket.close();

//                    if(FileScanThread.folderList.contains(input)){
//
//                    }else{
//                        System.err.println("Requested file "+ input + " does not exists on the server directory.");
//                    }
                }else if(input.contains("download,")){
                    System.out.println("DOWNLOAD INITIATED ...");
                    String fileName = input.split(",")[1];
                    downloadFile(fileName);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }


    }

    private void downloadFile(String fileName) throws FileNotFoundException {

        File file = new File(folderName+ fileName);
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bisFile = new BufferedInputStream(fis);
        DataInputStream dataInputStreamFile = new DataInputStream(bisFile);



        System.out.println("Sending file.");
        try {
//            OutputStream os = socket.getOutputStream();
            //Read File Contents into contents array
            byte[] contents;
            long fileLength = file.length();
            dataOutputStream.writeLong(fileLength);
            dataOutputStream.flush();
            dataInputStream.readUTF();

            long current = 0;

            while(current!=fileLength){
                int size = 10000;
                if(fileLength - current >= size)
                    current += size;
                else{
                    size = (int)(fileLength - current);
                    current = fileLength;
                }
                contents = new byte[size];
                bisFile.read(contents, 0, size);
                dataOutputStream.write(contents);
                System.out.print("Sending file ... "+(current*100)/fileLength+"% complete! \n");
            }


            dataOutputStream.flush();


            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
