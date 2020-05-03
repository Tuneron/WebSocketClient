package Domain;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class Log {

    private FileWriter fileWriter;
    private Integer connectionAttempts;
    private Integer disconnectCount;

    public Log() {
    }

    private static String init () throws IOException {
        String CONFIG_FOLDER = "config/";
        Path currentRelativePath = java.nio.file.Paths.get("");
        String absolutePath = currentRelativePath.toAbsolutePath().normalize().toString()+"/";
        String configPath = absolutePath + CONFIG_FOLDER;
        try (InputStream input = new FileInputStream(configPath + "Logs.txt")) {
            return configPath + "Logs.txt";
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            throw new IOException("Fail to load a logs!");
        }


    }

    public void load() throws IOException {
        fileWriter = new FileWriter(init());
        connectionAttempts = 0;
        disconnectCount = 0;
        fileWriter.write("Logs started here " + LocalDateTime.now() + "\n");
        fileWriter.flush();
    }

    public void  start() throws IOException {
        fileWriter.write("First connection start " + LocalDateTime.now() + "\n");
        fileWriter.flush();
    }

    public void  close() throws IOException {
        fileWriter.write("Logs end here " + LocalDateTime.now()  + "\n");
        fileWriter.flush();
    }

    public void disconnect() throws IOException {
        disconnectCount++;
        fileWriter.write("Lost connect #" + disconnectCount + " " + LocalDateTime.now()  + "\n");
        fileWriter.flush();
    }

    public   void connectionRestored() throws IOException{
        fileWriter.write("Connection restored " + LocalDateTime.now()  + "\n");
        fileWriter.flush();
    }

    public void exceptionLog(String message) throws IOException {
        fileWriter.write("An exception occurred during operation " + LocalDateTime.now() + " :  \n" + message  + "\n");
        fileWriter.flush();
    }
}
