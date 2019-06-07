import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileScanThread implements Runnable {

    final File folder;
    static List<String> folderList = new ArrayList<>();

    public FileScanThread(File folder) {
        this.folder = folder;
        search(".*\\.mp3", folder, folderList);

        System.out.println("ada");
        for (String s : folderList) {
            System.out.println(s);
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(900000);
            search(".*\\.mp3", folder, folderList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void search(final String pattern, final File folder, List<String> result) {
        for (final File f : folder.listFiles()) {

            if (f.isDirectory()) {
                search(pattern, f, result);
            }

            if (f.isFile()) {
                if (f.getName().matches(pattern)) {
                    result.add(f.getName());
                }
            }

        }
    }
}
